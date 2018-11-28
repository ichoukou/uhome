package com.ytoxl.module.uhome.uhomeorder.dataobject.tbl;

import java.util.Date;

public class OrderExpressTbl {

	protected Integer orderExpressId;
	protected Integer orderId;
	protected Integer expressId;
	protected String mailNo;
	protected Integer receiverAddressId;
	protected Date createTime;
	protected Integer orderReturnId;
	protected String expressName;
	protected Integer regionId;
	protected String  receiverName;
	protected String  receiveAddress;
	protected String  mobile;
	protected String telephone;
	protected String postCode;

	public Integer getOrderExpressId() {
		return orderExpressId;
	}

	public void setOrderExpressId(Integer orderExpressId) {
		this.orderExpressId = orderExpressId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getExpressId() {
		return expressId;
	}

	public void setExpressId(Integer expressId) {
		this.expressId = expressId;
	}

	public String getMailNo() {
		return mailNo;
	}

	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}

	public Integer getReceiverAddressId() {
		return receiverAddressId;
	}

	public void setReceiverAddressId(Integer receiverAddressId) {
		this.receiverAddressId = receiverAddressId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getOrderReturnId() {
		return orderReturnId;
	}

	public void setOrderReturnId(Integer orderReturnId) {
		this.orderReturnId = orderReturnId;
	}

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

}
