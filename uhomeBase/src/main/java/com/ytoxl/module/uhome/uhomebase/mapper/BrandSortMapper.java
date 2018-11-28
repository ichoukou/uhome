package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.BrandSort;

/**
 * @author shengjianming
 * 
 */
public interface BrandSortMapper<T extends BrandSort> extends BaseSqlMapper<T> {
	
	/**
	 * 根据品牌类型删除品牌排序
	 * @return
	 * @throws UhomeStoreException
	 */
	public void delAllBrandSort(@Param("type")Integer type) throws DataAccessException;

	/**
	 * 查询所有排序的品牌 
	 * @return
	 * @throws DataAccessException
	 */
	public List<BrandSort> getBrandListGroupByType() throws DataAccessException;

	
}
