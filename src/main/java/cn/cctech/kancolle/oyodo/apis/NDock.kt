package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.entities.Repair
import cn.cctech.kancolle.oyodo.managers.Dock
import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Transform
import cn.cctech.kancolle.oyodo.utils.CONDITION_REPAIR
import kotlin.math.max

data class NDock(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: List<NDockApiData>? = listOf()
) : JsonBean() {
    override fun process() {
        val shipIds = mutableListOf<Int>()
        api_data?.forEachIndexed { index, it ->
            val old = Dock.repairList[index].value
            if (old != null && old.valid() && old.state == 1 && it.api_state == 0) {
                val ship = Fleet.shipMap[old.shipId]
                ship?.let {
                    shipIds.add(it.id)
                    it.nowHp = it.maxHp
                    it.condition = max(CONDITION_REPAIR, it.condition)

                }
            }
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