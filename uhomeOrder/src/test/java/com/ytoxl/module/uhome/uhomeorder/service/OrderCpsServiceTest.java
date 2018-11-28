package com.ytoxl.module.uhome.uhomeorder.service;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomeorder.BaseTest;

public class OrderCpsServiceTest extends BaseTest {

	@Autowired
	private OrderCpsService orderCpsService;
	
	@Test
	public void testGetCommission() throws UhomeStoreException{
		BigDecimal commission = orderCpsService.getCommission(979);
		log.info("-------------佣金---------------" + commission);
	}
}
