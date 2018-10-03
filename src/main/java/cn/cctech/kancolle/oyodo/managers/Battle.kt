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
        BattleAir,
        BattleResult,
        Practice,
        PracticeNight,
        PracticeResult,
        BattleCombined,
        BattleCombinedAir,
        BattleCombinedEach,
        BattleCombinedEc,
        BattleCombinedWater,
        BattleCombinedWaterEach,
        BattleCombinedNight,
        BattleCombinedResult,
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

    var friendIndex = -1
    //    var friendList: MutableList<BehaviorSubject<Ship>> = mutableListOf()
    var friendFormation: Int = -1
    var enemyList: MutableList<Ship> = mutableListOf()
    var subEnemyList: MutableList<Ship> = mutableListOf()
    var enemyFormation: Int = -1
    var friendCombined = false
    var enemyCombined = false

    @Suppress("UNCHECKED_CAST")
    fun calcTargetDamage(targetList: List<Any>?, damageList: List<Any>?, flagList: List<Int>?, enemyOnly: Boolean = false) {
        if (targetList == null || damageList == null || flagList == null) return
        try {
            val shipIds = mutableListOf<Int>()
            for ((i, target) in targetList.withIndex()) {
                val flag = flagList[i]
                val tArr = target as ArrayList<Int>
                val dArr = damageList[i] as ArrayList<Double>
                for ((j, t) in tArr.withIndex()) {
                    if (t < 0) continue
                    if (enemyOnly && flag == 1) continue
                    try {
                        val subTeam = t >= 6
                        val index = if (subTeam) t - 6 else t
                        when (flag) {
                            0 -> {
                                val ship = if (subTeam) subEnemyList[index] else enemyList[index]
                                ship.damage[ship.damage.lastIndex] += dArr[j].toInt()
                            }
                            1 -> {
                                val friendList = getShips(if (subTeam) 1 else friendIndex)
                                val ship = friendList[index]
                                ship.damage[ship.damage.lastIndex] += dArr[j].toInt()
                                shipIds.add(ship.id)
                            }
                            else -> println("Unexpected flag $flag")
                        }
                    } catch (e: Exception) {
                        println("Can't set damage for ship $t in $flag\n" + e.printStackTrace())
                    }
                }
            }
            Fleet.shipWatcher.onNext(Transform.Change(shipIds))
        } catch (e: Exception) {
            println("Can't set hougeki damage\n" + e.printStackTrace())
        }
    }

    fun calcFriendOrdinalDamage(damageList: List<Double>?, combined: Boolean = false) {
        if (damageList == null) return
        val values = if (damageList.size == 7) damageList.take(6) else damageList
        val shipIds = mutableListOf<Int>()
        for ((i, value) in values.withIndex()) {
            try {
                val fleet = if (friendCombined) {
                    if (combined || i >= 6) 1 else 0
                } else friendIndex
                val index = if (i >= 6) i - 6 else i
                val friendList = getShips(fleet)
                val ship = friendList[index]
                val damage = value.toInt()
                if (damage > 0) ship.damage[ship.damage.lastIndex] += damage
                shipIds.add(ship.id)
            } catch (e: Exception) {
                println("Can't set friend damage for ship $i\n" + e.printStackTrace())
            }
        }
        Fleet.shipWatcher.onNext(Transform.Change(shipIds))
    }

    fun calcEnemyOrdinalDamage(damageList: List<Double>?, combined: Boolean = false) {
        if (damageList == null) return
        val values = if (damageList.size == 7) damageList.take(6) else damageList
        for ((i, value) in values.withIndex()) {
            try {
                val index = if (i >= 6) i - 6 else i
                val ship = if (combined || i >= 6) subEnemyList[index] else enemyList[index]
                val damage = value.toInt()
                if (damage > 0) ship.damage[ship.damage.lastIndex] += damage
            } catch (e: Exception) {
                println("Can't set enemy damage for ship $i\n" + e.printStackTrace())
            }
        }
    }

    fun newTurn() {
        fun newItem(ship: Ship) {
            ship.damage.add(0)
        }

        if (friendCombined) {
            getShips(0).forEach { newItem(it) }
            getShips(1).forEach { newItem(it) }
        } else getShips(friendIndex).forEach { newItem(it) }
        enemyList.forEach { newItem(it) }
        if (enemyCombined) subEnemyList.forEach { newItem(it) }
    }

    fun finishBattle() {
        fun setDamage(ship: Ship) {
            ship.nowHp -= ship.damage.sum()
            ship.damage.clear()
        }

        if (friendCombined) {
            getShips(0).forEach { setDamage(it) }
            getShips(1).forEach { setDamage(it) }
        } else getShips(friendIndex).forEach { setDamage(it) }
        enemyList.forEach { setDamage(it) }
        if (enemyCombined) subEnemyList.forEach { setDamage(it) }
    }

    fun calcRank() {
        val friendList = if (friendCombined) getShips(0).plus(getShips(1)) else getShips(friendIndex)
        val friendCount = friendList.size
        val friendSunkCount = friendList.count { it.hp() <= 0 }
        val friendNowSum = friendList.sumBy { it.nowHp }
//        val friendAfterSum = shipList.sumBy { it?.hp() ?: 0 }
//            friendFlagshipCritical = shipList[0]?.getHpFixed()?.times(4) ?: 0 <= shipList[0]?.maxHp ?: 0
        val friendDamageSum = friendList.sumBy { it.damage.sum() }
        val enemies = if (enemyCombined) enemyList.plus(subEnemyList) else enemyList
        val enemyCount = enemies.size
        val enemySunkCount = enemies.count { it.hp() <= 0 }
        val enemyNowSum = enemies.sumBy { it.nowHp }
//        val enemyAfterSum = enemyList.sumBy { it.hp() }
        val enemyFlagShipSunk = enemies[0].hp() <= 0
        val enemyDamageSum = enemies.sumBy { it.damage.sum() }

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

    fun calcAirRank() {
        val friendList = if (friendCombined) getShips(0).plus(getShips(1)) else getShips(friendIndex)
        val friendNowSum = friendList.sumBy { it.nowHp }
        val friendDamageSum = friendList.sumBy { it.damage.sum() }
        val friendDamageRate = friendDamageSum * 100 / friendNowSum
        rank = when {
            friendDamageSum <= 0 -> "SS"
            friendDamageRate < 10 -> "A"
            friendDamageRate < 20 -> "B"
            friendDamageRate < 50 -> "C"
            friendDamageRate < 80 -> "D"
            else -> "E"
        }
    }

    fun phaseShift(value: Phase) {
        phase.onNext(value)
    }

    fun clear() {
        friendIndex = -1
        friendFormation = -1
        friendCombined = false
        enemyList.clear()
        subEnemyList.clear()
        enemyFormation = -1
        enemyCombined = false
        area = -1
        map = -1
        heading = -1
        airCommand = -1
        rank = ""
        route = -1
        nodeType = -1
        get = ""
    }

}
