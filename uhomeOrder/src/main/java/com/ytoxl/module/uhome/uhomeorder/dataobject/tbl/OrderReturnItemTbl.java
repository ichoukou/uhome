package com.ytoxl.module.uhome.uhomeorder.dataobject.tbl;

import java.math.BigDecimal;
import java.util.Date;

public class OrderReturnItemTbl {

	protected Integer orderReturnItemId;
	protected Integer orderItemId;
	protected Integer num;
	protected Integer orderReturnId;
	protected String describe;
	protected String imageUrls;
	protected Short status;
	protected String noPassReason;
	protected Date updateTime;
	protected BigDecimal refundAmount; //应退金额

	public Integer getOrderReturnId() {
		return orderReturnId;
	}

	public void setOrderReturnId(Integer orderReturnId) {
		this.orderReturnId = orderReturnId;
	}

	public Integer getOrderReturnItemId() {
		return orderReturnItemId;
	}

	public void setOrderReturnItemId(Integer orderReturnItemId) {
		this.orderReturnItemId = orderReturnItemId;
	}

	public Integer getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(String imageUrls) {
		this.imageUrls = imageUrls;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getNoPassReason() {
		return noPassReason;
	}

	public void setNoPassReason(String noPassReason) {
		this.noPassReason = noPassReason;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}
	
}
