package com.ytoxl.module.uhome.uhomeorder.dataobject.tbl;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @author wangguoqing
 *
 */
public class OrderReturnPaymentTbl {
	
	protected Integer orderReturnPaymentId;
	protected Integer orderReturnId;
	protected String documentNo;
	protected Short status; // 0=待退款，1=已退款
	protected BigDecimal paymentAmount; // 退货订单实际退货金额
	protected String account;
	protected String accountInfo;
	protected Date payTime;
	protected String ipAddress;
	protected String returnReason;
	protected BigDecimal closingCostAmount; //退货订单没有使用优惠券的应退金额
	protected Date createTime;
	protected Date updateTime;
	
	public Integer getOrderReturnPaymentId() {
		return orderReturnPaymentId;
	}
	public void setOrderReturnPaymentId(Integer orderReturnPaymentId) {
		this.orderReturnPaymentId = orderReturnPaymentId;
	}
	public Integer getOrderReturnId() {
		return orderReturnId;
	}
	public void setOrderReturnId(Integer orderReturnId) {
		this.orderReturnId = orderReturnId;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAccountInfo() {
		return accountInfo;
	}
	public void setAccountInfo(String accountInfo) {
		this.accountInfo = accountInfo;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getReturnReason() {
		return returnReason;
	}
	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
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
	public BigDecimal getClosingCostAmount() {
		return closingCostAmount;
	}
	public void setClosingCostAmount(BigDecimal closingCostAmount) {
		this.closingCostAmount = closingCostAmount;
	}

}
