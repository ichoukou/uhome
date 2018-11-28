package com.ytoxl.module.uhome.uhomebase.dataobject.tbl;

/**
 * sku值
 * @author wangguoqing
 *
 */
public class SkuOptionValueTbl {
	protected Integer skuOptionValueId;
	protected Integer skuOptionId;
	protected String skuOptionValue; //  选项显示的名字对应的值
	protected Integer rank;

	public Integer getSkuOptionValueId() {
		return skuOptionValueId;
	}

	public void setSkuOptionValueId(Integer skuOptionValueId) {
		this.skuOptionValueId = skuOptionValueId;
	}

	public Integer getSkuOptionId() {
		return skuOptionId;
	}

	public void setSkuOptionId(Integer skuOptionId) {
		this.skuOptionId = skuOptionId;
	}

	public String getSkuOptionValue() {
		return skuOptionValue;
	}

	public void setSkuOptionValue(String skuOptionValue) {
		this.skuOptionValue = skuOptionValue;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

}
