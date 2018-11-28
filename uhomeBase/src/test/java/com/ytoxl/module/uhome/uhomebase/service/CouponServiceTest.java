package com.ytoxl.module.uhome.uhomebase.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.BaseTest;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;

public class CouponServiceTest extends BaseTest {
	
	@Autowired
	CouponService couponService;
	
	@Test
	public void createCoupon() throws UhomeStoreException{
		long startTime = System.currentTimeMillis();
		couponService.createCoupon(2, null);
		long endTime = System.currentTimeMillis();
		log.info("--------------time---------------------" + (endTime - startTime));
	}
	
}
