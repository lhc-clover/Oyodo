package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.managers.Resource

data class Material(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: List<MaterialApiData> = listOf()
) : JsonBean() {
    override fun process() {
        Resource.fuel.onNext(api_data[0].api_value)
        Resource.ammo.onNext(api_data[1].api_value)
        Resource.metal.onNext(api_data[2].api_value)
        Resource.bauxite.onNext(api_data[3].api_value)
        Resource.burner.onNext(api_data[4].api_value)
        Resource.bucket.onNext(api_data[5].api_value)
        Resource.research.onNext(api_data[6].api_value)
        Resource.improve.onNext(api_data[7].api_value)
    }
}

data class MaterialApiData(
        val api_member_id: Int = 0,
        val api_id: Int = 0,
        val api_value: Int = 0
)