package com.ytoxl.module.uhome.uhomeorder.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderHead;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderItem;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturn;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturnItem;

/**
 * 退货订单表头
 * @author user
 *
 * @param <T>
 */
public interface OrderReturnMapper<T extends OrderReturn> extends BaseSqlMapper<T> {
	/**
	 * 根据订单id获取退货订单信息
	 * @param orderId
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrderReturn> getOrderReturnById( @Param("orderId") Integer orderId)  throws DataAccessException;
	
	/**
	 * 根据订单id获取退货详细信息
	 * @param orderId
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrderItem> listReturnItems( @Param("orderId") Integer orderId)  throws DataAccessException;
	
	/**
	 * 根据orderReturnId查询退货订单明细
	 * @param orderReturnId
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrderReturnItem> listOrderReturnItems(Integer orderReturnId) throws DataAccessException;
	
	
	/**
	 * 根据退货订单状态以及orderId查询退货订单明细
	 * @param orderReturnId
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrderReturnItem> listOrderReturnItems4Manager(Map<String, Object> searchParams) throws DataAccessException;
	
	/**
	 * 根据用户的id查找我所有的退货记录项
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 */
	public OrderReturn getMyOrderId(@Param("userId") Integer userId) throws DataAccessException;
	
	/**
	 * 根据用户的id,和外部的查询信息查找我所有的退货记录项
	 * @param OrderReturn
	 * @return
	 * @throws DataAccessException
	 */
	public OrderReturn getMyOrderId(BasePagination<OrderReturn> orderreturnpage)throws DataAccessException;
	/**
	 * 根据退货id 查询所有的退货信息
	 * @param orderReturnId
	 * @return
	 * @throws DataAccessException
	 */
	public OrderReturn getOrderReturnInformationById(Integer orderReturnId)throws DataAccessException;
	/**
	 * 修改退货订单状态
	 * @param orderReturn
	 * @return
	 * @throws DataAccessException
	 */
	public Integer updateStatus(OrderReturn orderReturn)  throws DataAccessException;
	/**
	 * 根据orderid查找当前所有的退货记录
	 * @param orderId
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrderReturn> getOrderReturnByOrderId(Integer orderId)  throws DataAccessException;
	/***
	 * 根据orderItemId 获取相应的 订单明细
	 * @param orderId
	 * @return
	 * @throws DataAccessException
	 */
	public OrderReturn getOrderItemReturn(Integer orderItemId)  throws DataAccessException;
	
	
	/***
	 * 根据orderItemId 获取相应的退货订单信息
	 * @param orderId
	 * @return OrderReturn
	 * @throws DataAccessException
	 */
	public OrderReturn selectOrderReturnByItemId(Integer orderItemId)  throws DataAccessException;
	

	/***
	 * 根据orderReturnId 获取相应的退货订单信息
	 * @param mapparams
	 * @return OrderReturn
	 * @throws DataAccessException
	 */
	public OrderReturn getOrderReturnByReturnId(Integer orderReturnId)  throws DataAccessException;
	
	/***
	 * 根据orderReturnId 获取相应的退货订单信息
	 * @param mapparams
	 * @return OrderReturn
	 * @throws DataAccessException
	 */
	public OrderReturn selectOrderReturnByReturnId(Map mapparams)  throws DataAccessException;

	/**
	 * 分页查询我的退货订单信息
	 * @param map
	 * @return
	 */
	public Integer searchReturnOrdersCount(Map<String,Object> map) throws DataAccessException;
	
	
	/**根据退货订单状态查询
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrderReturn> getOrderReturn4Manager(Map<String,Object> map) throws DataAccessException;
	
	/**分页查询我的退货订单总数
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public Integer searchOrdersReturn4ManagerCount(Map<String,Object> map) throws DataAccessException;
	
	/**分页查询我的退货订单
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrderHead> searchOrdersReturn4Manager(Map<String,Object> map) throws DataAccessException;

	public List<OrderReturn>  searchReturnOrders(Map<String,Object> map) throws DataAccessException;

}
