package com.ytoxl.module.uhome.uhomereport.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.BrandSellPlanReport;

public interface BrandSellPlanReportMapper<T extends BrandSellPlanReport> extends BaseSqlMapper<T> {

	/**
	 * 按排期分页查询商家信息
	 * 
	 * @param map
	 * @return
	 */
	public List<BrandSellPlanReport> searchSellerByPlan(Map<String, Object> searchParams) throws DataAccessException;
	
	/**
	 * 按排期分页查询商家信息总记录数
	 * 
	 * @param searchParams
	 * @return
	 * @throws DataAccessException
	 */
	public Integer searchSellerCountByPlan(Map<String, Object> searchParams) throws DataAccessException;
	
	/**
	 * 导出报表列表
	 * @return
	 * @throws DataAccessException
	 */
	public List<BrandSellPlanReport> listSellerByPlan (Map<String, Object> searchParams)  throws DataAccessException;
	
	/**
	 * 获取商家的其他统计信息
	 * @param searchParams
	 * @return
	 * @throws DataAccessException
	 */
	public BrandSellPlanReport getSellerOtherCount (Map<String, Object> searchParams)  throws DataAccessException;
	
}
