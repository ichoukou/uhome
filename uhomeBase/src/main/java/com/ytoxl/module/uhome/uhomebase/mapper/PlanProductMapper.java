package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.dataobject.PlanProduct;

public interface PlanProductMapper<T extends PlanProduct> extends BaseSqlMapper<T> {
	
	/**
	 * 根据planId删除排期商品数据
	 * @param planId
	 */
	public void deletePlanProductByPlanId(Integer planId) throws DataAccessException;
	
	/**
	 * 修改排期商品权重 
	 * @param plan
	 */
	public void updatePlanProductRank(PlanProduct planProduct) throws DataAccessException;
	
	public List<PlanProduct> listPlanProuductsByPlanIds(List<Integer> planIds) throws DataAccessException;
	
	/**
	 * 根据planId查询排期商品
	 * @param planId
	 * @return
	 */
	public List<PlanProduct> listPlanProductsByPlanId(Integer planId) throws DataAccessException;
}
