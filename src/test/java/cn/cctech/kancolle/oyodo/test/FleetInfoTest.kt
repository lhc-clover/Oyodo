package cn.cctech.kancolle.oyodo.test

import cn.cctech.kancolle.oyodo.Oyodo
import cn.cctech.kancolle.oyodo.apis.Port
import cn.cctech.kancolle.oyodo.apis.RequireInfo
import cn.cctech.kancolle.oyodo.apis.SlotDeprive
import cn.cctech.kancolle.oyodo.apis.Start
import cn.cctech.kancolle.oyodo.managers.*
import com.google.gson.reflect.TypeToken
import org.junit.BeforeClass
import org.junit.Test

class FleetInfoTest {

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
    fun fleetList() {
        Oyodo.attention().watch(Fleet.deckShipIds[0], { ids ->
            println("ids : $ids")
            val fleet = ids.map {
                val ship = Fleet.shipMap[it]
                ship
            }
            fleet.forEach { println(it?.name) }
        })

        val port = readApiFile<Port>("port", object : TypeToken<Port>() {}.type)
        port.process()
    }

    @Test
    fun fleetWatcher() {
        val port = readApiFile<Port>("port", object : TypeToken<Port>() {}.type)
        port.process()
        readApiFile<SlotDeprive>("slot_deprive", object : TypeToken<SlotDeprive>() {}.type).process()
        Oyodo.attention().watch(Fleet.shipWatcher, {
            when (it) {
                is Transform.All -> println("All")
                is Transform.Change -> println("Change")
                is Transform.Remove -> println("Remove")
                is Transform.Add -> println("Add")
            }
        })
        readApiFile<SlotDeprive>("slot_deprive", object : TypeToken<SlotDeprive>() {}.type).process()
    }

    @Test
    fun fleetHeader() {
        val port = readApiFile<Port>("fleet/port_for_fleet_info", object : TypeToken<Port>() {}.type)
        port.process()
        println("Fleet 1")
        getShips(0).forEach { println("${it.name} : ${it.items.forEach { print("${Fleet.slotMap[it]?.name}/") }} : ${it.carrys}") }
        println("Level : " + getFleetLevel(0))
        println("AirPower : " + getFleetAirPower(0))
        println("Fleet 2")
        getShips(1).forEach { println(it.name) }
        println("Level : " + getFleetLevel(1))
        println("Fleet 3")
        getShips(2).forEach { println(it.name) }
        println("Level : " + getFleetLevel(2))
        println("Fleet 4")
        getShips(3).forEach { println(it.name) }
        println("Level : " + getFleetLevel(3))
    }

}
