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
public class PlanMapperTest extends BaseTest {
	
	@Autowired
	PlanMapper<Plan> planMapper;
	@Autowired
	PlanService planService;
	
	@Test
	public void getPlanListNoticeByCurrentTimeAndType() {
		//planMapper.getPlanListNoticeByCurrentTimeAndType((short)1);
		planMapper.getPlanListSecKillNoticeByCurrentTime();
		planMapper.getPlanListSpecialSellerNoticeByCurrentTime();

	}
	
	@Test
	public void getPlanListByCurrentTimeAndTypeAndStatus() throws UhomeStoreException {
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("type", Plan.PLAN_TYPE_SEC_KILL);
//		map.put("status", Plan.PLAN_STATUS_RELEASE);
//		List<Plan> plans = planMapper.getPlanListByCurrentTimeAndTypeAndStatus(map);
//		log.info("####################:"+plans.size());
		planService.listPlanSecKillToday();
	}
	
	@Test
	public void getPlanListSecKillNoticeByCurrentTime(){
		log.info("@@@@@@@@@@@@@@@@@@@#:"+planMapper.getPlanListSecKillNoticeByCurrentTime());
	}

}
