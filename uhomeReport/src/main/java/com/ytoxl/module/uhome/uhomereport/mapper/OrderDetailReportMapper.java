package com.ytoxl.module.uhome.uhomereport.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.OrderDetailReport;

/**
 *  
 * @author  
 *
 * @param <T>
 */
public interface OrderDetailReportMapper<T extends OrderDetailReport> extends BaseSqlMapper<T> {
	/**
	 * 根据订单时间获取订单信息
	 * @param orderNo
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrderDetailReport> searchOrderByCreateTime( Map<String,Object> map)  throws DataAccessException;
	public Integer searchOrderByCreateTimeCount(Map<String,Object> map) throws DataAccessException;
	
	 
}
