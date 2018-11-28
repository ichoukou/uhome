package com.ytoxl.module.uhome.uhomebase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Coupon;
import com.ytoxl.module.uhome.uhomebase.mapper.CouponMapper;
import com.ytoxl.module.uhome.uhomebase.service.Coupon4TimerService;

@Service
public class Coupon4TimerServiceImpl implements Coupon4TimerService {

	@Autowired
	private CouponMapper<Coupon> couponMapper;
	@Value("${everytime_delete_coupon_num}")
	private Integer num;
	
	/**
	 * 删除过期的优惠券明细表记录
	 * @throws UhomeStoreException
	 */
	@Override
	public void deleteOverdueCoupon() throws UhomeStoreException {
		couponMapper.deleteOverdueCoupons(num);
	}

}
