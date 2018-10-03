package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.data.MissionRequireType
import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Resource
import cn.cctech.kancolle.oyodo.managers.Transform
import cn.cctech.kancolle.oyodo.managers.User
import cn.cctech.kancolle.oyodo.utils.setMissionProgress

data class DestroyShip(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: DestroyShipApiData? = DestroyShipApiData()
) : JsonBean() {
    override fun process() {
        api_data?.api_material?.get(0)?.let { Resource.fuel.onNext(it) }
        api_data?.api_material?.get(1)?.let { Resource.ammo.onNext(it) }
        api_data?.api_material?.get(2)?.let { Resource.metal.onNext(it) }
        api_data?.api_material?.get(3)?.let { Resource.bauxite.onNext(it) }

        val slotDestroy = params["api_slot_dest_flag"]?.equals("1") ?: false
        params["api_ship_id"]?.let {
            val shipIds = it.split("%2C")
            val slotIds = mutableListOf<Int>()
            shipIds.forEach { shipId ->
                val ship = try {
                    Fleet.shipMap[shipId.toInt()]!!
                } catch (e: Exception) {
                    null
                }
                ship?.let {
                    Fleet.shipMap.remove(it.id)
                    if (slotDestroy) it.items.forEach { itemId ->
                        Fleet.slotMap.remove(itemId)
                        slotIds.add(itemId)
                    }
                }
                Fleet.deckShipIds.forEach {
                    val ids = it.value.minus(shipId.toInt())
                    it.onNext(ids)
                }
            }
            Fleet.shipWatcher.onNext(Transform.Remove(shipIds.map { it.toInt() }))
            User.shipCount.onNext(Fleet.shipMap.size)
            Fleet.slotWatcher.onNext(Transform.Remove(slotIds))
            User.slotCount.onNext(Fleet.slotMap.size)
        }

        setMissionProgress(this, MissionRequireType.DESTROY_SHIP)
    }
}

data class DestroyShipApiData(
        val api_material: List<Int>? = listOf()
)