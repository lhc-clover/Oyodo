package cn.cctech.kancolle.oyodo.entities

import cn.cctech.kancolle.oyodo.apis.ApiNdock

class Repair {

    var id: Int = -1
    var state: Int = 1
    var shipId: Int = -1
    var completeTime: Long = -1L

    constructor(entity: ApiNdock?) {
        try {
            id = entity?.api_id ?: -1
            state = entity?.api_state ?: 1
            shipId = entity?.api_ship_id ?: -1
            completeTime = entity?.api_complete_time ?: -1L
            val time = entity?.api_complete_time ?: -1L
            completeTime = if (state <= 0L || shipId <= 0) -1 else time
        } catch (e: Exception) {
            completeTime = -1
        }
    }

    fun valid(): Boolean {
        return state > 0 && shipId > 0
    }

}
