package com.ytoxl.module.uhome.uhomereport.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomereport.BaseTest;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.BuyerOperationsReport;

public class BuyerOperationsReportServiceTest extends BaseTest {
	@Autowired
	private OperationsReportService operationsReportService;

	@Test
	public void TestPagination() throws UhomeStoreException {
		BasePagination<BuyerOperationsReport> buyerOperationsReportPage = new BasePagination<BuyerOperationsReport>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("startTime", "2013-06-25");
		map.put("endTime", "2013-06-26");
		buyerOperationsReportPage.setParams(map);
		operationsReportService.searchBuyerOperationsReport(buyerOperationsReportPage);
	}

}
