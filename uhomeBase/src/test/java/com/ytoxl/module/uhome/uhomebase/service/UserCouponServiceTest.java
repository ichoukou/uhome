package com.ytoxl.module.uhome.uhomebase.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.ytoxl.module.uhome.uhomebase.BaseTest;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;

public class UserCouponServiceTest extends BaseTest {
	
	@Autowired
	UserCouponService userCouponService;
	@Autowired
	CouponService couponService;
	
	@Test
	public void getUserCouponStatus() throws UhomeStoreException{
		userCouponService.getUserCouponStatus("1369290523xy8");
	}
	
	@Test
	public void addCoupon() throws UhomeStoreException{
		List<String> couponNos = couponService.createCoupon(4,"wanx");
		log.info("~~~~~~~~~~~~~~~~~~~~:"+couponNos);
	}
	
}
