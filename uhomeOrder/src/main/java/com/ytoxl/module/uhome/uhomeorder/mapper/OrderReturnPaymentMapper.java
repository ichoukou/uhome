package com.ytoxl.module.uhome.uhomeorder.mapper;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturnPayment;

/**
 * @author wangguoqing
 *
 */
public interface OrderReturnPaymentMapper<T extends OrderReturnPayment> extends BaseSqlMapper<OrderReturnPayment>  {
	/**
	 * 确认退款
	 * @param orderReturnPayment
	 * @throws DataAccessException
	 */
	public void updateStatus(OrderReturnPayment orderReturnPayment)throws DataAccessException;

	/**
	 * 根据orderReturnId 获取OrderReturnPayment 
	 * @param orderReturnId
	 * @return
	 * @throws DataAccessException
	 */
	public OrderReturnPayment getOrderReturnPaymentByOrderReturnId(Integer orderReturnId) throws DataAccessException;
	
	/**
	 * 更新退货订单的退款金额
	 * @param orderReturnPayment
	 * @throws DataAccessException
	 */
	public void updateOrderReturnPayAmount(OrderReturnPayment orderReturnPayment)throws DataAccessException;
}
