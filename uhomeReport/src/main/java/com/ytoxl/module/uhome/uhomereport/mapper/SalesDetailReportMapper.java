package com.ytoxl.module.uhome.uhomereport.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.SalesDetailReport;

/**
 * 报表
 * 
 * @author user
 * 
 * @param <T>
 */
public interface SalesDetailReportMapper<T extends SalesDetailReport> extends
		BaseSqlMapper<T> {

	/**
	 * 分页查询
	 * 
	 * @param map
	 * @return
	 */
	public List<SalesDetailReport> searchSalesDetailReports(
			Map<String, Object> searchParams) throws DataAccessException;

	/**
	 * 查询总数
	 * 
	 * @param map
	 * @return
	 */
	public Integer searchSalesDetailReportsCount(
			Map<String, Object> searchParams) throws DataAccessException;

	/**
	 * 报表数据
	 * 
	 * @param map
	 * @return
	 */
	public List<SalesDetailReport> listSalesDetailReports(Map<String, Object> searchParams)
			throws DataAccessException;
	
	/**
	 * 查询排期范围内的总金额和总数量
	 * 
	 * @param map
	 * @return
	 */
	public List<SalesDetailReport> searchTotalNumAndAmount(
			Map<String, Object> searchParams) throws DataAccessException;
	

}
