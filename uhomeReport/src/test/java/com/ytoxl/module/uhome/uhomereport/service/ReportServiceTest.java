package com.ytoxl.module.uhome.uhomereport.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomereport.BaseTest;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.AreaSellReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.OrderDetailReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.SellerReport;

/**
 * @author shengjianming
 *
 */
public class ReportServiceTest extends BaseTest {
 
	@Autowired
	ProductReportService productReportService;
	
	@Test
	public void searchOrdersByCreateTime() throws UhomeStoreException{		
 		BasePagination<OrderDetailReport> orderReportDetailPage = new BasePagination<OrderDetailReport>();
 		Map<String,String> map = new HashMap<String,String>();
		map.put("beginTime", "2013-08-02");
		map.put("endTime", "2013-09-01");
		orderReportDetailPage.setParams(map);
		orderReportDetailPage.setCurrentPage(1);
 		orderReportDetailPage.setLimit(5);
 		this.productReportService.searchOrdersByCreateTime(orderReportDetailPage);
		log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@:"+orderReportDetailPage.getResult().size());
 	}
	
	@Test
	public void searchSellerByUserName() throws UhomeStoreException{
		BasePagination<SellerReport> sellerReportPage = new BasePagination<SellerReport>();
 		Map<String,String> map = new HashMap<String,String>();
	//	map.put("beginTime", "2013-08-02");
	//	map.put("endTime", "2013-09-01");
 		sellerReportPage.setParams(map);
 		sellerReportPage.setCurrentPage(1);
 		sellerReportPage.setLimit(6);
 		this.productReportService.searchSellerByUserName(sellerReportPage);
		log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@:"+sellerReportPage.getResult().size());
	}
	
	@Test
	public void searchAreaSellByBrand() throws UhomeStoreException{
		BasePagination<AreaSellReport> areaSellReportDetailPage = new BasePagination<AreaSellReport>();
 		Map<String,String> map = new HashMap<String,String>();
		//map.put("beginTime", "2013-08-02");
		//map.put("endTime", "2013-09-01");
 		map.put("brandId", "5");
 		areaSellReportDetailPage.setParams(map);
 		areaSellReportDetailPage.setCurrentPage(1);
 		areaSellReportDetailPage.setLimit(7);
 		this.productReportService.searchAreaSellByBrand(areaSellReportDetailPage);
		log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@:"+areaSellReportDetailPage.getResult().size());
	}
}
