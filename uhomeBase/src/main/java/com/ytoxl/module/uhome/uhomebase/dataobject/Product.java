package com.ytoxl.module.uhome.uhomebase.dataobject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.ytoxl.module.uhome.uhomebase.dataobject.tbl.ProductTbl;

public class Product extends ProductTbl {

	public static final Short ISIMPORT_NO = 0;// 非进口
	public static final Short ISIMPORT_YES = 1;// 进口
	public static final Short[] ISIMPORTS = new Short[]{ISIMPORT_NO, ISIMPORT_YES};
	
	public static final Short STATUS_DRAFT = 1; // 草稿
	public static final Short STATUS_CHECK_PEND = 2; // 待审核
	public static final Short STATUS_PASS = 3; // 审核通过
	public static final Short STATUS_NO_PASS = 4; // 审核不通过
	public static final Short STATUS_DELETE = 5; // 删除
	public static final Short STATUS_PLAN_PEND = 6; //等待排期
	public static final Short STATUS_PLANED = 7; //已排期
	public static final Short STATUS_SPECIAL_SELLER_INPROGRESS = 8; //特卖中
	public static final Short STATUS_SELLOUT = 9; //已售磬
	public static final Short STATUS_EXPRIED = 10; //已过期
	//页面显示数量
	public static final Short SHOW_LIMIT_20 = 20;
	public static final Short SHOW_LIMIT_40 = 40;
	public static final Short SHOW_LIMIT_60 = 60;
	
	//是否仅显示有货商品标识
	public static final Short SHOW_ALL = 0;
	public static final Short SHOW_PART = 1;
	
	//商品状态
	public static final Short[] STATUSES = new Short[] { STATUS_DRAFT, STATUS_PLAN_PEND, STATUS_PLANED, 
		STATUS_SPECIAL_SELLER_INPROGRESS, STATUS_SELLOUT, STATUS_EXPRIED };
	 //审核状态
	public static final Short[] REVIEWSTATUSES = new Short[] { STATUS_CHECK_PEND, STATUS_PASS, STATUS_NO_PASS };
	
	//因 搜索页面和进口商品页面 很相似  用type来区分
	public static final Short PAGE_TYPE_SEARCH = 1;
	public static final Short PAGE_TYPE_IMPORT = 2;
	
	//url优化
	
	@SuppressWarnings("serial")
	public static final Map<String,String> DIRSMAP = new HashMap<String,String>(){
		{
			put("100000","asc");
			put("100001","desc");
		}
	};
	@SuppressWarnings("serial")
	public static final Map<String,String> SORTSMAP = new HashMap<String,String>(){
		{
		put("100","salesQuantity");
		put("200","rebate");
		put("300","salePrice");
		put("400","createTime");
		}
	};
	
	//搜索按销售数量排序用
	public static final String SALESQUANTITY="salesQuantity";
	
	//用于前台商品详细的展示 例如：颜色-->存放颜色的list
	protected Map<String,Set<SkuOptionValue>> skuOptionValueMap;
	//是否有规格或者颜色  用于前台判断  没有不现实  规格颜色
	protected Boolean isSkuOptionValue;
	
	//用于点击放入购物车用 key是颜色+规格的id,value是productSkuId  前台没有用到  用于service层
	protected Map<String,String> productSkuMap;
	//用于前台商品详细的展示 例如：颜色-->存放颜色的list    存放json格式  便于 js取值
	protected String productSkuMapJson;
	//每个sku对应的库存 skuId对应inventtory       存放json格式  便于 js取值
	protected String productSkuInventoryMapJson;
	
	protected Brand brand; // 关联品牌
	protected List<Plan> plans;// 关联排期
	protected Seller seller;// 关联卖家
	//卖家被禁用  前台显示  该商品 已售馨   
	protected Boolean isSellerOff;
	//商品在当前时间是否没有排期了  若有用户点击过来显示 已经下架
	protected Boolean isOutOfStock;

	protected List<ProductSku> productSkus; // 关联商品SKU
	
	protected List<SkuOption> skuOptions; // 商品SKU信息
	
	protected Integer saleInventoryCount;// 特卖库存总数
	protected Integer secKillInventoryCount;// 秒杀库存总数
	protected Integer specialSellerPlanNum; //特卖排期个数
	protected Integer salesNum; //商品销售次数
	
	protected Date maxSellStartTime; //最大开始可售日期
	protected Date minSellEndTime; //最小结束可售日期
	
	protected String expireFlag; //商品是否过期
	//商品编号 用户批量上传商品 区别同一个excel中相同的商品 sku不一样
	protected String productNo;
	//商品品类 用户批量上传商品 
	protected ProductCategory productCategory;
	

	protected Integer productSkuColorNum;
	protected Integer productSkuSizeNum;
	

	protected Postage postage;
	

	//图片集合
	protected List<String> imageList;
	
