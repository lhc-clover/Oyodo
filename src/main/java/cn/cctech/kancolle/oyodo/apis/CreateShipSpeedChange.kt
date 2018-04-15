package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.managers.Dock

data class CreateShipSpeedChange(
        val api_result: Int = 0,
        val api_result_msg: String = ""
) : JsonBean() {
    override fun process() {
        val buildDockId = params["api_kdock_id"]?.toInt() ?: 0
        val buildDock = try {
            Dock.buildList[buildDockId - 1]
        } catch (e: Exception) {
            null
        }
        buildDock?.value?.let {
            it.completeTime = 0
            it.state = 3
            buildDock.onNext(it)
        }
    }
}