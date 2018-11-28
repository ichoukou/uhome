package com.ytoxl.module.uhome.uhomecontent.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.OrderCrm;
import com.ytoxl.module.uhome.uhomecontent.mapper.OrderCrmMapper;
import com.ytoxl.module.uhome.uhomecontent.service.OrderCrmService;

/**
 * @author liyasi
 *
 */

@Service
public class OrderCrmServiceImpl implements OrderCrmService {
	
	@Autowired
	private OrderCrmMapper<OrderCrm> orderCrmMapper;
	
	
	@Override
	public List<OrderCrm> listOrderCrmByOrderId(Integer orderId)
			throws UhomeStoreException {
		List<OrderCrm> orderCrmList = orderCrmMapper.listOrderCrmByOrderId(orderId);
		return orderCrmList;
	}


	@Override
	public Integer addOrderCrm(OrderCrm orderCrm) throws UhomeStoreException {
		return orderCrmMapper.add(orderCrm);
	}
}
