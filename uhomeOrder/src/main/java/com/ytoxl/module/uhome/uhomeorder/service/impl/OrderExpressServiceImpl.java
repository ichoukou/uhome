package com.ytoxl.module.uhome.uhomeorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderExpress;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderExpressMapper;
import com.ytoxl.module.uhome.uhomeorder.service.OrderExpressService;
@Service
public class OrderExpressServiceImpl implements OrderExpressService {
	@Autowired
	private OrderExpressMapper<OrderExpress> orderExpressMapper;
	@Override
	public Integer add(OrderExpress orderExpress) throws UhomeStoreException {
		// TODO Auto-generated method stub
		return orderExpressMapper.add(orderExpress);
	}

}
