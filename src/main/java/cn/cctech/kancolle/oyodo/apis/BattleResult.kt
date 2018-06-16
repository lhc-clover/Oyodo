package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.data.MissionRequireType
import cn.cctech.kancolle.oyodo.managers.Battle
import cn.cctech.kancolle.oyodo.utils.setMissionProgress

data class BattleResult(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: BattleResultApiData? = BattleResultApiData()
) : JsonBean() {
    override fun process() {
        api_data?.api_get_ship?.let {
            Battle.get = it.api_ship_name
        }
        Battle.phaseShift(Battle.Phase.BattleResult)

        setMissionProgress(this, MissionRequireType.BATTLE)
    }
}

data class BattleResultApiData(
        val api_ship_id: List<Int> = listOf(),
        val api_win_rank: String? = null,
        val api_get_exp: Int = 0,
        val api_mvp: Int = 0,
        val api_member_lv: Int = 0,
        val api_member_exp: Int = 0,
        val api_get_base_exp: Int = 0,
        val api_get_ship_exp: List<Int> = listOf(),
        val api_get_exp_lvup: List<List<Int>> = listOf(),
        val api_dests: Int = 0,
        val api_destsf: Int = 0,
        val api_quest_name: String = "",
        val api_quest_level: Int = 0,
        val api_enemy_info: ApiEnemyInfo? = ApiEnemyInfo(),
        val api_first_clear: Int = 0,
        val api_mapcell_incentive: Int = 0,
        val api_get_flag: List<Int> = listOf(),
        val api_get_ship: ApiGetShip? = ApiGetShip(),
        val api_get_eventflag: Int = 0,
        val api_get_exmap_rate: Int = 0,
        val api_get_exmap_useitem_id: Int = 0,
        val api_escape_flag: Int = 0,
        val api_escape: Any = Any()
)

data class ApiGetShip(
        val api_ship_id: Int = 0,
        val api_ship_type: String = "",
        val api_ship_name: String = "",
        val api_ship_getmes: String = ""
)

data class ApiEnemyInfo(
        val api_level: String = "",
        val api_rank: String = "",
        val api_deck_name: String = ""
)