package cn.cctech.kancolle.oyodo.managers

import cn.cctech.kancolle.oyodo.entities.Ship
import io.reactivex.subjects.BehaviorSubject

object Fleet : IManager() {

    private const val kFleetCount = 4

    val deckShipIds = (0 until kFleetCount).map { BehaviorSubject.create<List<Int>>() }
    val deckNames = (0 until kFleetCount).map { BehaviorSubject.create<String>() }
    val shipMap = HashMap<String, BehaviorSubject<Ship>>()

}
