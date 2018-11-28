package com.ytoxl.module.uhome.uhomeorder.service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderPayment;


public interface OrderPaymentService {
	/**
	 * 修改支付状态
	 * @param orderPayment
	 * @return
	 * @throws UhomeStoreException
	 */
	public Integer updateStatus(OrderPayment orderPayment)  throws UhomeStoreException;
	
	/**
	 * 根据订单id获取支付信息
	 * @param orderId
	 * @return
	 * @throws UhomeStoreException
	 */
	public OrderPayment getPaymentByOrderId(Integer orderId) throws UhomeStoreException;
}
