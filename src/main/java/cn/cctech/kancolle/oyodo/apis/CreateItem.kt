package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.data.MissionRequireType
import cn.cctech.kancolle.oyodo.entities.Slot
import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Raw
import cn.cctech.kancolle.oyodo.managers.Transform
import cn.cctech.kancolle.oyodo.managers.User
import cn.cctech.kancolle.oyodo.utils.setMissionProgress

data class CreateItem(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: CreateItemApiData? = CreateItemApiData()
) : JsonBean() {
    override fun process() {
        if (api_data?.api_create_flag == 1) {
            val rawSlot = Raw.rawSlotMap[api_data.api_slot_item?.api_slotitem_id]
            val slot = Slot(rawSlot, null)
            val slotId = api_data.api_slot_item?.api_id ?: Int.MAX_VALUE
            Fleet.slotMap[slotId] = slot
            Fleet.slotWatcher.onNext(Transform.Add(listOf(slotId)))
            User.slotCount.onNext(Fleet.slotMap.size)
        }

        setMissionProgress(this, MissionRequireType.CREATE_ITEM)
    }
}

data class CreateItemApiData(
        val api_create_flag: Int = 0,
        val api_shizai_flag: Int = 0,
        val api_slot_item: CreateItemApiSlotItem? = CreateItemApiSlotItem(),
        val api_material: List<Int>? = listOf(),
        val api_type3: Int = 0,
        val api_unsetslot: List<Int>? = listOf()
)

data class CreateItemApiSlotItem(
        val api_id: Int = 0,
        val api_slotitem_id: Int = 0
)