package com.ytoxl.module.uhome.uhomebase.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ytoxl.module.core.common.constant.CommonConstants;
import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.common.utils.DateUtil;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.Plan;
import com.ytoxl.module.uhome.uhomebase.dataobject.PlanProduct;
import com.ytoxl.module.uhome.uhomebase.dataobject.Postage;
import com.ytoxl.module.uhome.uhomebase.dataobject.Product;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductCategory;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSkuOptionValue;
import com.ytoxl.module.uhome.uhomebase.dataobject.Seller;
import com.ytoxl.module.uhome.uhomebase.dataobject.UserInfo;
import com.ytoxl.module.uhome.uhomebase.mapper.BrandMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.PlanMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.PlanProductMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.PostageMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.ProductCategoryMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.ProductMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.ProductSkuMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.ProductSkuOptionValueMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.UserInfoMapper;
import com.ytoxl.module.uhome.uhomebase.service.PlanService;
import com.ytoxl.module.uhome.uhomebase.service.PostageService;
import com.ytoxl.module.uhome.uhomebase.service.ProductService;
import com.ytoxl.module.user.dataobject.User;

/**
 * @author wangguoqing
 *
 */

@Service
public class PlanServiceImpl implements PlanService {
	
	@Autowired
	private ProductCategoryMapper<ProductCategory> productCategoryMapper;
	@Autowired
	private PlanMapper<Plan> planMapper;
	@Autowired
	private PlanProductMapper<PlanProduct> planProductMapper;
	@Autowired
	private BrandMapper<Brand> brandMapper;
	@Autowired
	private ProductMapper<Product> productMapper;
	@Autowired
	private ProductSkuMapper<ProductSku> productSkuMapper;
	@Autowired
	private UserInfoMapper<UserInfo> userInfoMapper;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductSkuOptionValueMapper<ProductSkuOptionValue> productSkuOptionValueMapper;
	@Autowired
	private PostageMapper<Postage> postageMapper;
	@Autowired
	private PostageService postageService;
	
	/**
	 * 获取商品类别
	 * @return
	 */
	@Override
	public List<ProductCategory> listCategoriesAndPlanNum(BasePagination<Plan> planPage, Short type) throws UhomeStoreException {
		Map<String, Object> searchParams = planPage.getSearchParamsMap();
		List<ProductCategory> list = new ArrayList<ProductCategory>();
		if(type ==null || type!=null&&type.equals(Plan.TYPE_SPECIAL_SELLER)){
			list = planMapper.listProductCategoryAndPlanNum(searchParams);
		}else if(type.equals(Plan.TYPE_SEC_KILL)){
			list = planMapper.listChildProductCategoryAndPlanNum(searchParams);
		}
		return list;
	}
	
	/**
	 * 后台搜索查询特卖排期
	 * @param planPage
	 */
	@Override
	public void searchSpecialSellerPlans(BasePagination<Plan> planPage, List<Map<Date, Boolean>> dates) throws UhomeStoreException {
		Map<String, Object> searchParams = planPage.getSearchParamsMap();
		searchParams.put(CommonConstants.PAGE_SORT, "a.startTime");
		
		Collection<Plan> result = planMapper.searchSpecialSellerPlans(searchParams);
		if(result != null && result.size() > 0){
			Date currentTime = new Date();
			for(Plan plan : result){
				if(plan.getStatus().equals(Plan.STATUS_RELEASE) 
						&& currentTime.compareTo(plan.getStartTime()) >= 0 
						&& currentTime.compareTo(plan.getEndTime()) <= 0){
					//特卖中
					plan.setActivityStatus(Plan.ACTIVITYSTATUS_SPECIAL_SELLER_INPROGRESS);
				}
				if(currentTime.compareTo(plan.getEndTime()) > 0){
					//已过期
					plan.setActivityStatus(Plan.ACTIVITYSTATUS_SPECIAL_SELLER_END);
				}
			}
			handleResult(result, dates);
			//设置查询日期默认值
			Map<String, String> params = planPage.getParams();
			if(params == null){
				params = new HashMap<String, String>();
				if(!params.containsKey("startTime")){
					Map<Date, Boolean> map = (Map<Date, Boolean>) dates.get(0);
					Date startTime =  (Date) map.keySet().toArray()[0];
					params.put("startTime", DateUtil.toDay(startTime));
				}
				if(!params.containsKey("endTime")){
					Map<Date, Boolean> map = (Map<Date, Boolean>) dates.get(dates.size()-1);
					Date endTime = (Date) map.keySet().toArray()[0];
					params.put("endTime", DateUtil.toDay(endTime));
				}
				planPage.setParams(params);
			}
		}
		planPage.setResult(result);
	}
	
