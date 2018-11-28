package com.ytoxl.module.uhome.uhomereport.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomereport.BaseTest;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.EverydaySalesDetailReport;

public class EverySalesDetailReportServiceTest extends BaseTest {
	@Autowired
	private ProductReportService productService;

	@Test
	public void TestPagination() throws UhomeStoreException {
		BasePagination<EverydaySalesDetailReport> everydaySalesDetailReportPage = new BasePagination<EverydaySalesDetailReport>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("time", "2013-08");
		everydaySalesDetailReportPage.setParams(map);
		productService.searchEverydaySalesDetailReports(everydaySalesDetailReportPage);
	}

}
