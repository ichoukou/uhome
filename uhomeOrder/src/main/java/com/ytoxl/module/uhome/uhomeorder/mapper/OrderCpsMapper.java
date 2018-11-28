package com.ytoxl.module.uhome.uhomeorder.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderCps;

public interface OrderCpsMapper<T extends OrderCps> extends BaseSqlMapper<T> {
	/**
	 * 根据orderId查询orderCps相关数据
	 * @param orderId
	 * @return
	 * @throws DataAccessException
	 */
	public OrderCps getOrderCpsByOrderId(Integer orderId) throws DataAccessException;
	
	/**
	 * 查询为推送的数据(orderCps的状态为0订单状态为待发货)
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrderCps> listOrderCpsByStatus(Integer num)throws DataAccessException;
	
	/**
	 * 推送数据的总数
	 * @return
	 * @throws DataAccessException
	 */
	public Integer getOrderCpsByStatusCount()throws DataAccessException;
	
	/**推送后修改推送状态
	 * @param orderCpsId
	 * @param status
	 * @throws DataAccessException
	 */
	public void updateOrderCpsStatusByOrderCpsId(@Param("orderCpsId")Integer orderCpsId,@Param("status")Short status)throws DataAccessException;

}
