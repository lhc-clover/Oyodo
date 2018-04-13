package cn.cctech.kancolle.oyodo.managers

import io.reactivex.subjects.BehaviorSubject

object User : IManager() {

    var nickname = BehaviorSubject.createDefault<String>("") //昵称
    var level = BehaviorSubject.createDefault(0) //等级
    var shipCount = BehaviorSubject.createDefault(0) //拥有舰娘
    var shipMax = BehaviorSubject.createDefault(0) //最大舰娘
    var slotCount = BehaviorSubject.createDefault(0) //拥有装备
    var slotMax = BehaviorSubject.createDefault(0) //最大装备
    var kDockCount = BehaviorSubject.createDefault(2) //开放建造池
    var nDockCount = BehaviorSubject.createDefault(2) //开放维修池
    var deckCount = BehaviorSubject.createDefault(4) //开放舰队

}
