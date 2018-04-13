package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.entities.Build
import cn.cctech.kancolle.oyodo.entities.Ship
import cn.cctech.kancolle.oyodo.entities.Slot
import cn.cctech.kancolle.oyodo.managers.*

data class GetShip(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: GetShipApiData? = GetShipApiData()
) : JsonBean() {
    override fun process() {
        // build dock
        api_data?.api_kdock?.forEachIndexed { index, it -> Dock.buildList[index].onNext(Build(it)) }
        api_data?.api_ship?.let {
            // ship
            val rawShip = Raw.rawShipMap[api_data.api_ship.api_ship_id]
            val ship = Ship(api_data.api_ship, rawShip)
            Fleet.shipMap[api_data.api_ship.api_id] = ship
            Fleet.shipWatcher.onNext(Transform.Add(listOf(ship.id)))
            User.shipCount.onNext(Fleet.shipMap.size)
            // slot
            val slotIds = mutableListOf<Int>()
            api_data.api_slotitem?.forEach {
                val rawSlot = Raw.rawSlotMap[it.api_slotitem_id]
                val slot = Slot(rawSlot, null)
                Fleet.slotMap[it.api_id] = slot
                slotIds.add(it.api_id)
            }
            Fleet.slotWatcher.onNext(Transform.Add(slotIds))
            User.slotCount.onNext(Fleet.slotMap.size)
        }
    }
}

data class GetShipApiData(
        val api_id: Int = 0,
        val api_ship_id: Int = 0,
        val api_kdock: List<KDockApiData>? = listOf(),
        val api_ship: ApiShip? = ApiShip(),
        val api_slotitem: List<GetShipSlotItem>? = listOf()
)

data class GetShipSlotItem(
        val api_id: Int = 0,
        val api_slotitem_id: Int = 0
)
