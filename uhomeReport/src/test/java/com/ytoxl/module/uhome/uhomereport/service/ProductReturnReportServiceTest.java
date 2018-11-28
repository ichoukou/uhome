package com.ytoxl.module.uhome.uhomereport.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomereport.BaseTest;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.ProductReturnReport;

public class ProductReturnReportServiceTest extends BaseTest {

	@Autowired
	private ProductReportService prodcutReportService;
	
	@Test
	public void TestPagination() throws UhomeStoreException{
		BasePagination<ProductReturnReport> productReturnPage = new BasePagination<ProductReturnReport>();
		Map<String,String> map = new HashMap<String,String>();
		map.put("stime","2013-07-01");
		map.put("etime", "2013-07-31");
		productReturnPage.setParams(map);
		productReturnPage.setCurrentPage(1);
		productReturnPage.setLimit(10);
		prodcutReportService.searchProductReturn(productReturnPage);
		log.info("@@@@@@@@@@@@@@@@@@@:"+productReturnPage.getResult().size());
	}
}
