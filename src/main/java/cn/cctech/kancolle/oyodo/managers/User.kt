package cn.cctech.kancolle.oyodo.managers

import io.reactivex.subjects.BehaviorSubject

object User : IManager() {

    var nickname = BehaviorSubject.create<String>() //昵称
    var level = BehaviorSubject.create<Int>() //等级
    var shipCount = BehaviorSubject.create<Int>() //拥有舰娘
    var shipMax = BehaviorSubject.create<Int>() //最大舰娘
    var slotCount = BehaviorSubject.create<Int>() //拥有装备
    var slotMax = BehaviorSubject.create<Int>() //最大装备
    var kDockCount = BehaviorSubject.create<Int>() //开放建造池
    var nDockCount = BehaviorSubject.create<Int>() //开放维修池
    var deckCount = BehaviorSubject.create<Int>() //开放舰队

}
