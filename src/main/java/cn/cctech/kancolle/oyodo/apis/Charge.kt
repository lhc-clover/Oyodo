package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Material

data class Charge(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: ChargeApiData = ChargeApiData()
) : JsonBean() {
    override fun process() {
        val fuel = api_data.api_material.getOrNull(0)
        fuel?.let { Material.fuel.onNext(it) }
        val ammo = api_data.api_material.getOrNull(1)
        ammo?.let { Material.ammo.onNext(it) }
        val metal = api_data.api_material.getOrNull(2)
        metal?.let { Material.metal.onNext(it) }
        val bauxite = api_data.api_material.getOrNull(3)
        bauxite?.let { Material.bauxite.onNext(it) }

        api_data.api_ship.forEach { chargeApiShip ->
            val ship = Fleet.shipMap[chargeApiShip.api_id]
            ship?.let {
                val entity = it.value
                entity.nowFuel = chargeApiShip.api_fuel
                entity.nowBullet = chargeApiShip.api_bull
                it.onNext(entity)
            }
        }
    }
}

data class ChargeApiData(
        val api_ship: List<ChargeApiShip> = listOf(),
        val api_material: List<Int> = listOf(),
        val api_use_bou: Int = 0
)

data class ChargeApiShip(
        val api_id: Int = 0,
        val api_fuel: Int = 0,
        val api_bull: Int = 0,
        val api_onslot: List<Int> = listOf()
)