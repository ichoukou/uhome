package com.ytoxl.module.uhome.uhomereport.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomereport.BaseTest;
import com.ytoxl.module.uhome.uhomereport.dataobject.PlanCheck;
import com.ytoxl.module.uhome.uhomereport.mapper.PlanCheckMapper;


public class PlanCheckServiceTest extends BaseTest {
	@Autowired
	private PlanCheckService planCheckService;
	@Autowired
	private PlanCheckMapper planCheckMapper;

	@Test
	public void listSellerPlanChcek() throws UhomeStoreException{
		BasePagination<PlanCheck> planCheckPage = new BasePagination<PlanCheck>();
		planCheckService.listSellerPlanCheckBySellerId(1,planCheckPage);
	}
	
	public void testlistPlanCheckByPlanId() throws UhomeStoreException {

		BasePagination<PlanCheck> planCheckPage = new BasePagination<PlanCheck>();
 
		Map<String,String> map = new HashMap<String,String>();
		map.put("feedbackCount", "1");
		map.put("name", "八一");
		planCheckMapper.searchPlanCheckByPlanId(map);
 
 		log.info("=====================" + planCheckPage.getResult().size());
 
	}

	@Test
	public void testaddPlanCheck() throws UhomeStoreException {

		PlanCheck planCheck = new PlanCheck();
 
 		planCheck.setPlanId(18);
 		planCheck.setSellerId(4);
 		planCheck.setFeedbackCount(1);
 		//planCheckMapper.add(planCheck);
 
 		log.info("=====================" );
 
	}
	
	@Test
	public void testupdateFeedbackStatus() throws UhomeStoreException {

		Map<String,Object> map = new HashMap<String, Object>();
		map.put("planId", 25);
		map.put("sellerId", 1);
		planCheckMapper.updateFeedBackStatusByPlanIdAndSellerId(map);
 
	}
 
}
