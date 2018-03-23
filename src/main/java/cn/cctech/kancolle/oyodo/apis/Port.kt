package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.entities.Expedition
import cn.cctech.kancolle.oyodo.entities.Repair
import cn.cctech.kancolle.oyodo.entities.Ship
import cn.cctech.kancolle.oyodo.managers.*
import io.reactivex.subjects.BehaviorSubject

data class Port(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: ApiData = ApiData()
) : JsonBean() {

    override fun process() {
        // api_material
        Material.fuel.onNext(api_data.api_material[0].api_value)
        Material.ammo.onNext(api_data.api_material[1].api_value)
        Material.metal.onNext(api_data.api_material[2].api_value)
        Material.bauxite.onNext(api_data.api_material[3].api_value)
        Material.burner.onNext(api_data.api_material[4].api_value)
        Material.bucket.onNext(api_data.api_material[5].api_value)
        Material.research.onNext(api_data.api_material[6].api_value)
        Material.improve.onNext(api_data.api_material[7].api_value)
        // api_deck_port
        api_data.api_deck_port.forEachIndexed { index, it ->
            Fleet.deckShipIds[index].onNext(it.api_ship)
            Dock.expeditionList[index].onNext(Expedition(it))
            Fleet.deckNames[index].onNext(it.api_name)
        }
        // api_ndock
        api_data.api_ndock.forEachIndexed { index, it -> Dock.repairList[index].onNext(Repair(it)) }
        // api_basic
        User.nickname.onNext(api_data.api_basic.api_nickname)
        User.level.onNext(api_data.api_basic.api_level)
        User.shipMax.onNext(api_data.api_basic.api_max_chara)
        User.slotMax.onNext(api_data.api_basic.api_max_slotitem)
        User.kDockCount.onNext(api_data.api_basic.api_count_kdock)
        User.nDockCount.onNext(api_data.api_basic.api_count_ndock)
        User.deckCount.onNext(api_data.api_basic.api_count_deck)
        // api_ship
        Fleet.shipMap.clear()
        api_data.api_ship.forEach {
            val rawShip = Raw.rawShipMap[it.api_ship_id]
            val ship = Ship(it, rawShip)
            Fleet.shipMap[it.api_id] = BehaviorSubject.createDefault(ship)
        }
        User.shipCount.onNext(Fleet.shipMap.size)
    }

}

data class ApiData(
        val api_material: List<ApiMaterial> = listOf(),
        val api_deck_port: List<ApiDeckPort> = listOf(),
        val api_ndock: List<ApiNdock> = listOf(),
        val api_ship: List<ApiShip> = listOf(),
        val api_basic: ApiBasic = ApiBasic(),
        val api_log: List<ApiLog> = listOf(),
        val api_p_bgm_id: Int = 0,
        val api_parallel_quest_count: Int = 0
)

data class ApiLog(
        val api_no: Int = 0,
        val api_type: String = "",
        val api_state: String = "",
        val api_message: String = ""
)

data class ApiNdock(
        val api_member_id: Int = 0,
        val api_id: Int = 0,
        val api_state: Int = 0,
        val api_ship_id: Int = 0,
        val api_complete_time: Long = 0,
        val api_complete_time_str: String = "",
        val api_item1: Int = 0,
        val api_item2: Int = 0,
        val api_item3: Int = 0,
        val api_item4: Int = 0
)

data class ApiShip(
        val api_id: Int = 0,
        val api_sortno: Int = 0,
        val api_ship_id: Int = 0,
        val api_lv: Int = 0,
        val api_exp: List<Int> = listOf(),
        val api_nowhp: Int = 0,
        val api_maxhp: Int = 0,
        val api_soku: Int = 0,
        val api_leng: Int = 0,
        val api_slot: List<Int> = listOf(),
        val api_onslot: List<Int> = listOf(),
        val api_slot_ex: Int = 0,
        val api_kyouka: List<Int> = listOf(),
        val api_backs: Int = 0,
        val api_fuel: Int = 0,
        val api_bull: Int = 0,
        val api_slotnum: Int = 0,
        val api_ndock_time: Int = 0,
        val api_ndock_item: List<Int> = listOf(),
        val api_srate: Int = 0,
        val api_cond: Int = 0,
        val api_karyoku: List<Int> = listOf(),
        val api_raisou: List<Int> = listOf(),
        val api_taiku: List<Int> = listOf(),
        val api_soukou: List<Int> = listOf(),
        val api_kaihi: List<Int> = listOf(),
        val api_taisen: List<Int> = listOf(),
        val api_sakuteki: List<Int> = listOf(),
        val api_lucky: List<Int> = listOf(),
        val api_locked: Int = 0,
        val api_locked_equip: Int = 0
)

data class ApiMaterial(
        val api_member_id: Int = 0,
        val api_id: Int = 0,
        val api_value: Int = 0
)

data class ApiDeckPort(
        val api_member_id: Int = 0,
        val api_id: Int = 0,
        val api_name: String = "",
        val api_name_id: String = "",
        val api_mission: List<String> = listOf(),
        val api_flagship: String = "",
        val api_ship: List<Int> = listOf()
)

data class ApiBasic(
        val api_member_id: String = "",
        val api_nickname: String = "",
        val api_nickname_id: String = "",
        val api_active_flag: Int = 0,
        val api_starttime: Long = 0,
        val api_level: Int = 0,
        val api_rank: Int = 0,
        val api_experience: Int = 0,
        val api_comment: String = "",
        val api_comment_id: String = "",
        val api_max_chara: Int = 0,
        val api_max_slotitem: Int = 0,
        val api_max_kagu: Int = 0,
        val api_playtime: Long = 0,
        val api_tutorial: Int = 0,
        val api_furniture: List<Int> = listOf(),
        val api_count_deck: Int = 0,
        val api_count_kdock: Int = 0,
        val api_count_ndock: Int = 0,
        val api_fcoin: Int = 0,
        val api_st_win: Int = 0,
        val api_st_lose: Int = 0,
        val api_ms_count: Int = 0,
        val api_ms_success: Int = 0,
        val api_pt_win: Int = 0,
        val api_pt_lose: Int = 0,
        val api_pt_challenged: Int = 0,
        val api_pt_challenged_win: Int = 0,
        val api_firstflag: Int = 0,
        val api_tutorial_progress: Int = 0,
        val api_pvp: List<Int> = listOf(),
        val api_medals: Int = 0,
        val api_large_dock: Int = 0
)
