package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.managers.Fleet

data class SlotExchangeIndex(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: SlotExchangeIndexApiData = SlotExchangeIndexApiData()
) : JsonBean() {
    override fun process() {
        val shipId = params["api_id"]?.toInt()
        val ship = Fleet.shipMap[shipId]
        ship?.let {
            val entity = it.value
            entity.items.clear()
            entity.items.addAll(api_data.api_slot)
            it.onNext(entity)
        }
    }
}

data class SlotExchangeIndexApiData(
        val api_slot: List<Int> = listOf()
)