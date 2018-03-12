package cn.cctech.kancolle.oyodo.entities

import cn.cctech.kancolle.oyodo.apis.ApiMstSlotitem
import cn.cctech.kancolle.oyodo.apis.ApiSlotItem

class Slot {

    var name: String = ""
    var type: Int = 0 //分类
    var typeCalc: Int = 0 //计算分类
    var aac: Int = 0 //对空
    var mastery: Int = 0 //熟练度
    var level: Int = 0 //改修
    var scout: Int = 0 //索敌

    constructor(raw: ApiMstSlotitem?, port: ApiSlotItem?) {
        name = raw?.api_name ?: ""
        type = raw?.api_type?.get(3) ?: 0
        typeCalc = raw?.api_type?.get(2) ?: 0
        aac = raw?.api_tyku ?: 0
        scout = raw?.api_saku ?: 0
        mastery = port?.api_alv ?: 0
        level = port?.api_level ?: 0
    }
}
