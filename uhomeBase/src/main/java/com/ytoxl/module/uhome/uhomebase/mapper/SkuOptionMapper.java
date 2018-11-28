package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.dataobject.SkuOption;

/**
 * 产品SKU
 * @author huayucai
 * 
 * @param <T>
 */
public interface SkuOptionMapper<T extends SkuOption> extends BaseSqlMapper<T> {
	
	/**
	 * 查询产品规格选项
	 * @return
	 */
	public List<SkuOption> listSkuOptions() throws DataAccessException;
	
	/**
	 * 根据id集合查询产品规格选项数据
	 * @param ids
	 * @return
	 */
	public List<SkuOption> getSkuOptionsByIds(List<Integer> ids) throws DataAccessException;
	
}