	/**
	 * 后台搜索查询秒杀排期
	 * @param planPage
	 */
	@Override
	public void searchSecKillPlans(BasePagination<Plan> planPage, List<Map<Date, Boolean>> dates) throws UhomeStoreException {
		Map<String, Object> searchParams = planPage.getSearchParamsMap();
		searchParams.put(CommonConstants.PAGE_SORT, "a.startTime");
		
		Collection<Plan> result = planMapper.searchSecKillPlans(searchParams);
		if(result != null && result.size() > 0){
			Date currentTime = new Date();
			for(Plan plan : result){
				if(plan.getStatus().equals(Plan.STATUS_RELEASE)
						&&currentTime.compareTo(plan.getStartTime()) >= 0
						&& currentTime.compareTo(plan.getEndTime()) <= 0){
					//秒杀中
					plan.setActivityStatus(Plan.ACTIVITYSTATUS_SECKILL_INPROGRESS);
				}
				if(currentTime.compareTo(plan.getEndTime()) > 0){
					//秒杀结束
					plan.setActivityStatus(Plan.ACTIVITYSTATUS_SECKILL_END);
				}
			}
			handleResult(result, dates);
			//设置查询日期默认值
			Map<String, String> params = planPage.getParams();
			if(params == null){
				params = new HashMap<String, String>();
				if(!params.containsKey("startTime")){
					Map<Date, Boolean> map = (Map<Date, Boolean>) dates.get(0);
					Date startTime =  (Date) map.keySet().toArray()[0];
					params.put("startTime", DateUtil.toDay(startTime));
				}
				if(!params.containsKey("endTime")){
					Map<Date, Boolean> map = (Map<Date, Boolean>) dates.get(dates.size()-1);
					Date endTime = (Date) map.keySet().toArray()[0];
					params.put("endTime", DateUtil.toDay(endTime));
				}
				planPage.setParams(params);
			}
		}
		planPage.setResult(result);
	}

	private void handleResult(Collection<Plan> result, List<Map<Date, Boolean>> dates) {
		Date[] startTimeArray = new Date[result.size()];
		Date[] endTimeArray = new Date[result.size()];
		int index = 0;
		for(Plan plan : result){
			startTimeArray[index] = plan.getStartTime();
			endTimeArray[index] = plan.getEndTime();
			index++;
		}
		//自然顺序排序
		Arrays.sort(startTimeArray);
		Arrays.sort(endTimeArray);
		//排期列表中最小开始时间与最大结束时间
		Date minStartTime = startTimeArray[0];
		Date maxEndTime = endTimeArray[endTimeArray.length - 1];

		for(Plan plan : result){
			Date startTime = plan.getStartTime();
			Date endTime = plan.getEndTime();
			//开始时间与最小开始时间间隔天数
			double dayInterval = DateUtil.dayInterval(startTime, minStartTime); 
			plan.setDayInterval(Double.valueOf(dayInterval).intValue());
			//排期天数
			double days = DateUtil.dayInterval(endTime, startTime);
			plan.setDays(Double.valueOf(days).intValue());
		}
		//得到最小开始时间到最大结束时间的日期集合,至少31天
		Date date = minStartTime;
		while(DateUtil.compareDay(date, maxEndTime) || DateUtil.isSameDay(date, maxEndTime)){
			Map<Date, Boolean> map = new HashMap<Date, Boolean>();
			map.put(date, DateUtil.isWorkDay(date));
			dates.add(map);
			date = DateUtil.nextDate(date);
		}
		if(dates.size() < 31){
			while(dates.size() < 31){
				Date nextDate = DateUtil.nextDate(date);
				Map<Date, Boolean> map = new HashMap<Date, Boolean>();
				map.put(date, DateUtil.isWorkDay(nextDate));
				dates.add(map);
				date = nextDate;
			}
		}
	}
	
