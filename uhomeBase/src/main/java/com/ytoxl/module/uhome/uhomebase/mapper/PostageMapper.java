package com.ytoxl.module.uhome.uhomebase.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.dataobject.Postage;

public interface PostageMapper<T extends Postage> extends BaseSqlMapper<T> {
	
	/**
	 * 根据 outId、type查询postage
	 * @param outId
	 * @param type
	 * @return
	 */
	public Postage getPostageByOutIdAndType(@Param("outId")Integer outId, @Param("type")Short type) throws DataAccessException;
	
	/**
	 * 根据 outId、type批量查询postage
	 * @param outIds
	 * @param type
	 * @return
	 * @throws DataAccessException
	 */
	public List<Postage> getPostagesByOutIdsAndType(@Param("list")List<Integer> outIds,@Param("type")Short type) throws DataAccessException;
	
	/**
	 * 将productSkuId转换成productId
	 * @param productSkuIds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Integer> productSkuIdToProuctId(List<Integer> productSkuIds) throws DataAccessException;
	
	/**
	 * 根据productId按排期查询邮费
	 * @param productId 
	 * @return
	 * @throws DataAccessException
	 */
	public BigDecimal getPostageByProductIdInPlan(@Param("productId")Integer productId) throws DataAccessException;
	
	/**
	 * 根据商品productId查询商品邮费
	 * @param productId
	 * @return
	 * @throws DataAccessException
	 */
	public BigDecimal getPostageByProductIdInsSelf(@Param("productId")Integer productId) throws DataAccessException;
	/**
	 * 查询商品当前所在排期id
	 * @param productIds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Integer> getPlansByProductIds(List<Integer> productIds) throws DataAccessException;

}
