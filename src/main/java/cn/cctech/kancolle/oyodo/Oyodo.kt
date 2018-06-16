package cn.cctech.kancolle.oyodo

import cn.cctech.kancolle.oyodo.apis.*
import cn.cctech.kancolle.oyodo.managers.Raw
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.Subject
import java.io.FileReader
import java.io.StringReader
import java.lang.reflect.Type

@Suppress("unused", "MemberVisibilityCanBePrivate")
class Oyodo {

    private var startFilePath: String? = ""

    companion object {

        @Volatile
        private var singleInstance: Oyodo? = null

        fun attention(): Oyodo {
            var instance = singleInstance
            if (instance == null) {
                synchronized(Oyodo::class.java, {
                    instance = singleInstance
                    if (instance == null) {
                        instance = Oyodo()
                        singleInstance = instance
                    }
                })
            }
            return instance!!
        }
    }

    fun init(path: String) {
        startFilePath = path
    }

    @Throws(Exception::class)
    fun checkStart(): Boolean {
        if (startFilePath.isNullOrEmpty()) throw Exception("|api_start2| file not set yet. Please call 'init' first.")
        var isInit = Raw.rawShipMap.size > 0 && Raw.rawSlotMap.size > 0
        if (!isInit) {
            readStartFile()
            isInit = Raw.rawShipMap.size > 0 && Raw.rawSlotMap.size > 0
        }
        return isInit
    }

    private fun readStartFile() {
        val jsonReader = FileReader(startFilePath)
        jsonReader.skip(7) // skip 'svdata='
        val start = Gson().fromJson<Start>(jsonReader, object : TypeToken<Start>() {}.type)
        start.process()
    }

    @Throws(Exception::class)
    fun api(url: String, requestBody: ByteArray, responseBody: ByteArray) {
        if (!checkStart()) throw Exception("|api_start2| file not set yet. Please call 'init' first.")
        else {
            getTypeByUrl(url)?.let {
                val reader = JsonReader(StringReader(parseContent(responseBody)))
                reader.isLenient = true
                val apiBean = Gson().fromJson(reader, it) as JsonBean
                apiBean.setParams(String(requestBody))
                apiBean.process()
            }
        }
    }

    private fun getTypeByUrl(url: String): Type? {
        return when {
            url.endsWith("api_port/port") -> object : TypeToken<Port>() {}.type
            url.endsWith("api_get_member/require_info") -> object : TypeToken<RequireInfo>() {}.type
            url.endsWith("api_get_member/deck") -> object : TypeToken<Deck>() {}.type
            url.endsWith("api_get_member/ndock") -> object : TypeToken<NDock>() {}.type
            url.endsWith("api_get_member/kdock") -> object : TypeToken<KDock>() {}.type
            url.endsWith("api_req_mission/result") -> object : TypeToken<MissionResult>() {}.type
            url.endsWith("api_req_nyukyo/start") -> object : TypeToken<NyukyoStart>() {}.type
            url.endsWith("api_req_hensei/change") -> object : TypeToken<Change>() {}.type
            url.endsWith("api_req_hokyu/charge") -> object : TypeToken<Charge>() {}.type
            url.endsWith("api_req_kaisou/slot_exchange_index") -> object : TypeToken<SlotExchangeIndex>() {}.type
            url.endsWith("api_get_member/ship3") -> object : TypeToken<Ship3>() {}.type
            url.endsWith("api_req_kaisou/slot_deprive") -> object : TypeToken<SlotDeprive>() {}.type
            url.endsWith("api_req_kousyou/createitem") -> object : TypeToken<CreateItem>() {}.type
            url.endsWith("api_req_kousyou/createship") -> object : TypeToken<CreateShip>() {}.type
            url.endsWith("api_get_member/slot_item") -> object : TypeToken<SlotItem>() {}.type
            url.endsWith("api_req_kousyou/getship") -> object : TypeToken<GetShip>() {}.type
            url.endsWith("api_req_kousyou/destroyship") -> object : TypeToken<DestroyShip>() {}.type
            url.endsWith("api_req_kousyou/destroyitem2") -> object : TypeToken<DestroyItem>() {}.type
            url.endsWith("api_req_kousyou/remodel_slot") -> object : TypeToken<RemodelSlot>() {}.type
            url.endsWith("api_get_member/material") -> object : TypeToken<Material>() {}.type
            url.endsWith("api_req_nyukyo/speedchange") -> object : TypeToken<SpeedChange>() {}.type
            url.endsWith("api_req_kaisou/powerup") -> object : TypeToken<PowerUp>() {}.type
            url.endsWith("api_req_kousyou/createship_speedchange") -> object : TypeToken<CreateShipSpeedChange>() {}.type
            url.endsWith("api_get_member/questlist") -> object : TypeToken<QuestList>() {}.type
            url.endsWith("api_req_quest/clearitemget") -> object : TypeToken<QuestClear>() {}.type
            url.endsWith("api_req_map/start") -> object : TypeToken<BattleStart>() {}.type
            url.endsWith("api_req_map/next") -> object : TypeToken<BattleNext>() {}.type
            url.endsWith("api_req_sortie/battle") -> object : TypeToken<BattleDaytime>() {}.type
            url.endsWith("api_req_battle_midnight/battle") -> object : TypeToken<BattleNight>() {}.type
            url.endsWith("api_req_battle_midnight/sp_midnight") -> object : TypeToken<BattleNightSp>() {}.type
            url.endsWith("api_req_sortie/battleresult") -> object : TypeToken<BattleResult>() {}.type
            url.endsWith("api_req_practice/battle") -> object : TypeToken<Practice>() {}.type
            url.endsWith("api_req_practice/midnight_battle") -> object : TypeToken<PracticeNight>() {}.type
            url.endsWith("api_req_practice/battle_result") -> object : TypeToken<PracticeResult>() {}.type
            else -> null
        }
    }

    private fun parseContent(content: ByteArray): String {
        val result: String
        val tmp = String(content)
        result = tmp.replaceFirst("svdata=".toRegex(), "")
        return result
    }

    fun <T> watch(data: Subject<T>, watcher: (T) -> Unit): Disposable? {
        return data.subscribe { watcher(it) }
    }

    fun <T> watch(data: Subject<T>, watcher: Watcher<T>): Disposable? {
        return data.subscribe { watcher.onChange(it) }
    }

}
