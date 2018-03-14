package cn.cctech.kancolle.oyodo.test

import cn.cctech.kancolle.oyodo.apis.*
import cn.cctech.kancolle.oyodo.managers.Dock
import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Material
import cn.cctech.kancolle.oyodo.managers.Raw
import com.google.gson.reflect.TypeToken
import org.junit.BeforeClass
import org.junit.Test
import java.util.*

class DockEventTest {

    companion object {
        @BeforeClass
        @JvmStatic
        fun prepare() {
            val start = readApiFile<Start>("api_start2", object : TypeToken<Start>() {}.type)
            start.process()
            val requireInfo = readApiFile<RequireInfo>("require_info", object : TypeToken<RequireInfo>() {}.type)
            requireInfo.process()
            val port = readApiFile<Port>("port", object : TypeToken<Port>() {}.type)
            port.process()
        }
    }

    @Test
    fun repair() {
        val ndock = readApiFile<NDock>("ndock", object : TypeToken<NDock>() {}.type)
        ndock.process()
        Dock.repairList.forEach {
            val ship = Fleet.shipMap[it.value.shipId]?.value
            val time = Date(it.value.completeTime)
            ship?.let { System.out.println("${it.name} will finish repair at $time") }
        }
    }

    @Test
    fun build() {
        val kdock = readApiFile<KDock>("kdock", object : TypeToken<KDock>() {}.type)
        kdock.process()
        Dock.buildList.forEach {
            val rawShip = Raw.rawShipMap[it.value.shipId]
            val time = Date(it.value.completeTime)
            rawShip?.let { System.out.println("${it.api_name} will finish build at $time") }
        }
    }

    @Test
    fun change() {
        System.out.println("Fleet 1 is ${Fleet.deckShipIds[0].value}")
        System.out.println("Fleet 2 is ${Fleet.deckShipIds[1].value}")
        val change = readApiFileWithParams<Change>("change", object : TypeToken<Change>() {}.type)
        change.process()
        System.out.println("----------change----------")
        System.out.println("Fleet 1 is ${Fleet.deckShipIds[0].value}")
        System.out.println("Fleet 2 is ${Fleet.deckShipIds[1].value}")
        val changeAdd = readApiFileWithParams<Change>("change_add", object : TypeToken<Change>() {}.type)
        changeAdd.process()
        System.out.println("----------change_add----------")
        System.out.println("Fleet 1 is ${Fleet.deckShipIds[0].value}")
        System.out.println("Fleet 2 is ${Fleet.deckShipIds[1].value}")
        val changeRemove = readApiFileWithParams<Change>("change_remove", object : TypeToken<Change>() {}.type)
        changeRemove.process()
        System.out.println("----------change_remove----------")
        System.out.println("Fleet 1 is ${Fleet.deckShipIds[0].value}")
        System.out.println("Fleet 2 is ${Fleet.deckShipIds[1].value}")
        val changeClear = readApiFileWithParams<Change>("change_clear", object : TypeToken<Change>() {}.type)
        changeClear.process()
        System.out.println("----------change_clear----------")
        System.out.println("Fleet 1 is ${Fleet.deckShipIds[0].value}")
        System.out.println("Fleet 2 is ${Fleet.deckShipIds[1].value}")
    }

    @Test
    fun charge() {
        System.out.println("Before == fuel ${Material.fuel.value} ammo ${Material.ammo.value} metal ${Material.metal.value} bauxite ${Material.bauxite.value}")
        val charge = readApiFile<Charge>("charge", object : TypeToken<Charge>() {}.type)
        charge.api_data.api_ship.forEach {
            val ship = Fleet.shipMap[it.api_id]?.value
            println("Before == ${ship?.name} @ ${ship?.nowFuel}/${ship?.maxFuel} -- ${ship?.nowBullet}/${ship?.maxBullet}")
        }
        charge.process()
        System.out.println("After == fuel ${Material.fuel.value} ammo ${Material.ammo.value} metal ${Material.metal.value} bauxite ${Material.bauxite.value}")
        charge.api_data.api_ship.forEach {
            val ship = Fleet.shipMap[it.api_id]?.value
            println("After == ${ship?.name} @ ${ship?.nowFuel}/${ship?.maxFuel} -- ${ship?.nowBullet}/${ship?.maxBullet}")
        }
    }

}
