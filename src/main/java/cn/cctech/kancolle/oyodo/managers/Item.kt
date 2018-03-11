package cn.cctech.kancolle.oyodo.managers

import cn.cctech.kancolle.oyodo.entities.Slot
import io.reactivex.subjects.BehaviorSubject

object Item : IManager() {

    val slotMap = HashMap<String, BehaviorSubject<Slot>>()

}
