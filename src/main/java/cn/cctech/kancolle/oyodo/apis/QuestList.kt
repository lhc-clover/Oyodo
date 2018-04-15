package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.entities.Quest
import cn.cctech.kancolle.oyodo.managers.Mission
import com.google.gson.JsonArray

data class QuestList(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: QuestListApiData = QuestListApiData()
) : JsonBean() {
    override fun process() {
        val map = Mission.questMap.value
        fun clearQuestState() = map.forEach { it.value.state = 1 }
        val tabId = try {
            params["api_tab_id"]!!.toInt()
        } catch (e: Exception) {
            -1
        }
        if (tabId == 9 || !Mission.isSameDay()) {
            clearQuestState()
        }
        api_data.api_list.forEach {
            try {
                val quest = Quest(it)
                map[quest.id] = quest
            } catch (e: Exception) {
                println("$it is not a quest object")
            }
        }
        Mission.questMap.onNext(map)
    }
}

data class QuestListApiData(
        val api_count: Int = 0,
        val api_completed_kind: Int = 0,
        val api_page_count: Int = 0,
        val api_disp_page: Int = 0,
        val api_list: JsonArray = JsonArray(),
        val api_exec_count: Int = 0,
        val api_exec_type: Int = 0
)

data class QuestListBean(
        val api_no: Int = 0,
        val api_category: Int = 0,
        val api_type: Int = 0,
        val api_state: Int = 0,
        val api_title: String = "",
        val api_detail: String = "",
        val api_voice_id: Int = 0,
        val api_get_material: List<Int> = listOf(),
        val api_bonus_flag: Int = 0,
        val api_progress_flag: Int = 0,
        val api_invalid_flag: Int = 0
)