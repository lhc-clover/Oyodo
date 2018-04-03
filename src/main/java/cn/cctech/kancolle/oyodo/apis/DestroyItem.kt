package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Resource
import cn.cctech.kancolle.oyodo.managers.User
import io.reactivex.subjects.BehaviorSubject

data class DestroyItem(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: DestroyItemApiData = DestroyItemApiData()
) : JsonBean() {
    override fun process() {
        fun plusValue(material: BehaviorSubject<Int>, value: Int) {
            if (value > 0) material.onNext(material.value.plus(value))
        }
        plusValue(Resource.fuel, api_data.api_get_material[0])
        plusValue(Resource.ammo, api_data.api_get_material[1])
        plusValue(Resource.metal, api_data.api_get_material[2])
        plusValue(Resource.bauxite, api_data.api_get_material[3])

        params["api_slotitem_ids"]?.let {
            it.split("%2C").forEach {
                val slotId = try {
                    it.toInt()
                } catch (e: Exception) {
                    -1
                }
                Fleet.slotMap.remove(slotId)
            }
        }
        User.slotCount.onNext(Fleet.slotMap.size)
    }
}

data class DestroyItemApiData(
        val api_get_material: List<Int> = listOf()
)