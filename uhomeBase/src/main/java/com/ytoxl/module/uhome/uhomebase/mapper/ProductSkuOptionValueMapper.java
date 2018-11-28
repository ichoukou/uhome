package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSkuOptionValue;

/**
 * 产品SKU
 * @author huayucai
 * 
 * @param <T>
 */
public interface ProductSkuOptionValueMapper<T extends ProductSkuOptionValue> extends BaseSqlMapper<T> {
	
	/**
	 * 批量添加数据
	 * @param list
	 */
	public void addBatch(@Param("productSkuId") Integer productSkuId, @Param("list") List<ProductSkuOptionValue> list) throws DataAccessException;
	
	/**
	 * 根据productSkuId查询SKU选项值信息
	 * @param productSkuId
	 * @return
	 */
	public List<ProductSkuOptionValue> listProductSkuOptionValuesByProductSkuId(
			Integer productSkuId) throws DataAccessException;

	/**
	 * 根据productSkuId删除SKU选项值信息
	 * @param productSkuId
	 */
	public void delProductSkuOptionValueByProductSkuId(Integer productSkuId)
			throws DataAccessException;
	
	/**
	 * 根据productId删除数据
	 * @param productSkuId
	 */
	public void delProductSkuOptionValueByProductId(Integer productSkuId)
		throws DataAccessException;
	
}
