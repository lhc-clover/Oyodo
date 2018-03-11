package cn.cctech.kancolle.oyodo.managers

import cn.cctech.kancolle.oyodo.entities.Build
import cn.cctech.kancolle.oyodo.entities.Expedition
import cn.cctech.kancolle.oyodo.entities.Repair
import io.reactivex.subjects.BehaviorSubject

object Dock : IManager() {

    val expeditionList = (0 until 3).map { BehaviorSubject.create<Expedition>() }
    val buildList = (0 until 4).map { BehaviorSubject.create<Build>() }
    val repairList = (0 until 4).map { BehaviorSubject.create<Repair>() }

}
