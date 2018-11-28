package com.ytoxl.module.uhome.uhomereport.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.EverydaySalesDetailReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.SalesDetailReport;

/**
 * 报表
 * 
 * @author user
 * 
 * @param <T>
 */
public interface EverydaySalesDetailReportMapper<T extends EverydaySalesDetailReport> extends
		BaseSqlMapper<T> {

	/**
	 * 分页查询
	 * 
	 * @param map
	 * @return
	 */
	public List<EverydaySalesDetailReport> searchEverydaySalesDetailReports(
			Map<String, Object> searchParams) throws DataAccessException;

	/**
	 * 查询总数
	 * 
	 * @param map
	 * @return
	 */
	public Integer searchEverydaySalesDetailReportsCount(
			Map<String, Object> searchParams) throws DataAccessException;

	/**
	 * 报表数据
	 * 
	 * @param map
	 * @return
	 */
	public List<EverydaySalesDetailReport> listEverydaySalesDetailReports(Map<String, Object> searchParams)
			throws DataAccessException;
	
	
	/**
	 * 查询每天订单的数量及总额
	 * 
	 * @param map
	 * @return
	 */
	public List<EverydaySalesDetailReport> searchEverydaySalesDetail(
			Map<String, Object> searchParams) throws DataAccessException;
	
	/**
	 * 查询尺寸和颜色
	 * 
	 * @param map
	 * @return
	 */
	public List<EverydaySalesDetailReport> searchColourAndSize(
			Map<String, Object> searchParams) throws DataAccessException;

}
