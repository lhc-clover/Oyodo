package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.managers.Battle

data class BattleNight(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: BattleNightApiData? = BattleNightApiData()
) : JsonBean() {
    override fun process() {
        Battle.newTurn()
        Battle.calcTargetDamage(api_data?.api_hougeki?.api_df_list,
                api_data?.api_hougeki?.api_damage,
                api_data?.api_hougeki?.api_at_eflag)
        Battle.calcRank()
        Battle.phaseShift(Battle.Phase.BattleNight)
    }
}

data class BattleNightApiData(
        val api_deck_id: Int = 0,
        val api_formation: List<Int> = listOf(),
        val api_f_nowhps: List<Int> = listOf(),
        val api_f_maxhps: List<Int> = listOf(),
        val api_fParam: List<List<Int>> = listOf(),
        val api_ship_ke: List<Int> = listOf(),
        val api_ship_lv: List<Int> = listOf(),
        val api_e_nowhps: List<Int> = listOf(),
        val api_e_maxhps: List<Int> = listOf(),
        val api_eSlot: List<List<Int>> = listOf(),
        val api_eParam: List<List<Int>> = listOf(),
        val api_touch_plane: List<Int> = listOf(),
        val api_flare_pos: List<Int> = listOf(),
        val api_hougeki: ApiHougeki? = ApiHougeki()
)
