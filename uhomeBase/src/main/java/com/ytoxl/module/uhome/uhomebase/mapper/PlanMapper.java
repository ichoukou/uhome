package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Plan;
import com.ytoxl.module.uhome.uhomebase.dataobject.Product;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductCategory;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku;

/**
 * @author wangguoqing
 *
 */

public interface PlanMapper<T extends Plan> extends BaseSqlMapper<T> {
	
	/**
	 * 查询所有产品类别信息及排期个数
	 * @return
	 */
	public List<ProductCategory> listProductCategoryAndPlanNum(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 查询所有产品子类别信息及排期个数
	 * @return
	 */
	public List<ProductCategory> listChildProductCategoryAndPlanNum(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 批量更新排期状态
	 * @param planIds
	 */
	public void updateStatusByPlanIds(List<Integer> planIds) throws DataAccessException;
	
	/**
	 * 根据商品id查询商品当前时所在的排期
	 * @param productId
	 * @return
	 */
	public Plan getCurrentPlanByProductId(Integer productId) throws DataAccessException;
	
	/**
	 * 根据商品id查询当前日期之后的最后一个排期 
	 * @param productId
	 * @return
	 */
	public Plan getLastPlanByProductId(Integer productId) throws DataAccessException;
	
	/**
	 * 根据当前时间和类型(特卖、秒杀)查询所有的排期
	 * @return
	 * @throws DataAccessException
	 */
	public List<Plan> getPlanListByCurrentTimeAndType(Short type) throws DataAccessException;
	
	/**
	 * 根据当前时间、类型(特卖、秒杀)、状态查询所有的排期
	 * @return
	 * @throws DataAccessException
	 */
	public List<Plan> getPlanListByCurrentTimeAndTypeAndStatus(Map<String,Object> map) throws DataAccessException;
	
	/**
	 * 明天的秒杀 排期 列表
	 * @return
	 * @throws DataAccessException
	 */
	public List<Plan> getTomorrowSecKillPlanList() throws DataAccessException;
	
	/**
	 * 根据当前时间、类型(特卖、秒杀)、状态是 已经发布的 预告的排期 当前时间加7天时间的预告  分成2个方法  一个 方法报sql异常
	 * @param type
	 * @param status
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Plan> getPlanListNoticeByCurrentTimeAndType(Short type) throws DataAccessException;
	
	/**
	 * 根据当前时间、类型(秒杀)、状态是 已经发布的 预告的排期 当前时间加7天时间的预告
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Plan> getPlanListSecKillNoticeByCurrentTime() throws DataAccessException;
	
	/**
	 * 根据当前时间、类型(特卖)、状态是 已经发布的 预告的排期 当前时间加7天时间的预告 
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Plan> getPlanListSpecialSellerNoticeByCurrentTime() throws DataAccessException;
	
	/**
	 *  根据当前时间 已经发布的 预告的排期  当前时间加4天时间的预告
	 * @return
	 * @throws DataAccessException
	 */
	public List<Plan> getPlanListBrandNoticeByCurrentTime() throws DataAccessException;
	
	/**
	 * 按最新上线的品牌   排期  排序
	 * @return
	 * @throws DataAccessException
	 */
	public List<Plan> getPlanListLastOnlineSpecialSeller() throws DataAccessException;
	
	/**
	 * 按品牌的折扣最低排序
	 * @return
	 * @throws DataAccessException
	 */
	public List<Plan> getPlanListLowestDiscount() throws DataAccessException;
	
	/**
	 * 最受欢迎的品牌 排序
	 * @return
	 * @throws DataAccessException
	 */
	public List<Plan> getPlanListMostPopular() throws DataAccessException;
	
	/**
	 * 通过目录id查询其排期
	 * @param categoryId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Plan> getPlanListLastOnlineByCategoryId(Integer categoryId) throws DataAccessException;
	public List<Plan> getPlanListMostPopularByCategoryId(Integer categoryId) throws DataAccessException;
	public List<Plan> getPlanListLowestDiscountByCategoryId(Integer categoryId) throws DataAccessException;
	
	/**
	 * 根据排期id查询所有在排期内的商品  并按折扣从小到大排序
	 * @param PlanId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Product> getPlanProductListByPlanId(Integer planId) throws DataAccessException;
	
	/**
	 * 根据排期id查询所有在排期内的秒杀商品  
	 * @param PlanId
	 * @return
	 * @throws DataAccessException
	 */
	public Product getPlanSecKillProductByPlanId(Integer planId) throws DataAccessException;
	
	/**
	 * 通过planId 查询排期商品的sku
	 * @param plandId
	 * @return
	 * @throws DataAccessException
	 */
	public ProductSku getPlanSecKillProductSkuByPlanId(Integer plandId) throws DataAccessException;
	
	/**
	 * 根据排期id添加排期中的商品
	 * @param planId
	 * @param productId
	 * @return
	 * @throws DataAccessException
	 */
	public Integer addPlanProductByPlanIdAndProductId(Integer planId,Integer productId) throws DataAccessException;
	
	/**
	 * 根据排期id删除排期中的商品
	 * @param planId
	 * @param productId
	 * @return
	 * @throws DataAccessException
	 */
	public Integer delPlanProductByPlanIdAndProductId(Integer planId,Integer productId)throws DataAccessException;

	/**
	 * 后台搜索查询特卖排期
	 * @param map
	 * @return
	 */
	public List<Plan> searchSpecialSellerPlans(Map<String, Object> map) throws DataAccessException;
	public Integer searchSpecialSellerPlansCount(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 后台搜索查询秒杀排期
	 * @param map
	 * @return
	 */
	public List<Plan> searchSecKillPlans(Map<String, Object> map) throws DataAccessException;
	public Integer searchSecKillPlansCount(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 前台品牌特卖查询  通过排期id查询 所有的商品
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Product> searchProductList4Front(Map<String, Object> map) throws DataAccessException;
	public Integer searchProductList4FrontCount(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 前台品牌特卖查询  通过排期id查询有货的商品
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Product> searchProductList4FrontStore(Map<String, Object> map) throws DataAccessException;
	public Integer searchProductList4FrontStoreCount(Map<String, Object> map) throws DataAccessException;
	
	
	/**
	 * 按销售数量排序
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Product> searchProductList4FrontBySalesQuantity(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 按销售数量排序 显示有货商品
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Product> searchProductList4FrontBySalesQuantityAndStore(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 根据排期的id查询此排期中 折扣最低的商品信息
	 * @param planId
	 * @return
	 * @throws DataAccessException
	 */
	public Product getMinRebateProductByPlanId(Integer planId)throws DataAccessException;
	
	/**
	 *根据品牌id查询最近一个排期  品牌介绍  最近的一个排期特卖商品
	 */
	public Plan getNearPlanByBrandId(Integer brandId) throws DataAccessException;
	
	/**
	 * 前台品牌介绍   商品销售的历史记录
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Product> searchPlanProductList4FrontByPlanId(Map<String, Object> map) throws DataAccessException;
	public List<Product> searchPlanProductList4FrontByPlanIdCount(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 根据品牌ID集合查询当天开售的排期
	 * @param brandIds
	 * @return
	 */
	public List<Plan> listTodaySpecialSellerPlansByBrandIds(List<Integer> brandIds) throws DataAccessException;
	/**
	 * 根据排期集合查询当天开售的排期
	 * @param brandIds
	 * @return
	 */
	public List<Plan> listTodaySpecialSellerPlansByPlanIds(List<Integer> planIds) throws DataAccessException;
	/**
	 * 获取所有发布和下架的排期
	 * @param brandIds
	 * @return
	 */
	public List<Plan> listAllPlans() throws DataAccessException;
	/**
	 * 根据品牌id和当前时间查找正在排期和即将发布的排期
	 * @param brandId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Plan> listPlansByBrandIdAndEndTime(Integer brandId)throws DataAccessException; 
	

	/**
	 * 通过商家ID获取所有发布和下架的排期
	 * @param sellerId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Plan> listPlansBySellerId(Map<String,String> map)throws DataAccessException; 

	/**
	 * 根据 ids查询排期名称
	 * @param planId
	 * @return
	 */
	public String getNamesByPlanIds(List<Integer> planIds) throws DataAccessException;
	
	/**
	 * 查询可以参加优惠活动的排期
	 * @param map
	 * @return
	 */
	public List<Plan> listPlans4EventRange(@Param("startTime") Date startTime, @Param("endTime") Date endTime) throws DataAccessException;
	

}
