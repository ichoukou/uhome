package com.ytoxl.module.uhome.uhomebase.service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;

public interface Coupon4TimerService {

	/**
	 * 删除过期的优惠券明细表记录
	 * @throws UhomeStoreException
	 */
	public void deleteOverdueCoupon() throws UhomeStoreException;
}
