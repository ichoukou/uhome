package com.ytoxl.module.uhome.uhomeorder.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderHead;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturnExport;

/**
 * 订单表头
 * @author user
 *
 * @param <T>
 */
public interface OrderHeadMapper<T extends OrderHead> extends BaseSqlMapper<T> {
	/**
	 * 根据订单号获取订单信息
	 * @param orderNo
	 * @return
	 * @throws DataAccessException
	 */
	public OrderHead getOrderHeadByOrderNo( @Param("orderNo") String orderNo)  throws DataAccessException;
	
	/**
	 * 分页查询我的订单信息
	 * @param map
	 * @return
	 */
	public List<OrderHead> searchOrders(Map<String,Object> map) throws DataAccessException;
	public Integer searchOrdersCount(Map<String,Object> map) throws DataAccessException;
	
	/**
	 * 前台我的订单 除了待付款状态的查询
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrderHead> searchOrders4Front(Map<String,Object> map) throws DataAccessException;
	public Integer searchOrders4FrontCount(Map<String,Object> map) throws DataAccessException;
	
	/**
	 * 前台我的订单 待付款状态的查询
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrderHead> searchOrders4FrontMyOrders(Map<String,Object> map) throws DataAccessException;
	public Integer searchOrders4FrontMyOrdersCount(Map<String,Object> map) throws DataAccessException;
	
	/**
	 * 根据订单id获取订单信息
	 * @param orderId
	 * @return
	 * @throws DataAccessException
	 */
	public OrderHead getOrderById( @Param("orderId") Integer orderId)  throws DataAccessException;
	/**
	 * 查询要导出的订单信息
	 * @param map
	 * @return
	 */
	public List<OrderHead> listOrders(Map<String,Object> map) throws DataAccessException;
	
	/**
	 * 查询要导出的退货订单信息
	 * @param map
	 * @return
	 */
	public List<OrderReturnExport> listReturnOrders(Map<String,Object> map) throws DataAccessException;
	
	
	
	/**
	 * 根据订单id获取订单产品明细
	 * @param orderId
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrderHead> listProductSkus(@Param("orderId") Integer orderId)  throws DataAccessException;
	
	/**
	 * 分页查询我的退货订单信息
	 * @param map
	 * @return
	 */
	public Integer searchDeliveredOrdersCount(Map<String,Object> map) throws DataAccessException;
	public List<OrderHead>  searchDeliveredOrders(Map<String,Object> map) throws DataAccessException;
	
	/***
	 * 根据orderid 查询我的退货订单的默认地址
	 * @param OrderId
	 * @return
	 * @throws DataAccessException
	 */
	public OrderHead searchHeadAddress(Integer OrderId) throws DataAccessException;

	public void getOrderItemById(Integer orderId)throws DataAccessException;
	
	/**
	 * 更改订单状态
	 * @param orderHead
	 * @return
	 * @throws DataAccessException
	 */
	public Integer updateStatus(OrderHead orderHead)  throws DataAccessException;
	
	/**
	 * 更新收货时间
	 * @param orderId
	 * @return
	 * @throws DataAccessException
	 */
	public Integer updateReceiveProductTime(Integer orderId) throws DataAccessException;
	
	/**
	 * 更新发货时间
	 * @param orderId
	 * @return
	 * @throws DataAccessException
	 */
	public Integer updateSendProductTimeAndOrderStatus(OrderHead OrderHead) throws DataAccessException;
	
	/**
	 * 查询订单根据当前用户id 
	 * @param orderHead
	 * @return
	 * @throws DataAccessException
	 */
	public OrderHead getOrderHead(OrderHead orderHead)  throws DataAccessException;
	/***
	 * 根据orderId修改当前的订单状态
	 * @param orderId
	 * @return
	 * @throws DataAccessException
	 */

	public Integer updateStatusByOrderId(Integer orderId)  throws DataAccessException;
	
	/**
	 * 根据订单id获取订单产品明细
	 * @param defalutDay
	 * @return
	 * @throws DataAccessException
	 */
	public Integer updateStatusToFinished(@Param("defaultDay") Integer defaultDay, @Param("limit") Integer limit)  throws DataAccessException;
	
	/**
	 * cancelTime时间内更新limit条未付款数据为取消状态。
	 * @param cancelTime
	 * @param limit
	 * @return
	 * @throws DataAccessException
	 */
	public Integer updateStatusToCanceled(@Param("cancelTime") Integer cancelTime, @Param("limit") Integer limit)  throws DataAccessException;
	
	/**
	 * 查询cancelTime时间内limit条未付款订单数据。
	 * @param cancelTime
	 * @param limit
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrderHead> listNotPayOrders(@Param("cancelTime") Integer cancelTime, @Param("limit") Integer limit)  throws DataAccessException;
	
	/**
	 * 后台订单分页
	 * @param map
	 * @throws DataAccessException
	 */
	public List<OrderHead> searchOrders4Manager(Map<String,Object> map) throws DataAccessException;
	public Integer searchOrders4ManagerCount(Map<String,Object> map) throws DataAccessException;
	
	/**
	 * 根据parentOrderId查询所有相同的订单
	 * @param parentOrderId
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrderHead> listOrderByParentOrderId(@Param("parentOrderId")Integer parentOrderId,@Param("buyerId")Integer buyerId,@Param("status")String status) throws DataAccessException;

	/**
	 * 对于使用优惠券的订单会有一个父订单，子订单的parentid为父订单id
	 * @param orderId
	 * @param parentOrderId
	 * @return
	 * @throws DataAccessException
	 */
	public Integer updateOrderParentId(@Param("orderId")Integer orderId, @Param("parentOrderId") Integer parentOrderId) throws DataAccessException;
	
	/**
	 * 检查订单合法性
	 * @param orderHead
	 * @return
	 * @throws DataAccessException
	 */
	public OrderHead checkUserOrder(OrderHead orderHead)  throws DataAccessException;
	
	
	/**
	 * 根据orderId删除orderhead
	 * 
	 * 
	 * @throws DataAccessException
	 */
	public Integer deleteOrderHeadByOrderId(Integer orderId)throws DataAccessException;
	
	/**
	 * 根据父订单id查询所有订单
	 * @param parentOrderId
	 * @return
	 * @throws DataAccessException
	 */
	public Integer getOrderHeadCountByOrderParentId(Integer parentOrderId)throws DataAccessException;
}
