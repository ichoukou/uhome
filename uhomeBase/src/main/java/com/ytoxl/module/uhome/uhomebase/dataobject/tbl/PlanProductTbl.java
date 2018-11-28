package com.ytoxl.module.uhome.uhomebase.dataobject.tbl;

/**
 * 商品排期
 * 
 * @author wangguoqing
 * 
 */
public class PlanProductTbl {
	protected Integer planProductId;
	protected Integer productId;
	protected Integer productSkuId;
	protected Integer planId;
	protected Integer rank;

	public Integer getPlanProductId() {
		return planProductId;
	}

	public void setPlanProductId(Integer planProductId) {
		this.planProductId = planProductId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(Integer productSkuId) {
		this.productSkuId = productSkuId;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

}
