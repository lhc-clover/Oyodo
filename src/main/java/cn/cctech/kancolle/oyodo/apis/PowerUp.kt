package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.entities.Ship
import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Raw
import cn.cctech.kancolle.oyodo.managers.User

data class PowerUp(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: PowerUpApiData = PowerUpApiData()
) : JsonBean() {
    override fun process() {
        if (api_data.api_powerup_flag == 1) {
            params["api_id_items"]?.let {
                it.split("%2C").forEach {
                    val ship = try {
                        Fleet.shipMap[it.toInt()]!!.value
                    } catch (e: Exception) {
                        null
                    }
                    ship?.let {
                        it.items.forEach { itemId -> Fleet.slotMap.remove(itemId) }
                        Fleet.shipMap.remove(it.id)
                    }
                }
            }
            val rawShip = Raw.rawShipMap[api_data.api_ship.api_ship_id]
            val ship = Ship(api_data.api_ship, rawShip)
            Fleet.shipMap[ship.id]?.onNext(ship)
            User.shipCount.onNext(Fleet.shipMap.size)
            User.slotCount.onNext(Fleet.slotMap.size)
        }
    }
}

data class PowerUpApiData(
        val api_powerup_flag: Int = 0,
        val api_ship: ApiShip = ApiShip(),
        val api_deck: List<ApiDeck> = listOf()
)

data class ApiDeck(
        val api_member_id: Int = 0,
        val api_id: Int = 0,
        val api_name: String = "",
        val api_name_id: String = "",
        val api_mission: List<Int> = listOf(),
        val api_flagship: String = "",
        val api_ship: List<Int> = listOf()
)
