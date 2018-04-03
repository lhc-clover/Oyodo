package cn.cctech.kancolle.oyodo.test

import cn.cctech.kancolle.oyodo.Oyodo
import cn.cctech.kancolle.oyodo.Watcher
import cn.cctech.kancolle.oyodo.apis.Port
import cn.cctech.kancolle.oyodo.managers.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Before
import org.junit.Test

class IManagerTest {

    @Before
    fun prepare() {
        val portJson = "{\"api_result\":1,\"api_result_msg\":\"成功\",\"api_data\":{\"api_material\":[{\"api_member_id\":11141519,\"api_id\":1,\"api_value\":1020},{\"api_member_id\":11141519,\"api_id\":2,\"api_value\":1020},{\"api_member_id\":11141519,\"api_id\":3,\"api_value\":1000},{\"api_member_id\":11141519,\"api_id\":4,\"api_value\":1000},{\"api_member_id\":11141519,\"api_id\":5,\"api_value\":2},{\"api_member_id\":11141519,\"api_id\":6,\"api_value\":4},{\"api_member_id\":11141519,\"api_id\":7,\"api_value\":4},{\"api_member_id\":11141519,\"api_id\":8,\"api_value\":0}],\"api_deck_port\":[{\"api_member_id\":11141519,\"api_id\":1,\"api_name\":\"第1艦隊\",\"api_name_id\":\"\",\"api_mission\":[0,0,0,0],\"api_flagship\":\"0\",\"api_ship\":[1,2,3,4,-1,-1]}],\"api_ndock\":[{\"api_member_id\":11141519,\"api_id\":1,\"api_state\":0,\"api_ship_id\":0,\"api_complete_time\":0,\"api_complete_time_str\":\"0\",\"api_item1\":0,\"api_item2\":0,\"api_item3\":0,\"api_item4\":0},{\"api_member_id\":11141519,\"api_id\":2,\"api_state\":0,\"api_ship_id\":0,\"api_complete_time\":0,\"api_complete_time_str\":\"0\",\"api_item1\":0,\"api_item2\":0,\"api_item3\":0,\"api_item4\":0},{\"api_member_id\":11141519,\"api_id\":3,\"api_state\":-1,\"api_ship_id\":0,\"api_complete_time\":0,\"api_complete_time_str\":\"0\",\"api_item1\":0,\"api_item2\":0,\"api_item3\":0,\"api_item4\":0},{\"api_member_id\":11141519,\"api_id\":4,\"api_state\":-1,\"api_ship_id\":0,\"api_complete_time\":0,\"api_complete_time_str\":\"0\",\"api_item1\":0,\"api_item2\":0,\"api_item3\":0,\"api_item4\":0}],\"api_ship\":[{\"api_id\":1,\"api_sortno\":74,\"api_ship_id\":37,\"api_lv\":2,\"api_exp\":[216,84,57],\"api_nowhp\":15,\"api_maxhp\":15,\"api_leng\":1,\"api_slot\":[3,-1,-1,-1,-1],\"api_onslot\":[0,0,0,0,0],\"api_slot_ex\":0,\"api_kyouka\":[0,2,1,0,0],\"api_backs\":1,\"api_fuel\":15,\"api_bull\":20,\"api_slotnum\":2,\"api_ndock_time\":0,\"api_ndock_item\":[0,0],\"api_srate\":0,\"api_cond\":56,\"api_karyoku\":[12,29],\"api_raisou\":[29,69],\"api_taiku\":[15,39],\"api_soukou\":[6,19],\"api_kaihi\":[42,79],\"api_taisen\":[20,49],\"api_sakuteki\":[5,19],\"api_lucky\":[10,49],\"api_locked\":0,\"api_locked_equip\":0},{\"api_id\":2,\"api_sortno\":15,\"api_ship_id\":33,\"api_lv\":1,\"api_exp\":[72,28,72],\"api_nowhp\":15,\"api_maxhp\":15,\"api_leng\":1,\"api_slot\":[4,-1,-1,-1,-1],\"api_onslot\":[0,0,0,0,0],\"api_slot_ex\":0,\"api_kyouka\":[0,0,0,0,0],\"api_backs\":2,\"api_fuel\":15,\"api_bull\":20,\"api_slotnum\":2,\"api_ndock_time\":0,\"api_ndock_item\":[0,0],\"api_srate\":0,\"api_cond\":49,\"api_karyoku\":[12,29],\"api_raisou\":[27,69],\"api_taiku\":[12,39],\"api_soukou\":[5,19],\"api_kaihi\":[40,79],\"api_taisen\":[20,49],\"api_sakuteki\":[5,19],\"api_lucky\":[10,49],\"api_locked\":0,\"api_locked_equip\":0},{\"api_id\":3,\"api_sortno\":12,\"api_ship_id\":10,\"api_lv\":1,\"api_exp\":[72,28,72],\"api_nowhp\":15,\"api_maxhp\":15,\"api_leng\":1,\"api_slot\":[5,-1,-1,-1,-1],\"api_onslot\":[0,0,0,0,0],\"api_slot_ex\":0,\"api_kyouka\":[0,0,0,0,0],\"api_backs\":2,\"api_fuel\":15,\"api_bull\":20,\"api_slotnum\":2,\"api_ndock_time\":0,\"api_ndock_item\":[0,0],\"api_srate\":0,\"api_cond\":49,\"api_karyoku\":[12,29],\"api_raisou\":[27,69],\"api_taiku\":[12,39],\"api_soukou\":[5,19],\"api_kaihi\":[40,79],\"api_taisen\":[20,49],\"api_sakuteki\":[5,19],\"api_lucky\":[10,49],\"api_locked\":0,\"api_locked_equip\":0},{\"api_id\":4,\"api_sortno\":32,\"api_ship_id\":2,\"api_lv\":1,\"api_exp\":[0,100,0],\"api_nowhp\":13,\"api_maxhp\":13,\"api_leng\":1,\"api_slot\":[6,-1,-1,-1,-1],\"api_onslot\":[0,0,0,0,0],\"api_slot_ex\":0,\"api_kyouka\":[0,0,0,0,0],\"api_backs\":2,\"api_fuel\":15,\"api_bull\":15,\"api_slotnum\":2,\"api_ndock_time\":0,\"api_ndock_item\":[0,0],\"api_srate\":0,\"api_cond\":49,\"api_karyoku\":[7,29],\"api_raisou\":[18,49],\"api_taiku\":[8,29],\"api_soukou\":[5,18],\"api_kaihi\":[37,69],\"api_taisen\":[16,39],\"api_sakuteki\":[4,17],\"api_lucky\":[10,49],\"api_locked\":0,\"api_locked_equip\":0},{\"api_id\":5,\"api_sortno\":12,\"api_ship_id\":10,\"api_lv\":1,\"api_exp\":[0,100,0],\"api_nowhp\":15,\"api_maxhp\":15,\"api_leng\":1,\"api_slot\":[7,-1,-1,-1,-1],\"api_onslot\":[0,0,0,0,0],\"api_slot_ex\":0,\"api_kyouka\":[0,0,0,0,0],\"api_backs\":2,\"api_fuel\":15,\"api_bull\":20,\"api_slotnum\":2,\"api_ndock_time\":0,\"api_ndock_item\":[0,0],\"api_srate\":0,\"api_cond\":49,\"api_karyoku\":[12,29],\"api_raisou\":[27,69],\"api_taiku\":[12,39],\"api_soukou\":[5,19],\"api_kaihi\":[40,79],\"api_taisen\":[20,49],\"api_sakuteki\":[5,19],\"api_lucky\":[10,49],\"api_locked\":0,\"api_locked_equip\":0}],\"api_basic\":{\"api_member_id\":\"11141519\",\"api_nickname\":\"CC\",\"api_nickname_id\":\"153232567\",\"api_active_flag\":1,\"api_starttime\":1473318252446,\"api_level\":1,\"api_rank\":10,\"api_experience\":20,\"api_fleetname\":null,\"api_comment\":\"\",\"api_comment_id\":\"\",\"api_max_chara\":100,\"api_max_slotitem\":497,\"api_max_kagu\":0,\"api_playtime\":0,\"api_tutorial\":0,\"api_furniture\":[1,38,72,102,133,164],\"api_count_deck\":1,\"api_count_kdock\":2,\"api_count_ndock\":2,\"api_fcoin\":0,\"api_st_win\":2,\"api_st_lose\":0,\"api_ms_count\":0,\"api_ms_success\":0,\"api_pt_win\":0,\"api_pt_lose\":0,\"api_pt_challenged\":0,\"api_pt_challenged_win\":0,\"api_firstflag\":1,\"api_tutorial_progress\":100,\"api_pvp\":[0,0],\"api_medals\":0,\"api_large_dock\":0},\"api_log\":[{\"api_no\":0,\"api_type\":\"1\",\"api_state\":\"0\",\"api_message\":\"「入渠」していた艦の修理が、完了しました。\"},{\"api_no\":1,\"api_type\":\"11\",\"api_state\":\"0\",\"api_message\":\"図鑑の内容が更新されました！\"},{\"api_no\":2,\"api_type\":\"11\",\"api_state\":\"0\",\"api_message\":\"図鑑の内容が更新されました！\"},{\"api_no\":3,\"api_type\":\"11\",\"api_state\":\"0\",\"api_message\":\"図鑑の内容が更新されました！\"},{\"api_no\":4,\"api_type\":\"11\",\"api_state\":\"0\",\"api_message\":\"図鑑の内容が更新されました！\"},{\"api_no\":5,\"api_type\":\"11\",\"api_state\":\"0\",\"api_message\":\"図鑑の内容が更新されました！\"}],\"api_p_bgm_id\":101,\"api_parallel_quest_count\":5}}"
        val token = object : TypeToken<Port>() {}.type
        val port = Gson().fromJson<Port>(portJson, token)
        port.process()
    }

    @Test
    fun watch() {
        Oyodo.attention().watch(Resource.fuel, { System.out.println("fuel : $it") })
        Resource.fuel.onNext(1)
        Oyodo.attention().watch(Resource.fuel, object : Watcher<Int> {
            override fun onChange(data: Int) {
                System.out.println("fuel : $data")
            }
        })
        Resource.fuel.onNext(12450)
        Resource.fuel.onNext(666)
        Resource.fuel.onNext(233)
    }

}