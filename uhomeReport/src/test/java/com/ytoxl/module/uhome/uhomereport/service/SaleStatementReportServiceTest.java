package com.ytoxl.module.uhome.uhomereport.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomereport.BaseTest;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.OrderWaitSendReport;

public class SaleStatementReportServiceTest extends BaseTest {

	@Autowired
	private SaleStatementReportService saleStatementReportSevice;
	
	@Test
	public void TestPagination() throws UhomeStoreException{
		BasePagination<OrderWaitSendReport> orderWaitSendReportPage = new BasePagination<OrderWaitSendReport>();
		Map<String,String> map = new HashMap<String,String>();
		//map.put("stime","2013-07-01");
		//map.put("etime", "2013-07-31");
		orderWaitSendReportPage.setParams(map);
		orderWaitSendReportPage.setCurrentPage(1);
		orderWaitSendReportPage.setLimit(10);
		saleStatementReportSevice.searchOrderWaitSendReport(orderWaitSendReportPage);
		log.info("@@@@@@@@"+orderWaitSendReportPage.getResult().size());
	}
}