	/**
	 * 编辑特卖排期时查询卖家,
	 * 并根据排斯类型获取卖家所特卖的商品。
	 * @param startTime
	 * @param endTime
	 * @param brandId
	 * @param productCategoryId
	 * @return
	 */
	@Override
	public List<Seller> listSellersForSpecialSell(Date startTime, Date endTime,
			Integer brandId, Integer productCategoryId, Integer planId) throws UhomeStoreException {
		GregorianCalendar gc = new GregorianCalendar();
		//开始时间
		gc.setTime(startTime);
		gc.add(Calendar.HOUR_OF_DAY, 10);
		startTime = gc.getTime();
		//结束时间
		gc.setTime(endTime);
		gc.add(Calendar.HOUR_OF_DAY, 10);
		gc.add(Calendar.SECOND, -1);
		endTime = gc.getTime();
		
		List<Seller> sellers = userInfoMapper.listSellersByBrandId(brandId);
		if(sellers.size() > 0){
			for(Seller seller : sellers){
				//获取卖家可参加特卖活动的商品
				List<Product> products = productMapper
						.listSpecialSellProductsBysellerId(startTime, endTime,
								seller.getSellerId(), brandId, productCategoryId, planId);
				seller.setProducts(products);
			}
		}
		return sellers;
	}
	
	/**
	 * 编辑秒杀排期时查询卖家
	 * @param startTime
	 * @param brandId
	 * @param productCategoryId
	 * @return
	 */
	@Override
	public List<Seller> listSellersForSeckill(Date startTime, Integer brandId,
			Integer productCategoryId, Integer planId) throws UhomeStoreException {
		startTime = DateUtil.add(startTime, Calendar.HOUR_OF_DAY, 10);
		List<Seller> sellers = userInfoMapper.listSellersByBrandId(brandId);
		if(sellers.size() > 0){
			for(Seller seller : sellers){
				//获取卖家可参加秒杀活动的商品
				List<ProductSku> productSkus = productSkuMapper
						.listSecKillProductsBysellerId(startTime, seller.getSellerId(), brandId, productCategoryId, planId);
				//获取商品sku选项信息
				if(productSkus.size() > 0){
					for(ProductSku proSku : productSkus){
						Integer productSkuId = proSku.getProductSkuId();
						List<ProductSkuOptionValue> proSkuOptionValues = productSkuOptionValueMapper
								.listProductSkuOptionValuesByProductSkuId(productSkuId);
						proSku.setProductSkuOptionValues(proSkuOptionValues);
					}
				}
				seller.setProductSkus(productSkus);
			}		
		}
		return sellers;
	}

	/**
	 * 保存排期
	 * @param plan
	 */
	@Override
	public void savePlan(Plan plan) throws UhomeStoreException {
		Date startTime = plan.getStartTime();
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(startTime);
		gc.add(Calendar.HOUR_OF_DAY, 10);
		plan.setStartTime(gc.getTime());
		
		if(plan.getType().equals(Plan.TYPE_SPECIAL_SELLER)){
			gc.setTime(plan.getEndTime());
			gc.add(Calendar.HOUR_OF_DAY, 10);
			gc.add(Calendar.SECOND, -1);
			plan.setEndTime(gc.getTime());
		}else if(plan.getType().equals(Plan.TYPE_SEC_KILL)){
			gc.add(Calendar.SECOND, 1);
			plan.setEndTime(gc.getTime());
		}
		
		if(plan.getPlanId() == null){
			//添加排期
			planMapper.add(plan);
		}else{
			//修改排期
			planMapper.update(plan);
			planProductMapper.deletePlanProductByPlanId(plan.getPlanId());
		}
		
		//保存排期商品
		List<PlanProduct> planProducts = plan.getPlanProducts();
		if(planProducts!=null && planProducts.size()>0){
			for(PlanProduct planProduct : planProducts){
				planProduct.setPlanId(plan.getPlanId());
				planProductMapper.add(planProduct);
			}
		}
		//保存邮费信息
		Postage postage = plan.getPostage();
		if(postage == null){
			postage = new Postage();
		}
		postage.setOutId(plan.getPlanId());
		postage.setType(Postage.TYPE_PLAN);
		postageService.savePostage(postage);
	}
	
	/**
	 * 批量发布
	 * @param planIds
	 */
	@Override
	public void updateReleaseBatch(List<Integer> planIds) throws UhomeStoreException {
		if (planIds != null && planIds.size() > 0)
			planMapper.updateStatusByPlanIds(planIds);
	}
	
	/**
	 * 更新排期状态
	 * @param plan
	 */
	@Override
	public void updateStatus(Plan plan) throws UhomeStoreException {
		planMapper.update(plan);
	}

