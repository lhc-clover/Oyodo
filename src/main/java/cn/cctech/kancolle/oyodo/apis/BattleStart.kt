package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.managers.Battle

data class BattleStart(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: BattleStartApiData? = BattleStartApiData()
) : JsonBean() {
    override fun process() {
        Battle.area = api_data?.api_maparea_id ?: -1
        Battle.map = api_data?.api_mapinfo_no ?: -1
//        Battle.node = api_data?.api_from_no ?: -1
        Battle.route = api_data?.api_no ?: -1
        Battle.nodeType = api_data?.api_color_no ?: -1
//        val fleet = (params["api_deck_id"]?.toInt() ?: 0) - 1
//        Battle.friendList = Fleet.getShips(fleet)
        Battle.friendIndex = (params["api_deck_id"]?.toInt() ?: 0) - 1
        Battle.phaseShift(Battle.Phase.Start)
    }
}

data class BattleStartApiData(
        val api_cell_data: List<ApiCellData> = listOf(),
        val api_rashin_flg: Int = 0,
        val api_rashin_id: Int = 0,
        val api_maparea_id: Int = -1,
        val api_mapinfo_no: Int = -1,
        val api_no: Int = -1,
        val api_color_no: Int = 0,
        val api_event_id: Int = 0,
        val api_event_kind: Int = 0,
        val api_next: Int = 0,
        val api_bosscell_no: Int = 0,
        val api_bosscomp: Int = 0,
        val api_airsearch: ApiAirsearch? = ApiAirsearch(),
        val api_from_no: Int = -1
)

data class ApiCellData(
        val api_id: Int = 0,
        val api_no: Int = 0,
        val api_color_no: Int = 0,
        val api_passed: Int = 0
)

data class ApiAirsearch(
        val api_plane_type: Int = 0,
        val api_result: Int = 0
)