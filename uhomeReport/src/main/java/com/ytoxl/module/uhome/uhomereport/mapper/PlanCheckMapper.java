package com.ytoxl.module.uhome.uhomereport.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomereport.dataobject.PlanCheck;
 
/**
 * 结算
 * @author shengjiangming
 *
 * @param <T>
 */
public interface PlanCheckMapper<T extends PlanCheck> extends BaseSqlMapper<T> {
	/**
	 * 查询待对账的商家
	 * @return
	 */
	public List<PlanCheck> searchPlanCheckByPlanId(Map<String, Object> map) throws DataAccessException;
	
	public Integer searchPlanCheckByPlanIdCount(Map<String, Object> map) throws DataAccessException;

	public void updateStatusByPlanCheckId(Map<String,Object> map) throws DataAccessException;
	
	public PlanCheck getPlanCheckByPlanCheckId(Integer planCheckId) throws DataAccessException;

	
	/**
	 * 获取商家结算信息
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<PlanCheck> getSellerPlanCheckBySellerId(Map<String,Object> map) throws DataAccessException;

	public Integer searchSellerPlanCheckCount(Map<String,Object> map) throws DataAccessException;
	public void addPlanCheck(PlanCheck planCheck) throws DataAccessException;

	public void updateIsConfirmByPlanCheckId(Integer planCheckId) throws DataAccessException;
	
//	public PlanCheck searchePlanCheckByPlanIdAndSellerId(Map<String,Object> map) throws DataAccessException;
	
	public void updateFeedBackStatusByPlanIdAndSellerId(Map<String,Object> map) throws DataAccessException;
	
	
}
