package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Resource
import cn.cctech.kancolle.oyodo.managers.User

data class DestroyShip(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: DestroyShipApiData = DestroyShipApiData()
) : JsonBean() {
    override fun process() {
        Resource.fuel.onNext(api_data.api_material[0])
        Resource.ammo.onNext(api_data.api_material[1])
        Resource.metal.onNext(api_data.api_material[2])
        Resource.bauxite.onNext(api_data.api_material[3])

        val slotDestroy = params["api_slot_dest_flag"]?.equals(1) ?: false
        params["api_ship_id"]?.let {
            it.split("%2C").forEach { shipId ->
                val ship = try {
                    Fleet.shipMap[shipId.toInt()]!!.value
                } catch (e: Exception) {
                    null
                }
                ship?.let {
                    Fleet.shipMap.remove(it.id)
                    if (slotDestroy) it.items.forEach { itemId -> Fleet.slotMap.remove(itemId) }
                }
            }
        }
        User.shipCount.onNext(Fleet.shipMap.size)
        User.slotCount.onNext(Fleet.slotMap.size)
    }
}

data class DestroyShipApiData(
        val api_material: List<Int> = listOf()
)