package com.ytoxl.module.uhome.uhomebase.common;

import java.math.BigDecimal;


public class CommonConstants {
	//分页常量
	public final static String PAGE_START = "start";
	public final static String PAGE_LIMIT = "limit";
	public final static String PAGE_SORT = "sort";
	public final static String PAGE_DIR = "dir";
	public final static Integer PAGE_DEFAULT_TOTAL=10000;
	//freemarker 文件后缀
	public final static String FREEMARK_POSTFIX = ".ftl";
	//freemarker 运单模版名称
	public final static String FREEMARK_WAYBILLNAME = "WayBillTemplate";
	//购物车对应的cookie名称
	public final static String COOKIE_SHOPPINGCART = "_cookie_shoppingcart";
	//用户浏览商品的cookie的名称
	public final static String COOKIE_USER_PRODUCT_HISTORY = "_user_product_history";
	//用户用户自定义的批量查询id名称
	public final static String ID_USER_CHOOSE = "iDs";
	
	public static final String BEGINTIME = "beginTime";
	public static final String ENDTIME = "endTime";
	public static final String ENDTIME_VALUE = " 24:00:00";

	//分配给联盟的身份标识对应cookie名称
	public final static String COOKIE_UNIONID = "union_id";
	//网站主的euid标识对应cookie名称
	public final static String COOKIE_FEEDBACK = "feedback";
	//联盟下面投放广告活动链接的网站主的ID对应cookie名称
	public final static String COOKIE_MID = "mid";
	//提供给联盟的自定义链接地址对应cookie名称
	public final static String COOKIE_TO = "to";
	//佣金比例
	public final static BigDecimal COMMISSION_REBATE = new BigDecimal(0.08);
	//分页每页多少条
	public final static Integer PAGE_SIZE = new Integer(100);
	
	//邮费类型
	/**排期 邮费类型 1**/
	public final static Short POSTAGE_PLAN = 1;
	
}
