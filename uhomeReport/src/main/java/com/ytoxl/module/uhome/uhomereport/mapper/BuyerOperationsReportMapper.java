package com.ytoxl.module.uhome.uhomereport.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.BuyerOperationsReport;

/**
 * 报表
 * 
 * @author hwx
 * 
 * @param <T>
 */
public interface BuyerOperationsReportMapper<T extends BuyerOperationsReport> extends
		BaseSqlMapper<T> {

	/**
	 * 买家运营报表显示查询
	 * 
	 * @param map
	 * @return
	 */
	public BuyerOperationsReport searchBuyerOperationsReports(
			Map<String, Object> searchParams) throws DataAccessException;



	

}
