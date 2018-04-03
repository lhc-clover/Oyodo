package cn.cctech.kancolle.oyodo.test

import cn.cctech.kancolle.oyodo.Oyodo
import cn.cctech.kancolle.oyodo.apis.Port
import cn.cctech.kancolle.oyodo.apis.QuestList
import cn.cctech.kancolle.oyodo.apis.RequireInfo
import cn.cctech.kancolle.oyodo.apis.Start
import cn.cctech.kancolle.oyodo.managers.*
import com.google.gson.reflect.TypeToken
import org.junit.BeforeClass
import org.junit.Test


class PortEventTest {

    companion object {
        @BeforeClass
        @JvmStatic
        fun read() {
            val start = readApiFile<Start>("api_start2", object : TypeToken<Start>() {}.type)
            start.process()
            val requireInfo = readApiFile<RequireInfo>("require_info", object : TypeToken<RequireInfo>() {}.type)
            requireInfo.process()
            val port = readApiFile<Port>("port", object : TypeToken<Port>() {}.type)
            port.process()
        }
    }

    @Test
    fun material() {
        Oyodo.attention().watch(Resource.fuel, { System.out.println("fuel : $it") })
        Oyodo.attention().watch(Resource.ammo, { System.out.println("ammo : $it") })
        Oyodo.attention().watch(Resource.metal, { System.out.println("metal : $it") })
        Oyodo.attention().watch(Resource.bauxite, { System.out.println("bauxite : $it") })
        Oyodo.attention().watch(Resource.burner, { System.out.println("burner : $it") })
        Oyodo.attention().watch(Resource.bucket, { System.out.println("bucket : $it") })
        Oyodo.attention().watch(Resource.research, { System.out.println("research : $it") })
        Oyodo.attention().watch(Resource.improve, { System.out.println("improve : $it") })
    }

    @Test
    fun user() {
        Oyodo.attention().watch(User.nickname, { System.out.println("nickname : $it") })
        Oyodo.attention().watch(User.level, { System.out.println("level : $it") })
        Oyodo.attention().watch(User.shipMax, { System.out.println("shipMax : $it") })
        Oyodo.attention().watch(User.slotMax, { System.out.println("slotMax : $it") })
        Oyodo.attention().watch(User.kDockCount, { System.out.println("kDockCount : $it") })
        Oyodo.attention().watch(User.nDockCount, { System.out.println("nDockCount : $it") })
        Oyodo.attention().watch(User.deckCount, { System.out.println("deckCount : $it") })
    }

    @Test
    fun fleet() {
        Fleet.deckNames.forEach { Oyodo.attention().watch(it, { System.out.println("fleetName : $it") }) }
        Fleet.deckShipIds.forEach { Oyodo.attention().watch(it, { System.out.println("shipIds : $it") }) }
        System.out.println("Port ship count : ${Fleet.shipMap.size}")
        System.out.println("Port slot count : ${Fleet.slotMap.size}")
    }

    @Test
    fun dock() {
        Dock.expeditionList.forEach { Oyodo.attention().watch(it, { System.out.println("Expedition : ${it.fleetIndex} to ${it.missionId}") }) }
        Dock.repairList.forEach {
            Oyodo.attention().watch(it, {
                val ship = Fleet.shipMap[it.shipId]?.value
                System.out.println("Repair : ${ship?.name}")
            })
        }
    }

    @Test
    fun raw() {
        System.out.println("Raw ship count : ${Raw.rawShipMap.size}")
        System.out.println("Raw slot count : ${Raw.rawSlotMap.size}")
    }

    @Test
    fun questList() {
        Oyodo.attention().watch(Mission.questMap, {
            println("Size : ${it.size}")
            it.filter { it.value.state >= 2 }.forEach { _, quest ->
                println(quest.title)
            }
        })
        val questList = readApiFileWithParams<QuestList>("questlist", object : TypeToken<QuestList>() {}.type)
        questList.process()
    }

}