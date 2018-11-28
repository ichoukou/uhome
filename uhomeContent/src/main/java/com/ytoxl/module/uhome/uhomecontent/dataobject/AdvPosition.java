package com.ytoxl.module.uhome.uhomecontent.dataobject;

import com.ytoxl.module.uhome.uhomecontent.dataobject.tbl.AdvPositionTbl;

public class AdvPosition extends AdvPositionTbl {
	public static final Short ADVTYPE_SINGLE=1;   //单图
	public static final Short ADVTYPE_REPEAT=2;   //轮换	
	public static final Short ADVTYPE_HIGH=3;     //高级
	public static final Short ADVTYPE_MULTIPLE=4; //多图平铺
	
	//广告位置定义
	public static final String ADV_OPTION_001="SEC_KILL_01";
	public static final String ADV_OPTION_002="SEC_KILL_02";
	public static final String ADV_OPTION_003="SEC_KILL_03";
	public static final String ADV_OPTION_004="SEC_KILL_04";
	public static final String ADV_OPTION_005="SEC_KILL_05";
	public static final String ADV_OPTION_006="SEC_KILL_06";
	public static final String ADV_OPTION_007="SEC_KILL_07";
	//首页往期特卖广告
	public static final String ADV_OPTION_008="home_history_special_adv";
	
	//首页顶部广告
	public static final String ADV_OPTION_009="home_top_adv";
	
	//首页右边5个广告 304*80
	
	public static final String ADV_OPTION_010="home_right_adv_80";
	//首页右边5个广告 304*300
	public static final String ADV_OPTION_011="home_right_adv_300";
	//首页轮播广告 280*960
	public static final String ADV_OPTION_012 = "home_broadcast_adv";
	
	//各个位置的个数
	public static final Short ADV_NUM = 7;
}
