package cn.cctech.kancolle.oyodo.managers

import cn.cctech.kancolle.oyodo.entities.Quest
import io.reactivex.subjects.BehaviorSubject
import java.util.*

object Mission : IManager() {

    val questMap: BehaviorSubject<MutableMap<Int, Quest>> = BehaviorSubject.createDefault(mutableMapOf())
    private var lastUpdate = getNowTime()

    fun isSameDay(): Boolean {
        val now = getNowTime()
        val result = now.get(Calendar.ERA) == lastUpdate.get(Calendar.ERA)
                && now.get(Calendar.YEAR) == lastUpdate.get(Calendar.YEAR)
                && now.get(Calendar.DAY_OF_YEAR) == lastUpdate.get(Calendar.DAY_OF_YEAR)
        lastUpdate = now
        return result
    }

    private fun getNowTime(): Calendar {
        return Calendar.getInstance(TimeZone.getTimeZone("GMT+09:00"))
    }
}