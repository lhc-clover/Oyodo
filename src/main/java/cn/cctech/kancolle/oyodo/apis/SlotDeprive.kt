package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.entities.Ship
import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Raw
import cn.cctech.kancolle.oyodo.managers.Transform

data class SlotDeprive(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: SlotDepriveApiData = SlotDepriveApiData()
) : JsonBean() {
    override fun process() {
        val setShip = api_data.api_ship_data.api_set_ship
        val setEntity = Ship(setShip, Raw.rawShipMap[setShip.api_ship_id])
        Fleet.shipMap[setShip.api_id] = setEntity

        val unsetShip = api_data.api_ship_data.api_unset_ship
        val unsetEntity = Ship(unsetShip, Raw.rawShipMap[unsetShip.api_ship_id])
        Fleet.shipMap[unsetShip.api_id] = unsetEntity

        Fleet.shipWatcher.onNext(Transform.Change(listOf(setShip.api_id, unsetShip.api_id)))
    }
}

data class SlotDepriveApiData(
        val api_ship_data: SlotDepriveApiShipData = SlotDepriveApiShipData(),
        val api_unset_list: ApiUnsetList = ApiUnsetList()
)

data class ApiUnsetList(
        val api_type3No: Int = 0,
        val api_slot_list: List<Int> = listOf()
)

data class SlotDepriveApiShipData(
        val api_set_ship: ApiShip = ApiShip(),
        val api_unset_ship: ApiShip = ApiShip()
)

//data class ApiUnsetShip(
//        val api_id: Int = 0,
//        val api_sortno: Int = 0,
//        val api_ship_id: Int = 0,
//        val api_lv: Int = 0,
//        val api_exp: List<Int> = listOf(),
//        val api_nowhp: Int = 0,
//        val api_maxhp: Int = 0,
//        val api_soku: Int = 0,
//        val api_leng: Int = 0,
//        val api_slot: List<Int> = listOf(),
//        val api_onslot: List<Int> = listOf(),
//        val api_slot_ex: Int = 0,
//        val api_kyouka: List<Int> = listOf(),
//        val api_backs: Int = 0,
//        val api_fuel: Int = 0,
//        val api_bull: Int = 0,
//        val api_slotnum: Int = 0,
//        val api_ndock_time: Int = 0,
//        val api_ndock_item: List<Int> = listOf(),
//        val api_srate: Int = 0,
//        val api_cond: Int = 0,
//        val api_karyoku: List<Int> = listOf(),
//        val api_raisou: List<Int> = listOf(),
//        val api_taiku: List<Int> = listOf(),
//        val api_soukou: List<Int> = listOf(),
//        val api_kaihi: List<Int> = listOf(),
//        val api_taisen: List<Int> = listOf(),
//        val api_sakuteki: List<Int> = listOf(),
//        val api_lucky: List<Int> = listOf(),
//        val api_locked: Int = 0,
//        val api_locked_equip: Int = 0,
//        val api_sally_area: Int = 0
//)
//
//data class ApiSetShip(
//        val api_id: Int = 0,
//        val api_sortno: Int = 0,
//        val api_ship_id: Int = 0,
//        val api_lv: Int = 0,
//        val api_exp: List<Int> = listOf(),
//        val api_nowhp: Int = 0,
//        val api_maxhp: Int = 0,
//        val api_soku: Int = 0,
//        val api_leng: Int = 0,
//        val api_slot: List<Int> = listOf(),
//        val api_onslot: List<Int> = listOf(),
//        val api_slot_ex: Int = 0,
//        val api_kyouka: List<Int> = listOf(),
//        val api_backs: Int = 0,
//        val api_fuel: Int = 0,
//        val api_bull: Int = 0,
//        val api_slotnum: Int = 0,
//        val api_ndock_time: Int = 0,
//        val api_ndock_item: List<Int> = listOf(),
//        val api_srate: Int = 0,
//        val api_cond: Int = 0,
//        val api_karyoku: List<Int> = listOf(),
//        val api_raisou: List<Int> = listOf(),
//        val api_taiku: List<Int> = listOf(),
//        val api_soukou: List<Int> = listOf(),
//        val api_kaihi: List<Int> = listOf(),
//        val api_taisen: List<Int> = listOf(),
//        val api_sakuteki: List<Int> = listOf(),
//        val api_lucky: List<Int> = listOf(),
//        val api_locked: Int = 0,
//        val api_locked_equip: Int = 0,
//        val api_sally_area: Int = 0
//)