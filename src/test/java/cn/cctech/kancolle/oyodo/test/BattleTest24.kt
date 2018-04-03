package cn.cctech.kancolle.oyodo.test

import cn.cctech.kancolle.oyodo.Oyodo
import cn.cctech.kancolle.oyodo.apis.*
import cn.cctech.kancolle.oyodo.managers.Battle
import com.google.gson.reflect.TypeToken
import org.junit.BeforeClass
import org.junit.Test

class BattleTest24 {

    companion object {
        @BeforeClass
        @JvmStatic
        fun read() {
            val start = readApiFile<Start>("api_start2", object : TypeToken<Start>() {}.type)
            start.process()
            val requireInfo = readApiFile<RequireInfo>("battle/2-4-require_info", object : TypeToken<RequireInfo>() {}.type)
            requireInfo.process()
            val port = readApiFile<Port>("battle/2-4-port", object : TypeToken<Port>() {}.type)
            port.process()
        }
    }

    @Test
    fun battle() {
        Oyodo.attention().watch(Battle.phase, {
            when (it) {
                Battle.Phase.Idle -> println("idle")
                Battle.Phase.Start -> println("${Battle.area}-${Battle.map}-${Battle.route}")
                Battle.Phase.BattleDaytime -> {
                    Battle.friendList.forEach { println("${it.value.name} : ${it.value.hp()}/${it.value.maxHp}(-${it.value.damage.sum()}) ${it.value.damage}") }
                    Battle.enemyList.forEach { println("${it.name} : ${it.hp()}/${it.maxHp}(-${it.damage.sum()}) ${it.damage}") }
                }
                else -> println("unknown")
            }
        })
        val battleStart = readApiFileWithParams<BattleStart>("battle/2-4-start", object : TypeToken<BattleStart>() {}.type)
        battleStart.process()
        println("========== Battle A ==========")
        val battleA = readApiFileWithParams<BattleDaytime>("battle/2-4-A-battle", object : TypeToken<BattleDaytime>() {}.type)
        battleA.process()
        println("========== Battle L ==========")
        val battleL = readApiFileWithParams<BattleDaytime>("battle/2-4-L-battle", object : TypeToken<BattleDaytime>() {}.type)
        battleL.process()
        println("========== Battle N ==========")
        val battleN = readApiFileWithParams<BattleDaytime>("battle/2-4-N-battle", object : TypeToken<BattleDaytime>() {}.type)
        battleN.process()
    }

}