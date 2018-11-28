package com.ytoxl.module.uhome.uhomeorder.dataobject.tbl;

import java.math.BigDecimal;

public class OrderPostageTbl {

	protected Integer orderPostageId;
	protected Integer orderId;
	protected BigDecimal postage;
	public Integer getOrderPostageId() {
		return orderPostageId;
	}
	public void setOrderPostageId(Integer orderPostageId) {
		this.orderPostageId = orderPostageId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public BigDecimal getPostage() {
		return postage;
	}
	public void setPostage(BigDecimal postage) {
		this.postage = postage;
	} 
	
	
}
