package com.ytoxl.module.uhome.uhomeorder.mapper;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomeorder.dataobject.tbl.OrderPostage;

public interface OrderPostageMapper <T extends OrderPostage> extends BaseSqlMapper<T>{

	/**
	 * 根据订单id取得对应的邮费信息
	 * @param orderId
	 * @return
	 * @throws DataAccessException
	 */
	public OrderPostage getOrderPostageByOrderId(Integer orderId) throws DataAccessException;
}
