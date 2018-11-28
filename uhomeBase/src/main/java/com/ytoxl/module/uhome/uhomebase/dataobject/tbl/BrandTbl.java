package com.ytoxl.module.uhome.uhomebase.dataobject.tbl;

import java.util.Date;

import com.ytoxl.module.core.common.utils.DateUtil;

/**
 * 名牌
 * 
 * @author wangguoqing
 * 
 */
public class BrandTbl {
	protected Integer brandId;
	protected Integer productCategoryId; // 品牌所属类目
	protected String name;
	protected String brandPinYin;  //品牌名称拼音
	protected String englishName;
	protected String founder; // 品牌创始人
	protected String country; // 所属国家
	protected Date foundationTime; // 品牌创建时间
	protected String feature; // 品牌特点
	protected Short isHotSeller; //是否热卖品牌
	protected Integer rank; // 排序
	protected String logoImageUrl;
	protected String brandImageUrl;
	protected String brandBannerImageUrl;
	protected String describe; // 品牌的描述
	protected Date updateTime;
	protected Date createTime;
	protected Short isForbidden;

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

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getFounder() {
		return founder;
	}

	public void setFounder(String founder) {
		this.founder = founder;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getFoundationTime() {
		return foundationTime;
	}

	public void setFoundationTime(String foundationTime) {
		if(foundationTime == null || "".equals(foundationTime)){
			this.foundationTime  = null;
		}else{
			this.foundationTime = DateUtil.valueof(foundationTime,"yyyy");
		}
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Short getIsHotSeller() {
		return isHotSeller;
	}

	public void setIsHotSeller(Short isHotSeller) {
		this.isHotSeller = isHotSeller;
	}

	public String getLogoImageUrl() {
		return logoImageUrl;
	}

	public void setLogoImageUrl(String logoImageUrl) {
		this.logoImageUrl = logoImageUrl;
	}

	public String getBrandImageUrl() {
		return brandImageUrl;
	}

	public void setBrandImageUrl(String brandImageUrl) {
		this.brandImageUrl = brandImageUrl;
	}

	public String getBrandBannerImageUrl() {
		return brandBannerImageUrl;
	}

	public void setBrandBannerImageUrl(String brandBannerImageUrl) {
		this.brandBannerImageUrl = brandBannerImageUrl;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBrandPinYin() {
		return brandPinYin;
	}

	public void setBrandPinYin(String brandPinYin) {
		this.brandPinYin = brandPinYin;
	}

	public Short getIsForbidden() {
		return isForbidden;
	}

	public void setIsForbidden(Short isForbidden) {
		this.isForbidden = isForbidden;
	}
}
