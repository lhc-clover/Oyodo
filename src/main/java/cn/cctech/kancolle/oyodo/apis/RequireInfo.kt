package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.entities.Slot
import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Raw
import io.reactivex.subjects.BehaviorSubject


data class RequireInfo(
        val api_result: Int,
        val api_result_msg: String,
        val api_data: RequireInfoApiData
) : JsonBean() {
    override fun process() {
        Fleet.slotMap.clear()
        api_data.api_slot_item.forEach {
            val rawSlot = Raw.rawSlotMap[it.api_slotitem_id]
            val slot = Slot(rawSlot, it)
            Fleet.slotMap[it.api_id] = BehaviorSubject.create()
            Fleet.slotMap[it.api_id]?.onNext(slot)
        }
    }
}

data class RequireInfoApiData(
        val api_basic: RequireInfoApiBasic,
        val api_slot_item: List<ApiSlotItem>,
        val api_unsetslot: Map<String, List<Int>>,
        val api_kdock: List<ApiKdock>,
        val api_useitem: List<ApiUseitem>,
        val api_furniture: List<ApiFurniture>,
        val api_extra_supply: List<Int>
)

//data class ApiUnsetslot(
//        val api_slottype23: List<Int>,
//        val api_slottype21: List<Int>,
//        val api_slottype1: List<Int>,
//        val api_slottype2: List<Int>,
//        val api_slottype5: List<Int>,
//        val api_slottype4: List<Int>,
//        val api_slottype7: List<Int>,
//        val api_slottype10: List<Int>
//)

data class ApiFurniture(
        val api_id: Int,
        val api_furniture_type: Int,
        val api_furniture_no: Int,
        val api_furniture_id: Int
)

data class ApiUseitem(
        val api_id: Int,
        val api_count: Int
)

data class ApiSlotItem(
        val api_id: Int,
        val api_slotitem_id: Int,
        val api_locked: Int,
        val api_level: Int,
        val api_alv: Int
)

data class RequireInfoApiBasic(
        val api_member_id: Int,
        val api_firstflag: Int
)

data class ApiKdock(
        val api_id: Int,
        val api_state: Int,
        val api_created_ship_id: Int,
        val api_complete_time: Long,
        val api_complete_time_str: String,
        val api_item1: Int,
        val api_item2: Int,
        val api_item3: Int,
        val api_item4: Int,
        val api_item5: Int
)