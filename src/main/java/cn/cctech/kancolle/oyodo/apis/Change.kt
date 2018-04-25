package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.managers.Fleet
import java.util.*

data class Change(
        val api_result: Int = 0,
        val api_result_msg: String = ""
) : JsonBean() {
    override fun process() {
        var shipId = 0
        var fleetIdx = -1
        var shipIdx = -1
        try {
            fleetIdx = params["api_id"]!!.toInt() - 1
            shipId = params["api_ship_id"]!!.toInt()
            shipIdx = params["api_ship_idx"]!!.toInt()
        } catch (e: Exception) {
        }
        if (fleetIdx >= 0) {
            val fleet = Fleet.deckShipIds[fleetIdx]
            val fleetIds = fleet.value.toMutableList()
            when (shipId) {
                -2 -> {
                    val after = fleetIds.mapIndexed { index, it -> if (index != 0) -1 else it }
                    Fleet.deckShipIds[fleetIdx].onNext(after)
                }
                -1 -> {
                    fleetIds.removeAt(shipIdx)
                    fleetIds.add(-1)
                    fleet.onNext(fleetIds)
                }
                else -> {
                    val alreadyId = fleetIds[shipIdx]
                    if (alreadyId != -1) {
                        for ((index, it) in Fleet.deckShipIds.withIndex()) {
                            val anotherFleet = it.value?.toMutableList() ?: mutableListOf()
                            val targetIdx = anotherFleet.indexOf(shipId)
                            if (targetIdx != -1) {
                                if (fleetIdx == index) {
                                    fleetIds[targetIdx] = alreadyId
                                } else {
                                    anotherFleet[targetIdx] = alreadyId
                                    it.onNext(anotherFleet)
                                }
                            }
                        }
                    }
                    fleetIds[shipIdx] = shipId
                    fleet.onNext(fleetIds)
                }
            }
        }
    }
}