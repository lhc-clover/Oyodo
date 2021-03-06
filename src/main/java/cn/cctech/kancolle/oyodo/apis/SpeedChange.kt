package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.managers.Dock
import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Resource
import cn.cctech.kancolle.oyodo.managers.Transform
import cn.cctech.kancolle.oyodo.utils.CONDITION_REPAIR
import kotlin.math.max

data class SpeedChange(
        val api_result: Int = 0,
        val api_result_msg: String = ""
) : JsonBean() {
    override fun process() {
        val repairDockId = params["api_ndock_id"]?.toInt() ?: 0
        val repairDock = try {
            Dock.repairList[repairDockId - 1]
        } catch (e: Exception) {
            null
        }
        repairDock?.value?.let {
            // ship recovery
            val ship = Fleet.shipMap[it.shipId]
            ship?.let {
                it.nowHp = it.maxHp
                it.condition = max(CONDITION_REPAIR, it.condition)
                Fleet.shipWatcher.onNext(Transform.Change(listOf(it.id)))
            }
            // clear ndock
            it.state = 1
            it.shipId = -1
            it.completeTime = -1L
            repairDock.onNext(it)
            // use bucket
            Resource.bucket.onNext(Resource.bucket.value - 1)
        }
    }
}