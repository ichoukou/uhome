package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.AttentionDetail;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;

/**
 * @author wangguoqing
 *
 */
public interface AttentionDetailMapper<T extends AttentionDetail> extends BaseSqlMapper<T> {
	
	/**
	 * 根据品牌查询品牌有多少人喜欢
	 * @param brandid
	 * @return
	 * @throws UhomeStoreException
	 */
	public Integer getAttentionCountByBrand(Integer brandId) throws DataAccessException;
	
	/**
	 * 根据用户id查询此用户喜欢那些品牌
	 * @param userId
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Brand> getAttentionBrandByUserId(Integer userId) throws DataAccessException;

}
