package cn.cctech.kancolle.oyodo.entities

import cn.cctech.kancolle.oyodo.apis.KDockApiData

class Build {

    var id: Int = -1
    var state: Int = 1
    var shipId: Int = -1
    var completeTime: Long = -1L

    constructor(entity: KDockApiData?) {
        try {
            id = entity?.api_id ?: -1
            state = entity?.api_state ?: 1
            shipId = entity?.api_created_ship_id ?: -1
            val time = entity?.api_complete_time ?: -1L
            completeTime = if (state <= 0L || shipId <= 0) -1 else time
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun valid(): Boolean {
        return state > 0 && shipId > 0
    }

}
