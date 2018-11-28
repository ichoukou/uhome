package com.ytoxl.module.uhome.uhomebase.dataobject;

import java.util.List;

import com.ytoxl.module.uhome.uhomebase.dataobject.tbl.PlanTbl;

public class Plan extends PlanTbl {
	public static final Short TYPE_SEC_KILL = 1; // 类型是秒杀
	public static final Short TYPE_SPECIAL_SELLER = 2; // 类型是特卖
	public static final Short[] TYPES = new Short[] { TYPE_SEC_KILL, TYPE_SPECIAL_SELLER };

	public static final Short STATUS_UNRELEASE = 1; // 未发布
	public static final Short STATUS_RELEASE = 2; // 已经发布
	public static final Short STATUS_DELETE = 3; // 删除
	public static final Short STATUS_OFF_SHELF = 4; //已下架

	public static final Short TODAY = 0; // 今天
	public static final Short TOMORROW = 1; // 明天
	public static final Short THE_DAY_AFTER_TOMORROW = 3; // 后天
	public static final Short THREE_DAY_FROM_TODAY = 4; // 大后天
	public static final Short NOTICE = 7; // 特卖秒杀最多预告的天数
	
	//活动状态
	public static final Short ACTIVITYSTATUS_SPECIAL_SELLER = 1; //特卖
	public static final Short ACTIVITYSTATUS_SEC_KILL = 2; //秒杀
	public static final Short ACTIVITYSTATUS_EXPRIE_PRODUCT = 3; //过期商品
	public static final Short ACTIVITYSTATUS_SPECIAL_SELLER_PEND = 4; // 等待特卖
	public static final Short ACTIVITYSTATUS_SPECIAL_SELLER_INPROGRESS = 5; // 特卖中
	public static final Short ACTIVITYSTATUS_SECKILL_PEND = 6; // 等待秒杀
	public static final Short ACTIVITYSTATUS_SECKILL_INPROGRESS = 7; // 秒杀中
	public static final Short ACTIVITYSTATUS_SPECIAL_SELLER_END = 8; //特卖结束
	public static final Short ACTIVITYSTATUS_SECKILL_END = 9; //秒杀结束
	public static final Short[] ACTIVITYSTATUSES = new Short[] {ACTIVITYSTATUS_SPECIAL_SELLER, 
		ACTIVITYSTATUS_SEC_KILL, ACTIVITYSTATUS_EXPRIE_PRODUCT };
	
	public static final Short ALL = 0; //全部
	public static final Short SPECIAL_SELLER_INPROGRESS = 1; //特卖中
	public static final Short SECKILL_INPROGRESS = 2; //秒杀中
	public static final Short RELEASE = 3; //已发布
	public static final Short UNRELEASE = 4; //未发布
	public static final Short EXPRIE = 5; //已过期
	
	protected Brand brand;
	protected ProductCategory productCategory;
	protected List<Product> products;
	protected Product product; // 最低折扣商品
	protected List<PlanProduct> planProducts;
	protected Integer productCount; // (特卖/秒杀)商品数量
	protected PlanProduct seckillPlanProduct; // 关联秒杀排期商品
	protected Short activityStatus;// 活动状态
	protected String expireFlag; //排期是否过期(0表示未过期,1表示过期)
	protected Double minRebate; //最低折扣
	protected Integer dayInterval; //开始时间与查询到的排期列表中最小开始时间的间隔天数
	protected Integer days; //排期天数
	protected Postage postage;

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<PlanProduct> getPlanProducts() {
		return planProducts;
	}

	public void setPlanProducts(List<PlanProduct> planProducts) {
		this.planProducts = planProducts;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public PlanProduct getSeckillPlanProduct() {
		return seckillPlanProduct;
	}

	public void setSeckillPlanProduct(PlanProduct seckillPlanProduct) {
		this.seckillPlanProduct = seckillPlanProduct;
	}

	public Short getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(Short activityStatus) {
		this.activityStatus = activityStatus;
	}

	public String getExpireFlag() {
		return expireFlag;
	}

	public void setExpireFlag(String expireFlag) {
		this.expireFlag = expireFlag;
	}

	public Double getMinRebate() {
		return minRebate;
	}

	public void setMinRebate(Double minRebate) {
		this.minRebate = minRebate;
	}

	public Integer getDayInterval() {
		return dayInterval;
	}

	public void setDayInterval(Integer dayInterval) {
		this.dayInterval = dayInterval;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}
	
	public Postage getPostage() {
		return postage;
	}

	public void setPostage(Postage postage) {
		this.postage = postage;
	}

	@Override
	public String toString() {
		return "planId:" + this.planId + "-->" + "plan type:" + this.type;
	}

}
