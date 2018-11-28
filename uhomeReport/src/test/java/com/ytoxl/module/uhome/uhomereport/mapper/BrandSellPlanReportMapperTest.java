package com.ytoxl.module.uhome.uhomereport.mapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomereport.BaseTest;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.BrandSellPlanReport;

public class BrandSellPlanReportMapperTest extends BaseTest {

	@Autowired
	private BrandSellPlanReportMapper<BrandSellPlanReport> brandSellPlanReportMapper;
	
	//@Test
	public void searchSellerCountByPlan(){
		HashMap<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("time", "2013-07");

		Integer count = brandSellPlanReportMapper.searchSellerCountByPlan(searchParams);	
		log.info("共"+count+"条记录");
	}
	
	
	//@Test
	public void listSellerByPlan(){
		HashMap<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("time", "2013-07");
		
		List<BrandSellPlanReport> brandSellPlanReports = brandSellPlanReportMapper.listSellerByPlan(searchParams);
		for(BrandSellPlanReport brandSell : brandSellPlanReports){
			log.info("planId="+brandSell.getPlanId()+",brandId="+brandSell.getBrandId()
					+",sellerName="+brandSell.getSellerName()+",brandName="+brandSell.getBrandName()
					+",categoryName="+brandSell.getCategoryName()+",onlineSkuNum="+brandSell.getOnlineSkuNum()
					+",startTime="+brandSell.getStartTime());
		}
	}
	
	//@Test
	public void searchSellerByPlan(){
		HashMap<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("time", "2013-07");
		searchParams.put("start", 0);
		searchParams.put("limit", 5);
		//按排期分页查询商家信息
		List<BrandSellPlanReport> brandSellPlanReports = brandSellPlanReportMapper.searchSellerByPlan(searchParams);
		for(BrandSellPlanReport brandSell : brandSellPlanReports){
			log.info("planId="+brandSell.getPlanId()+",brandId="+brandSell.getBrandId()
					+",sellerName="+brandSell.getSellerName()+",brandName="+brandSell.getBrandName()
					+",categoryName="+brandSell.getCategoryName()+",onlineSkuNum="+brandSell.getOnlineSkuNum()
					+",startTime="+brandSell.getStartTime());
		}
	}
	
	@Test
	public void getSellerOtherCount(){
		//针对现有的一条记录做其他统计
		BrandSellPlanReport brandSell = new BrandSellPlanReport();
		brandSell.setSellerName("商家统计");
		brandSell.setBrandName("品牌统计");
		//查询需要用的参数
		brandSell.setPlanId(18);
		brandSell.setBrandId(1);
		brandSell.setSellerId(2);
		//设置查询参数
		HashMap<String, Object> otherSearchParams = new HashMap<String, Object>();
		otherSearchParams.put("time", "2013-07");
		otherSearchParams.put("planId", brandSell.getPlanId());
		otherSearchParams.put("brandId", brandSell.getBrandId());
		otherSearchParams.put("sellerId", brandSell.getSellerId());
		
		//查询商家的其他统计信息
		BrandSellPlanReport temp = brandSellPlanReportMapper.getSellerOtherCount(otherSearchParams);
		
		brandSell.setSkuSellNum(temp.getSkuSellNum());
		//判断销售数量是否为NULL
		if(temp.getSellNum() != null){
			brandSell.setSellNum(temp.getSellNum());
		}else{
			brandSell.setSellNum(0);
		}
		//判断销售金额是否为NULL
		if(temp.getSellAmount() != null){
			brandSell.setSellAmount(temp.getSellAmount());
		}else{
			brandSell.setSellAmount(new BigDecimal(0.00));
		}
		
			
		log.info("planId="+brandSell.getPlanId()+",brandId="+brandSell.getBrandId()
				+",sellerName="+brandSell.getSellerName()+",brandName="+brandSell.getBrandName()
				+",categoryName="+brandSell.getCategoryName()+",onlineSkuNum="+brandSell.getOnlineSkuNum()
				+",skuSellNum="+brandSell.getSkuSellNum()
				+",sellNum="+brandSell.getSellNum()+",sellAmount="+brandSell.getSellAmount()
				+",startTime="+brandSell.getStartTime());
	
	}
}
