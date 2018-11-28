package com.ytoxl.module.uhome.uhomebase.dataobject.tbl;

/**
 * sku选项
 * @author wangguoqing
 *
 */
public class SkuOptionTbl {
	protected Integer skuOptionId;
	protected String skuOptionName; //选项的名字 例如：颜色、规格、尺码等
	protected Integer rank;

	public Integer getSkuOptionId() {
		return skuOptionId;
	}

	public void setSkuOptionId(Integer skuOptionId) {
		this.skuOptionId = skuOptionId;
	}

	public String getSkuOptionName() {
		return skuOptionName;
	}

	public void setSkuOptionName(String skuOptionName) {
		this.skuOptionName = skuOptionName;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

}
