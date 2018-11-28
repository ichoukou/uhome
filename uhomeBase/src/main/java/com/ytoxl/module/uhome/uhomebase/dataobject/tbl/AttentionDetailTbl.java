package com.ytoxl.module.uhome.uhomebase.dataobject.tbl;

/**
 * 品牌喜欢明细
 * @author wangguoqing
 *
 */
public class AttentionDetailTbl {
	protected Integer attentionDetailId;
	protected Integer brandId;
	protected Integer userId;

	public Integer getAttentionDetailId() {
		return attentionDetailId;
	}

	public void setAttentionDetailId(Integer attentionDetailId) {
		this.attentionDetailId = attentionDetailId;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
