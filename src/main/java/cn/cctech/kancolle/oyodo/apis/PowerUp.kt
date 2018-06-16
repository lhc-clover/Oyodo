package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.data.MissionRequireType
import cn.cctech.kancolle.oyodo.entities.Ship
import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Raw
import cn.cctech.kancolle.oyodo.managers.Transform
import cn.cctech.kancolle.oyodo.managers.User
import cn.cctech.kancolle.oyodo.utils.setMissionProgress

data class PowerUp(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: PowerUpApiData = PowerUpApiData()
) : JsonBean() {
    override fun process() {
        if (api_data.api_powerup_flag == 1) {
            params["api_id_items"]?.let {
                val shipIds = it.split("%2C")
                val slotIds = mutableListOf<Int>()
                shipIds.forEach {
                    val ship = try {
                        Fleet.shipMap[it.toInt()]!!
                    } catch (e: Exception) {
                        null
                    }
                    ship?.let {
                        it.items.forEach { itemId ->
                            Fleet.slotMap.remove(itemId)
                            slotIds.add(itemId)
                        }
                        Fleet.shipMap.remove(it.id)
                    }
                }
                Fleet.shipWatcher.onNext(Transform.Remove(shipIds.map { it.toInt() }))
                Fleet.slotWatcher.onNext(Transform.Remove(slotIds))
            }
            val rawShip = Raw.rawShipMap[api_data.api_ship.api_ship_id]
            val ship = Ship(api_data.api_ship, rawShip)
            Fleet.shipMap[ship.id] = ship
            Fleet.shipWatcher.onNext(Transform.Change(listOf(ship.id)))
            User.shipCount.onNext(Fleet.shipMap.size)
            User.slotCount.onNext(Fleet.slotMap.size)
        }

        setMissionProgress(this, MissionRequireType.POWER_UP)
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
        val api_mission: List<String> = listOf(),
        val api_flagship: String = "",
        val api_ship: List<Int> = listOf()
)
