package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Transform

data class SlotExchangeIndex(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: SlotExchangeIndexApiData = SlotExchangeIndexApiData()
) : JsonBean() {
    override fun process() {
        val shipId = params["api_id"]?.toInt()
        val ship = Fleet.shipMap[shipId]
        ship?.let {
            it.items.clear()
            it.items.addAll(api_data.api_slot)
            Fleet.shipWatcher.onNext(Transform.Change(listOf(it.id)))
        }
    }
}

data class SlotExchangeIndexApiData(
        val api_slot: List<Int> = listOf()
)