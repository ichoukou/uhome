package com.ytoxl.module.uhome.uhomereport.mapper;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomereport.BaseTest;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.ProductReturnReport;

public class ProductReturnReportMapperTest extends BaseTest{

	@Autowired
	private ProductReturnReportMapper<ProductReturnReport> productReturnReportMapper;
	
	@Test
	public void searchProductReturnCount(){
		HashMap<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("stime", "2013-07-01");
		searchParams.put("etime", "2013-07-31");

		Integer count = productReturnReportMapper.searchProductReturnCount(searchParams);	
		System.out.println("共"+count+"条记录");
	}
	
	//@Test
	public void searchProductReturn(){
		HashMap<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("start", 0);
		searchParams.put("limit", 10);
		searchParams.put("stime", "2013-08-01");
		searchParams.put("etime", "2013-08-31");
		List<ProductReturnReport> list=null;
		list = productReturnReportMapper.searchProductReturnReport(searchParams);	

		for (ProductReturnReport p:list) {
			System.out.print("商家="+p.getSellerName());
			System.out.print(",品牌="+p.getBrandName());
			System.out.print(",商品品类="+p.getCategoryName());
			System.out.print(",商品名称="+p.getProductName());
			System.out.print(",skuCode="+p.getSkuCode());
			System.out.print(",退货数量="+p.getRetCount());
			System.out.print(",退货金额="+p.getRetAmount());
			System.out.print("\r\n");
		}
	}
	
	//@Test
	public void listProductReturnReport(){
		HashMap<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("stime", "2013-09-01");
		searchParams.put("etime", "2013-09-31");
		List<ProductReturnReport> list=null;
		list = productReturnReportMapper.listProductReturnReport(searchParams);	

		for (ProductReturnReport p:list) {
			System.out.print("商家="+p.getSellerName());
			System.out.print(",品牌="+p.getBrandName());
			System.out.print(",商品品类="+p.getCategoryName());
			System.out.print(",商品名称="+p.getProductName());
			System.out.print(",skuCode="+p.getSkuCode());
			System.out.print(",退货数量="+p.getRetCount());
			System.out.print(",退货金额="+p.getRetAmount());
			System.out.print("\r\n");
		}
	}
}
