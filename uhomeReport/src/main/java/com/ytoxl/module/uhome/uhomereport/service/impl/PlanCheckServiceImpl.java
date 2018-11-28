package com.ytoxl.module.uhome.uhomereport.service.impl;

 
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomereport.dataobject.PlanCheck;
import com.ytoxl.module.uhome.uhomereport.mapper.PlanCheckMapper;
import com.ytoxl.module.uhome.uhomereport.service.PlanCheckService;

@Service
public class PlanCheckServiceImpl implements PlanCheckService {
	
	private static Logger logger = LoggerFactory.getLogger(PlanCheckServiceImpl.class);
	 
	
	@Autowired
	private PlanCheckMapper<PlanCheck> planCheckMapper;
	
 
	@Override
	public void searchPlanCheckByPlanId(BasePagination<PlanCheck> planCheckPage) throws UhomeStoreException {
		Map<String, Object> searchParams = planCheckPage.getSearchParamsMap();
	 
		if (planCheckPage.isNeedSetTotal()) {
			Integer total = planCheckMapper.searchPlanCheckByPlanIdCount(searchParams);
			planCheckPage.setTotal(total);
		}
		Collection<PlanCheck> result = planCheckMapper.searchPlanCheckByPlanId(searchParams);
		planCheckPage.setResult(result); 
	}	
	
	@Override
	public void updateStatusByPlanCheckId(Integer planCheckId,Integer userId) throws UhomeStoreException {
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("planCheckId", planCheckId);
		 map.put("userId", userId);
		 planCheckMapper.updateStatusByPlanCheckId(map); 
	}




	@Override
	public PlanCheck getPlanCheckByPlanCheckId(Integer planCheckId) throws UhomeStoreException {
 		return planCheckMapper.getPlanCheckByPlanCheckId(planCheckId);
	}
	
	@Override
	public void listSellerPlanCheckBySellerId(Integer sellerId,BasePagination<PlanCheck> planCheckPage) throws UhomeStoreException {
		Map<String,Object> map = planCheckPage.getSearchParamsMap();
		map.put("sellerId", sellerId);
		if(planCheckPage.isNeedSetTotal()){
			Integer total = planCheckMapper.searchSellerPlanCheckCount(map);
			planCheckPage.setTotal(total);
		}
		Collection<PlanCheck> list = planCheckMapper.getSellerPlanCheckBySellerId(map);
		planCheckPage.setResult(list);

	}

	

	@Override
	public void updateIsConfirmByPlanCheckId(Integer planCheckId)
			throws UhomeStoreException {
		planCheckMapper.updateIsConfirmByPlanCheckId(planCheckId);
		
	}

	@Override
	public void savePlanCheck(PlanCheck planCheck) throws UhomeStoreException {
		planCheckMapper.addPlanCheck(planCheck);
		planCheck.setFeedbackTime(null);
		planCheck.setFeedbackStatus(null);
		planCheck.setUserId(null);
		planCheck.setFeedbackCount(2);
		
		planCheckMapper.addPlanCheck(planCheck);
	}

	@Override
	public void updateFeedBackStatus(Integer planId, Integer sellerId)
			throws UhomeStoreException {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("planId", planId);
		map.put("sellerId", sellerId);
		planCheckMapper.updateFeedBackStatusByPlanIdAndSellerId(map);
	}

}
