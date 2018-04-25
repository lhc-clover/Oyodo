package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.managers.Battle

data class BattleNext(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: BattleNextApiData? = BattleNextApiData()
) : JsonBean() {
    override fun process() {
        Battle.route = api_data?.api_no ?: -1
        Battle.nodeType = api_data?.api_color_no ?: -1
        Battle.friendFormation = -1
        Battle.enemyFormation = -1
        Battle.enemyList.clear()
        Battle.airCommand = -1
        Battle.heading = -1
        Battle.rank = ""
        Battle.get = ""

        Battle.finishBattle()

        Battle.phaseShift(Battle.Phase.Next)
    }
}

data class BattleNextApiData(
        val api_rashin_flg: Int = 0,
        val api_rashin_id: Int = 0,
        val api_maparea_id: Int = 0,
        val api_mapinfo_no: Int = 0,
        val api_no: Int = 0,
        val api_color_no: Int = 0,
        val api_event_id: Int = 0,
        val api_event_kind: Int = 0,
        val api_next: Int = 0,
        val api_bosscell_no: Int = 0,
        val api_bosscomp: Int = 0,
        val api_comment_kind: Int = 0,
        val api_production_kind: Int = 0,
        val api_airsearch: ApiAirsearch? = ApiAirsearch()
)
