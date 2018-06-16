package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.data.MissionRequireType
import cn.cctech.kancolle.oyodo.managers.Battle
import cn.cctech.kancolle.oyodo.utils.setMissionProgress

data class PracticeResult(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: PracticeResultApiData? = PracticeResultApiData()
) : JsonBean() {
    override fun process() {
        api_data?.api_win_rank?.let { Battle.rank = it }
        Battle.phaseShift(Battle.Phase.PracticeResult)

        setMissionProgress(this, MissionRequireType.PRACTICE)
    }
}

data class PracticeResultApiData(
        val api_ship_id: List<Int> = listOf(),
        val api_win_rank: String? = null,
        val api_get_exp: Int = 0,
        val api_member_lv: Int = 0,
        val api_member_exp: Int = 0,
        val api_get_base_exp: Int = 0,
        val api_mvp: Int = 0,
        val api_get_ship_exp: List<Int> = listOf(),
        val api_get_exp_lvup: List<List<Int>> = listOf(),
        val api_enemy_info: ApiEnemyInfo? = ApiEnemyInfo()
)
