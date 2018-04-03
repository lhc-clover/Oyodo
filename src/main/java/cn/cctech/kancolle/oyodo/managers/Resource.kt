package cn.cctech.kancolle.oyodo.managers

import io.reactivex.subjects.BehaviorSubject

object Resource : IManager() {

    var fuel = BehaviorSubject.create<Int>() //油
    var ammo = BehaviorSubject.create<Int>() //弹
    var metal = BehaviorSubject.create<Int>() //钢
    var bauxite = BehaviorSubject.create<Int>() //铝
    var bucket = BehaviorSubject.create<Int>() //桶
    var burner = BehaviorSubject.create<Int>() //喷火
    var research = BehaviorSubject.create<Int>() //紫菜
    var improve = BehaviorSubject.create<Int>() //螺丝

}
