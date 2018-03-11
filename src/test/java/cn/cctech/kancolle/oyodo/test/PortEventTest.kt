package cn.cctech.kancolle.oyodo.test

import cn.cctech.kancolle.oyodo.Oyodo
import cn.cctech.kancolle.oyodo.apis.Port
import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Material
import cn.cctech.kancolle.oyodo.managers.User
import com.google.gson.reflect.TypeToken
import org.junit.BeforeClass
import org.junit.Test

class PortEventTest {

    companion object {
        @BeforeClass
        @JvmStatic
        fun read() {
            val token = object : TypeToken<Port>() {}.type
            val port = readApiFile<Port>("port", token)
            port.process()
        }
    }

    @Test
    fun material() {
        Oyodo.attention().watch(Material.fuel, { System.out.println("fuel : $it") })
        Oyodo.attention().watch(Material.ammo, { System.out.println("ammo : $it") })
        Oyodo.attention().watch(Material.metal, { System.out.println("metal : $it") })
        Oyodo.attention().watch(Material.bauxite, { System.out.println("bauxite : $it") })
        Oyodo.attention().watch(Material.burner, { System.out.println("burner : $it") })
        Oyodo.attention().watch(Material.bucket, { System.out.println("bucket : $it") })
        Oyodo.attention().watch(Material.research, { System.out.println("research : $it") })
        Oyodo.attention().watch(Material.improve, { System.out.println("improve : $it") })
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
    }

    @Test
    fun dock() {
//        Dock.expeditionList.forEach { Oyodo.attention().watch(it, { System.out.println("nickname : ${it}") }) }
    }

}