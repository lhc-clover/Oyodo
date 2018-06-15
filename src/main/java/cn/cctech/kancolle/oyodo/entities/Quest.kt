package cn.cctech.kancolle.oyodo.entities

import cn.cctech.kancolle.oyodo.apis.QuestListBean
import cn.cctech.kancolle.oyodo.data.MissionRequireType
import cn.cctech.kancolle.oyodo.data.missionMap
import kotlin.math.ceil
import kotlin.math.floor

class Quest(bean: QuestListBean?) {

    var id: Int = 0
    var title: String = ""
    var state: Int = 0
    var category: Int = 0
    var current: Int = 0
    var max: Int = 0
    var description: String = ""
    var type: MissionRequireType = MissionRequireType.NONE

    init {
        setup(bean)
    }

    fun setup(bean: QuestListBean?) {
        id = bean?.api_no ?: 0
        title = bean?.api_title ?: ""
        state = bean?.api_state ?: 0
        category = bean?.api_category ?: 0
        missionMap[id]?.let {
            max = it.require
            description = it.description
            type = it.type
            if (bean?.api_state == 3) current = max
            when (bean?.api_progress_flag) {
                1 -> current = ceil(max / 2.0).toInt()
                2 -> current = floor(max * 0.8).toInt()
            }
        }
    }

}
