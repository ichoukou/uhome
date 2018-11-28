package com.ytoxl.module.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.BaseTest;
import com.ytoxl.module.uhome.uhomeInterface.service.OrderPushService;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;

public class OrderPushServiceTest extends BaseTest {
	private static Logger logger = LoggerFactory.getLogger(OrderPushServiceTest.class);
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private OrderPushService orderPushserviceImpl;
	
	@Test
	public void testOrderPushTimer(){
		try {
			orderPushserviceImpl.orderPushTimer();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
	}
	

}
