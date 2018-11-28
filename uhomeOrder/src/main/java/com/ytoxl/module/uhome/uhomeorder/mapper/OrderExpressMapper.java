package com.ytoxl.module.uhome.uhomeorder.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderExpress;

/**
 * 订单发运信息
 * @author user
 *
 * @param <T>
 */
public interface OrderExpressMapper<T extends OrderExpress> extends BaseSqlMapper<T> {
	/**
	 * 根据订单id获取发运信息
	 * @param orderId
	 * @return
	 * @throws DataAccessException
	 */
	public OrderExpress getOrderExpressById( @Param("orderId") Integer orderId)  throws DataAccessException;
	
	/**
	 * 确认发货
	 * @param orderExpress
	 * @throws DataAccessException
	 */
	public void updateOrderExpressByOrderId(OrderExpress orderExpress) throws DataAccessException;
	
	/**
	 * 根据退货的id查找退货物流信息
	 * @param orderReturnId
	 * @return
	 * @throws DataAccessException
	 */
	public OrderExpress getOrderExpressByOrderReturnId(Integer orderReturnId) throws DataAccessException;
	
	/**
	 * 跟orderId删除订单快递信息
	 * @param orderId
	 * @throws DataAccessException
	 */
	public Integer deleteOrderExpressByOrderId(Integer orderId)throws DataAccessException;
	
}
