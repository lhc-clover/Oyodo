package cn.cctech.kancolle.oyodo.entities

import cn.cctech.kancolle.oyodo.apis.ApiMstShip
import cn.cctech.kancolle.oyodo.apis.ApiShip

class Ship {

    var id: Int = 0
    var sortNum: Int = 0 //图鉴编号
    var level: Int = 0 //等级
    var nowHp: Int = 0 //当前HP
    var maxHp: Int = 0 //最大HP
    var nowFuel: Int = 0 //当前油料
    var maxFuel: Int = 0 //最大油料
    var nowBullet: Int = 0 //当前弹药
    var maxBullet: Int = 0 //最大弹药
    var condition: Int = 0 //士气
    var name: String = "" //舰名
    var items = mutableListOf<Int>() //装备
    var soku: Int = 0 //航速
    var carrys = mutableListOf<Int>()//搭载
    var scout: Int = 0 //索敌
    var yomi: String = "" //舰假名或舰阶

    constructor(portShip: ApiShip, rawShip: ApiMstShip?) {
        id = portShip.api_id
        sortNum = portShip.api_sortno
        level = portShip.api_lv
        nowHp = portShip.api_nowhp
        maxHp = portShip.api_maxhp
        nowFuel = portShip.api_fuel
        nowBullet = portShip.api_bull
        condition = portShip.api_cond
        items.addAll(portShip.api_slot)
        items.add(portShip.api_slot_ex)
        soku = portShip.api_soku
        carrys.addAll(portShip.api_onslot)
        scout = portShip.api_sakuteki[0]
        if (rawShip != null) {
            maxFuel = rawShip.api_fuel_max
            maxBullet = rawShip.api_bull_max
            name = rawShip.api_name
        }
    }
}
