package com.ytoxl.module.uhome.uhomereport.mapper;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.ProductSalesReport;

public interface ProductSalesReportMapper<T extends ProductSalesReport> extends BaseSqlMapper<T> {

	/**
	 * 查询商品销售报表
	 * 
	 * @param searchParams
	 * @return
	 * @throws DataAccessException
	 */
	public ProductSalesReport searchProductSalesReport(Map<String, Object> searchParams) throws DataAccessException;
}
