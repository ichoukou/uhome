package com.ytoxl.module.uhome.uhomeorder.mapper;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderPayment;

/**
 * 订单表头
 * @author user
 *
 * @param <T>
 */
public interface OrderPaymentMapper<T extends OrderPayment> extends BaseSqlMapper<T> {
	/**
	 * 修改支付状态
	 * @param OrderPayment
	 * @return
	 * @throws DataAccessException
	 */
	public Integer updateStatus(OrderPayment orderPayment)  throws DataAccessException;
	
	/**
	 * 根据orderId查找payment
	 * @param orderId
	 * @return
	 * @throws DataAccessException
	 */
	public OrderPayment getOrderPaymentByOrderId(Integer orderId)  throws DataAccessException;
	
	/**
	 * 根据优惠卷平摊抵扣后更新实际支付金额
	 * @param orderPayment
	 * @return
	 * @throws DataAccessException
	 */
	public Integer updateOrderActualPayment(OrderPayment orderPayment)  throws DataAccessException;
	
	/**
	 * 根据orderId删除orderpayemnt信息
	 * 
	 * @throws DataAccessException
	 */
	public Integer deleteOrderPaymentByOrderId(Integer orderId)throws DataAccessException;
}
