package cn.cctech.kancolle.oyodo.managers

import cn.cctech.kancolle.oyodo.entities.Ship
import cn.cctech.kancolle.oyodo.entities.Slot
import io.reactivex.subjects.BehaviorSubject

object Fleet : IManager() {

    private const val kFleetCount = 4

    val deckShipIds = (0 until kFleetCount).map { BehaviorSubject.create<List<Int>>() }
    val deckNames = (0 until kFleetCount).map { BehaviorSubject.create<String>() }
    val shipMap = HashMap<Int, BehaviorSubject<Ship>>()
    val slotMap = HashMap<Int, BehaviorSubject<Slot>>()

    fun getShips(deckIndex: Int): MutableList<BehaviorSubject<Ship>> {
        return try {
            Fleet.deckShipIds[deckIndex].value.map { Fleet.shipMap[it]!! }.toMutableList()
        } catch (e: Exception) {
            mutableListOf()
        }
    }
}
