package com.ytoxl.module.uhome.uhomeorder.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturnItem;

/**
 * @author wangguoqing
 *
 */
public interface OrderReturnItemMapper<T extends OrderReturnItem> extends BaseSqlMapper<T> {
	
	/**
	 * 根据退货订单id查询详细退货订单项
	 * @param orderReturnId
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrderReturnItem> listOrderReturnItemsByOrderReturnId(Integer orderReturnId)throws DataAccessException;
	
	/**
	 * 更新退货订单项的状态
	 * @param item
	 * @throws DataAccessException
	 */
	public void updateStatus(OrderReturnItem item) throws DataAccessException;
	
	/**
	 * 根据订单明细id 查询退货订单明细  退货管理用
	 * @param orderItemId
	 * @return
	 * @throws DataAccessException
	 */
	public Integer getOrderReturnItemsByOrderItemId(Integer orderItemId) throws DataAccessException;
	
	/**
	 * 查询当前订单产品已经退过的价钱总额 
	 * @param orderItemId
	 * @return
	 * @throws DataAccessException
	 */
	public BigDecimal getReturnedOrderItemSumPaymentAmount(Integer orderItemId) throws DataAccessException;

}
