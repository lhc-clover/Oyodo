package cn.cctech.kancolle.oyodo

import cn.cctech.kancolle.oyodo.apis.JsonBean
import cn.cctech.kancolle.oyodo.apis.Port
import cn.cctech.kancolle.oyodo.apis.RequireInfo
import cn.cctech.kancolle.oyodo.apis.Start
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import io.reactivex.subjects.Subject
import java.io.FileReader
import java.io.StringReader

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
        jsonReader.skip(7)
        val start = Gson().fromJson<Start>(jsonReader, object : TypeToken<Start>() {}.type)
        start.process()
        isStartInit = start.isInit()
    }

    @Throws(Exception::class)
    fun api(url: String, requestBody: ByteArray, responseBody: ByteArray) {
        if (!isStartInit) throw Exception("Please call 'init' first.")
        else {
            val type = when {
                url.endsWith("api_port/port") -> object : TypeToken<Port>() {}.type
                url.endsWith("api_get_member/require_info") -> object : TypeToken<RequireInfo>() {}.type
                else -> null
            }
            type?.let {
                val reader = JsonReader(StringReader(parseContent(responseBody)))
                reader.isLenient = true
                val apiBean = Gson().fromJson(reader, it) as JsonBean
                apiBean.setParams(String(requestBody))
                apiBean.process()
            }
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
