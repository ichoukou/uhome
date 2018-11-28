package com.ytoxl.module.uhome.uhomebase.dataobject.tbl;

import java.util.Date;

/**
 * 排期计划
 * 
 * @author wangguoqing
 * 
 */
public class PlanTbl {
	
	protected Integer planId;
	protected Integer brandId;
	protected Integer productCategoryId;
	protected String name; //活动名称
	protected Short type; // 排期类型：1=秒杀,2=特卖
	protected Date startTime;
	protected Date endTime;
	protected String imageUrl;
	protected String coverImageUrl;
	protected String planBannerImageUrl;
	protected Short status; //1=没有发布，2=已经发布，3=已经删除
	protected Date createTime;
	protected Date updateTime;

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCoverImageUrl() {
		return coverImageUrl;
	}

	public void setCoverImageUrl(String coverImageUrl) {
		this.coverImageUrl = coverImageUrl;
	}

	public String getPlanBannerImageUrl() {
		return planBannerImageUrl;
	}

	public void setPlanBannerImageUrl(String planBannerImageUrl) {
		this.planBannerImageUrl = planBannerImageUrl;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
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

}
