package com.ytoxl.module.uhome.uhomereport.dataobject.resultmap;

/**
 * 商品颜色/规格,只用作显示
 * 
 * @author xu
 *
 */
public class ReportProductSkuOptionValue {
	
	public static final Integer COLOR =1; //颜色
	public static final Integer SPECIFICATION =2; //颜色

	/** 规格类型：1-颜色，2-尺寸 */
	private Integer skuType;
	
	/** 规格名称，如：颜色、尺寸等 */
	private String skuOptionName;
	
	/** 规格值，如：红色、120g等 */
	private String skuOptionValue;

	public Integer getSkuType() {
		return skuType;
	}

	public void setSkuType(Integer skuType) {
		this.skuType = skuType;
	}

	public String getSkuOptionName() {
		return skuOptionName;
	}

	public void setSkuOptionName(String skuOptionName) {
		this.skuOptionName = skuOptionName;
	}

	public String getSkuOptionValue() {
		return skuOptionValue;
	}

	public void setSkuOptionValue(String skuOptionValue) {
		this.skuOptionValue = skuOptionValue;
	}
	
}
