package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.entities.Expedition
import cn.cctech.kancolle.oyodo.managers.Dock
import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Transform

data class Deck(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: List<DeckApiData>? = listOf()
) : JsonBean() {
    override fun process() {
        val shipIds = mutableListOf<Int>()
        api_data?.forEachIndexed { index, it ->
            val expedition = Expedition(it)
            if (expedition.valid()) {
                Dock.expeditionList[index].onNext(expedition)
                shipIds.addAll(Fleet.deckShipIds[index].value)
            }
        }
        Fleet.shipWatcher.onNext(Transform.Change(shipIds))
    }
}

data class DeckApiData(
        val api_member_id: Int = 0,
        val api_id: Int = 0,
        val api_name: String = "",
        val api_name_id: String = "",
        val api_mission: List<String>? = listOf(),
        val api_flagship: String = "",
        val api_ship: List<Int>? = listOf()
)