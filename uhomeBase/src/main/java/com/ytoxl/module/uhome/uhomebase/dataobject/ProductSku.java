package com.ytoxl.module.uhome.uhomebase.dataobject;

import java.math.BigDecimal;
import java.util.List;

import com.ytoxl.module.uhome.uhomebase.dataobject.tbl.ProductSkuTbl;

public class ProductSku extends ProductSkuTbl {
	// 以下类型用于区分库存来源
	public static final short TYPE_SEC_KILL = 1; // 类型是秒杀
	public static final short TYPE_SPECIAL_SELLER = 2; // 类型是特卖
	public static final short ISDELETE_NO = 0; //否
	public static final short ISDELETE_YES = 1; //是

	protected List<ProductSkuOptionValue> productSkuOptionValues;

	protected Product product; // 该sku对应的商品
	protected Integer num; // 订单中该商品购买数量
	protected Double seckillRebate; //秒杀折扣
	protected Integer seckillPlanNum; // 该sku已参加的秒杀活动数量
	protected Boolean isActivity; //当前是否在排期内
	protected BigDecimal postage;
	
	//sku颜色用于商品导入
	protected String productSkuColor;
	//sku尺寸用于商品导入
	protected String productSkuSize;
	
	protected List<Event> events;

	public List<ProductSkuOptionValue> getProductSkuOptionValues() {
		return productSkuOptionValues;
	}

	public void setProductSkuOptionValues(
			List<ProductSkuOptionValue> productSkuOptionValues) {
		this.productSkuOptionValues = productSkuOptionValues;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Double getSeckillRebate() {
		return seckillRebate;
	}

	public void setSeckillRebate(Double seckillRebate) {
		this.seckillRebate = seckillRebate;
	}

	public Integer getSeckillPlanNum() {
		return seckillPlanNum;
	}

	public void setSeckillPlanNum(Integer seckillPlanNum) {
		this.seckillPlanNum = seckillPlanNum;
	}

	@Override
	public String toString() {
		return this.productSkuId + "-->" + this.productSkuOptionValues;
	}

	public Boolean getIsActivity() {
		return isActivity;
	}

	public void setIsActivity(Boolean isActivity) {
		this.isActivity = isActivity;
	}

	public String getProductSkuColor() {
		return productSkuColor;
	}

	public void setProductSkuColor(String productSkuColor) {
		this.productSkuColor = productSkuColor;
	}

	public String getProductSkuSize() {
		return productSkuSize;
	}

	public void setProductSkuSize(String productSkuSize) {
		this.productSkuSize = productSkuSize;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public BigDecimal getPostage() {
		return postage;
	}

	public void setPostage(BigDecimal postage) {
		this.postage = postage;
	}
	
	
	
}
