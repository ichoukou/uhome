package com.ytoxl.module.uhome.uhomecontent.service;

import java.util.List;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.OrderCrm;

/**
 * @author liyasi
 *
 */
public interface OrderCrmService {
	
	/**
	 * 根据订单号查询客服信息
	 * @param orderId
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<OrderCrm> listOrderCrmByOrderId(Integer orderId) throws UhomeStoreException;
	
	/**
	 * 添加客服记录
	 * @param orderCrm
	 * @return
	 * @throws UhomeStoreException
	 */
	public Integer addOrderCrm(OrderCrm orderCrm) throws UhomeStoreException;
	
} 
