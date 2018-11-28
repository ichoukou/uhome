package com.ytoxl.module.uhome.uhomecontent.dataobject.tbl;

import java.util.Date;

/**
 * 专题模板广告信息
 * 
 * @author xu
 *
 */
public class SpecialtopicAdvertisementTbl {

	protected Integer specialTopicAdvId; //主键ID
	protected Integer specialTopicAdvPositionId; //广告位置id
	protected Integer specialTopicTempletId; //专题模板id
	protected String imageUrl;	//广告图片地址
	protected String turnUrl;	//跳转地址
	protected Date createTime;
	protected Date updateTime;
	/**二次需求，新增字段*/
	protected String productIds;	//产品编码（即id），多个以逗号分隔
	protected String productImageUrls;	//产品图片路径，多个以逗号分隔
	protected String productLinkUrls;	//产品链接路径，多个以逗号分隔
	
	public Integer getSpecialTopicAdvId() {
		return specialTopicAdvId;
	}
	public void setSpecialTopicAdvId(Integer specialTopicAdvId) {
		this.specialTopicAdvId = specialTopicAdvId;
	}
	public Integer getSpecialTopicAdvPositionId() {
		return specialTopicAdvPositionId;
	}
	public void setSpecialTopicAdvPositionId(Integer specialTopicAdvPositionId) {
		this.specialTopicAdvPositionId = specialTopicAdvPositionId;
	}
	public Integer getSpecialTopicTempletId() {
		return specialTopicTempletId;
	}
	public void setSpecialTopicTempletId(Integer specialTopicTempletId) {
		this.specialTopicTempletId = specialTopicTempletId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getTurnUrl() {
		return turnUrl;
	}
	public void setTurnUrl(String turnUrl) {
		this.turnUrl = turnUrl;
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
	public String getProductIds() {
		return productIds;
	}
	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}
	public String getProductImageUrls() {
		return productImageUrls;
	}
	public void setProductImageUrls(String productImageUrls) {
		this.productImageUrls = productImageUrls;
	}
	public String getProductLinkUrls() {
		return productLinkUrls;
	}
	public void setProductLinkUrls(String productLinkUrls) {
		this.productLinkUrls = productLinkUrls;
	}
	
}
