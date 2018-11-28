package com.ytoxl.module.uhome.uhomereport.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomereport.BaseTest;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.SalesDetailReport;

public class SalesDetailReportServiceTest extends BaseTest {
	@Autowired
	private ProductReportService productService;

	@Test
	public void TestPagination() throws UhomeStoreException {
		BasePagination<SalesDetailReport> salesDetailReportPage = new BasePagination<SalesDetailReport>();
		Map<String, String> map = new HashMap<String, String>();
		//map.put("startTime", "2013-01-01");
		//map.put("endTime", "2013-09-17");
		salesDetailReportPage.setParams(map);
//		long startTime=System.currentTimeMillis(); 
		productService.searchSalesDetailReports(salesDetailReportPage);
//		productService.listSalesDetailReports(salesDetailReportPage);
//		long endTime=System.currentTimeMillis(); //获取结束时间
//		System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
	}

}
