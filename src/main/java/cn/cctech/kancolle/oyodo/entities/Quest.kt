package cn.cctech.kancolle.oyodo.entities

import cn.cctech.kancolle.oyodo.apis.QuestListBean
import com.google.gson.Gson
import com.google.gson.JsonElement

class Quest {

    var id: Int = 0
    var title: String = ""
    var state: Int = 0
    var category: Int = 0

    constructor(json: JsonElement) {
        val entity = Gson().fromJson(json, QuestListBean::class.java)
        id = entity?.api_no ?: 0
        title = entity?.api_title ?: ""
        state = entity?.api_state ?: 0
        category = entity?.api_category ?: 0
    }
}
