package com.ytoxl.module.uhome.uhomereport.dataobject.resultmap;

import java.math.BigDecimal;
import java.util.Date;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;
import com.ytoxl.module.uhome.uhomebase.common.utils.excel.FormatType;

/**
 * 品牌销售明细 By排期
 * @author xu
 *
 */
public class BrandSellPlanReport {

	/** 排期ID */
	protected Integer planId;
	
	/** 商家ID */
	protected Integer sellerId;
	
	/** 商家名称 */
	@ExcelField(filedName="商家",sort=1)
	protected String sellerName;
	
	/** 品牌ID */
	protected Integer brandId;
	
	/** 品牌名称 */
	@ExcelField(filedName="品牌",sort=2)
	protected String brandName;
	
	/** 分类名称 */
	@ExcelField(filedName="分类",sort=3)
	protected String categoryName;
	
	/** 上线SKU数(即每个不同规格的商品) */
	@ExcelField(filedName="上线SKU数",sort=4)
	protected Integer onlineSkuNum;
	
	/** 有销售SKU数(即已卖出些规格的产品) */
	@ExcelField(filedName="有销售SKU数",sort=5)
	protected Integer skuSellNum;
	
	/** 销售数量(即已卖出产品数量) */
	@ExcelField(filedName="销售数量",sort=6)
	protected Integer sellNum;
	
	/** 销售金额 */
	@ExcelField(filedName="销售金额",sort=7)
	protected BigDecimal sellAmount;
	
	/** 上线日期 */
	@ExcelField(filedName="上线日期",sort=8,formatType=FormatType.DATE,pattern="yyyy-MM-dd HH:mm:ss")
	protected Date startTime;
	
	/** 下线日期 */
	@ExcelField(filedName="下线日期",sort=9,formatType=FormatType.DATE,pattern="yyyy-MM-dd HH:mm:ss")
	protected Date endTime;

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getOnlineSkuNum() {
		return onlineSkuNum;
	}

	public void setOnlineSkuNum(Integer onlineSkuNum) {
		this.onlineSkuNum = onlineSkuNum;
	}

	public Integer getSkuSellNum() {
		return skuSellNum;
	}

	public void setSkuSellNum(Integer skuSellNum) {
		this.skuSellNum = skuSellNum;
	}

	public Integer getSellNum() {
		return sellNum;
	}

	public void setSellNum(Integer sellNum) {
		this.sellNum = sellNum;
	}

	public BigDecimal getSellAmount() {
		return sellAmount;
	}

	public void setSellAmount(BigDecimal sellAmount) {
		this.sellAmount = sellAmount;
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

}
