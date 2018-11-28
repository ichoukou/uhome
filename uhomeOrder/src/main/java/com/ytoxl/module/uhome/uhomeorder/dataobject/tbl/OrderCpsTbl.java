package com.ytoxl.module.uhome.uhomeorder.dataobject.tbl;

import java.math.BigDecimal;
import java.util.Date;

public class OrderCpsTbl {
	protected Integer orderCpsId;
	protected Integer orderId;
	protected BigDecimal commission;
	protected String unionId;
	protected String feedback;
	protected String mid;
	protected String link;
	protected Short isNewCustom;
	protected Short status;
	protected Date createTime;
	protected Date updateTime;

	public Integer getOrderCpsId() {
		return orderCpsId;
	}

	public void setOrderCpsId(Integer orderCpsId) {
		this.orderCpsId = orderCpsId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Short getIsNewCustom() {
		return isNewCustom;
	}

	public void setIsNewCustom(Short isNewCustom) {
		this.isNewCustom = isNewCustom;
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