	protected String defaultImage;

	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}

	/**
	 * 获取商品第一张默认图片，添加相应前后缀可以访问缩略图
	 * @return
	 */
	public String getDefaultImage(){
		String thumbnail = null;
		if(StringUtils.isNotEmpty(imageUrls)){
			String[] thumbnails = imageUrls.split(",");
			thumbnail = thumbnails != null  && thumbnails.length > 0 ? thumbnails[0] : "";
		}
		return thumbnail;
	}
	
	public List<String> getImageList() {
		List<String> list = new ArrayList<String>();
		String[] str = imageUrls.split(",");
		list = Arrays.asList(str);
		return list;
	}
	
	/**
	 * 获取商品当前活动状态
	 * @param type
	 * @return
	 */
	private Plan getPlan(Short type) {
		Date currentTime = new Date();
		for (int i=0; plans!=null&&i<plans.size(); i++) {
			Plan plan = plans.get(i);
			if (plan.getType().equals(type) && Plan.STATUS_RELEASE.equals(plan.getStatus())) {
				if (currentTime.compareTo(plan.getStartTime()) >= 0
						&& currentTime.compareTo(plan.getEndTime()) <= 0) {
					if(type.equals(Plan.TYPE_SPECIAL_SELLER)){
						//特卖中
						plan.setActivityStatus(Plan.ACTIVITYSTATUS_SPECIAL_SELLER_INPROGRESS);
					}else if(type.equals(Plan.TYPE_SEC_KILL)){
						//秒杀中
						plan.setActivityStatus(Plan.ACTIVITYSTATUS_SECKILL_INPROGRESS);
					}
					return plan;
				} else if (currentTime.compareTo(plan.getStartTime()) < 0) {
					if(type.equals(Plan.TYPE_SPECIAL_SELLER)){
						//等待特卖
						plan.setActivityStatus(Plan.ACTIVITYSTATUS_SPECIAL_SELLER_PEND);
					}else if(type.equals(Plan.TYPE_SEC_KILL)){
						//等待秒杀
						plan.setActivityStatus(Plan.ACTIVITYSTATUS_SECKILL_PEND);
					}
					return plan;
				}
			}
		}
		return null;
	}

	public Plan getSpecialSellerPlan() {
		return getPlan(Plan.TYPE_SPECIAL_SELLER);
	}

	public Plan getSecKillPlan() {
		return getPlan(Plan.TYPE_SEC_KILL);
	}
	
	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}
	
	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public List<Plan> getPlans() {
		return plans;
	}

	public void setPlans(List<Plan> plans) {
		this.plans = plans;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public List<ProductSku> getProductSkus() {
		return productSkus;
	}

	public void setProductSkus(List<ProductSku> productSkus) {
		this.productSkus = productSkus;
	}
	
	public List<SkuOption> getSkuOptions() {
		return skuOptions;
	}

	public void setSkuOptions(List<SkuOption> skuOptions) {
		this.skuOptions = skuOptions;
	}

	public Integer getSaleInventoryCount() {
		return saleInventoryCount;
	}

	public void setSaleInventoryCount(Integer saleInventoryCount) {
		this.saleInventoryCount = saleInventoryCount;
	}

	public Integer getSecKillInventoryCount() {
		return secKillInventoryCount;
	}

	public void setSecKillInventoryCount(Integer secKillInventoryCount) {
		this.secKillInventoryCount = secKillInventoryCount;
	}

	public Map<String, String> getProductSkuMap() {
		return productSkuMap;
	}

	public Boolean getIsSkuOptionValue() {
		return isSkuOptionValue;
	}

	public void setIsSkuOptionValue(Boolean isSkuOptionValue) {
		this.isSkuOptionValue = isSkuOptionValue;
	}

	public void setProductSkuMap(Map<String, String> productSkuMap) {
		this.productSkuMap = productSkuMap;
	}

	public Map<String, Set<SkuOptionValue>> getSkuOptionValueMap() {
		return skuOptionValueMap;
	}

	public void setSkuOptionValueMap(
			Map<String, Set<SkuOptionValue>> skuOptionValueMap) {
		this.skuOptionValueMap = skuOptionValueMap;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public String getProductSkuInventoryMapJson() {
		return productSkuInventoryMapJson;
	}

	public Boolean getIsSellerOff() {
		return isSellerOff;
	}

	public void setIsSellerOff(Boolean isSellerOff) {
		this.isSellerOff = isSellerOff;
	}

	public Boolean getIsOutOfStock() {
		return isOutOfStock;
	}

	public void setIsOutOfStock(Boolean isOutOfStock) {
		this.isOutOfStock = isOutOfStock;
	}

	public void setProductSkuInventoryMapJson(String productSkuInventoryMapJson) {
		this.productSkuInventoryMapJson = productSkuInventoryMapJson;
	}

	public String getProductSkuMapJson() {
		return productSkuMapJson;
	}

	public void setProductSkuMapJson(String productSkuMapJson) {
		this.productSkuMapJson = productSkuMapJson;
	}

	public Integer getSpecialSellerPlanNum() {
		return specialSellerPlanNum;
	}

	public void setSpecialSellerPlanNum(Integer specialSellerPlanNum) {
		this.specialSellerPlanNum = specialSellerPlanNum;
	}
	
	public Integer getSalesNum() {
		return salesNum;
	}

	public void setSalesNum(Integer salesNum) {
		this.salesNum = salesNum;
	}

	public Date getMaxSellStartTime() {
		return maxSellStartTime;
	}

	public void setMaxSellStartTime(Date maxSellStartTime) {
		this.maxSellStartTime = maxSellStartTime;
	}

	public Date getMinSellEndTime() {
		return minSellEndTime;
	}

	public void setMinSellEndTime(Date minSellEndTime) {
		this.minSellEndTime = minSellEndTime;
	}

	public String getExpireFlag() {
		return expireFlag;
	}

	public void setExpireFlag(String expireFlag) {
		this.expireFlag = expireFlag;
	}

	public Integer getProductSkuColorNum() {
		return productSkuColorNum;
	}

	public void setProductSkuColorNum(Integer productSkuColorNum) {
		this.productSkuColorNum = productSkuColorNum;
	}

	public Integer getProductSkuSizeNum() {
		return productSkuSizeNum;
	}

	public void setProductSkuSizeNum(Integer productSkuSizeNum) {
		this.productSkuSizeNum = productSkuSizeNum;
	}


	public Postage getPostage() {
		return postage;
	}

	public void setPostage(Postage postage) {
		this.postage = postage;
	}

	@Override
	public String toString() {
		return productId+"-->"+name;
	}
}
