package com.ytoxl.module.uhome.uhomereport.service;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomereport.dataobject.PlanCheck;
 
/**
 * 结算
 * @author shengjianming
 *
 */
public interface PlanCheckService {
	
	/**
	 * 获取待结算商家
	 * @return
	 */
	public void searchPlanCheckByPlanId(BasePagination<PlanCheck> planCheckPage) throws UhomeStoreException;
	
	public void updateStatusByPlanCheckId(Integer planCheckId,Integer userId) throws UhomeStoreException;
	
	public PlanCheck getPlanCheckByPlanCheckId(Integer planCheckId) throws UhomeStoreException;
	
	
	/**
	 * 根据商家id获取商家结算信息
	 * @param planCheckPage
	 * @return
	 * @throws UhomeStoreException
	 */
	public void listSellerPlanCheckBySellerId(Integer sellerId,BasePagination<PlanCheck> planCheckPage) throws UhomeStoreException;
	 
	/**
	 * 保存商家结算初始信息
	 * @param planCheck
	 * @throws UhomeStoreException
	 */
	public void savePlanCheck(PlanCheck planCheck) throws UhomeStoreException;
	
	public void updateIsConfirmByPlanCheckId(Integer planCheckId) throws UhomeStoreException;

	public void updateFeedBackStatus(Integer planId,Integer sellerId) throws UhomeStoreException;
}
