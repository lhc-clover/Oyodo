package cn.cctech.kancolle.oyodo

import io.reactivex.subjects.Subject

class Oyodo {

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

    fun api(url: String, requestBody: ByteArray, responseBody: ByteArray) {

    }

    fun <T> watch(data: Subject<T>, watcher: (T) -> Unit) {
        data.subscribe { watcher(it) }
    }

    fun <T> watch(data: Subject<T>, watcher: Watcher<T>) {
        data.subscribe { watcher.onChange(it) }
    }

}
