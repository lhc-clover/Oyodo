package cn.cctech.kancolle.oyodo.utils

import cn.cctech.kancolle.oyodo.apis.BattleResult
import cn.cctech.kancolle.oyodo.apis.MissionResult
import cn.cctech.kancolle.oyodo.apis.JsonBean
import cn.cctech.kancolle.oyodo.apis.PracticeResult
import cn.cctech.kancolle.oyodo.data.MissionRequireType
import cn.cctech.kancolle.oyodo.data.missionMap
import cn.cctech.kancolle.oyodo.entities.Quest
import cn.cctech.kancolle.oyodo.entities.Ship
import cn.cctech.kancolle.oyodo.managers.Mission
import kotlin.math.min

fun setQuestCounter(quest: Quest, bean: JsonBean) {
    val increment = missionMap[quest.id]?.processor?.invoke(bean) ?: 0
    quest.current = min(quest.max, quest.current + increment)
}

fun setMissionProgress(bean: JsonBean, type: MissionRequireType) {
    val questMap = Mission.questMap.value
    questMap.values.filter {
        it.type == type
    }.forEach {
        setQuestCounter(it, bean)
    }
    Mission.questMap.onNext(questMap)
}

fun isBattleWin(bean: BattleResult): Boolean {
    val rank = bean.api_data?.api_win_rank ?: ""
    return (rank == "S" || rank == "A" || rank == "B")
}

fun isPracticeWin(bean: PracticeResult): Boolean {
    val rank = bean.api_data?.api_win_rank ?: ""
    return (rank == "S" || rank == "A" || rank == "B")
}

fun isExpeditionSuccess(bean: MissionResult): Boolean {
    return bean.api_data?.api_clear_result == 1
}

fun isShipSink(ship: Ship): Boolean {
    return ship.hp() <= 0
}
