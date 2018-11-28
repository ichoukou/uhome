package com.ytoxl.module.uhome.uhomeorder.dataobject.tbl;

import java.util.Date;

public class OrderReturnTbl {

	protected Integer orderReturnId;
	protected Integer orderId;
	protected String customerName;
	protected String telephone;
	protected Date createTime;
	protected Date updateTime;
	
	public Integer getOrderReturnId() {
		return orderReturnId;
	}
	public void setOrderReturnId(Integer orderReturnId) {
		this.orderReturnId = orderReturnId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
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
