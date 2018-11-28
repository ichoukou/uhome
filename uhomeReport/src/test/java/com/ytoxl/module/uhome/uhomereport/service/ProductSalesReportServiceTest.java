package com.ytoxl.module.uhome.uhomereport.service;

import java.util.HashMap;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomereport.BaseTest;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.ProductSalesReport;

public class ProductSalesReportServiceTest extends BaseTest {

	@Autowired
	private OperationsReportService operationsReportService;
	
	@Test
	public void testSearchProductSalesReport() throws UhomeStoreException{
		HashMap<String, Object> searchParams = new HashMap<String, Object>();
		//设置查询时间
		searchParams.put("stime", "2013-07-01");
		searchParams.put("etime", "2013-07-31");
		//查询商品销售报
		ProductSalesReport productSalesReport = operationsReportService.searchProductSalesReport(searchParams);
		
		
		log.info("日期:"+productSalesReport.getDate()+",销售金额:"
				+productSalesReport.getOrderAmount()+",订单数:"
				+productSalesReport.getOrderNum()+",客单价:"
				+productSalesReport.getUnitPrice());
	}
}
