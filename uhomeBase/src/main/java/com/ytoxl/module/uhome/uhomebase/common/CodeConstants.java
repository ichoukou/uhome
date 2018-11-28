package com.ytoxl.module.uhome.uhomebase.common;

public class CodeConstants {
	public static final String INFO_CODE_PRE = "I_";//页面显示
	public static final String EXCEPTION_CODE_PRE = "E_";//程序中使用
	
	/**
	 * 参数错误，适用于所有service中的所有方法验证必传参数错误时报错。
	 */
	public static final String E_PARAMETER_ERROR = "E_PARAMETER_ERROR";
	
	//重复提交订单异常
	public static final String MULTIPLE_SUBMIT = "MULTIPLE_SUBMIT";
	//提交订单时候数据异常
	public static final String PARAMETER_ERROR = "PARAMETER_ERROR";
	//提交订单时候没有登陆异常编码
	public static final String UNLOGIN_ERROR = "UNLOGIN_ERROR";
	//登陆超时异常编码
	public static final String LOGIN_TIMEOUT_ERROR = "LOGIN_TIMEOUT_ERROR";
	//商品已经不在特卖期异常编码
	public static final String PRODUCT_NOTACTIVITY_ERROR = "PRODUCT_NOTACTIVITY_ERROR";
	//商品库存不足异常编码
	public static final String PRODUCT_UNDERSTOCK_ERROR = "PRODUCT_UNDERSTOCK_ERROR";
	//商品库存不足异常编码
	public static final String ORDER_ADDRESS_ERROR = "ORDER_ADDRESS_ERROR";
	//管理员和卖家禁止购买
	public static final String MANAGE_SELLER_FORBID = "MANAGE_SELLER_FORBID";
	
	//优惠券激活码使用名额已满异常
	public static final String QUOTA_FULLF_ERROR = "QUOTA_FULLF_ERROR";
	
}
