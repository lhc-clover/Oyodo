package cn.cctech.kancolle.oyodo.test

import cn.cctech.kancolle.oyodo.Oyodo
import cn.cctech.kancolle.oyodo.apis.*
import cn.cctech.kancolle.oyodo.managers.Dock
import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Resource
import cn.cctech.kancolle.oyodo.managers.User
import com.google.gson.reflect.TypeToken
import org.junit.BeforeClass
import org.junit.Test
import java.text.DateFormat
import java.util.*

class FleetEventTest {

    companion object {
        @BeforeClass
        @JvmStatic
        fun prepare() {
            val start = readApiFile<Start>("api_start2", object : TypeToken<Start>() {}.type)
            start.process()
            val requireInfo = readApiFile<RequireInfo>("require_info", object : TypeToken<RequireInfo>() {}.type)
            requireInfo.process()
//            val port = readApiFile<Port>("port", object : TypeToken<Port>() {}.type)
//            port.process()
        }
    }

    private fun preparePort(portFile: String) {
        val port = readApiFile<Port>(portFile, object : TypeToken<Port>() {}.type)
        port.process()
    }

    @Test
    fun slotExchangeIndex() {
        preparePort("port")
        val slotExchangeIndex = readApiFileWithParams<SlotExchangeIndex>("slot_exchange_index",
                object : TypeToken<SlotExchangeIndex>() {}.type)
        val shipId = slotExchangeIndex.params["api_id"]!!.toInt()
        println("Before == " + Fleet.shipMap[shipId]?.value?.items?.joinToString(" / "))
        slotExchangeIndex.process()
        println("After == " + Fleet.shipMap[shipId]?.value?.items?.joinToString(" / "))
    }

    @Test
    fun ship3() {
        preparePort("port")
        val ship3 = readApiFile<Ship3>("ship3", object : TypeToken<Ship3>() {}.type)
        ship3.api_data.api_ship_data.forEach {
            val ship = Fleet.shipMap[it.api_id]
            println("Before == " + ship?.value?.items)
        }
        ship3.process()
        ship3.api_data.api_ship_data.forEach {
            val ship = Fleet.shipMap[it.api_id]
            println("After == " + ship?.value?.items)
        }
    }

    @Test
    fun slotDeprive() {
        preparePort("port")
        val slotDeprive = readApiFile<SlotDeprive>("slot_deprive", object : TypeToken<SlotDeprive>() {}.type)
        val setShipId = slotDeprive.api_data.api_ship_data.api_set_ship.api_id
        val unsetShipId = slotDeprive.api_data.api_ship_data.api_unset_ship.api_id
        Fleet.shipMap[setShipId]?.let { Oyodo.attention().watch(it, { println("${it.name} : ${it.items}") }) }
        Fleet.shipMap[unsetShipId]?.let { Oyodo.attention().watch(it, { println("${it.name} : ${it.items}") }) }
        slotDeprive.process()
    }

    @Test
    fun createItem() {
        preparePort("port")
        Oyodo.attention().watch(User.slotCount, { println("Slot count : $it") })
        val createItem = readApiFile<CreateItem>("createitem", object : TypeToken<CreateItem>() {}.type)
        createItem.process()
    }

    @Test
    fun getShip() {
        preparePort("port")
        Oyodo.attention().watch(User.shipCount, { println("Ship count : $it") })
        Oyodo.attention().watch(User.slotCount, { println("Slot count : $it") })
        val getShip = readApiFile<GetShip>("getship", object : TypeToken<GetShip>() {}.type)
        getShip.process()
    }

    @Test
    fun powerUp() {
        preparePort("port")
        Oyodo.attention().watch(User.shipCount, { println("Ship count : $it") })
        Oyodo.attention().watch(User.slotCount, { println("Slot count : $it") })
        val powerUp = readApiFileWithParams<PowerUp>("powerup", object : TypeToken<PowerUp>() {}.type)
        Oyodo.attention().watch(Fleet.shipMap[powerUp.api_data.api_ship.api_id]!!, {
            println("HP : ${it.maxHp}")
        })
        powerUp.process()
    }

    @Test
    fun destroyShip() {
        preparePort("destroyship/port")
        Oyodo.attention().watch(User.shipCount, { println("Ship count : $it") })
        Oyodo.attention().watch(User.slotCount, { println("Slot count : $it") })
        Oyodo.attention().watch(Resource.fuel, { println("fuel : $it") })
        Oyodo.attention().watch(Resource.ammo, { println("ammo : $it") })
        Oyodo.attention().watch(Resource.metal, { println("metal : $it") })
        Oyodo.attention().watch(Resource.bauxite, { println("bauxite : $it") })
        val destroyShip = readApiFileWithParams<DestroyShip>("destroyship/destroyship", object : TypeToken<DestroyShip>() {}.type)
        destroyShip.process()
    }

    @Test
    fun destroyItem() {
        preparePort("destroyitem/port")
        Oyodo.attention().watch(User.slotCount, { println("Slot count : $it") })
        Oyodo.attention().watch(Resource.fuel, { println("fuel : $it") })
        Oyodo.attention().watch(Resource.ammo, { println("ammo : $it") })
        Oyodo.attention().watch(Resource.metal, { println("metal : $it") })
        Oyodo.attention().watch(Resource.bauxite, { println("bauxite : $it") })
        val destroyItem = readApiFileWithParams<DestroyItem>("destroyitem/destroyitem", object : TypeToken<DestroyItem>() {}.type)
        destroyItem.process()
    }

    @Test
    fun speedChange() {
        preparePort("port")
        val ndock = readApiFile<NDock>("speedchange/ndock", object : TypeToken<NDock>() {}.type)
        ndock.process()
        Oyodo.attention().watch(Resource.bucket, { println("Bucket : $it") })
        Oyodo.attention().watch(Fleet.shipMap[57]!!, { println("${it.name} | HP:${it.nowHp}/${it.maxHp} Cond:${it.condition}") })
        Dock.repairList.forEach {
            Oyodo.attention().watch(it, { println("Repair complete at : ${DateFormat.getDateTimeInstance().format(Date(it.completeTime))}") })
        }
        val speedChange = readApiFileWithParams<SpeedChange>("speedchange/speedchange", object : TypeToken<SpeedChange>() {}.type)
        speedChange.process()
    }

}
