package cn.cctech.kancolle.oyodo.managers

import cn.cctech.kancolle.oyodo.entities.Ship
import io.reactivex.subjects.BehaviorSubject

object Battle : IManager() {

    enum class Phase {
        Idle,
        Start,
        BattleDaytime,
        BattleNight,
        BattleNightSp,
        BattleResult,
        Practice,
        PracticeNight,
        PracticeResult,
        Next
    }

    var phase: BehaviorSubject<Phase> = BehaviorSubject.createDefault(Phase.Idle)
    var area: Int = -1
    var map: Int = -1
    var heading: Int = -1
    var airCommand: Int = -1
    var rank: String = ""
    var route: Int = -1
    var nodeType: Int = -1
    var get: String = ""

    var friendList: MutableList<BehaviorSubject<Ship>> = mutableListOf()
    var friendFormation: Int = -1
    var enemyList: MutableList<Ship> = mutableListOf()
    var enemyFormation: Int = -1

    @Suppress("UNCHECKED_CAST")
    fun calcTargetDamage(targetList: List<Any>?, damageList: List<Any>?, flagList: List<Int>?) {
        if (targetList == null || damageList == null || flagList == null) return
        try {
            for ((i, target) in targetList.withIndex()) {
                val flag = flagList[i]
                val tArr = target as ArrayList<Int>
                val dArr = damageList[i] as ArrayList<Double>
                for ((j, t) in tArr.withIndex()) {
                    try {
                        when (flag) {
                            0 -> {
                                val ship = enemyList[t]
                                ship.damage.add(dArr[j].toInt())
                            }
                            1 -> {
                                val ship = friendList[t]
                                val entity = ship.value
                                entity.damage.add(dArr[j].toInt())
                                ship.onNext(entity)
                            }
                            else -> println("Unexpected flag $flag")
                        }
                    } catch (e: Exception) {
                        println("Can't set damage for ship $t in $flag\n" + e.printStackTrace())
                    }
                }
            }
        } catch (e: Exception) {
            println("Can't set hougeki damage\n" + e.printStackTrace())
        }
    }

    fun calcFriendOrdinalDamage(damageList: List<Double>?) {
        if (damageList == null) return
        for ((i, value) in damageList.withIndex()) {
            try {
                val ship = friendList[i]
                val entity = ship.value
                val damage = value.toInt()
                if (damage > 0) entity.damage.add(damage)
                ship.onNext(entity)
            } catch (e: Exception) {
                println("Can't set friend damage for ship $i\n" + e.printStackTrace())
            }
        }
    }

    fun calcEnemyOrdinalDamage(damageList: List<Double>?) {
        if (damageList == null) return
        for ((i, value) in damageList.withIndex()) {
            try {
                val ship = enemyList[i]
                val damage = value.toInt()
                if (damage > 0) ship.damage.add(damage)
            } catch (e: Exception) {
                println("Can't set enemy damage for ship $i\n" + e.printStackTrace())
            }
        }
    }

    fun newTurn() {
        fun setDamage(ship: Ship) {
            ship.nowHp -= ship.damage.sum()
            ship.damage.clear()
        }
        friendList.forEach { setDamage(it.value) }
        enemyList.forEach { setDamage(it) }
    }

    fun calcRank() {
        val friendCount = friendList.size
        val shipList = friendList.map { it.value }
        val friendSunkCount = shipList.count {
            it?.hp() ?: Int.MAX_VALUE <= 0
        }
        val friendNowSum = shipList.sumBy { it?.nowHp ?: 0 }
        val friendAfterSum = shipList.sumBy { it?.hp() ?: 0 }
//            friendFlagshipCritical = shipList[0]?.getHpFixed()?.times(4) ?: 0 <= shipList[0]?.maxHp ?: 0
        val friendDamageSum = shipList.sumBy { it?.damage?.sum() ?: 0 }
        val enemyCount = enemyList.size
        val enemySunkCount = enemyList.count { it.hp() <= 0 }
        val enemyNowSum = enemyList.sumBy { it.nowHp }
        val enemyAfterSum = enemyList.sumBy { it.hp() }
        val enemyFlagShipSunk = enemyList[0].hp() <= 0
        val enemyDamageSum = enemyList.sumBy { it.damage.sum() }

        val friendDamageRate = friendDamageSum * 100 / friendNowSum
        val enemyDamageRate = enemyDamageSum * 100 / enemyNowSum
        rank = if (friendSunkCount == 0) {
            if (enemySunkCount == enemyCount) {
                if (friendDamageSum == 0) // TODO:使用女神？
                    "SS"
                else
                    "S"
            } else if (enemyCount > 1 && enemySunkCount >= Math.floor(0.7 * enemyCount).toInt()) {
                "A"
            } else if (enemyFlagShipSunk && friendSunkCount < enemySunkCount) {
                "B"
            } /*else if (friendCount == 1 && friendFlagshipCritical) {
                "D"
            }*/ else if (enemyDamageRate * 2 > friendDamageRate * 5) {
                "B"
            } else if (enemyDamageRate * 10 > friendDamageRate * 9) {
                "C"
            } else {
                "D"
            }
        } else if (friendCount - friendSunkCount == 1) {
            "E"
        } else {
            "D"
        }
        println("Calc rank : $rank")
    }

    fun phaseShift(value: Phase) {
        phase.onNext(value)
    }

}
