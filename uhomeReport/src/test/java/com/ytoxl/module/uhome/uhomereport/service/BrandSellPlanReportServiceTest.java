package com.ytoxl.module.uhome.uhomereport.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomereport.BaseTest;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.BrandSellPlanReport;

public class BrandSellPlanReportServiceTest extends BaseTest {

	@Autowired
	private ProductReportService prodcutReportService;
	
	@Test
	public void TestPagination() throws UhomeStoreException{
		BasePagination<BrandSellPlanReport> sellerBrandReportPage = new BasePagination<BrandSellPlanReport>();
		Map<String,String> map = new HashMap<String,String>();
		map.put("time","2013-07");
		sellerBrandReportPage.setParams(map);
		sellerBrandReportPage.setCurrentPage(1);
		sellerBrandReportPage.setLimit(10);
		prodcutReportService.searchBrandSellReport(sellerBrandReportPage);
		log.info("@@@@@@@@@@@@@@@@@@@:"+sellerBrandReportPage.getResult().size());
	}
}
