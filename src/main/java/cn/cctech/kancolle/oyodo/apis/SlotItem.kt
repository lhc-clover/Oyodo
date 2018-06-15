package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.entities.Slot
import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Raw
import cn.cctech.kancolle.oyodo.managers.Transform
import cn.cctech.kancolle.oyodo.managers.User

data class SlotItem(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: List<ApiSlotItem>? = listOf()
) : JsonBean() {
    override fun process() {
        Fleet.slotMap.clear()
        api_data?.forEach {
            val rawSlot = Raw.rawSlotMap[it.api_slotitem_id]
            val slot = Slot(rawSlot, it)
            Fleet.slotMap[it.api_id] = slot
        }
        User.slotCount.onNext(Fleet.slotMap.count())
        Fleet.slotWatcher.onNext(Transform.All())
    }
}
