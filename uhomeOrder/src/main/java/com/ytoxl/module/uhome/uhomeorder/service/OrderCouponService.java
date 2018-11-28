package com.ytoxl.module.uhome.uhomeorder.service;

import java.util.List;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.UserCoupon;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderHead;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderItem;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderPayment;

public interface OrderCouponService {

	/**
	 * 经过折扣后实际交易的金额
	 * @param orderCoupon
	 * @param orderPayment
	 * @return
	 * @throws UhomeStoreException
	 */
	public Boolean getActualCost(UserCoupon userCoupon, List<OrderHead> orders) throws UhomeStoreException;
	

	/**
	 * 
	 * @param orderCoupon
	 * @param orderItems
	 * @return
	 * @throws UhomeStoreException
	 */
	public void getOrderItemActualPrice(OrderPayment orderPayment, List<OrderItem> orderItems) throws UhomeStoreException;
	
	
	public void getOrderItemActualPrice(OrderHead orderHead) throws UhomeStoreException;
	
	/**
	 * 使用优惠劵后，修改订单实际支付金额，修改每件商品最后的支付价格
	 * @param orders
	 * @param userCoupon
	 * @return
	 * @throws UhomeStoreException
	 */
	public void calCouponForOrders(List<OrderHead> orders,UserCoupon userCoupon)throws UhomeStoreException;
}