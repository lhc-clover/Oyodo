package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.entities.Slot
import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Raw
import cn.cctech.kancolle.oyodo.managers.Transform
import cn.cctech.kancolle.oyodo.managers.User

data class RequireInfo(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: RequireInfoApiData? = RequireInfoApiData()
) : JsonBean() {
    override fun process() {
        Fleet.slotMap.clear()
        api_data?.api_slot_item?.forEach {
            val rawSlot = Raw.rawSlotMap[it.api_slotitem_id]
            val slot = Slot(rawSlot, it)
            Fleet.slotMap[it.api_id] = slot
        }
        Fleet.slotWatcher.onNext(Transform.All())
        User.slotCount.onNext(Fleet.slotMap.size)
    }
}

data class RequireInfoApiData(
        val api_basic: RequireInfoApiBasic? = RequireInfoApiBasic(),
        val api_slot_item: List<ApiSlotItem>? = listOf(),
        val api_unsetslot: Map<String, List<Int>>? = mapOf(),
        val api_kdock: List<KDockApiData>? = listOf(),
        val api_useitem: List<ApiUseitem>? = listOf(),
        val api_furniture: List<ApiFurniture>? = listOf(),
        val api_extra_supply: List<Int>? = listOf()
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
        val api_furniture_type: Int = 0,
        val api_furniture_no: Int = 0,
        val api_furniture_id: Int = 0
)

data class ApiUseitem(
        val api_id: Int = 0,
        val api_count: Int = 0
)

data class ApiSlotItem(
        val api_id: Int = 0,
        val api_slotitem_id: Int = 0,
        val api_locked: Int = 0,
        val api_level: Int = 0,
        val api_alv: Int = 0
)

data class RequireInfoApiBasic(
        val api_member_id: Int = 0,
        val api_firstflag: Int = 0
)
