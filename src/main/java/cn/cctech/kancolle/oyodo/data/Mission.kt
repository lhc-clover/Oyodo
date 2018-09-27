package cn.cctech.kancolle.oyodo.data

import cn.cctech.kancolle.oyodo.apis.*
import cn.cctech.kancolle.oyodo.managers.Battle
import cn.cctech.kancolle.oyodo.managers.Fleet
import cn.cctech.kancolle.oyodo.utils.*

enum class MissionRequireType {
    NONE,
    BATTLE, //出击
    EXPEDITION, //远征
    REPAIR, //入渠
    SUPPLY, //补给
    PRACTICE, //演习
    CREATE_ITEM, //开发
    CREATE_SHIP, //建造
    DESTROY_ITEM, //废弃
    DESTROY_SHIP, //解体
    REMODEL_SLOT, //改修
    POWER_UP //强化
}

data class MissionData(val description: String, val require: Int, val type: MissionRequireType, val processor: (JsonBean) -> Int)

@Suppress("UNCHECKED_CAST", "MoveLambdaOutsideParentheses")
val missionMap = mapOf(

        /** ==========出击类========== */
        201 to MissionData(
                "获得胜利1次",
                1,
                MissionRequireType.BATTLE,
                { if (isBattleWin(it as IBattleResult<IBattleResultApiData>)) 1 else 0 }),
        216 to MissionData(
                "进行战斗1次",
                1,
                MissionRequireType.BATTLE,
                { 1 }),
        210 to MissionData(
                "进行战斗10次",
                10,
                MissionRequireType.BATTLE,
                { 1 }),
        218 to MissionData(
                "击沉补给舰3只",
                3,
                MissionRequireType.BATTLE,
                { Battle.enemyList.filter { it.type == 15 }.count { isShipSink(it) } }),
        226 to MissionData(
                "2-1～2-5 BOSS战胜利5次",
                5,
                MissionRequireType.BATTLE,
                { if ((Battle.area == 2) && (Battle.nodeType == BossBattle) && isBattleWin(it as IBattleResult<IBattleResultApiData>)) 1 else 0 }),
        230 to MissionData(
                "击沉潜水舰6只",
                6,
                MissionRequireType.BATTLE,
                { Battle.enemyList.filter { it.type == 13 }.count { isShipSink(it) } }),

        /** ==========演习类========== */
        303 to MissionData(
                "进行演习3次",
                3,
                MissionRequireType.PRACTICE,
                { 1 }),
        304 to MissionData(
                "演习胜利5次",
                5,
                MissionRequireType.PRACTICE,
                { if (isPracticeWin(it as PracticeResult)) 1 else 0 }),

        /** ==========远征类========== */
        402 to MissionData(
                "远征成功3次",
                3,
                MissionRequireType.EXPEDITION,
                { if (isExpeditionSuccess(it as MissionResult)) 1 else 0 }),
        403 to MissionData(
                "远征成功10次",
                10,
                MissionRequireType.EXPEDITION,
                { if (isExpeditionSuccess(it as MissionResult)) 1 else 0 }),

        /** ==========整备类========== */
        503 to MissionData(
                "入渠5次",
                5,
                MissionRequireType.REPAIR,
                { 1 }),
        504 to MissionData(
                "补给15次",
                15,
                MissionRequireType.SUPPLY,
                { 1 }),

        /** ==========工厂类========== */
        605 to MissionData(
                "开发1次",
                1,
                MissionRequireType.CREATE_ITEM,
                { 1 }),
        606 to MissionData(
                "建造1次",
                1,
                MissionRequireType.CREATE_SHIP,
                { 1 }),
        607 to MissionData(
                "开发3次",
                3,
                MissionRequireType.CREATE_ITEM,
                { 1 }),
        608 to MissionData(
                "建造3次",
                3,
                MissionRequireType.CREATE_SHIP,
                { 1 }),
        609 to MissionData(
                "解体2次",
                2,
                MissionRequireType.DESTROY_SHIP,
                { 1 }),
        619 to MissionData(
                "改修1次",
                1,
                MissionRequireType.REMODEL_SLOT,
                { 1 }),
        673 to MissionData(
                "废弃小口径主炮4个",
                4,
                MissionRequireType.DESTROY_ITEM,
                {
                    it.params["api_slotitem_ids"]?.split("%2C")?.map {
                        val slotId = try {
                            it.toInt()
                        } catch (e: Exception) {
                            -1
                        }
                        Fleet.slotMap[slotId]
                    }?.count { it?.type == 1 } ?: 0
                }),
        674 to MissionData(
                "废弃机枪3个",
                3,
                MissionRequireType.DESTROY_ITEM,
                {
                    it.params["api_slotitem_ids"]?.split("%2C")?.map {
                        val slotId = try {
                            it.toInt()
                        } catch (e: Exception) {
                            -1
                        }
                        Fleet.slotMap[slotId]
                    }?.count { it?.type == 21 } ?: 0
                }),

        /** ==========强化类========== */
        702 to MissionData(
                "近代化改修2次",
                2,
                MissionRequireType.POWER_UP,
                { 1 })
)
