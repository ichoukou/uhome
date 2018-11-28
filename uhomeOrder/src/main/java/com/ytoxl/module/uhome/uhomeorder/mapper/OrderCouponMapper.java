package com.ytoxl.module.uhome.uhomeorder.mapper;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomeorder.dataobject.tbl.OrderCoupon;


public interface OrderCouponMapper<T extends OrderCoupon> extends BaseSqlMapper<T> {

	/**
	 * 根据orderId删除orderCoupon
	 * 
	 * @throws DataAccessException
	 */
	public Integer deleteOrderCouponByOrderId(Integer orderId)throws DataAccessException;

}
