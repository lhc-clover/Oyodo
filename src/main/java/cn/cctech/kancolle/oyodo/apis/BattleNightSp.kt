package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.entities.Ship
import cn.cctech.kancolle.oyodo.managers.Battle
import cn.cctech.kancolle.oyodo.managers.Raw

data class BattleNightSp(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: BattleNightSpApiData? = BattleNightSpApiData()
) : JsonBean() {
    override fun process() {
        Battle.friendFormation = api_data?.api_formation?.get(0) ?: -1
        Battle.enemyFormation = api_data?.api_formation?.get(1) ?: -1
        Battle.heading = api_data?.api_formation?.get(2) ?: -1

        val enemies = mutableListOf<Ship>()
        api_data?.api_ship_ke?.forEachIndexed { index, id ->
            val rawShip = Raw.rawShipMap[id]
            val enemy = Ship(rawShip)
            enemy.level = api_data.api_ship_lv[index]
            enemy.nowHp = api_data.api_e_nowhps[index]
            enemy.maxHp = api_data.api_e_maxhps[index]
            enemy.items.addAll(api_data.api_eSlot[index])
            enemies.add(enemy)
        }
        Battle.enemyList = enemies

        Battle.newTurn()
        Battle.calcEnemyOrdinalDamage(api_data?.api_n_support_info?.api_support_hourai?.api_damage)
        Battle.calcTargetDamage(api_data?.api_hougeki?.api_df_list,
                api_data?.api_hougeki?.api_damage,
                api_data?.api_hougeki?.api_at_eflag)
        Battle.calcRank()

        Battle.phaseShift(Battle.Phase.BattleNightSp)
    }
}

data class BattleNightSpApiData(
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
        val api_n_support_flag: Int = 0,
        val api_n_support_info: ApiNSupportInfo? = ApiNSupportInfo(),
        val api_touch_plane: List<Int> = listOf(),
        val api_flare_pos: List<Int> = listOf(),
        val api_hougeki: ApiHougeki? = ApiHougeki()
)

data class ApiNSupportInfo(
        val api_support_hourai: ApiSupportHourai = ApiSupportHourai()
)
