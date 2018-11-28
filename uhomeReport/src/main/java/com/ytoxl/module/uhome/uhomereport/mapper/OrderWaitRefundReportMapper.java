package com.ytoxl.module.uhome.uhomereport.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.EverydaySalesDetailReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.OrderWaitRefundReport;

/**
 * 报表
 * 
 * @author user
 * 
 * @param <T>
 */
public interface OrderWaitRefundReportMapper<T extends OrderWaitRefundReport>
		extends BaseSqlMapper<T> {

	/**
	 * 分页查询
	 * 
	 * @param map
	 * @return
	 */
	public List<OrderWaitRefundReport> searchReports(
			Map<String, Object> searchParams) throws DataAccessException;

	/**
	 * 查询总数
	 * 
	 * @param map
	 * @return
	 */
	public Integer searchReportsCount(Map<String, Object> searchParams)
			throws DataAccessException;

	/**
	 * 报表数据
	 * 
	 * @param map
	 * @return
	 */
	public List<OrderWaitRefundReport> listReports(
			Map<String, Object> searchParams) throws DataAccessException;

	/**
	 * 查询尺寸和颜色
	 * 
	 * @param map
	 * @return
	 */
	public List<OrderWaitRefundReport> searchColourAndSize(
			Map<String, Object> searchParams) throws DataAccessException;

}
