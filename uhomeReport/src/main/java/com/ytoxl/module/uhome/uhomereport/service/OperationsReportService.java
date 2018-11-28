package com.ytoxl.module.uhome.uhomereport.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.BuyerOperationsReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.ProductSalesReport;

/**
 * 运营管理报表Service
 * 
 * @author xu
 *
 */
public interface OperationsReportService {

	/**
	 * 查询商品销售报表
	 * 
	 * @param searchParams
	 * @return
	 * @throws UhomeStoreException
	 */
	public ProductSalesReport searchProductSalesReport(Map<String, Object> searchParams) throws UhomeStoreException;
	
	
	/**
	 * 买家运营报表显示查询
	 * 
	 * @author hwx
	 * @return
	 * @throws DataAccessException
	 */
	public BuyerOperationsReport searchBuyerOperationsReport(
			BasePagination<BuyerOperationsReport> buyerOperationsReportPage)
			throws UhomeStoreException;
}
