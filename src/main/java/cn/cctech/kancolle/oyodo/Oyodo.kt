package cn.cctech.kancolle.oyodo

import cn.cctech.kancolle.oyodo.apis.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import io.reactivex.subjects.Subject
import java.io.FileReader
import java.io.StringReader
import java.lang.reflect.Type

class Oyodo {

    private var isStartInit = false

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

    fun init(startFilePath: String) {
        val jsonReader = FileReader(startFilePath)
        jsonReader.skip(7) // skip 'svdata='
        val start = Gson().fromJson<Start>(jsonReader, object : TypeToken<Start>() {}.type)
        start.process()
        isStartInit = start.isInit()
    }

    @Throws(Exception::class)
    fun api(url: String, requestBody: ByteArray, responseBody: ByteArray) {
        if (!isStartInit) throw Exception("Please call 'init' first.")
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
            url.endsWith("api_req_hensei/change") -> object : TypeToken<KDock>() {}.type
            url.endsWith("api_req_hokyu/charge") -> object : TypeToken<Charge>() {}.type
            url.endsWith("api_req_kaisou/slot_exchange_index") -> object : TypeToken<SlotExchangeIndex>() {}.type
            url.endsWith("api_get_member/ship3") -> object : TypeToken<Ship3>() {}.type
            url.endsWith("api_req_kaisou/slot_deprive") -> object : TypeToken<SlotDeprive>() {}.type
            else -> null
        }
    }

    private fun parseContent(content: ByteArray): String {
        val result: String
        val tmp = String(content)
        result = tmp.replaceFirst("svdata=".toRegex(), "")
        return result
    }

    fun <T> watch(data: Subject<T>, watcher: (T) -> Unit) {
        data.subscribe { watcher(it) }
    }

    fun <T> watch(data: Subject<T>, watcher: Watcher<T>) {
        data.subscribe { watcher.onChange(it) }
    }

}
