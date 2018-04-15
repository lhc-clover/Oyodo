package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.managers.Resource

data class Material(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: List<MaterialApiData>? = listOf()
) : JsonBean() {
    override fun process() {
        api_data?.get(0)?.api_value?.let { Resource.fuel.onNext(it) }
        api_data?.get(1)?.api_value?.let { Resource.ammo.onNext(it) }
        api_data?.get(2)?.api_value?.let { Resource.metal.onNext(it) }
        api_data?.get(3)?.api_value?.let { Resource.bauxite.onNext(it) }
        api_data?.get(4)?.api_value?.let { Resource.burner.onNext(it) }
        api_data?.get(5)?.api_value?.let { Resource.bucket.onNext(it) }
        api_data?.get(6)?.api_value?.let { Resource.research.onNext(it) }
        api_data?.get(7)?.api_value?.let { Resource.improve.onNext(it) }
    }
}

data class MaterialApiData(
        val api_member_id: Int = 0,
        val api_id: Int = 0,
        val api_value: Int = 0
)