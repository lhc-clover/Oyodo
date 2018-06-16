package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.data.MissionRequireType
import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Resource
import cn.cctech.kancolle.oyodo.managers.Transform
import cn.cctech.kancolle.oyodo.utils.setMissionProgress
import kotlin.math.max

data class NyukyoStart(
        val api_result: Int = 0,
        val api_result_msg: String = ""
) : JsonBean() {
    override fun process() {
        val useBucket = params["api_highspeed"]?.toInt() ?: 0 == 1
        if (useBucket) {
            val ship = try {
                val shipId = params["api_ship_id"]?.toInt() ?: -1
                Fleet.shipMap[shipId]
            } catch (e: Exception) {
                null
            }
            ship?.let {
                it.nowHp = it.maxHp
                it.condition = max(40, it.condition)
                Fleet.shipWatcher.onNext(Transform.Change(listOf(it.id)))

            }
            Resource.bucket.onNext(Resource.bucket.value - 1)

        }

        setMissionProgress(this, MissionRequireType.REPAIR)
    }
}