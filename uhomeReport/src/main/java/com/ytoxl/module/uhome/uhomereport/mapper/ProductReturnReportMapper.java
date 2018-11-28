package com.ytoxl.module.uhome.uhomereport.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.ProductReturnReport;

public interface ProductReturnReportMapper<T extends ProductReturnReport> extends BaseSqlMapper<T> {

	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	public List<ProductReturnReport> searchProductReturnReport(Map<String, Object> searchParams) throws DataAccessException;
	
	/**
	 * 查询退货商品总记录数
	 * 
	 * @param searchParams
	 * @return
	 * @throws DataAccessException
	 */
	public Integer searchProductReturnCount(Map<String, Object> searchParams) throws DataAccessException;
	
	/**
	 * 导出报表列表
	 * @return
	 * @throws DataAccessException
	 */
	public List<ProductReturnReport> listProductReturnReport (Map<String, Object> searchParams)  throws DataAccessException;
	
}
