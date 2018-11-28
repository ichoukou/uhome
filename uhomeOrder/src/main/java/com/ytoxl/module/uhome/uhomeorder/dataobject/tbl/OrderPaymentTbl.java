package com.ytoxl.module.uhome.uhomeorder.dataobject.tbl;

import java.math.BigDecimal;
import java.util.Date;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;
import com.ytoxl.module.uhome.uhomebase.common.utils.excel.FormatType;

public class OrderPaymentTbl {

	protected Integer orderPaymentId;
	protected Integer orderId;
	protected String documentNo;
	protected Short documentType; // 单据类型：1=收款，2=退款
	protected Short status; // 1=已付款，2=待退款，3=已退款
	protected BigDecimal paymentAmount; // 订单实际支付金额
	protected Short hasInvoice; // 是否需要发票，0=不要，1=要
	@ExcelField(filedName="发票抬头",sort=13)
	protected String invoiceTitle;
	protected String account;
	protected String accountInfo;
	@ExcelField(filedName="订单支付时间",sort=17,formatType=FormatType.DATE,pattern="yyyy-MM-dd HH:mm:ss")
	protected Date payTime;
	protected String ipAddress;
	protected String returnReason;
	protected Date createTime;
	protected Date updateTime;
	protected BigDecimal closingCostAmount; //订单没有使用优惠券应付总金额

	public Integer getOrderPaymentId() {
		return orderPaymentId;
	}

	public void setOrderPaymentId(Integer orderPaymentId) {
		this.orderPaymentId = orderPaymentId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public Short getDocumentType() {
		return documentType;
	}

	public void setDocumentType(Short documentType) {
		this.documentType = documentType;
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

	public Short getHasInvoice() {
		return hasInvoice;
	}

	public void setHasInvoice(Short hasInvoice) {
		this.hasInvoice = hasInvoice;
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

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
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

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public BigDecimal getClosingCostAmount() {
		return closingCostAmount;
	}

	public void setClosingCostAmount(BigDecimal closingCostAmount) {
		this.closingCostAmount = closingCostAmount;
	}


}
