package com.ytoxl.module.uhome.uhomeInterface.service;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.ytoxl.module.uhome.uhomeInterface.model.duomai.OrderPush;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;

public interface OrderPushService {
	/**
	 * 根据订单编号查询推送信息
	 * @param orderNo
	 * @return
	 * @throws UhomeStoreException
	 */
	public OrderPush getOrderPushByOrderNo(String orderNo)throws UhomeStoreException;
	
	/**
	 * bean转换为Map
	 * @param object
	 * @return
	 * @throws UhomeStoreException
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Map<String,String> toHashMap(OrderPush object) throws UhomeStoreException, IntrospectionException, IllegalAccessException, InvocationTargetException;

	/**
	 * 推送订单信息
	 * @return
	 * @throws UhomeStoreException
	 */
	public void orderPushTimer()throws UhomeStoreException;
}
