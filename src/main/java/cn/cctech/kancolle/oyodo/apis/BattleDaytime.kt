package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.entities.Ship
import cn.cctech.kancolle.oyodo.managers.Battle
import cn.cctech.kancolle.oyodo.managers.Battle.calcEnemyOrdinalDamage
import cn.cctech.kancolle.oyodo.managers.Battle.calcFriendOrdinalDamage
import cn.cctech.kancolle.oyodo.managers.Battle.calcRank
import cn.cctech.kancolle.oyodo.managers.Battle.calcTargetDamage
import cn.cctech.kancolle.oyodo.managers.Battle.newTurn
import cn.cctech.kancolle.oyodo.managers.Raw

data class BattleDaytime(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: BattleApiData? = BattleApiData()
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

        newTurn()

        calcFriendOrdinalDamage(api_data?.api_kouku?.api_stage3?.api_fdam)
        calcEnemyOrdinalDamage(api_data?.api_kouku?.api_stage3?.api_edam)
        calcFriendOrdinalDamage(api_data?.api_air_base_injection?.api_stage3?.api_fdam)
        calcEnemyOrdinalDamage(api_data?.api_air_base_injection?.api_stage3?.api_edam)
        calcFriendOrdinalDamage(api_data?.api_injection_kouku?.api_stage3?.api_fdam)
        calcEnemyOrdinalDamage(api_data?.api_injection_kouku?.api_stage3?.api_edam)
        api_data?.api_air_base_attack?.forEach {
            calcFriendOrdinalDamage(it.api_stage3?.api_fdam)
            calcEnemyOrdinalDamage(it.api_stage3?.api_edam)
        }

        calcEnemyOrdinalDamage(api_data?.api_support_info?.api_support_airattack?.api_stage3?.api_edam)
        calcEnemyOrdinalDamage(api_data?.api_support_info?.api_support_hourai?.api_damage)

        calcTargetDamage(api_data?.api_opening_taisen?.api_df_list,
                api_data?.api_opening_taisen?.api_damage,
                api_data?.api_opening_taisen?.api_at_eflag)
        calcFriendOrdinalDamage(api_data?.api_opening_atack?.api_fdam)
        calcEnemyOrdinalDamage(api_data?.api_opening_atack?.api_edam)
        calcTargetDamage(api_data?.api_hougeki1?.api_df_list,
                api_data?.api_hougeki1?.api_damage,
                api_data?.api_hougeki1?.api_at_eflag)
        calcTargetDamage(api_data?.api_hougeki2?.api_df_list,
                api_data?.api_hougeki2?.api_damage,
                api_data?.api_hougeki2?.api_at_eflag)
        calcTargetDamage(api_data?.api_hougeki3?.api_df_list,
                api_data?.api_hougeki3?.api_damage,
                api_data?.api_hougeki3?.api_at_eflag)
        calcFriendOrdinalDamage(api_data?.api_raigeki?.api_fdam)
        calcEnemyOrdinalDamage(api_data?.api_raigeki?.api_edam)

        calcRank()

        Battle.phaseShift(Battle.Phase.BattleDaytime)
    }
}

data class BattleApiData(
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
        val api_support_flag: Int = 0,
        val api_support_info: ApiSupportInfo? = ApiSupportInfo(),
        val api_opening_taisen_flag: Int = 0,
        val api_opening_taisen: ApiOpeningTaisen? = ApiOpeningTaisen(),
        val api_opening_flag: Int = 0,
        val api_opening_atack: ApiOpeningAtack? = ApiOpeningAtack(),
        val api_hourai_flag: List<Int> = listOf(),
        val api_hougeki1: ApiHougeki? = ApiHougeki(),
        val api_hougeki2: ApiHougeki? = ApiHougeki(),
        val api_hougeki3: ApiHougeki? = ApiHougeki(),
        val api_raigeki: ApiRaigeki? = ApiRaigeki(),
        val api_air_base_injection: ApiAirBaseInjection? = ApiAirBaseInjection(),
        val api_injection_kouku: ApiInjectionKouku? = ApiInjectionKouku(),
        val api_air_base_attack: List<ApiAirBaseAttack> = listOf()
)

data class ApiHougeki(
        val api_at_eflag: List<Int> = listOf(),
        val api_at_list: List<Any> = listOf(),
        val api_at_type: List<Any> = listOf(),
        val api_df_list: List<Any> = listOf(),
        val api_si_list: List<Any> = listOf(),
        val api_cl_list: List<Any> = listOf(),
        val api_damage: List<Any> = listOf()
)

data class ApiKouku(
//        val api_plane_from: List<List<Int>> = listOf(),
//        val api_stage2: ApiStage2 = ApiStage2(),
        val api_stage3: ApiStage3? = ApiStage3(),
        val api_stage1: ApiStage1? = ApiStage1()
)

data class ApiStage2(
        val api_f_count: Int = 0,
        val api_f_lostcount: Int = 0,
        val api_e_count: Int = 0,
        val api_e_lostcount: Int = 0
)

data class ApiStage3(
//        val api_frai_flag: List<Int> = listOf(),
//        val api_erai_flag: List<Int> = listOf(),
//        val api_fbak_flag: List<Int> = listOf(),
//        val api_ebak_flag: List<Int> = listOf(),
//        val api_fcl_flag: List<Int> = listOf(),
//        val api_ecl_flag: List<Int> = listOf(),
        val api_fdam: List<Double> = listOf(),
        val api_edam: List<Double> = listOf()
)

data class ApiStage1(
        val api_f_count: Int = 0,
        val api_f_lostcount: Int = 0,
        val api_e_count: Int = 0,
        val api_e_lostcount: Int = 0,
        val api_disp_seiku: Int = 0,
        val api_touch_plane: List<Int> = listOf()
)

data class ApiSupportInfo(
        val api_support_airattack: ApiSupportAirAttack? = ApiSupportAirAttack(),
        val api_support_hourai: ApiSupportHourai? = ApiSupportHourai()
)

data class ApiSupportAirAttack(
        val api_stage3: ApiStage3? = ApiStage3()
)

data class ApiSupportHourai(
        var api_damage: List<Double> = listOf()
)

data class ApiOpeningTaisen(
        var api_at_eflag: List<Int> = listOf(),
        var api_df_list: List<Any> = listOf(),
        var api_damage: List<Any> = listOf()
)

data class ApiOpeningAtack(
        var api_fdam: List<Double> = listOf(),
        var api_edam: List<Double> = listOf()
)

data class ApiRaigeki(
        var api_frai: List<Int> = listOf(),
        var api_erai: List<Int> = listOf(),
        var api_fdam: List<Double> = listOf(),
        var api_edam: List<Double> = listOf(),
        var api_fydam: List<Double> = listOf(),
        var api_eydam: List<Double> = listOf(),
        var api_fcl: List<Int> = listOf(),
        var api_ecl: List<Int> = listOf()
)

data class ApiAirBaseInjection(
        val api_stage3: ApiStage3? = ApiStage3()
)

data class ApiInjectionKouku(
        val api_stage3: ApiStage3? = ApiStage3()
)

data class ApiAirBaseAttack(
        val api_stage3: ApiStage3? = ApiStage3()
)
