package com.ytoxl.module.uhome.uhomebase.service;

import java.util.List;
import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.Plan;
import com.ytoxl.module.uhome.uhomebase.dataobject.Product;

/**
 * @author wangguoqing
 *
 */
public interface BrandService {
	
	public Brand getBrandByBrandId(Integer brandId) throws UhomeStoreException;
	
	public Integer updateBrandByBrandId(Brand brand) throws UhomeStoreException;
	
	public Integer addBrand(Brand brand) throws UhomeStoreException;
	/**
	 * 管理员品牌列表
	 * @return
	 * @throws UhomeStoreException
	 */
	
	public void searchBrands(BasePagination<Brand> brandPage)throws UhomeStoreException;
	
	public List<Brand> listBrands() throws UhomeStoreException;
	public List<Brand> getDistinctList() throws UhomeStoreException;
	
	/**
	 * 热卖品牌 最多18个
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Brand> listHotBrands() throws UhomeStoreException;
	
	/**
	 * 品牌详情页面做多9个
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Brand> listHotBrands4FrontBrandDetail()throws UhomeStoreException;
	
	/**
	 * 首页品牌预约  按时间倒序  最多18个
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Brand> listSubscribeBrands() throws UhomeStoreException;
	
	/**
	 * 查询卖家可售品牌
	 * @param sellerid
	 * @return
	 */
	public List<Brand> listBrandsBySellerId(Integer sellerid) throws UhomeStoreException;
	
	/**
	 * 根据品牌的id查询品牌  前台品牌详情用
	 * @param brand
	 * @return
	 * @throws UhomeStoreException
	 */
	public Brand getBrandByBrandId4Front(Brand brand) throws UhomeStoreException;
	
	/**
	 * 根据brandPinYin来查询品牌
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Brand> listBrandsByBrandPinYin(Brand brand) throws UhomeStoreException;
	
	/**
	 * 品牌介绍页面  热卖商品分页
	 * @param productPage
	 * @throws UhomeStoreException
	 */
	public void searchProductByBrandId(BasePagination<Product> productPage) throws UhomeStoreException;
	
	/**
	 * 根据品牌名称查询精确的品牌名
	 * @param productCategoryId
	 * @return
	 */
	public Brand searchBrandByName(Brand  brand) throws UhomeStoreException;
	
	/**
	 * 根据品牌id和当前时间查找正在排期和即将发布的排期
	 * @param brandId
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Plan> listPlansByBrandIdAndEndTime(Integer brandId)throws UhomeStoreException; 

	/**
	 * 根据品牌Id查询该品牌下的商品
	 * @param brandId
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Product> listProductsByBrandId(Integer brandId) throws UhomeStoreException;
	
	/**
	 * 品牌禁用
	 * @param brandId
	 * @return
	 * @throws UhomeStoreException
	 */
	public void updateIsForbbdenByBrandId(Integer brandId,Short isForBidden) throws UhomeStoreException;
	/**
	 * 返回优化排序的品牌
	 * @return
	 * @throws UhomeStoreException
	 */

	public List<Brand> listBrandOrderBySort(Integer BrandType) throws UhomeStoreException;

	/**
	 * 返回所有排序的品牌
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Brand> listBrandBySort(Integer type) throws UhomeStoreException;
	/**
	 * 获取品牌名称首字母用作查询索引
	 * @param fromalChinese
	 * @return
	 */
	public String getPinyinName(String name) throws UhomeStoreException;
	 
} 
