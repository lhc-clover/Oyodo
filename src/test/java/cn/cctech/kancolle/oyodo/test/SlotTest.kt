package cn.cctech.kancolle.oyodo.test

import cn.cctech.kancolle.oyodo.Oyodo
import cn.cctech.kancolle.oyodo.apis.RequireInfo
import cn.cctech.kancolle.oyodo.apis.SlotItem
import cn.cctech.kancolle.oyodo.apis.Start
import cn.cctech.kancolle.oyodo.managers.Fleet
import com.google.gson.reflect.TypeToken
import org.junit.BeforeClass
import org.junit.Test

class SlotTest {

    companion object {
        @BeforeClass
        @JvmStatic
        fun prepare() {
            val start = readApiFile<Start>("api_start2", object : TypeToken<Start>() {}.type)
            start.process()
            val requireInfo = readApiFile<RequireInfo>("require_info", object : TypeToken<RequireInfo>() {}.type)
            requireInfo.process()
        }
    }

    @Test
    fun portSlotMap() {
        Oyodo.attention().watch(Fleet.slotWatcher, {
            println("Transform $it")
            println("Count ${Fleet.slotMap.count()}")
        })
        val slotItem = readApiFile<SlotItem>("slot_item", object : TypeToken<SlotItem>() {}.type)
        slotItem.process()
    }
}