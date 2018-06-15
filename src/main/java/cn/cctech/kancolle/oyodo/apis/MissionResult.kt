package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.data.MissionRequireType
import cn.cctech.kancolle.oyodo.utils.setMissionProgress

data class MissionResult(
        val api_result: Int? = 0,
        val api_result_msg: String? = "",
        val api_data: MissionResultData? = MissionResultData()
) : JsonBean() {
    override fun process() {
        setMissionProgress(this, MissionRequireType.EXPEDITION)
    }
}

data class MissionResultData(
        val api_ship_id: List<Int?>? = listOf(),
        val api_clear_result: Int? = 0,
        val api_get_exp: Int? = 0,
        val api_member_lv: Int? = 0,
        val api_member_exp: Int? = 0,
        val api_get_ship_exp: List<Int?>? = listOf(),
        val api_get_exp_lvup: List<List<Int?>?>? = listOf(),
        val api_maparea_name: String? = "",
        val api_detail: String? = "",
        val api_quest_name: String? = "",
        val api_quest_level: Int? = 0,
        val api_get_material: List<Int?>? = listOf(),
        val api_useitem_flag: List<Int?>? = listOf()
)