package cn.cctech.kancolle.oyodo.utils

val MAX_SHIP_COUNT = arrayOf(6, 6, 6, 6)
//val MAX_SHIP_COUNT = arrayOf(6, 6, 7, 6)
const val MAX_FLEET_COUNT = 4

// Spot type
const val StartSpot = 0
const val Unknown = 1
const val ObtainRes = 2
const val LoseRes = 3
const val BattleSpot = 4
const val BossBattle = 5
const val BattleAvoid = 6
const val AirStrike = 7
const val EscortSuccess = 8
const val TransportMunitions = 9
const val LongDistanceAerialBattle = 10
const val ManualSelection = 11
const val AerialRecon = 12
const val NightBattle = 13
const val EnemyCombinedFleet = 14

// Speed type
enum class Speed { SLOW, FAST }

// Slot type
const val GUN_SMALL = 1 //小口径主砲
const val GUN_MEDIUM = 2 //中口径主砲
const val GUN_LARGE = 3 //大口径主砲
const val SUB_GUN = 4 //副砲
const val TORPEDO = 5 //魚雷
const val FIGHTER = 6 //艦上戦闘機
const val BOMBER = 7 //艦上爆撃機
const val TORPEDO_BOMBER = 8 //艦上攻撃機
const val SCOUT = 9 //艦上偵察機
const val SEA_SCOUT = 10 //水上偵察機
const val SEA_BOMBER = 11 //水上爆撃機
const val RADAR_SMALL = 12 //小型電探
const val RADAR_LARGE = 13 //大型電探
const val SONAR = 14 //ソナー
const val DEPTH_CHARGE = 15 //爆雷
const val EXT_ARMOR = 16 //追加装甲
const val TURBINE = 17 //機関部強化
const val SANSHIKIDAN = 18 //対空強化弾
const val AP_SHELL = 19 //対艦強化弾
const val VT_FUZE = 20 //VT信管
const val MACHINE_GUN = 21 //対空機銃
const val KOHYOTEKI = 22 //特殊潜航艇
const val DAMECON = 23 //応急修理要員
const val LANDING_CRAFT = 24 //上陸用舟艇
const val AUTOGYRO = 25 //オートジャイロ
const val ANTISUB_PATROL = 26 //対潜哨戒機
const val EXT_ARMOR_M = 27 //追加装甲中型
const val EXT_ARMOR_L = 28 //追加装甲大型
const val SEARCHLIGHT = 29 //探照灯
const val DRUM_CAN = 30 //簡易輸送部材
const val REPAIR_INFRA = 31 //艦艇修理施設
const val SS_TORPEDO = 32 //潜水艦魚雷
const val STAR_SHELL = 33 //照明弾
const val COMMAND_FAC = 34 //司令部施設
const val AVI_PERSONNEL = 35 //航空要員
const val ANTI_AIR_DEVICE = 36 //高射装置
const val ANTI_GROUND_EQIP = 37 //対地装備
const val GUN_LARGE_II = 38 //大口径主砲II
const val SHIP_PERSONNEL = 39 //水上艦要員
const val SONAR_LARGE = 40 //大型ソナー
const val FLYING_BOAT = 41 //大型飛行艇
const val SEARCHLIGHT_LARGE = 42 //大型探照灯
const val COMBAT_FOOD = 43 //戦闘糧食
const val SUPPLIES = 44 //補給物資
const val SEA_FIGHTER = 45 //水上戦闘機
const val AMP_TANK = 46 //特型内火艇
const val LBA_AIRCRAFT = 47 //陸上攻撃機
const val ITCP_FIGHTER = 48 //局地戦闘機
const val SAIUN_PART = 50 //輸送機材
const val SUBMARINE_RADER = 51 //潜水艦装備
const val JET_FIGHTER = 56 //噴式戦闘機
const val JET_BOMBER = 57 //噴式戦闘爆撃機
const val JET_TORPEDO_BOMBER = 58 //噴式攻撃機
const val JET_SCOUT = 59 //噴式偵察機
const val RADER_LARGE_II = 93 //大型電探II
const val SCOUT_II = 94 //艦上偵察機II

// Air power mastery bonus
val kBasicMasteryMinBonus = intArrayOf(0, 10, 25, 40, 55, 70, 85, 100)
val kBasicMasteryMaxBonus = intArrayOf(9, 24, 39, 54, 69, 84, 99, 120)
val kFighterMasteryBonus = intArrayOf(0, 0, 2, 5, 9, 14, 14, 22, 0, 0, 0)
val kSeaBomberMasteryBonus = intArrayOf(0, 0, 1, 1, 1, 3, 3, 6, 0, 0, 0)

/** 小破(75%)  */
const val SLIGHT_DAMAGE = 0.75
/** 中破(50%)  */
const val HALF_DAMAGE = 0.5
/** 大破(25%)  */
const val BADLY_DAMAGE = 0.25

const val CONDITION_NORMAL = 49
const val CONDITION_REPAIR = 40
