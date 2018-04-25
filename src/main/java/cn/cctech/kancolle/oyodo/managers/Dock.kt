package cn.cctech.kancolle.oyodo.managers

import cn.cctech.kancolle.oyodo.entities.Build
import cn.cctech.kancolle.oyodo.entities.Expedition
import cn.cctech.kancolle.oyodo.entities.Repair
import cn.cctech.kancolle.oyodo.utils.MAX_FLEET_COUNT
import io.reactivex.subjects.BehaviorSubject

object Dock : IManager() {

    val expeditionList = (0 until MAX_FLEET_COUNT).map { BehaviorSubject.create<Expedition>() }
    val buildList = (0 until MAX_FLEET_COUNT).map { BehaviorSubject.create<Build>() }
    val repairList = (0 until MAX_FLEET_COUNT).map { BehaviorSubject.create<Repair>() }

}
