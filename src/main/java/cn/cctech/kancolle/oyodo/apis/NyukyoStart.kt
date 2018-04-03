package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Resource
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
                val entity = it.value
                entity.nowHp = entity.maxHp
                entity.condition = max(40, entity.condition)
                it.onNext(entity)
            }
            Resource.bucket.onNext(Resource.bucket.value - 1)
        }

    }
}