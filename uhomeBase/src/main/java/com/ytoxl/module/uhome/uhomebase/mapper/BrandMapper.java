package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;

/**
 * @author wangguoqing
 * 
 */
public interface BrandMapper<T extends Brand> extends BaseSqlMapper<T> {
	
	/**
	 * 返回所有的品牌
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Brand> getBrandList() throws DataAccessException;
	
	/**
	 * 根据卖家的id查询其所有的品牌
	 * @param sellerId
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Brand> getBrandListBySeller(Integer sellerId) throws DataAccessException;
	
	/**
	 * 根据品牌的排序和所要的个数返回品牌列表
	 * @param num
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Brand> getBrandListByRank(Integer num) throws DataAccessException;

	/**
	 * 根据品牌的名字查询品牌
	 * @param name
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Brand> searchBrandByName(String name) throws DataAccessException;
	
	/**
	 * 按名字查询精确的品牌
	 * @param name
	 * @return
	 * @throws UhomeStoreException
	 */
	public Brand getBrandByName(Brand brand) throws DataAccessException;
	
	/**
	 * 根据品牌id查询品牌
	 * @param brandId
	 * @return
	 * @throws DataAccessException
	 */
	public Brand getBrandByBrandId(Integer brandId) throws DataAccessException;
	
	/**
	 * 根据品牌id查询品牌 不查详细字段
	 * @param brandId
	 * @return
	 * @throws DataAccessException
	 */
	public Brand getBrandById(Integer brandId) throws DataAccessException;
	
	
	/**
	 * 热卖品牌 按照更新时间排期倒序 最多18个
	 * @return
	 * @throws DataAccessException
	 */
	public List<Brand> getHotBrands(Integer maxNum) throws DataAccessException;
	
	/**
	 * 品牌预约 按照更新时间排期倒序 最多18个
	 * @param maxNum
	 * @return
	 * @throws DataAccessException
	 */
	public List<Brand> getlistSubscribeBrands(Integer maxNum) throws DataAccessException;
	
	/**
	 * 查询卖家可售品牌
	 * @param sellerid
	 * @return
	 */
	public List<Brand> listBrandsBySellerId(Integer sellerId) throws DataAccessException;
	
	/**
	 * 根据brandPinYin来查询品牌
	 * @param sellerid
	 * @return
	 */
	public List<Brand> listBrandsByBrandPinYin(Brand brand) throws DataAccessException;
	
	/**
	 *管理员查询品牌列表
	 * @param sellerid
	 * @return
	 */
	public List<Brand> searchBrands(Map<String ,Object> map) throws DataAccessException;
	/**
	 *管理员查询品牌列表
	 * @param sellerid
	 * @return
	 */
	public List<Brand> getDistinctList() throws DataAccessException;
	
	/**
	 * 查询管理员下面所有的品牌的总数
	 * @param productCategoryId
	 * @return
	 */
	public int searchBrandsTotal(Map<String ,Object> map) throws DataAccessException;
	
	/**
	 * 品牌禁用
	 * @param brandId
	 * @return
	 * @throws DataAccessException
	 */
	public void updateIsForbbdenByBrandId(@Param("brandId")Integer brandId,@Param("isForbidden")Short isForbidden) throws DataAccessException;
	/**
	 * 返回前40品牌
	 * @return
	 * @throws UhomeStoreException
	 */

	public List<Brand> getBrandListOrderBySort(@Param("type")Integer type) throws DataAccessException;

	/**
	 * 返回前40品牌
	 * @return
	 * @throws UhomeStoreException
	 */
	
	public List<Brand> getBrandListBySort(@Param("type")Integer type) throws DataAccessException;

}
