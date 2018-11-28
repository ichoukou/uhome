package com.ytoxl.module.uhome.uhomereport.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.MonthReport;

/**
 * 报表
 * @author user
 *
 * @param <T>
 */
public interface MonthReportMapper<T extends MonthReport> extends BaseSqlMapper<T> {
	
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	public List<MonthReport> searchMonthReports(Map<String, Object> searchParams) throws DataAccessException;
	public Integer searchMonthReportsCount(Map<String, Object> searchParams) throws DataAccessException;
	
	/**
	 * 订单总数量及金额
	 * @return
	 * @throws DataAccessException
	 */
	public MonthReport getTotalPrice(Map<String, Object> searchParams)  throws DataAccessException;
	
	/**
	 * 退货订单总数量及金额
	 * @return
	 * @throws DataAccessException
	 */
	public MonthReport getTotalReturnPrice(Map<String, Object> searchParams)  throws DataAccessException;
	
	/**
	 * 导出报表列表
	 * @return
	 * @throws DataAccessException
	 */
	public List<MonthReport> listMonthReports (Map<String, Object> searchParams)  throws DataAccessException;
	
}
