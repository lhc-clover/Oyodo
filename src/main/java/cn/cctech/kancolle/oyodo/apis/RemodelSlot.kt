package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.data.MissionRequireType
import cn.cctech.kancolle.oyodo.utils.setMissionProgress

data class RemodelSlot(
        val api_result: Int? = 0,
        val api_result_msg: String? = ""
) : JsonBean() {
    override fun process() {
        setMissionProgress(this, MissionRequireType.REMODEL_SLOT)
    }
}