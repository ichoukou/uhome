package com.ytoxl.module.uhome.uhomeorder.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderItem;

/**
 * 订单表头
 * @author user
 *
 * @param <T>
 */
public interface OrderItemMapper<T extends OrderItem> extends BaseSqlMapper<T> {
	
	/**
	 * 根据订单id获取订单明细
	 * @param orderId
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrderItem> listOrderItemsByOrderId(@Param("orderId") Integer orderId)  throws DataAccessException;

	/**
	 * 根据订单id获取订单明细，前台使用
	 * @param orderId
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrderItem> listOrderItemsByOrderId4Front(@Param("orderId") Integer orderId)  throws DataAccessException;
	
	

	/**
	 * 根据订单id获取订单项
	 * @param orderId
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrderItem> listItemsOrderId(@Param("orderId") Integer orderId)  throws DataAccessException;
	
	/**
	 * 根据明细Id获取选中的订单明细
	 * @param orderId
	 * @param orderItemIds
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrderItem> listOrderItemsByIds(@Param("orderId") Integer orderId, @Param("orderItemIds") List<Integer> orderItemIds) throws DataAccessException;

	/**
	 * 根据订单明细Idid获取订单可退货数量
	 * @param orderItemId
	 * @return
	 * @throws DataAccessException
	 */
	public Integer getOrderItemReturnNumById(@Param("orderItemId") Integer orderItemId) throws DataAccessException;

	/**
	 * 通过orderId获取订单明细  导出
	 * @param orderId
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrderItem> getOrderItemByOrderId(Integer orderId)throws DataAccessException;
	/**
	 * 通过orderId获取订单明细  导出
	 * @param orderId
	 * @return
	 * @throws DataAccessException
	 */
	public OrderItem getOrderItemByItemId(Integer itemId)throws DataAccessException;
	/**
	 * 更新每条订单明细中折扣后的总金额
	 * @param orderItem
	 * @return
	 * @throws DataAccessException
	 */
	public Integer updateOrderItemReducedPrice(OrderItem orderItem) throws DataAccessException;

	/**
	 * 检查当前提交退货订单的合法性
	 * @param userId
	 * @param orderItemId
	 * @return
	 * @throws DataAccessException
	 */
	public Integer checkOrderItem(@Param("userId") Integer userId, @Param("orderItemId") Integer orderItemId) throws DataAccessException;
	
	/**
	 * 根据orderId删除orderItem
	 * 
	 * @throws DataAccessException
	 */
	public Integer deleteOrderItemByOrderId(Integer orderId)throws DataAccessException;
	
	/**
	 * 推送用到的数据
	 * @param orderId
	 * @return
	 * @throws DataAccessException
	 */
	public  List<OrderItem> getOrderItemByOrderIdForPush(Integer orderId)throws DataAccessException;
}
