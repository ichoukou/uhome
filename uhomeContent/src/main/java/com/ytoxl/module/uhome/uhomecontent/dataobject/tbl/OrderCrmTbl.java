package com.ytoxl.module.uhome.uhomecontent.dataobject.tbl;

import java.util.Date;

/**
 * 客服操作信息
 * @author liyasi
 *
 */

public class OrderCrmTbl {
	protected Integer orderCrmId;
	protected Integer orderId;  //订单编号
	protected Integer userId;  //操作Id
	protected String userName;
	protected Date recordTime;  //操作时间
	protected String remark;  //操作内容
	
	public Integer getOrderCrmId() {
		return orderCrmId;
	}
	public void setOrderCrmId(Integer orderCrmId) {
		this.orderCrmId = orderCrmId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
