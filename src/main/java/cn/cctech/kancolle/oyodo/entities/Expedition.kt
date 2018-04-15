package cn.cctech.kancolle.oyodo.entities

import cn.cctech.kancolle.oyodo.apis.ApiDeckPort
import cn.cctech.kancolle.oyodo.apis.DeckApiData

class Expedition {

    var missionId: String = "0"
    var fleetIndex: Int = -1
    var returnTime: Long = -1

    constructor(entity: ApiDeckPort?) {
        try {
            fleetIndex = entity!!.api_id
            missionId = entity.api_mission[1]
            val time = java.lang.Long.parseLong(entity.api_mission[2])
            returnTime = if (time == 0L) -1 else time
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    constructor(entity: DeckApiData?) {
        try {
            fleetIndex = entity!!.api_id
            missionId = entity.api_mission!![1]
            val time = java.lang.Long.parseLong(entity.api_mission[2])
            returnTime = if (time == 0L) -1 else time
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun valid(): Boolean {
        return missionId != "0" && fleetIndex >= 0
    }

}
