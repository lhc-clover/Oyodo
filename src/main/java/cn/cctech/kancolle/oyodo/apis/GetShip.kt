package cn.cctech.kancolle.oyodo.apis

import cn.cctech.kancolle.oyodo.entities.Build
import cn.cctech.kancolle.oyodo.entities.Ship
import cn.cctech.kancolle.oyodo.entities.Slot
import cn.cctech.kancolle.oyodo.managers.Dock
import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.managers.Raw
import cn.cctech.kancolle.oyodo.managers.User
import io.reactivex.subjects.BehaviorSubject

data class GetShip(
        val api_result: Int = 0,
        val api_result_msg: String = "",
        val api_data: GetShipApiData = GetShipApiData()
) : JsonBean() {
    override fun process() {
        // build dock
        api_data.api_kdock.forEachIndexed { index, it -> Dock.buildList[index].onNext(Build(it)) }
        // ship
        val rawShip = Raw.rawShipMap[api_data.api_ship.api_ship_id]
        val ship = Ship(api_data.api_ship, rawShip)
        Fleet.shipMap[api_data.api_ship.api_id] = BehaviorSubject.createDefault(ship)
        User.shipCount.onNext(Fleet.shipMap.size)
        // slot
        api_data.api_slotitem.forEach {
            val rawSlot = Raw.rawSlotMap[it.api_slotitem_id]
            val slot = Slot(rawSlot, null)
            Fleet.slotMap[it.api_id] = BehaviorSubject.createDefault(slot)
        }
        User.slotCount.onNext(Fleet.slotMap.size)
    }
}

data class GetShipApiData(
        val api_id: Int = 0,
        val api_ship_id: Int = 0,
        val api_kdock: List<KDockApiData> = listOf(),
        val api_ship: ApiShip = ApiShip(),
        val api_slotitem: List<GetShipSlotItem> = listOf()
)

data class GetShipSlotItem(
        val api_id: Int = 0,
        val api_slotitem_id: Int = 0
)
