package com.ytoxl.module.uhome.uhomeorder.dataobject.tbl;

import java.math.BigDecimal;
import java.util.Date;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;
import com.ytoxl.module.uhome.uhomebase.common.utils.excel.FormatType;

public class OrderHeadTbl {

	protected Integer orderId;
	@ExcelField(filedName="订单号",sort=0)
	protected String orderNo;
	protected Integer userId;
	protected Integer skuNum;
	protected Integer receiverAddressId; //该订单关联的发送地址
	protected BigDecimal totalPrice;
	protected Short status;
	@ExcelField(filedName="订单创建时间",sort=11,formatType=FormatType.DATE,pattern="yyyy-MM-dd HH:mm:ss")
	protected Date createTime;
	protected Date updateTime;
	protected Integer parentOrderId;
	protected Date receiveProductTime;
	protected Date sendProductTime;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getSkuNum() {
		return skuNum;
	}

	public void setSkuNum(Integer skuNum) {
		this.skuNum = skuNum;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
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

	public Integer getReceiverAddressId() {
		return receiverAddressId;
	}

	public void setReceiverAddressId(Integer receiverAddressId) {
		this.receiverAddressId = receiverAddressId;
	}

	public Integer getParentOrderId() {
		return parentOrderId;
	}

	public void setParentOrderId(Integer parentOrderId) {
		this.parentOrderId = parentOrderId;
	}

	public Date getReceiveProductTime() {
		return receiveProductTime;
	}

	public void setReceiveProductTime(Date receiveProductTime) {
		this.receiveProductTime = receiveProductTime;
	}

	public Date getSendProductTime() {
		return sendProductTime;
	}

	public void setSendProductTime(Date sendProductTime) {
		this.sendProductTime = sendProductTime;
	}


}
