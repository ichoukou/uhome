package com.ytoxl.module.uhome.uhomeorder.dataobject.tbl;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author wangguoqing
 *
 */
public class OrderCouponTbl {
	protected Integer orderCouponId;
	protected Integer orderId;
	protected String couponNo;
	protected BigDecimal allowance; // 抵扣金额
	protected BigDecimal limitCharge; //最小金额
	protected Timestamp createTime;
	public Integer getOrderCouponId() {
		return orderCouponId;
	}
	public void setOrderCouponId(Integer orderCouponId) {
		this.orderCouponId = orderCouponId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getCouponNo() {
		return couponNo;
	}
	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}
	public BigDecimal getAllowance() {
		return allowance;
	}
	public void setAllowance(BigDecimal allowance) {
		this.allowance = allowance;
	}
	public BigDecimal getLimitCharge() {
		return limitCharge;
	}
	public void setLimitCharge(BigDecimal limitCharge) {
		this.limitCharge = limitCharge;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	
}