	/**
	 * 根据planId查询排期
	 * @param planId
	 * @return
	 */
	@Override
	public Plan getPlan(Integer planId, Short planType) throws UhomeStoreException {
		Plan plan = planMapper.get(planId);
		List<PlanProduct> planProducts = planProductMapper.listPlanProductsByPlanId(planId);
		
		if(planType.equals(Plan.TYPE_SEC_KILL)){
			for(PlanProduct planProduct : planProducts){
				ProductSku proSku = productSkuMapper.get(planProduct.getProductSkuId());
				
				List<ProductSkuOptionValue> proSkuOptionValues = productSkuOptionValueMapper
						.listProductSkuOptionValuesByProductSkuId(planProduct.getProductSkuId());
				proSku.setProductSkuOptionValues(proSkuOptionValues);
				List<ProductSku> proSkus = new ArrayList<ProductSku>();
				proSkus.add(proSku);
				planProduct.getProduct().setProductSkus(proSkus);
				
			}
		}
		
		plan.setPlanProducts(planProducts);
		
		//查询邮费信息
		Postage postage = postageMapper.getPostageByOutIdAndType(planId, Postage.TYPE_PLAN);
		if(postage != null){
			postage.setOption(Postage.OPTION_FREE);
			plan.setPostage(postage);
		}
		return plan;
	}
	
	/**
	 * 修改排期商品权重
	 * @param planProducts
	 */
	@Override
	public void updatePlanProductRank(List<PlanProduct> planProducts) throws UhomeStoreException {
		if(planProducts!=null && planProducts.size()>0){
			for(PlanProduct planProduct : planProducts){
				planProductMapper.updatePlanProductRank(planProduct);
			}
		}
	}

	/**
	 * 批量更新排期状态
	 * @param planIds
	 */
	@Override
	public void updateStatusByPlanIds(List<Integer> planIds) throws UhomeStoreException {
		if(planIds != null && planIds.size() > 0){
			planMapper.updateStatusByPlanIds(planIds);
		}
	}
	
