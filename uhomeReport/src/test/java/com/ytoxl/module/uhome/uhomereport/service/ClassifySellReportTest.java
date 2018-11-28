package com.ytoxl.module.uhome.uhomereport.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.service.ProductService;
import com.ytoxl.module.uhome.uhomereport.BaseTest;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.ClassifySellReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.MemberSellReport;

public class ClassifySellReportTest extends BaseTest {
	@Autowired
	private ProductReportService productService;
	
	@Test
	public void TestPagination() throws UhomeStoreException{
		BasePagination<ClassifySellReport> classifySellReportPage = new BasePagination<ClassifySellReport>();
		Map<String,String> map = new HashMap<String,String>();
		map.put("beginTime","2013-01-01");
		map.put("endTime", "2013-09-17");
		classifySellReportPage.setParams(map);
		productService.searchClassifySellReports(classifySellReportPage);
	}
}
