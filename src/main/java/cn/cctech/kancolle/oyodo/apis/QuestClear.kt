package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.managers.Mission

data class QuestClear(
        val api_result: Int? = 0,
        val api_result_msg: String? = "",
        val api_data: QuestClearApiData? = QuestClearApiData()
) : JsonBean() {
    override fun process() {
        val questId = params["api_quest_id"]?.toInt() ?: -1
        Mission.questMap.value.remove(questId)
    }
}

data class QuestClearApiData(
        val api_material: List<Int?>? = listOf(),
        val api_bounus_count: Int? = 0,
        val api_bounus: List<ApiBounu?>? = listOf()
)

data class ApiBounu(
        val api_type: Int? = 0,
        val api_count: Int? = 0,
        val api_item: ApiItem? = ApiItem()
)

data class ApiItem(
        val api_id: Int? = 0,
        val api_name: String? = ""
)