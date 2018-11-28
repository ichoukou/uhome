package com.ytoxl.module.uhome.uhomeorder.service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;

/**
 * 后台订单管理service
 *
 */
public interface OrderService4Timer {
	
	/**
	 * 更改订单状态为取消
	 * @return
	 * @throws UhomeStoreException
	 */
	public Integer updateStatusToCanceled() throws UhomeStoreException;
	
	/**
	 * 更改订单状态为已完成
	 * @return
	 * @throws UhomeStoreException
	 */
	public Integer updateStatusToFinished() throws UhomeStoreException;
}
