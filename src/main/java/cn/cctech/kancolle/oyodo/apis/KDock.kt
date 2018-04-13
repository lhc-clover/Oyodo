package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.entities.Build
import cn.cctech.kancolle.oyodo.managers.Dock

data class KDock(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: List<KDockApiData>? = listOf()
) : JsonBean() {
    override fun process() {
        api_data?.forEachIndexed { index, it -> Dock.buildList[index].onNext(Build(it)) }
    }
}

data class KDockApiData(
        val api_id: Int = 0,
        val api_state: Int = 0,
        val api_created_ship_id: Int = 0,
        val api_complete_time: Long = 0,
        val api_complete_time_str: String = "",
        val api_item1: Int = 0,
        val api_item2: Int = 0,
        val api_item3: Int = 0,
        val api_item4: Int = 0,
        val api_item5: Int = 0
)