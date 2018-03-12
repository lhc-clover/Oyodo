package cn.cctech.kancolle.oyodo.managers

import cn.cctech.kancolle.oyodo.apis.ApiMstShip
import cn.cctech.kancolle.oyodo.apis.ApiMstSlotitem

object Raw : IManager() {

    val rawShipMap = HashMap<Int, ApiMstShip>()
    val rawSlotMap = HashMap<Int, ApiMstSlotitem>()

}
