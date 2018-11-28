package com.ytoxl.module.uhome.uhomeorder.service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderExpress;


public interface OrderExpressService {
	/**
	 * 根据订单id获取退货订单信息
	 * @param orderId
	 * @return
	 * @throws UhomeStoreException
	 */
	public Integer add(OrderExpress orderExpress)  throws UhomeStoreException;
}
