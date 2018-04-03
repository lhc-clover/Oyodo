package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.entities.Ship
import cn.cctech.kancolle.oyodo.managers.Battle
import cn.cctech.kancolle.oyodo.managers.Raw

data class Practice(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: PracticeApiData? = PracticeApiData()
) : JsonBean() {
    override fun process() {
        Battle.friendFormation = api_data?.api_formation?.get(0) ?: -1
        Battle.enemyFormation = api_data?.api_formation?.get(1) ?: -1
        Battle.heading = api_data?.api_formation?.get(2) ?: -1
        Battle.airCommand = api_data?.api_kouku?.api_stage1?.api_disp_seiku ?: -1

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

        Battle.calcFriendOrdinalDamage(api_data?.api_kouku?.api_stage3?.api_fdam)
        Battle.calcEnemyOrdinalDamage(api_data?.api_kouku?.api_stage3?.api_edam)

        Battle.calcTargetDamage(api_data?.api_opening_taisen?.api_df_list,
                api_data?.api_opening_taisen?.api_damage,
                api_data?.api_opening_taisen?.api_at_eflag)
        Battle.calcFriendOrdinalDamage(api_data?.api_opening_atack?.api_fdam)
        Battle.calcEnemyOrdinalDamage(api_data?.api_opening_atack?.api_edam)
        Battle.calcTargetDamage(api_data?.api_hougeki1?.api_df_list,
                api_data?.api_hougeki1?.api_damage,
                api_data?.api_hougeki1?.api_at_eflag)
        Battle.calcTargetDamage(api_data?.api_hougeki2?.api_df_list,
                api_data?.api_hougeki2?.api_damage,
                api_data?.api_hougeki2?.api_at_eflag)
        Battle.calcTargetDamage(api_data?.api_hougeki3?.api_df_list,
                api_data?.api_hougeki3?.api_damage,
                api_data?.api_hougeki3?.api_at_eflag)
        Battle.calcFriendOrdinalDamage(api_data?.api_raigeki?.api_fdam)
        Battle.calcEnemyOrdinalDamage(api_data?.api_raigeki?.api_edam)

        Battle.calcRank()

        Battle.phaseShift(Battle.Phase.Practice)
    }
}

data class PracticeApiData(
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
        val api_midnight_flag: Int = 0,
        val api_search: List<Int> = listOf(),
        val api_stage_flag: List<Int> = listOf(),
        val api_kouku: ApiKouku? = ApiKouku(),
        val api_opening_taisen_flag: Int = 0,
        val api_opening_taisen: ApiOpeningTaisen? = ApiOpeningTaisen(),
        val api_opening_flag: Int = 0,
        val api_opening_atack: ApiOpeningAtack? = ApiOpeningAtack(),
        val api_hourai_flag: List<Int> = listOf(),
        val api_hougeki1: ApiHougeki? = ApiHougeki(),
        val api_hougeki2: ApiHougeki? = ApiHougeki(),
        val api_hougeki3: ApiHougeki? = ApiHougeki(),
        val api_raigeki: ApiRaigeki? = ApiRaigeki()
)
