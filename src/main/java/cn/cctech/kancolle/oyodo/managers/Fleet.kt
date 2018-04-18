package cn.cctech.kancolle.oyodo.managers

import cn.cctech.kancolle.oyodo.entities.Ship
import cn.cctech.kancolle.oyodo.entities.Slot
import cn.cctech.kancolle.oyodo.utils.*
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

object Fleet : IManager() {

    val deckShipIds = (0 until MAX_FLEET_COUNT).map { BehaviorSubject.createDefault<List<Int>>(emptyList()) }
    val deckNames = (0 until MAX_FLEET_COUNT).map { BehaviorSubject.createDefault<String>("${it + 1}") }
    val shipMap = HashMap<Int, Ship>()
    val slotMap = HashMap<Int, Slot>()
    val shipWatcher: PublishSubject<Transform> = PublishSubject.create<Transform>()
    val slotWatcher: PublishSubject<Transform> = PublishSubject.create<Transform>()
    var lastUpdate: Long = 0

}

sealed class Transform {
    class All : Transform()
    class Add(val ids: List<Int>) : Transform()
    class Remove(val ids: List<Int>) : Transform()
    class Change(val ids: List<Int>) : Transform()
}

fun getShips(deckIndex: Int): MutableList<Ship> {
    return try {
        Fleet.deckShipIds[deckIndex].value.filter { it > 0 }.map { Fleet.shipMap[it]!! }.toMutableList()
    } catch (e: Exception) {
        mutableListOf()
    }
}

fun getFleetSpeedType(index: Int): Speed {
    val isSlowFleet = getShips(index).any { it.soku < 10 /* 10=FAST */ }
    return if (isSlowFleet) Speed.SLOW else Speed.FAST
}

fun getFleetLevel(index: Int): Int {
    return getShips(index).sumBy { it.level }
}

fun getFleetAirPower(index: Int): Pair<Int, Int> {
    var min = 0
    var max = 0
    getShips(index).forEach {
        val valuePair = it.getAirPower()
        min += valuePair.first
        max += valuePair.second
    }
    return Pair(min, max)
}

fun getFleetScout(index: Int): Double {
    var equipScoutSum = 0.0
    var shipScoutSum = 0.0
    getShips(index).forEach {
        var shipScout = it.scout.toDouble()
        it.items.map { Fleet.slotMap[it] }
                .forEach {
                    it?.let {
                        shipScout -= it.scout.toDouble()
                        equipScoutSum += it.calcScout()
                    }
                }
        shipScoutSum += Math.sqrt(shipScout)
    }
    return equipScoutSum + shipScoutSum + getCommandLevelPenaltyScout() + getFleetCountBonusScout(index)
}

private fun getCommandLevelPenaltyScout(): Double {
    return try {
        Math.ceil(User.level.value * 0.4) * -1
    } catch (e: Exception) {
        0.0
    }
}

private fun getFleetCountBonusScout(index: Int): Double {
    return try {
        val shipCount = Fleet.deckShipIds[index].value.count { it > 0 }
        (MAX_SHIP_COUNT[index] - shipCount) * 2.0
    } catch (e: Exception) {
        0.0
    }
}

fun isFleetInBattle(index: Int): Boolean {
    return Battle.friendIndex == index
}

fun isFleetInExpedition(index: Int): Boolean {
    val expedition = Dock.expeditionList.find { it?.value?.fleetIndex == index + 1 }
    return try {
        expedition?.value?.missionId?.toInt()!! > 0
    } catch (e: Exception) {
        false
    }
}

fun isFleetNeedSupply(index: Int): Boolean {
    return getShips(index).count {
        (it.nowFuel < it.maxFuel) || (it.nowBullet < it.maxBullet)
    } > 0
}

fun isFleetBadlyDamage(index: Int): Boolean {
    return getShips(index).count {
        val percent: Double = it.hp().toDouble().div(it.maxHp)
        percent in 0.0..BADLY_DAMAGE
    } > 0
}

fun isFleetMemberRepair(index: Int): Boolean {
    return getShips(index).count { ship ->
        Dock.repairList.any { ship.id == it.value?.shipId }
    } > 0
}

fun isFleetLock(index: Int): Boolean {
    return index >= User.deckCount.value
}

fun getCondRecoveryTime(index: Int): Long {
    val minCond = getShips(index).minBy { ship -> ship.condition }?.condition
            ?: CONDITION_NORMAL
    val diff = CONDITION_NORMAL - minCond
    return if (!isFleetInBattle(index) && !isFleetInExpedition(index) && diff > 0) {
        val minute = Math.ceil(diff.div(3.0)).toInt() * 3
        Fleet.lastUpdate + minute * 60 * 1000
    } else 0
}
