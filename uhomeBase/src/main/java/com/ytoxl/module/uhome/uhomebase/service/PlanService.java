package com.ytoxl.module.uhome.uhomebase.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Plan;
import com.ytoxl.module.uhome.uhomebase.dataobject.PlanProduct;
import com.ytoxl.module.uhome.uhomebase.dataobject.Product;
import com.ytoxl.module.uhome.uhomebase.dataobject.Seller;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductCategory;

/**
 * 活动
 * @author wangguoqing
 *
 */
public interface PlanService {
	
	/**
	 * 获取商品类别
	 * @return
	 */
	public List<ProductCategory> listCategoriesAndPlanNum(BasePagination<Plan> planPage, Short type) throws UhomeStoreException;
	
	/**
	 * 后台搜索查询特卖排期
	 * @param planPage
	 */
	public void searchSpecialSellerPlans(BasePagination<Plan> planPage, List<Map<Date, Boolean>> dates) throws UhomeStoreException;
	
	/**
	 * 后台搜索查询秒杀排期
	 * @param planPage
	 */
	public void searchSecKillPlans(BasePagination<Plan> planPage, List<Map<Date, Boolean>> dates) throws UhomeStoreException;
	
	/**
	 * 编辑特卖排期时查询卖家
	 * @param startTime
	 * @param endTime
	 * @param brandId
	 * @param productCategoryId
	 * @return
	 */
	public List<Seller> listSellersForSpecialSell(Date startTime, Date endTime,
			Integer brandId, Integer productCategoryId, Integer planId)
			throws UhomeStoreException;
	
	/**
	 * 编辑秒杀排期时查询卖家
	 * @param startTime
	 * @param brandId
	 * @param productCategoryId
	 * @return
	 */
	public List<Seller> listSellersForSeckill(Date startTime, Integer brandId,
			Integer productCategoryId, Integer planId)
			throws UhomeStoreException;
	
	/**
	 * 保存排期
	 * @param plan
	 */
	public void savePlan(Plan plan) throws UhomeStoreException;
	
	/**
	 * 批量发布
	 * @param planIds
	 */
	public void updateReleaseBatch(List<Integer> planIds) throws UhomeStoreException;
	
	/**
	 * 更新排期状态
	 * @param plan
	 */
	public void updateStatus(Plan plan) throws UhomeStoreException;
	
	/**
	 * 根据planId查询排期
	 * @param planId
	 * @return
	 */
	public Plan getPlan(Integer planId, Short planType) throws UhomeStoreException;
	
	/**
	 * 修改排期商品权重
	 * @param planProducts
	 */
	public void updatePlanProductRank(List<PlanProduct> planProducts) throws UhomeStoreException;
	
	/**
	 * 批量更新排期状态
	 * @param planIds
	 */
	public void updateStatusByPlanIds(List<Integer> planIds) throws UhomeStoreException;
	
	/**
	 * 根据排期id查询排期，并查询其所有的商品
	 * @param planId
	 * @return
	 * @throws UhomeStoreException
	 */
	public Plan getPlanByPlanId(Integer planId) throws UhomeStoreException;

	/**
	 * 今天秒杀
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Plan>listPlanSecKillToday() throws UhomeStoreException;

	/**
	 * 明天秒杀
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Plan>listPlanSecKillTomorrow() throws UhomeStoreException;

	/**
	 * 根据当前时间和数量查询预告的秒杀
	 * @param num
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Plan> listPlanSecKillNotice() throws UhomeStoreException;

	/***
	 * 最受欢迎的品牌
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Plan> listPlanMostPopularBrands() throws UhomeStoreException;

	/**
	 * 折扣最低的品牌
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Plan> listPlanLowestDiscountBrands() throws UhomeStoreException;

	/**
	 * 最新上线的品牌
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Plan> listPlanLastOnlineBrands() throws UhomeStoreException;

	/**
	 * 即将上线的品牌
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Plan> listPlanSoonBrands() throws UhomeStoreException;
	
	/**
	 * 查询所有的排期
	 * @return
	 * @throws UhomeStoreException
	 */
	public Map<Integer, Map<String,List<Plan>>> listPlanByCategorys()throws UhomeStoreException;
	
	/**
	 * 根据类目id查找最受欢迎的排期
	 * @param ProductCategoryId
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Plan> listPlanMostPopularByCategoryId(Integer productCategoryId)throws UhomeStoreException;
	
	/**
	 * 根据类目id查找最新上线的排期
	 * @param ProductCategoryId
	 * @return
	 * @throws UhomeStoreException
	 */

	public List<Plan> listPlanLastOnlineByCategoryId(Integer productCategoryId)throws UhomeStoreException;
	
	/**
	 * 根据类目id查找最低折扣的排期
	 * @param ProductCategoryId
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Plan> listPlanLowestDiscountByCategoryId(Integer productCategoryId)throws UhomeStoreException;
	

	/**
	 * 开售的品牌
	 * @param time
	 * @return
	 * @throws UhomeStoreException
	 */
	public Map<String,List<Plan>> listPlanOpenSalesBrand() throws UhomeStoreException;
	
	/**
	 * 通过排期的id查询所有的商品  并分页
	 * @param planId
	 * @throws UhomeStoreException
	 */
	public void searchProductListByPlanId(BasePagination<Product> productPage) throws UhomeStoreException;
	/**
	 * 通过排期id  查找此排期中折扣最低的那个件商品    品牌商品页面
	 * @param planId
	 * @return
	 * @throws UhomeStoreException
	 */
	public Product getMinRebateProductByPlanId(BasePagination<Product> productPage) throws UhomeStoreException;
	
	/**
	 * 根据传入的排期 赛选出符合目录的排期  
	 * @param plans
	 * @param productCategory
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Plan> getPlansByCategoryId(List<Plan> plans,ProductCategory productCategory) throws UhomeStoreException;
	
	/**
	 * 根据品牌ID集合查询当天开售的排期
	 * @param brandIds
	 * @return
	 */
	public List<Plan> listTodaySpecialSellerPlansByBrandIds(List<Integer> brandIds) throws UhomeStoreException;
	/**
	 * 根据品牌planid集合查询当天开售的排期
	 * @param brandIds
	 * @return
	 */
	public List<Plan> listTodaySpecialSellerPlansByPlanIds(List<Integer> brandIds) throws UhomeStoreException;
	
	/**
	 * 获取所有发布和下架排期
	 * @param brandIds
	 * @return
	 */
	public List<Plan> listAllPlans() throws UhomeStoreException;
	
	/**
	 * 通过商家ID获取所有发布和下架的排期
	 * @param brandIds
	 * @return
	 */
	public List<Plan> listPlansBySellerId(Map<String,String> map) throws UhomeStoreException;
	
	
}
