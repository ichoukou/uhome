package com.ytoxl.module.uhome.uhomereport.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.OrderWaitSendReport;

public interface OrderWaitSendReportMapper<T extends OrderWaitSendReport> extends BaseSqlMapper<T> {

	/**
	 * 分页查询待发货订单
	 * 
	 * @param map
	 * @return
	 */
	public List<OrderWaitSendReport> searchOrderWaitSendReport(Map<String, Object> searchParams) throws DataAccessException;
	
	/**
	 * 查询待发货订单报表总记录数
	 * 
	 * @param searchParams
	 * @return
	 * @throws DataAccessException
	 */
	public Integer getOrderWaitSendCount(Map<String, Object> searchParams) throws DataAccessException;
	
	/**
	 * 导出待发货订单报表
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrderWaitSendReport> listOrderWaitSendReport (Map<String, Object> searchParams)  throws DataAccessException;
	
}
