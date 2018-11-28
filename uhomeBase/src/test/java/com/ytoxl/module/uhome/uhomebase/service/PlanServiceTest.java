package com.ytoxl.module.uhome.uhomebase.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.BaseTest;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Plan;
import com.ytoxl.module.uhome.uhomebase.mapper.PlanMapper;

/**
 * @author wangguoqing
 *
 */
public class PlanServiceTest extends BaseTest {
	@Autowired
	PlanMapper<Plan> planMapper;
	@Autowired
	PlanService planService;
	
	@Test
	public void listPlanSecKillNotice() throws UhomeStoreException{
		log.info("######################:"+planService.listPlanSecKillNotice());
	}
	
	@Test
	public void listSellersByBrandId() throws UhomeStoreException{
//		List<SellerModel> sellers = planService.listSellersByBrandId(100000,
//				Plan.TYPE_SEC_KILL);
//		JSONArray jsonArray = JSONArray.fromObject(sellers);
		 
//		log.info(jsonArray.toString());
	}
	
	
}
