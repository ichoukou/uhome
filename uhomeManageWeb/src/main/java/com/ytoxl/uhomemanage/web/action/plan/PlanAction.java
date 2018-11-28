package com.ytoxl.uhomemanage.web.action.plan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.Plan;
import com.ytoxl.module.uhome.uhomebase.dataobject.Postage;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductCategory;
import com.ytoxl.module.uhome.uhomebase.dataobject.Seller;
import com.ytoxl.module.uhome.uhomebase.service.BrandService;
import com.ytoxl.module.uhome.uhomebase.service.PlanService;
import com.ytoxl.module.uhome.uhomebase.service.ProductService;
import com.ytoxl.uhomemanage.web.action.BaseAction;

@SuppressWarnings("serial")
public class PlanAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(PlanAction.class);

	private static final String SEARCH_SELL_PLANS = "searchSellPlans";
	private static final String SEARCH_SECKILL_PLANS = "searchSeckillPlans";
	private static final String SELL_PRODUCTS = "sellProducts";
	private static final String SECKILL_PRODUCTS = "seckillProducts";
	private static final String EDIT_SELL_PLAN = "editSellPlan";
	private static final String EDIT_SECKILL_PLAN = "editSeckillPlan";
	private static final String VIEW_PLAN_PRODUCTS = "viewPlanProducts";
	
	private BasePagination<Plan> planPage;
	private Plan plan;
	private List<Seller> sellers;
	
	private Date startTime;
	private Date endTime;
	private Integer brandId;
	private Integer productCategoryId;
	private Integer planId;
	private Short type;
	private List<Integer> planIds;
	
	private List<Map<Date, Boolean>> dates;//用于排期界面日期条数据显示
	private Integer totalDays;//排期列表总天数,用于设置界面日期容器及列表容器的宽度
	private String resultName;//结果视图名称

	private List<ProductCategory> categoryAndPlanNumList;//分类及相应排期个数
	
	@Autowired
	private PlanService planService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private ProductService productService;
	
	/**
	 * 查询排期列表
	 * @return
	 */
	public String searchPlans() {
		try {
			if (planPage == null) {
				planPage = new BasePagination<Plan>();
			}
			//分类及相应排期个数
			categoryAndPlanNumList = planService.listCategoriesAndPlanNum(planPage, type);
			dates = new ArrayList<Map<Date, Boolean>>();
			
			//查询特卖排期
			if (type==null || type!=null&&type.equals(Plan.TYPE_SPECIAL_SELLER)) {
		
				planService.searchSpecialSellerPlans(planPage, dates);
				resultName = SEARCH_SELL_PLANS;
			
			//查询秒杀排期
			} else if (type.equals(Plan.TYPE_SEC_KILL)) {
				
				planService.searchSecKillPlans(planPage, dates);
				resultName = SEARCH_SECKILL_PLANS;
			
			}
			totalDays = dates.size();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return resultName;
	}
	
	/**
	 * 特卖编辑页面查询卖家信息
	 * @return
	 */
	public String listSellersForSpecialSell() {
		try {
			sellers = planService.listSellersForSpecialSell(startTime, endTime, brandId, productCategoryId, planId);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return SELL_PRODUCTS;
	}
	
	/**
	 * 秒杀编辑页面查询卖家信息
	 * @return
	 */
	public String listSellersForSeckill() {
		try {
			sellers = planService.listSellersForSeckill(startTime, brandId, productCategoryId, planId);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return SECKILL_PRODUCTS;
	}

	/**
	 * 编辑排期
	 * @return
	 */
	public String editPlan() {
		if (type.equals(Plan.TYPE_SPECIAL_SELLER)) {
			resultName = EDIT_SELL_PLAN;
		} else if (type.equals(Plan.TYPE_SEC_KILL)) {
			resultName = EDIT_SECKILL_PLAN;
		}
		if (plan != null && plan.getPlanId() != null) {
			try {
				plan = planService.getPlan(plan.getPlanId(), type);
			} catch (UhomeStoreException e) {
				logger.error(e.getMessage());
			}
		}
		return resultName;
	}

	/**
	 * 获取排期商品
	 * @return
	 */
	public String getPlanProducts() {
		try {
			plan = planService.getPlan(plan.getPlanId(), type);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return VIEW_PLAN_PRODUCTS;
	}
	
	/**
	 * 保存排期
	 * @return
	 */
	public String savePlan() {
		try {
			planService.savePlan(plan);
			setMessage(Boolean.TRUE.toString(), "保存成功！");
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
		return JSONMSG;
	}

	/**
	 * 删除排期
	 * @return
	 */
	public String deletePlan() {
		try {
			plan.setStatus(Plan.STATUS_DELETE);
			planService.updateStatus(plan);
			setMessage(Boolean.TRUE.toString(), "删除成功！");
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
		return JSONMSG;
	}

	/**
	 * 发布排期
	 * @return
	 */
	public String release() {
		try {
			plan.setStatus(Plan.STATUS_RELEASE);
			planService.updateStatus(plan);
			setMessage(Boolean.TRUE.toString(), "发布成功！");
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
		return JSONMSG;
	}
	
	/**
	 * 批量发布排期
	 * @return
	 */
	public String releaseBatch() {
		try {
			planService.updateReleaseBatch(planIds);
			setMessage(Boolean.TRUE.toString(), "批量发布成功！");
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
		return JSONMSG;
	}
	
	/**
	 * 修改权重
	 * @return
	 */
	public String editPlanProductRank(){
		try {
			planService.updatePlanProductRank(plan.getPlanProducts());
			setMessage(Boolean.TRUE.toString(), "修改权重成功");
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
		return JSONMSG;
	}
	
	/**
	 * 下架
	 * @return
	 */
	public String offShelf(){
		try {
			plan.setStatus(Plan.STATUS_OFF_SHELF);
			planService.updateStatus(plan);
			setMessage(Boolean.TRUE.toString(), "下架成功！");
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
		return JSONMSG;
	}

	public List<ProductCategory> getCategoryAndPlanNumList() {
		return categoryAndPlanNumList;
	}

	/**
	 * 获取商品大分类
	 * @return
	 */
	public List<ProductCategory> getProductCategories(){
		List<ProductCategory> productCategories = new ArrayList<ProductCategory>();
		try {
			productCategories = productService.listProductCategories();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return productCategories;
	}
	
	/**
	 * 获取商品子分类
	 * @return
	 */
	public List<ProductCategory> getChildProductCategories() {
		List<ProductCategory> productCategories = new ArrayList<ProductCategory>();
		try {
			productCategories = productService.listChildProductCategories();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return productCategories;
	}
	
	/**
	 * 获取全部品牌
	 * @return
	 */
	public List<Brand> getBrands() {
		List<Brand> brands = new ArrayList<Brand>();
		try {
			brands = brandService.listBrands();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return brands;
	}
	
	/**
	 * 邮费
	 * @return
	 */
	public Short[] getPostageOptions(){
		return Postage.OPTIONS;
	}
	
	/**
	 * 默认邮费选项
	 * @return
	 */
	public Short getDefaultPostageOption(){
		return Postage.OPTION_FREE;
	}

	public BasePagination<Plan> getPlanPage() {
		return planPage;
	}

	public void setPlanPage(BasePagination<Plan> planPage) {
		this.planPage = planPage;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public List<Seller> getSellers() {
		return sellers;
	}

	public void setSellers(List<Seller> sellers) {
		this.sellers = sellers;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(Integer productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public List<Map<Date, Boolean>> getDates() {
		return dates;
	}

	public void setDates(List<Map<Date, Boolean>> dates) {
		this.dates = dates;
	}

	public Integer getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(Integer totalDays) {
		this.totalDays = totalDays;
	}

	public List<Integer> getPlanIds() {
		return planIds;
	}

	public void setPlanIds(List<Integer> planIds) {
		this.planIds = planIds;
	}
}
