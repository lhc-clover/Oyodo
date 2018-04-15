package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.entities.Repair
import cn.cctech.kancolle.oyodo.managers.Dock
import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Transform

data class NDock(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: List<NDockApiData>? = listOf()
) : JsonBean() {
    override fun process() {
        val shipIds = mutableListOf<Int>()
        api_data?.forEachIndexed { index, it ->
            Dock.repairList[index].onNext(Repair(it))
            shipIds.add(it.api_ship_id)
        }
        Fleet.shipWatcher.onNext(Transform.Change(shipIds))
    }
}

data class NDockApiData(
        val api_member_id: Int = 0,
        val api_id: Int = 0,
        val api_state: Int = 0,
        val api_ship_id: Int = 0,
        val api_complete_time: Long = 0,
        val api_complete_time_str: String = "",
        val api_item1: Int = 0,
        val api_item2: Int = 0,
        val api_item3: Int = 0,
        val api_item4: Int = 0
)