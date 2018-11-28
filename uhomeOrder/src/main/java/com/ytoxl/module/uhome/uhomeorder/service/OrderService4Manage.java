package com.ytoxl.module.uhome.uhomeorder.service;

import java.math.BigDecimal;
import java.util.List;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderExpress;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderHead;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderItem;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturnExport;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturnItem;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturnPayment;

/**
 * 后台订单管理service
 *
 */
public interface OrderService4Manage {
	/**
	 * 根据id查找订单
	 * @param orderId
	 * @throws UhomeStoreException
	 */
	public OrderHead getOrderById(Integer orderId) throws UhomeStoreException; 
	
	/**
	 * 查找订单
	 * @return
	 * @throws UhomeStoreException
	 */
	public void searchOrders(BasePagination<OrderHead> orderPage) throws UhomeStoreException;
	
	/**
	 * 后台订单
	 * @param orderPage
	 * @throws UhomeStoreException
	 */
	public void searchOrders4Manager(BasePagination<OrderHead> orderPage) throws UhomeStoreException;
	
	/**
	 * 导出订单
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<OrderHead> listOrders(BasePagination<OrderHead> orderPage) throws UhomeStoreException;
	
	/**导出退货订单
	 * @param orderPage
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<OrderReturnExport> listReturnOrders(BasePagination<OrderHead> orderPage) throws UhomeStoreException;
	
	
	/**
	 * 批量发货
	 * @return
	 * @throws UhomeStoreException
	 */
	public void batchUpload(List<String[]> excels, Integer userId) throws UhomeStoreException;
	
	/**
	 * 确认发货
	 * @return
	 * @throws UhomeStoreException
	 */
	public String confirmSendProduct(OrderExpress orderExpress) throws UhomeStoreException;
	
	/**
	 * 审核
	 * @return
	 * @throws UhomeStoreException
	 */
	public String audit(OrderReturnItem orderReturnItem) throws UhomeStoreException;

	/**
	 * 同意退款
	 * @return
	 * @throws UhomeStoreException
	 */
	public String agreePayment(OrderReturnItem orderReturnItem) throws UhomeStoreException;
	
	/**
	 * 确认退款
	 * @param orderPayment
	 * @return
	 * @throws UhomeStoreException
	 */
	public String confirmPayment(OrderReturnPayment orderReturnPayment) throws UhomeStoreException;
	
	public BigDecimal getEveryRebatePrice(OrderItem orderItem,OrderReturnItem orderReturnItem) throws UhomeStoreException;
	
}
