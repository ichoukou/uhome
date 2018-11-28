package com.ytoxl.module.uhome.uhomebase.dataobject.tbl;

/**
 * @author wangguoqing
 * 
 */
public class ProductSkuOptionValueTbl {
	
	protected Integer productSkuOptionValueId;
	protected Integer productSkuId;
	protected Integer skuOptionValueId;
	protected String overrideSkuOptionValue;

	public Integer getProductSkuOptionValueId() {
		return productSkuOptionValueId;
	}

	public void setProductSkuOptionValueId(Integer productSkuOptionValueId) {
		this.productSkuOptionValueId = productSkuOptionValueId;
	}

	public Integer getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(Integer productSkuId) {
		this.productSkuId = productSkuId;
	}

	public Integer getSkuOptionValueId() {
		return skuOptionValueId;
	}

	public void setSkuOptionValueId(Integer skuOptionValueId) {
		this.skuOptionValueId = skuOptionValueId;
	}

	public String getOverrideSkuOptionValue() {
		return overrideSkuOptionValue;
	}

	public void setOverrideSkuOptionValue(String overrideSkuOptionValue) {
		this.overrideSkuOptionValue = overrideSkuOptionValue;
	}

}
