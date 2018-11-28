package com.ytoxl.module.uhome.uhomeorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderPayment;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderPaymentMapper;
import com.ytoxl.module.uhome.uhomeorder.service.OrderPaymentService;
@Service
public class OrderPaymentServiceImpl implements OrderPaymentService {
	@Autowired
	private OrderPaymentMapper<OrderPayment> orderPaymentMapper;
	
	/**
	 * 修改支付状态
	 * @param orderPayment
	 * @return
	 * @throws UhomeStoreException
	 */
	@Override
	public Integer updateStatus(OrderPayment orderPayment)
			throws UhomeStoreException {
		// TODO Auto-generated method stub
		return orderPaymentMapper.updateStatus(orderPayment);
	}

	@Override
	public OrderPayment getPaymentByOrderId(Integer orderId) throws UhomeStoreException {
		return orderPaymentMapper.getOrderPaymentByOrderId(orderId);
	}

}
