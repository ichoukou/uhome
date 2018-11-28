package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.dataobject.SkuOptionValue;

/**
 * 产品SKU
 * @author huayucai
 * 
 * @param <T>
 */
public interface SkuOptionValueMapper<T extends SkuOptionValue> extends BaseSqlMapper<T> {
	

	/**
	 * 查询产品规格值
	 * @param skuOptionsId
	 * @return
	 */
	public List<SkuOptionValue> listSkuOptionValues(Integer skuOptionId) throws DataAccessException;
	
	/**
	 * 根据id集合查询产品规格选项值数据
	 * @param skuOptionValueIds
	 * @return
	 */
	public List<SkuOptionValue> listSkuOptionValuesByIds(List<Integer> skuOptionValueIds) throws DataAccessException;
	
	/**
	 * 根据选项的值查询选项
	 * @param skuOptionValue
	 * @return
	 * @throws DataAccessException
	 */
	public SkuOptionValue getSkuOptionValuesBySkuOptionValue(String skuOptionValue) throws DataAccessException;
	
	/**
	 * 根据传入的skuOptionName 查询第一个选项  颜色或者尺码规格
	 * @param skuOptionName
	 * @return
	 * @throws DataAccessException
	 */
	public SkuOptionValue getFirstSkuOptionValues(String skuOptionName)throws DataAccessException;
	
	/**
	 * 获取此product中唯一没有的颜色或者尺码
	 * @param skuOptionName
	 * @param alreadySkuOptionValues
	 * @return
	 * @throws DataAccessException
	 */
	public SkuOptionValue getUniqueSkuOptionValues(@Param("skuOptionName")String skuOptionName,@Param("alreadySkuOptionValues")String alreadySkuOptionValues)throws DataAccessException;
	
	
}
