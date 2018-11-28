package com.ytoxl.module.uhome.uhomebase.dataobject.tbl;

import java.math.BigDecimal;
import java.util.Date;

public class ProductTbl {
	protected Integer productId;//商品id
	protected Integer brandId;//商标id
	protected Integer productCategoryId;//商品分类id
	protected Integer sellerId;//商家id
	protected String name;//商品名称
	protected String describe;//描述
	protected String secKillDescribe;
	protected String imageUrls;
	protected BigDecimal secKillPrice;//特价价格
	protected BigDecimal salePrice;//销售价
	protected BigDecimal marketPrice;//市场价
	protected Short isImport;
	protected Integer rank;//权重
	protected Short status; // 状态：1=草稿，2=审核中，3=审核通过，4=审核不通过
	protected String remark; // 备注没有通过审核的原因
	protected String keyWord;//关键字
	protected Integer hits; // 点击量
	protected Double rebate; // 折扣
	protected Date sellStartTime;//销售开始时间
	protected Date sellEndTime;//销售开始时间
	protected Date createTime;//创建时间

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getBrandId() {
		return brandId;
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

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getSecKillDescribe() {
		return secKillDescribe;
	}

	public void setSecKillDescribe(String secKillDescribe) {
		this.secKillDescribe = secKillDescribe;
	}

	public String getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(String imageUrls) {
		this.imageUrls = imageUrls;
	}

	public BigDecimal getSecKillPrice() {
		return secKillPrice;
	}

	public void setSecKillPrice(BigDecimal secKillPrice) {
		this.secKillPrice = secKillPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Short getIsImport() {
		return isImport;
	}

	public void setIsImport(Short isImport) {
		this.isImport = isImport;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public Double getRebate() {
		return rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}

	public Date getSellStartTime() {
		return sellStartTime;
	}

	public void setSellStartTime(Date sellStartTime) {
		this.sellStartTime = sellStartTime;
	}

	public Date getSellEndTime() {
		return sellEndTime;
	}

	public void setSellEndTime(Date sellEndTime) {
		this.sellEndTime = sellEndTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	protected Date updateTime;

}
