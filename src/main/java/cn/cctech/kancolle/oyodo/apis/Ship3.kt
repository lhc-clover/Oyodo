package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.entities.Ship
import cn.cctech.kancolle.oyodo.entities.Slot
import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Raw
import cn.cctech.kancolle.oyodo.managers.Transform

data class Ship3(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: Ship3ApiData = Ship3ApiData()
) : JsonBean() {
    override fun process() {
        api_data.api_ship_data.forEach {
            val rawShip = Raw.rawShipMap[it.api_ship_id]
            val ship = Ship(it, rawShip)
            Fleet.shipMap[it.api_id] = ship
            it.api_slot.forEach {
                val rawSlot = Raw.rawSlotMap[it]
                val slot = Slot(rawSlot)
                Fleet.slotMap
            }
        }
        Fleet.shipWatcher.onNext(Transform.Add(api_data.api_ship_data.map { it.api_id }))
        api_data.api_deck_data.forEach {
            val fleetIds = it.api_ship
            Fleet.deckShipIds[it.api_id - 1].onNext(fleetIds)
        }
    }
}

data class Ship3ApiData(
        val api_ship_data: List<ApiShipData> = listOf(),
        val api_deck_data: List<ApiDeckData> = listOf(),
        val api_slot_data: Map<String, List<Int>> = mapOf()
)

data class ApiShipData(
        val api_id: Int = 0,
        val api_sortno: Int = 0,
        val api_ship_id: Int = 0,
        val api_lv: Int = 0,
        val api_exp: List<Int> = listOf(),
        val api_nowhp: Int = 0,
        val api_maxhp: Int = 0,
        val api_soku: Int = 0,
        val api_leng: Int = 0,
        val api_slot: List<Int> = listOf(),
        val api_onslot: List<Int> = listOf(),
        val api_slot_ex: Int = 0,
        val api_kyouka: List<Int> = listOf(),
        val api_backs: Int = 0,
        val api_fuel: Int = 0,
        val api_bull: Int = 0,
        val api_slotnum: Int = 0,
        val api_ndock_time: Long = 0,
        val api_ndock_item: List<Int> = listOf(),
        val api_srate: Int = 0,
        val api_cond: Int = 0,
        val api_karyoku: List<Int> = listOf(),
        val api_raisou: List<Int> = listOf(),
        val api_taiku: List<Int> = listOf(),
        val api_soukou: List<Int> = listOf(),
        val api_kaihi: List<Int> = listOf(),
        val api_taisen: List<Int> = listOf(),
        val api_sakuteki: List<Int> = listOf(),
        val api_lucky: List<Int> = listOf(),
        val api_locked: Int = 0,
        val api_locked_equip: Int = 0,
        val api_sally_area: Int = 0
)

data class ApiDeckData(
        val api_member_id: Int = 0,
        val api_id: Int = 0,
        val api_name: String = "",
        val api_name_id: String = "",
        val api_mission: List<String> = listOf(),
        val api_flagship: String = "",
        val api_ship: List<Int> = listOf()
)
