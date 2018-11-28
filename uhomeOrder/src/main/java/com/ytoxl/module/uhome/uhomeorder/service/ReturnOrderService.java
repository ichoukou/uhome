package com.ytoxl.module.uhome.uhomeorder.service;

import java.math.BigDecimal;
import java.util.List;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderExpress;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderHead;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderItem;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturn;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturnItem;


public interface ReturnOrderService {
	/**
	 * 根据订单id获取退货订单信息
	 * @param orderId
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<OrderReturn> getOrderReturnById( Integer orderId)  throws UhomeStoreException;
	/**根据orderPage 查询得到退货订单明细
	 * @param orderPage
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<OrderReturn> getOrderReturnById(BasePagination<OrderHead> orderPage) throws UhomeStoreException;
	/**
	 * 根据orderReturn 添加新的退货原因和描述
	 * @param orderReturn
	 * @throws UhomeStoreException 
	 */
	public void saveOrderReturn(OrderReturn orderReturn) throws UhomeStoreException;
	/**
	 * 根据orderreturnpage查询所有的分页退货记录商品
	 * @param orderreturnpage
	 * @throws UhomeStoreException
	 */
	public void getMyReturnOrders(BasePagination<OrderReturn> orderHead)throws UhomeStoreException;
	/***
	 * 根据order
	 * @param orderId
	 * @return
	 * @throws UhomeStoreException
	 */
	public OrderReturn getOrderReturnByOrderReturnId(Integer orderReturnId)throws UhomeStoreException;
	
	/**
	 * 修改退货订单状态
	 * @param orderReturn
	 * @return
	 * @throws UhomeStoreException
	 */
	public Integer updateStatus(OrderReturn orderReturn)  throws UhomeStoreException;
	
	/**
	 * 根据orderid查找当前所有的退货记录
	 * @param orderId
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<OrderItem> getOrderReturnByOrderId(Integer orderId)  throws UhomeStoreException;
	/***
	 * 根据orderItemId查询某个订单的明细
	 * @param orderId
	 * @throws UhomeStoreException
	 */
	public OrderReturn getOrderItemReturn(Integer  orderItemId)  throws UhomeStoreException;
	/***
	 * 根据orderItemId查询某个订单的明细
	 * @param orderId
	 * @throws UhomeStoreException
	 */
	public void saveOrderReturnItem(OrderReturnItem  OrderReturnItem)  throws UhomeStoreException;
	
	
	//添加退货信息
	public void saveOrderReturnModel(OrderReturn orderReturn) throws UhomeStoreException;
	
	//修改退货记录的状态
	public void updateStatusByOrderId(Integer orderId) throws UhomeStoreException;
	//添加某个快递公司的账号和名称
	public void addmail(OrderExpress oderexExpress)  throws UhomeStoreException;
	//通过ReturnId 得到return的详细信息
	public  OrderReturnItem getOrderReturnItemById(Integer orderReturnItemId)throws UhomeStoreException;
	
	/**
	 * 获取选中的退货商品信息
	 * @param userId
	 * @param orderId
	 * @param orderItemId
	 * @return
	 * @throws UhomeStoreException
	 */
	public OrderHead getReturnOrder(Integer userId,Integer orderId,  List<Integer> orderItemIds) throws UhomeStoreException;
	/**
	 * 查询退货订单项orderItem的应退金额
	 * @param orderItem 
	 * @param returnNum 实退数量
	 * @return
	 * @throws UhomeStoreException
	 */
	public BigDecimal getRefundAmount(OrderItem orderItem,Integer returnNum) throws UhomeStoreException;
}
