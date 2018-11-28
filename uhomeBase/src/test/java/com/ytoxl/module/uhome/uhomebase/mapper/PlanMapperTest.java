package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.BaseTest;
import com.ytoxl.module.uhome.uhomebase.dataobject.Plan;

/**
 * @author wangguoqing
 *
 */
public class PlanMapperTest extends BaseTest {
	
	@Autowired
	public PlanMapper planMapper;
	
	@Test
	public void getPlanByPlanId() {
		

	}
	
	@Test
	public void searchSpecialSellerPlans(){
		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("productCategoryId", 100002);
		map.put("searchTime", "2013-03-05");
		map.put("start", 0);
		map.put("limit", 10);
		List<Plan> plans = planMapper.searchSpecialSellerPlans(map);
		log.info("================="+plans.size());
		
	}
	
	@Test
	public void searchSecKillPlans(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("productCategoryId", 100000);
		map.put("searchTime", "2013-03-05");
		List<Plan> plans = planMapper.searchSecKillPlans(map);
		log.info("================="+plans.size());
		
	}
	
	@Test
	public void getPlanListLastOnlineByCategoryId(){
		List<Plan> plans = planMapper.getPlanListLastOnlineByCategoryId(100000);
		log.info("~~~~~~~~~~~~~~~~~~~~~~~~:plans"+plans);
	}
	
	


}
