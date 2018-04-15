package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.managers.Raw

data class Start(
        val api_result: Int,
        val api_result_msg: String,
        val api_data: StartApiData
) : JsonBean() {
    override fun process() {
        api_data.api_mst_ship.forEach { Raw.rawShipMap[it.api_id] = it }
        api_data.api_mst_slotitem.forEach { Raw.rawSlotMap[it.api_id] = it }
    }
}

data class StartApiData(
        val api_mst_ship: List<ApiMstShip>,
        val api_mst_shipgraph: List<ApiMstShipgraph>,
        val api_mst_slotitem_equiptype: List<ApiMstSlotitemEquiptype>,
        val api_mst_equip_exslot: List<Int>,
        val api_mst_equip_exslot_ship: List<ApiMstEquipExslotShip>,
        val api_mst_stype: List<ApiMstStype>,
        val api_mst_slotitem: List<ApiMstSlotitem>,
        val api_mst_furniture: List<ApiMstFurniture>,
        val api_mst_furnituregraph: List<ApiMstFurnituregraph>,
        val api_mst_useitem: List<ApiMstUseitem>,
        val api_mst_payitem: List<ApiMstPayitem>,
        val api_mst_item_shop: ApiMstItemShop,
        val api_mst_maparea: List<ApiMstMaparea>,
        val api_mst_mapinfo: List<ApiMstMapinfo>,
        val api_mst_mapbgm: List<ApiMstMapbgm>,
        val api_mst_mission: List<ApiMstMission>,
        val api_mst_const: ApiMstConst,
        val api_mst_shipupgrade: List<ApiMstShipupgrade>,
        val api_mst_bgm: List<ApiMstBgm>
)

data class ApiMstUseitem(
        val api_id: Int,
        val api_usetype: Int,
        val api_category: Int,
        val api_name: String,
        val api_description: List<String>,
        val api_price: Int
)

data class ApiMstPayitem(
        val api_id: Int,
        val api_type: Int,
        val api_name: String,
        val api_description: String,
        val api_item: List<Int>,
        val api_price: Int
)

data class ApiMstStype(
        val api_id: Int,
        val api_sortno: Int,
        val api_name: String,
        val api_scnt: Int,
        val api_kcnt: Int,
        val api_equip_type: Map<String, String>
)

//data class ApiEquipType(
//		val 1: Int,
//		val 2: Int,
//		val 3: Int,
//		val 4: Int,
//		val 5: Int,
//		val 6: Int,
//		val 7: Int,
//		val 8: Int,
//		val 9: Int,
//		val 10: Int,
//		val 11: Int,
//		val 12: Int,
//		val 13: Int,
//		val 14: Int,
//		val 15: Int,
//		val 16: Int,
//		val 17: Int,
//		val 18: Int,
//		val 19: Int,
//		val 20: Int,
//		val 21: Int,
//		val 22: Int,
//		val 23: Int,
//		val 24: Int,
//		val 25: Int,
//		val 26: Int,
//		val 27: Int,
//		val 28: Int,
//		val 29: Int,
//		val 30: Int,
//		val 31: Int,
//		val 32: Int,
//		val 33: Int,
//		val 34: Int,
//		val 35: Int,
//		val 36: Int,
//		val 37: Int,
//		val 38: Int,
//		val 39: Int,
//		val 40: Int,
//		val 41: Int,
//		val 42: Int,
//		val 43: Int,
//		val 44: Int,
//		val 45: Int,
//		val 46: Int,
//		val 47: Int,
//		val 48: Int,
//		val 49: Int,
//		val 50: Int,
//		val 51: Int,
//		val 52: Int,
//		val 53: Int,
//		val 54: Int,
//		val 55: Int,
//		val 56: Int,
//		val 57: Int,
//		val 58: Int,
//		val 59: Int,
//		val 60: Int,
//		val 61: Int,
//		val 62: Int,
//		val 63: Int,
//		val 64: Int,
//		val 65: Int,
//		val 66: Int,
//		val 67: Int,
//		val 68: Int,
//		val 69: Int,
//		val 70: Int,
//		val 71: Int,
//		val 72: Int,
//		val 73: Int,
//		val 74: Int,
//		val 75: Int,
//		val 76: Int,
//		val 77: Int,
//		val 78: Int,
//		val 79: Int,
//		val 80: Int,
//		val 81: Int,
//		val 82: Int,
//		val 83: Int,
//		val 84: Int,
//		val 85: Int,
//		val 86: Int,
//		val 87: Int,
//		val 88: Int,
//		val 89: Int,
//		val 90: Int,
//		val 91: Int,
//		val 92: Int,
//		val 93: Int,
//		val 94: Int
//)

data class ApiMstBgm(
        val api_id: Int,
        val api_name: String
)

data class ApiMstFurnituregraph(
        val api_id: Int,
        val api_type: Int,
        val api_no: Int,
        val api_filename: String,
        val api_version: String
)

data class ApiMstItemShop(
        val api_cabinet_1: List<Int>,
        val api_cabinet_2: List<Int>
)

