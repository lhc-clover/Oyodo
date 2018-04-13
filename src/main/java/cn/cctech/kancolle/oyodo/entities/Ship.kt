package cn.cctech.kancolle.oyodo.entities

import cn.cctech.kancolle.oyodo.apis.ApiMstShip
import cn.cctech.kancolle.oyodo.apis.ApiShip
import cn.cctech.kancolle.oyodo.apis.ApiShipData
import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.utils.*

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
    var itemEx = 0 //打孔装备
    var soku: Int = 0 //航速
    var carrys = mutableListOf<Int>()//搭载
    var scout: Int = 0 //索敌
    var yomi: String = "" //舰假名或舰阶

    var damage: MutableList<Int> = arrayListOf() //损伤

    constructor(portShip: ApiShip, rawShip: ApiMstShip?) {
        id = portShip.api_id
        sortNum = portShip.api_sortno
        level = portShip.api_lv
        nowHp = portShip.api_nowhp
        maxHp = portShip.api_maxhp
        nowFuel = portShip.api_fuel
        nowBullet = portShip.api_bull
        condition = portShip.api_cond
        items.addAll((0 until portShip.api_slotnum).map { portShip.api_slot[it] })
        itemEx = portShip.api_slot_ex
        soku = portShip.api_soku
        carrys.addAll(portShip.api_onslot)
        scout = portShip.api_sakuteki[0]
        if (rawShip != null) {
            maxFuel = rawShip.api_fuel_max
            maxBullet = rawShip.api_bull_max
            name = rawShip.api_name
        }
    }

    constructor(portShip: ApiShipData, rawShip: ApiMstShip?) {
        id = portShip.api_id
        sortNum = portShip.api_sortno
        level = portShip.api_lv
        nowHp = portShip.api_nowhp
        maxHp = portShip.api_maxhp
        nowFuel = portShip.api_fuel
        nowBullet = portShip.api_bull
        condition = portShip.api_cond
        items.addAll((0 until portShip.api_slotnum).map { portShip.api_slot[it] })
        itemEx = portShip.api_slot_ex
        soku = portShip.api_soku
        carrys.addAll(portShip.api_onslot)
        scout = portShip.api_sakuteki[0]
        if (rawShip != null) {
            maxFuel = rawShip.api_fuel_max
            maxBullet = rawShip.api_bull_max
            name = rawShip.api_name
        }
    }

    constructor(rawShip: ApiMstShip?) {
        rawShip?.let {
            id = it.api_id
            sortNum = it.api_sortno
            name = it.api_name
            soku = it.api_soku
            yomi = it.api_yomi
        }
    }

    fun hp() = maxOf(nowHp - damage.sum(), 0)

    fun getAirPower(): Pair<Int, Int> {
        val totalAAC = intArrayOf(0, 0)
        for ((index, equipId) in items.withIndex()) {
            val carry = try {
                carrys[index]
            } catch (e: Exception) {
                0
            }
            Fleet.slotMap[equipId]?.let {
                val baseAAC = calcBasicAAC(it.type, it.calcLevelAAC(), carry)
                val masteryAAC = it.calcMasteryAAC(0)
                totalAAC[0] += Math.floor(baseAAC + masteryAAC[0]).toInt()
                totalAAC[1] += Math.floor(baseAAC + masteryAAC[1]).toInt()
            }
        }
        return Pair(totalAAC[0], totalAAC[1])
    }

    private fun calcBasicAAC(type: Int, aac: Double, carry: Int): Double {
        return when (type) {
            FIGHTER, BOMBER, TORPEDO_BOMBER, SEA_BOMBER, SEA_FIGHTER, LBA_AIRCRAFT, ITCP_FIGHTER,
            JET_FIGHTER, JET_BOMBER, JET_TORPEDO_BOMBER -> Math.sqrt(carry.toDouble()) * aac
            else -> 0.0
        }
    }

}
