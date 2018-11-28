package com.ytoxl.module.uhome.uhomeorder.service;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;

public interface OrderCpsService {

	/**
	 * 保存cps推广来的订单信息
	 * @param request
	 * @param orderIds
	 */
	public void addOrderCps(HttpServletRequest request, List<Integer> orderIds) throws UhomeStoreException;
	
	/**
	 * 计算佣金
	 * @param orderId
	 * @return
	 */
	public BigDecimal getCommission(Integer orderId) throws UhomeStoreException;
}
