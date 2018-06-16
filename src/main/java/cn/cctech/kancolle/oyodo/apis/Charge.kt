package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.data.MissionRequireType
import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Resource
import cn.cctech.kancolle.oyodo.managers.Transform
import cn.cctech.kancolle.oyodo.utils.setMissionProgress

data class Charge(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: ChargeApiData? = ChargeApiData()
) : JsonBean() {
    override fun process() {
        val fuel = api_data?.api_material?.getOrNull(0)
        fuel?.let { Resource.fuel.onNext(it) }
        val ammo = api_data?.api_material?.getOrNull(1)
        ammo?.let { Resource.ammo.onNext(it) }
        val metal = api_data?.api_material?.getOrNull(2)
        metal?.let { Resource.metal.onNext(it) }
        val bauxite = api_data?.api_material?.getOrNull(3)
        bauxite?.let { Resource.bauxite.onNext(it) }

        val shipIds = mutableListOf<Int>()
        api_data?.api_ship?.forEach { chargeApiShip ->
            val ship = Fleet.shipMap[chargeApiShip.api_id]
            ship?.let {
                shipIds.add(ship.id)
                it.nowFuel = chargeApiShip.api_fuel
                it.nowBullet = chargeApiShip.api_bull
            }
        }
        Fleet.shipWatcher.onNext(Transform.Change(shipIds))

        setMissionProgress(this, MissionRequireType.SUPPLY)
    }
}

data class ChargeApiData(
        val api_ship: List<ChargeApiShip>? = listOf(),
        val api_material: List<Int>? = listOf(),
        val api_use_bou: Int = 0
)

data class ChargeApiShip(
        val api_id: Int = 0,
        val api_fuel: Int = 0,
        val api_bull: Int = 0,
        val api_onslot: List<Int>? = listOf()
)