	@Override
	public Plan getPlanByPlanId(Integer planId) throws UhomeStoreException {
		List<Product> products = planMapper.getPlanProductListByPlanId(planId);
		Plan plan = new Plan();
		plan.setProducts(products);
		return plan;
	}

	
	@Override
	public List<Plan> listPlanSecKillToday() throws UhomeStoreException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", Plan.TYPE_SEC_KILL);
		map.put("status", Plan.STATUS_RELEASE);
		List<Plan> plans = planMapper.getPlanListByCurrentTimeAndTypeAndStatus(map);
		//for(int i = 0; i < plans.size(); i++);
		Iterator<Plan> ite = plans.iterator();
		while(ite.hasNext()){
			//Plan plan = plans.get(i);
			Plan plan = ite.next();
			//商品信息
			Product product = planMapper.getPlanSecKillProductByPlanId(plan.getPlanId());
			if(product == null){
				ite.remove();
				continue;
			}
			//商品的sku信息
			ProductSku productSku = planMapper.getPlanSecKillProductSkuByPlanId(plan.getPlanId());
			List<ProductSku> productSkus = new ArrayList<ProductSku>();
			productSkus.add(productSku);
			product.setProductSkus(productSkus);
			List<Product> products = new ArrayList<Product>();
			products.add(product);
			plan.setProducts(products);
			//品牌信息
			plan.setBrand(brandMapper.get(plan.getBrandId()));
			//库存
			int secKillInventory = 0;
			if(productSku != null && productSku.getSecKillInventory() != null){
				secKillInventory = productSku.getSecKillInventory();
			}
			product.setSecKillInventoryCount(secKillInventory);
		}
		return plans;
	}

	
	@Override
	public List<Plan> listPlanSecKillTomorrow() throws UhomeStoreException {
		List<Plan> plans = planMapper.getTomorrowSecKillPlanList();
//		for(int i =0; i < plans.size(); i++);
		Iterator<Plan> ite = plans.iterator();
		while(ite.hasNext()){
//			Plan plan = plans.get(i);
			Plan plan = ite.next();
			//商品信息
			Product product = planMapper.getPlanSecKillProductByPlanId(plan.getPlanId());
			if(product == null){
				ite.remove();
				continue;
			}
			//商品的sku信息
			ProductSku porductSku = planMapper.getPlanSecKillProductSkuByPlanId(plan.getPlanId());
			List<ProductSku> productSkus = new ArrayList<ProductSku>();
			productSkus.add(porductSku);
			product.setProductSkus(productSkus);
			List<Product> products = new ArrayList<Product>();
			products.add(product);
			plan.setProducts(products);
			//品牌信息
			plan.setBrand(brandMapper.get(plan.getBrandId()));
		}
		return plans;
	}

	
	@Override
	public List<Plan> listPlanSecKillNotice() throws UhomeStoreException {
		//List<Plan> plans = planMapper.getPlanListNoticeByCurrentTimeAndType(Plan.PLAN_TYPE_SEC_KILL);
		List<Plan> plans = planMapper.getPlanListSecKillNoticeByCurrentTime();
//		for(int i =0; i < plans.size(); i++);
		Iterator<Plan> ite = plans.iterator();
		while(ite.hasNext()){
//			Plan plan = plans.get(i);
			Plan plan = ite.next();
			//商品信息
			Product product = planMapper.getPlanSecKillProductByPlanId(plan.getPlanId());
			if(null == product){
				ite.remove();
				continue;
			}
			//商品的sku信息
			ProductSku productSku = planMapper.getPlanSecKillProductSkuByPlanId(plan.getPlanId());
			List<ProductSku> productSkus = new ArrayList<ProductSku>();
			productSkus.add(productSku);
			product.setProductSkus(productSkus);
			List<Product> products = new ArrayList<Product>();
			products.add(product);
			plan.setProducts(products);
			//品牌信息
			plan.setBrand(brandMapper.get(plan.getBrand().getBrandId()));
			//设置秒杀的库存 
			product.setSecKillInventoryCount(productSku.getSecKillInventory());
		}
		return plans;
	}

	
	@Override
	public List<Plan> listPlanMostPopularBrands() throws UhomeStoreException {
		//最受欢迎的品牌    按每个品牌本次排期内的所有商品pv相加
		List<Plan> plans = planMapper.getPlanListMostPopular();
		for(int i = 0; i< plans.size(); i++){
			Plan plan = plans.get(i);
			//Brand brand = brandMapper.getBrandByBrandId(plan.getBrand().getBrandId());
			Brand brand = brandMapper.getBrandById(plan.getBrand().getBrandId());
			plan.setBrand(brand);
			//查询所有排期中的商品
			//List<Product> products = planMapper.getPlanProductListByPlanId(plan.getPlanId());
			//plan.setProducts(products);
			//查询折扣最低的一个
			Product p = planMapper.getMinRebateProductByPlanId(plan.getPlanId());
			plan.setProduct(p);
		}
		return plans;
	}

	
	@Override
	public List<Plan> listPlanLowestDiscountBrands() throws UhomeStoreException {
		List<Plan> plans = planMapper.getPlanListLowestDiscount();
		for(int i = 0; i< plans.size(); i++){
			Plan plan = plans.get(i);
			//Brand brand = brandMapper.getBrandByBrandId(plan.getBrand().getBrandId());
			Brand brand = brandMapper.getBrandById(plan.getBrand().getBrandId());
			plan.setBrand(brand);
			//查询所有排期中的商品
			//List<Product> products = planMapper.getPlanProductListByPlanId(plan.getPlanId());
			//plan.setProducts(products);
			//查询折扣最低的一个
			Product p = planMapper.getMinRebateProductByPlanId(plan.getPlanId());
			plan.setProduct(p);
		}
		return plans;
	}

	@Override
	public List<Plan> listPlanLastOnlineBrands() throws UhomeStoreException {
		List<Plan> plans = planMapper.getPlanListLastOnlineSpecialSeller();
		for(int i = 0; i< plans.size(); i++){
			Plan plan = plans.get(i);
			//Brand brand = brandMapper.getBrandByBrandId(plan.getBrand().getBrandId());
			Brand brand = brandMapper.getBrandById(plan.getBrand().getBrandId());
			plan.setBrand(brand);
			//查询所有排期中的商品
			//List<Product> products = planMapper.getPlanProductListByPlanId(plan.getPlanId());
			//plans.get(i).setProducts(products);
			//查询折扣最低的一个
			Product p = planMapper.getMinRebateProductByPlanId(plan.getPlanId());
			plan.setProduct(p);
		}
		return plans;
	}

	
	@Override
	public List<Plan> listPlanSoonBrands() throws UhomeStoreException {
		List<Plan> plans = planMapper.getPlanListSpecialSellerNoticeByCurrentTime();
		/*for(int i = 0; i< plans.size(); i++){
			Plan plan = plans.get(i);
			Brand brand = brandMapper.getBrandById(plan.getBrand().getBrandId());
			plan.setBrand(brand);
			//查询折扣最低的一个
			Product p = planMapper.getMinRebateProductByPlanId(plan.getPlanId());
			plan.setProduct(p);
		}*/
		return plans;
	}
	
	@Override
	public Map<String,List<Plan>> listPlanOpenSalesBrand()throws UhomeStoreException {
		//通过排期  查询7天的品牌预告 今天10点之前显示  今天、明天、后天  10点之后  显示   明天、后天、大后天
		Map<String,List<Plan>> map = new HashMap<String,List<Plan>>();
		
		List<Plan> todayPlans = new ArrayList<Plan>();
		List<Plan> tomorrowPlans = new ArrayList<Plan>();
		List<Plan> afterTomorrowPlans = new ArrayList<Plan>();
		List<Plan> threeDayFromTodayPlnas = new ArrayList<Plan>();
		
		List<Plan> plans = planMapper.getPlanListBrandNoticeByCurrentTime();
		//当天
		Date todayDate = new Date();
		//明天
		Date tomorrow = DateUtil.nextDate(todayDate);
		//后天
		Date afterTomorrow = DateUtil.nextNumDate(todayDate, 2);
		//大后天
		Date threeDayFromToday = DateUtil.nextNumDate(todayDate, 3);
		
		for(int i = 0; i < plans.size(); i++){
			Date date = plans.get(i).getStartTime();
			String d = DateUtil.toDay(date);
			Brand brand = new Brand();
			brand = brandMapper.get(plans.get(i).getBrand().getBrandId());
			plans.get(i).setBrand(brand);
			//今天
			if(d.equals(DateUtil.toDay(todayDate))){
				todayPlans.add(plans.get(i));
			}
			//明天
			if(d.equals(DateUtil.toDay(tomorrow))){
				tomorrowPlans.add(plans.get(i));
			}
			//后天
			if(d.equals(DateUtil.toDay(afterTomorrow))){
				afterTomorrowPlans.add(plans.get(i));
			}
			//大后天
			if(d.equals(DateUtil.toDay(threeDayFromToday))){
				threeDayFromTodayPlnas.add(plans.get(i));
			}
		}
		map.put("today", todayPlans);
		map.put("tomorrow", tomorrowPlans);
		map.put("afterTomorrow", afterTomorrowPlans);
		map.put("threeDayFromToday", threeDayFromTodayPlnas);
		return map;
	}

	@Override
	public void searchProductListByPlanId(BasePagination<Product> productPage) throws UhomeStoreException {
		
			//url转化  将相应的数字转化成对应的字符  sort dir  
			String sort = productPage.getSort();
			String dir = productPage.getDir();
			if("100000".equals(dir)){
				productPage.setDir("asc");
			}else if("100001".equals(dir)){
				productPage.setDir("desc");
			}
			for(Entry<String,String> entry:Product.SORTSMAP.entrySet()){
				if(entry.getKey().equals(sort)){
					productPage.setSort(entry.getValue());
				}
			}
			//设置每页显示的数量 
			if(null==productPage.getLimit()
					||(productPage.getLimit().intValue()!=Product.SHOW_LIMIT_20.intValue()
							&&productPage.getLimit().intValue()!=Product.SHOW_LIMIT_40.intValue()
							&&productPage.getLimit().intValue()!=Product.SHOW_LIMIT_60.intValue())){
				productPage.setLimit(Product.SHOW_LIMIT_20.intValue());
			}
			Map<String, Object> searchParams = productPage.getSearchParamsMap();
			
			//是否仅显示有货商品标识
			Integer showScope = 0;
			
			if(null!=searchParams.get("showScope")){
				showScope = Integer.parseInt((String) searchParams.get("showScope"));
			}else if(null==showScope||showScope.intValue()!=Product.SHOW_PART){
				showScope=Product.SHOW_ALL.intValue();
			}
			if(productPage.isNeedSetTotal()){
				Integer total=0;
					if(Product.SHOW_ALL.intValue()==showScope){
						total = planMapper.searchProductList4FrontCount(searchParams);
					}
					if(Product.SHOW_PART.intValue()==showScope){
						total = planMapper.searchProductList4FrontStoreCount(searchParams);
					}
				
				productPage.setTotal(total);
			}
			Collection<Product> result=null;
			//显示所有商品
			if(Product.SHOW_ALL.intValue()==showScope){
				//如果是按销售数量排序  则执行另外一条 sql
				if(Product.SALESQUANTITY.equals(productPage.getSort())){
					result = planMapper.searchProductList4FrontBySalesQuantity(searchParams);
				}else{
					result = planMapper.searchProductList4Front(searchParams);
				}
			}
			//显示有货商品
			if(Product.SHOW_PART.intValue()==showScope){
				//如果是按销售数量排序  则执行另外一条 sql
				if(Product.SALESQUANTITY.equals(productPage.getSort())){
					result = planMapper.searchProductList4FrontBySalesQuantityAndStore(searchParams);
				}else{
					result = planMapper.searchProductList4FrontStore(searchParams);
				}
			}
			
			
			if(result != null){
				//商品获取品牌属性
				for(Product product : result){
					//关联卖家如果卖家被禁用  前台显示  该商品 已售馨  
					User u = userInfoMapper.getUserBySellerId(product.getSellerId());
					if(u != null && u.getStatus() != null && u.getStatus().intValue() == User.STATUS_UNABLE){
						//禁用
						product.setIsSellerOff(true);
					}
					//Brand brand = brandMapper.getBrandByBrandId(product.getBrandId());
					Brand brand = brandMapper.getBrandById(product.getBrandId());
					product.setBrand(brand);
					//关联排期
					List<Plan> plans = new ArrayList<Plan>();
					plans.add(planMapper.get(Integer.parseInt(String.valueOf(searchParams.get("planId")))));
					product.setPlans(plans);
					//获取sku信息
					List<ProductSku> productSkus = productMapper.getProductSkuListByProductId(product.getProductId());
					product.setProductSkus(productSkus);
					//设置商品库存
					product.setSaleInventoryCount(productService.getProductInventoryCount(product));
				}
			}
			productPage.setResult(result);
	}

	/* (non-Javadoc)
	 * @see com.ytoxl.module.uhome.uhomebase.service.PlanService#getMinBebateProductByPlanId(java.lang.Integer)
	 */
	@Override
	public Product getMinRebateProductByPlanId(BasePagination<Product> productPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = productPage.getSearchParamsMap();
		Product product = planMapper.getMinRebateProductByPlanId(Integer.parseInt(String.valueOf(searchParams.get("planId"))));
		return product;
	}

	/* (non-Javadoc)
	 * @see com.ytoxl.module.uhome.uhomebase.service.PlanService#listPlansByCategoryName(java.util.List, com.ytoxl.module.uhome.uhomebase.dataobject.ProductCategory)
	 */
	@Override
	public List<Plan> getPlansByCategoryId(List<Plan> plans,
			ProductCategory productCategory) throws UhomeStoreException {
		//获取包括名字的目录
		//List<ProductCategory> productCategorys = productCategoryMapper.listProductCategroyByCategoryName(productCategory.getName());
		List<ProductCategory> productCategorys = productCategoryMapper.listProductCategroyByCategoryId(productCategory.getProductCategoryId());
		List<Plan> planss = new ArrayList<Plan>();
		for(ProductCategory pc : productCategorys){
			for(Plan p : plans){
				//如果这个排期的品牌属于 这个子目录
				if(p.getBrand().getProductCategoryId().equals(pc.getProductCategoryId())){
					planss.add(p);
				}
			}
		}
		return planss;
	}

	@Override
	public Map<Integer, Map<String,List<Plan>>> listPlanByCategorys() throws UhomeStoreException {
		Map<Integer, Map<String,List<Plan>>> map = new LinkedHashMap<Integer, Map<String,List<Plan>>>();
		//所有的一级类目
		Map<String,List<Plan>> m;
		List<ProductCategory> pcs = productCategoryMapper.listProductCategory();
		for(ProductCategory pc : pcs){
			m = new LinkedHashMap<String,List<Plan>>();
			List<Plan> p1 = planMapper.getPlanListMostPopularByCategoryId(pc.getProductCategoryId());
			List<Plan> p2 = planMapper.getPlanListLastOnlineByCategoryId(pc.getProductCategoryId());
			List<Plan> p3 = planMapper.getPlanListLowestDiscountByCategoryId(pc.getProductCategoryId());
			for(int i = 0; i< p1.size(); i++){
				Brand brand = new Brand();
				brand = brandMapper.get(p1.get(i).getBrand().getBrandId());
				p1.get(i).setBrand(brand);
				//查询所有排期中的商品
				List<Product> products = planMapper.getPlanProductListByPlanId(p1.get(i).getPlanId());
				p1.get(i).setProducts(products);
			}
			for(int i = 0; i< p2.size(); i++){
				Brand brand = new Brand();
				brand = brandMapper.get(p2.get(i).getBrand().getBrandId());
				p2.get(i).setBrand(brand);
				//查询所有排期中的商品
				List<Product> products = planMapper.getPlanProductListByPlanId(p2.get(i).getPlanId());
				p2.get(i).setProducts(products);
			}
			for(int i = 0; i< p3.size(); i++){
				Brand brand = new Brand();
				brand = brandMapper.get(p3.get(i).getBrand().getBrandId());
				p3.get(i).setBrand(brand);
				//查询所有排期中的商品
				List<Product> products = planMapper.getPlanProductListByPlanId(p3.get(i).getPlanId());
				p3.get(i).setProducts(products);
			}
			m.put("mostPopular", p1);
			m.put("lastOnLine", p2);
			m.put("lowestDiscount", p3);
			map.put(pc.getProductCategoryId(), m);
		}
		return map;
	}
	
	@Override
	public List<Plan> listPlanMostPopularByCategoryId(Integer productCategoryId) throws UhomeStoreException {
		List<Plan> p = planMapper.getPlanListMostPopularByCategoryId(productCategoryId);
		for(int i = 0; i< p.size(); i++){
			Plan plan = p.get(i);
			Brand brand = brandMapper.getBrandById(plan.getBrand().getBrandId());
			plan.setBrand(brand);
			//查询所有排期中的商品
			//List<Product> products = planMapper.getPlanProductListByPlanId(p.get(i).getPlanId());
			//p.get(i).setProducts(products);
			//查询折扣最低的一个
			Product product = planMapper.getMinRebateProductByPlanId(plan.getPlanId());
			plan.setProduct(product);
		}
		return p;
	}
	
	@Override
	public List<Plan> listPlanLastOnlineByCategoryId(Integer productCategoryId)throws UhomeStoreException {
		List<Plan> p = planMapper.getPlanListLastOnlineByCategoryId(productCategoryId);
		for(int i = 0; i< p.size(); i++){
			Plan plan = p.get(i);
			//brand = brandMapper.get(p.get(i).getBrand().getBrandId());
			Brand brand = brandMapper.getBrandById(plan.getBrand().getBrandId());
			plan.setBrand(brand);
			//查询所有排期中的商品
			//List<Product> products = planMapper.getPlanProductListByPlanId(p.get(i).getPlanId());
			//p.get(i).setProducts(products);
			//查询折扣最低的一个
			Product product = planMapper.getMinRebateProductByPlanId(plan.getPlanId());
			plan.setProduct(product);
		}
		return p;
	}
	
	@Override
	public List<Plan> listPlanLowestDiscountByCategoryId(Integer productCategoryId) throws UhomeStoreException {
		List<Plan> p = planMapper.getPlanListLowestDiscountByCategoryId(productCategoryId);
		for(int i = 0; i< p.size(); i++){
			Plan plan = p.get(i);
			Brand brand = brandMapper.getBrandById(plan.getBrand().getBrandId());
			plan.setBrand(brand);
			//查询所有排期中的商品
			//List<Product> products = planMapper.getPlanProductListByPlanId(p.get(i).getPlanId());
			//p.get(i).setProducts(products);//查询折扣最低的一个
			//查询折扣最低的一个
			Product product = planMapper.getMinRebateProductByPlanId(plan.getPlanId());
			plan.setProduct(product);
		}
		return p;
	}
	
	/**
	 * 根据品牌ID集合查询当天开售的排期(品牌定阅邮件发送)
	 * @param brandIds
	 * @return
	 */
	@Override
	public List<Plan> listTodaySpecialSellerPlansByBrandIds(List<Integer> brandIds) throws UhomeStoreException {
		List<Plan> plans = planMapper.listTodaySpecialSellerPlansByBrandIds(brandIds);
		return plans;
	}
	/**
	 * 根据品牌planid集合查询当天开售的排期
	 * @param brandIds
	 * @return
	 */
	@Override
	public List<Plan> listTodaySpecialSellerPlansByPlanIds(List<Integer> planids) throws UhomeStoreException {
		return planMapper.listTodaySpecialSellerPlansByPlanIds(planids);
	}
	/**
	 * 获取所有发布和下架排期
	 * @param brandIds
	 * @return
	 */
	@Override
	public List<Plan> listAllPlans() throws UhomeStoreException {
		return planMapper.listAllPlans();
	}
	
	/**
	 * 通过商家ID获取所有发布和下架的排期
	 * @param brandIds
	 * @return
	 */
	@Override
	public List<Plan> listPlansBySellerId(Map<String,String> map) throws UhomeStoreException {
		return planMapper.listPlansBySellerId(map);
	}
	
}
