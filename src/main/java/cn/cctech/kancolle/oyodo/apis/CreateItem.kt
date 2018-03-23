package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.entities.Slot
import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Raw
import cn.cctech.kancolle.oyodo.managers.User
import io.reactivex.subjects.BehaviorSubject

data class CreateItem(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: CreateItemApiData = CreateItemApiData()
) : JsonBean() {
    override fun process() {
        if (api_data.api_create_flag == 1) {
            val rawSlot = Raw.rawSlotMap[api_data.api_slot_item.api_slotitem_id]
            val slot = Slot(rawSlot, null)
            Fleet.slotMap[api_data.api_slot_item.api_id] = BehaviorSubject.createDefault(slot)
            User.slotCount.onNext(Fleet.slotMap.size)
        }
    }
}

data class CreateItemApiData(
        val api_create_flag: Int = 0,
        val api_shizai_flag: Int = 0,
        val api_slot_item: CreateItemApiSlotItem = CreateItemApiSlotItem(),
        val api_material: List<Int> = listOf(),
        val api_type3: Int = 0,
        val api_unsetslot: List<Int> = listOf()
)

data class CreateItemApiSlotItem(
        val api_id: Int = 0,
        val api_slotitem_id: Int = 0
)