data class ApiMstMaparea(
        val api_id: Int,
        val api_name: String,
        val api_type: Int
)

data class ApiMstMapbgm(
        val api_id: Int,
        val api_maparea_id: Int,
        val api_no: Int,
        val api_moving_bgm: Int,
        val api_map_bgm: List<Int>,
        val api_boss_bgm: List<Int>
)

data class ApiMstShipupgrade(
        val api_id: Int,
        val api_current_ship_id: Int,
        val api_original_ship_id: Int,
        val api_upgrade_type: Int,
        val api_upgrade_level: Int,
        val api_drawing_count: Int,
        val api_catapult_count: Int,
        val api_report_count: Int,
        val api_sortno: Int
)

data class ApiMstMapinfo(
        val api_id: Int,
        val api_maparea_id: Int,
        val api_no: Int,
        val api_name: String,
        val api_level: Int,
        val api_opetext: String,
        val api_infotext: String,
        val api_item: List<Int>,
        val api_max_maphp: Any,
        val api_required_defeat_count: Any,
        val api_sally_flag: List<Int>
)

data class ApiMstShip(
        val api_id: Int,
        val api_sortno: Int,
        val api_name: String,
        val api_yomi: String,
        val api_stype: Int,
        val api_ctype: Int,
        val api_afterlv: Int,
        val api_aftershipid: String,
        val api_taik: List<Int>,
        val api_souk: List<Int>,
        val api_houg: List<Int>,
        val api_raig: List<Int>,
        val api_tyku: List<Int>,
        val api_luck: List<Int>,
        val api_soku: Int,
        val api_leng: Int,
        val api_slot_num: Int,
        val api_maxeq: List<Int>,
        val api_buildtime: Int,
        val api_broken: List<Int>,
        val api_powup: List<Int>,
        val api_backs: Int,
        val api_getmes: String,
        val api_afterfuel: Int,
        val api_afterbull: Int,
        val api_fuel_max: Int,
        val api_bull_max: Int,
        val api_voicef: Int
)

data class ApiMstFurniture(
        val api_id: Int,
        val api_type: Int,
        val api_no: Int,
        val api_title: String,
        val api_description: String,
        val api_rarity: Int,
        val api_price: Int,
        val api_saleflg: Int,
        val api_season: Int
)

data class ApiMstSlotitemEquiptype(
        val api_id: Int,
        val api_name: String,
        val api_show_flg: Int
)

data class ApiMstEquipExslotShip(
        val api_slotitem_id: Int,
        val api_ship_ids: List<Int>
)

data class ApiMstSlotitem(
        val api_id: Int,
        val api_sortno: Int,
        val api_name: String,
        val api_type: List<Int>,
        val api_taik: Int,
        val api_souk: Int,
        val api_houg: Int,
        val api_raig: Int,
        val api_soku: Int,
        val api_baku: Int,
        val api_tyku: Int,
        val api_tais: Int,
        val api_atap: Int,
        val api_houm: Int,
        val api_raim: Int,
        val api_houk: Int,
        val api_raik: Int,
        val api_bakk: Int,
        val api_saku: Int,
        val api_sakb: Int,
        val api_luck: Int,
        val api_leng: Int,
        val api_rare: Int,
        val api_broken: List<Int>,
        val api_info: String,
        val api_usebull: String
)

data class ApiMstConst(
        val api_boko_max_ships: ApiBokoMaxShips,
        val api_parallel_quest_max: ApiParallelQuestMax,
        val api_dpflag_quest: ApiDpflagQuest
)

data class ApiDpflagQuest(
        val api_string_value: String,
        val api_int_value: Int
)

data class ApiParallelQuestMax(
        val api_string_value: String,
        val api_int_value: Int
)

data class ApiBokoMaxShips(
        val api_string_value: String,
        val api_int_value: Int
)

data class ApiMstMission(
        val api_id: Int,
        val api_disp_no: String,
        val api_maparea_id: Int,
        val api_name: String,
        val api_details: String,
        val api_time: Int,
        val api_deck_num: Int,
        val api_difficulty: Int,
        val api_use_fuel: Double,
        val api_use_bull: Double,
        val api_win_item1: List<Int>,
        val api_win_item2: List<Int>,
        val api_return_flag: Int
)

data class ApiMstShipgraph(
        val api_id: Int,
        val api_sortno: Int,
        val api_filename: String,
        val api_version: List<String>,
        val api_boko_n: List<Int>,
        val api_boko_d: List<Int>,
        val api_kaisyu_n: List<Int>,
        val api_kaisyu_d: List<Int>,
        val api_kaizo_n: List<Int>,
        val api_kaizo_d: List<Int>,
        val api_map_n: List<Int>,
        val api_map_d: List<Int>,
        val api_ensyuf_n: List<Int>,
        val api_ensyuf_d: List<Int>,
        val api_ensyue_n: List<Int>,
        val api_battle_n: List<Int>,
        val api_battle_d: List<Int>,
        val api_weda: List<Int>,
        val api_wedb: List<Int>
)