package com.ytoxl.module.uhome.uhomecontent.dataobject.tbl;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 广告主表
 * @author guoxinzhi
 *
 */
public class AdvertisementTbl {
	protected Integer advertisementId;
	protected Integer productId;
	protected Integer brandId;
	protected Integer advPositionId;    //not null 广告显示的位置
	protected String name;              //null comment 广告名称
	protected String linkUrl;           //广告将要跳转到的url
	protected String imageUrl;          //广告资源地址例 图片等
	protected Integer rank;
	protected Date startTime;
	protected Date endTime;
	protected Date createTime;
	protected Date updateTime;
	protected Short isDefault;        //1是默认广告，2不是默认广告
	protected Short isLogin;        //0不验证  1验证登录
	
	
	
	
	public Short getIsLogin() {
		return isLogin;
	}
	public void setIsLogin(Short isLogin) {
		this.isLogin = isLogin;
	}
	public Integer getAdvertisementId() {
		return advertisementId;
	}
	public void setAdvertisementId(Integer advertisementId) {
		this.advertisementId = advertisementId;
	}
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
	public Integer getAdvPositionId() {
		return advPositionId;
	}
	public void setAdvPositionId(Integer advPositionId) {
		this.advPositionId = advPositionId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
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
	public Short getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Short isDefault) {
		this.isDefault = isDefault;
	}
	
}
