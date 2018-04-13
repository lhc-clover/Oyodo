package cn.cctech.kancolle.oyodo.entities

import cn.cctech.kancolle.oyodo.apis.ApiMstSlotitem
import cn.cctech.kancolle.oyodo.apis.ApiSlotItem
import cn.cctech.kancolle.oyodo.utils.*

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

    fun calcLevelAAC(): Double {
        return when (typeCalc) {
            FIGHTER -> aac + 0.2 * level
            BOMBER, JET_BOMBER -> if (aac > 0) {
                aac + 0.25 * level
            } else {
                0.0
            }
            else -> aac.toDouble()
        }
    }

    fun calcMasteryAAC(mode: Int): DoubleArray {
        var minMastery = mastery
        if (mode == 1) {
            minMastery = 0
        }
        val rangeAAC = doubleArrayOf(0.0, 0.0)
        when (typeCalc) {
            FIGHTER, SEA_FIGHTER -> {
                rangeAAC[0] += kFighterMasteryBonus[minMastery].toDouble()
                rangeAAC[1] += kFighterMasteryBonus[mastery].toDouble()
                rangeAAC[0] += Math.sqrt(kBasicMasteryMinBonus[minMastery] / 10.0)
                rangeAAC[1] += Math.sqrt(kBasicMasteryMaxBonus[mastery] / 10.0)
            }
            BOMBER, TORPEDO_BOMBER, JET_BOMBER -> {
                rangeAAC[0] += Math.sqrt(kBasicMasteryMinBonus[minMastery] / 10.0)
                rangeAAC[1] += Math.sqrt(kBasicMasteryMaxBonus[mastery] / 10.0)
            }
            SEA_BOMBER -> {
                rangeAAC[0] += kSeaBomberMasteryBonus[minMastery].toDouble()
                rangeAAC[1] += kSeaBomberMasteryBonus[mastery].toDouble()
                rangeAAC[0] += Math.sqrt(kBasicMasteryMinBonus[minMastery] / 10.0)
                rangeAAC[1] += Math.sqrt(kBasicMasteryMaxBonus[mastery] / 10.0)
            }
            SEA_SCOUT, SCOUT -> {
            }
            else -> {
            }
        }
        return rangeAAC
    }

    fun calcScout(): Double {
        return when (typeCalc) {
            TORPEDO_BOMBER -> 0.8 * scout
            SCOUT -> 1.0 * scout
            SCOUT_II -> 1.0 * scout
            SEA_SCOUT -> 1.2 * (scout + 1.2 * Math.sqrt(level.toDouble()))
            SEA_BOMBER -> 1.1 * scout
            RADAR_LARGE -> 0.6 * (scout + 1.4 * Math.sqrt(level.toDouble()))
            RADAR_SMALL -> 0.6 * (scout + 1.25 * Math.sqrt(level.toDouble()))
            else -> 0.6 * scout
        }
    }

}
