package com.qf.picnotext;

/**
 * Created by lenovo on 2016/9/30.
 */
public interface Contact {
    //头部图片
    String BASE_URL = "http://q.chanyouji.com/";
//    String PLAN_HEADIMG="api/v1/adverts.json?market=qq&first_launch=false";
    //攻略 目的地列表
    String PLAN_DESTINATIONS="http://q.chanyouji.com/api/v2/destinations.json";
    //攻略 目的地详情
    String PLAN_DES_DETAILS="http://q.chanyouji.com/api/v3/destinations/%d.json //";

    //游记接口
    String TRAVEL_NOTE = "http://q.chanyouji.com/api/v1/timelines.json?page=%d";
}
