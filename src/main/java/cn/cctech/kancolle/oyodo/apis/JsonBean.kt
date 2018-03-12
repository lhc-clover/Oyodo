package cn.cctech.kancolle.oyodo.apis

abstract class JsonBean {

    private var params = mutableMapOf<String, String>()

    abstract fun process()

    fun setParams(form: String) {
        form.split("&")
                .map {
                    it.replace("%5F", "_").replace("%2D", "-").split("=")
                }
                .forEach {
                    params[it[0]] = it[1]
                }
    }
}
