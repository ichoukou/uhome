package com.ytoxl.module.uhome.uhomereport.dataobject.resultmap;

import java.math.BigDecimal;
import java.util.Date;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;
import com.ytoxl.module.uhome.uhomebase.common.utils.excel.FormatType;

public class MonthReport {
	
	@ExcelField(filedName="订单编号",sort=1)
	protected String orderNo;
	@ExcelField(filedName="订单类型",sort=2)
	protected Short type;
	@ExcelField(filedName="订单完成时间",sort=3,formatType=FormatType.DATE,pattern="yyyy-MM-dd HH:mm:ss")
	protected Date payTime;
	@ExcelField(filedName="品牌",sort=4)
	protected String brandName;
	@ExcelField(filedName="商品名称",sort=5)
	protected String productName;
	@ExcelField(filedName="单价",sort=6)
	protected BigDecimal closingCost;
	@ExcelField(filedName="优惠金额",sort=7)
	protected BigDecimal allowance;
	@ExcelField(filedName="数量",sort=8)
	protected Integer num;
	@ExcelField(filedName="金额",sort=9)
	protected BigDecimal totalPrice;
	
	protected Integer orderId;
	protected Integer sellerId;
	protected Integer brandId;
	protected Integer sellOrderCount;         //销售订单总数量
	protected BigDecimal sellOrderAmount;         //销售订单总金额
	protected Integer returnOrderCount;         //退货订单总数量
	protected BigDecimal returnOrderAmount;         //退货订单总金额
	protected BigDecimal payableAmount;         //应付款项
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public BigDecimal getClosingCost() {
		return closingCost;
	}
	public void setClosingCost(BigDecimal closingCost) {
		this.closingCost = closingCost;
	}
	public BigDecimal getAllowance() {
		return allowance;
	}
	public void setAllowance(BigDecimal allowance) {
		this.allowance = allowance;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public Integer getSellOrderCount() {
		return sellOrderCount;
	}
	public void setSellOrderCount(Integer sellOrderCount) {
		this.sellOrderCount = sellOrderCount;
	}
	public BigDecimal getSellOrderAmount() {
		return sellOrderAmount;
	}
	public void setSellOrderAmount(BigDecimal sellOrderAmount) {
		this.sellOrderAmount = sellOrderAmount;
	}
	public Integer getReturnOrderCount() {
		return returnOrderCount;
	}
	public void setReturnOrderCount(Integer returnOrderCount) {
		this.returnOrderCount = returnOrderCount;
	}
	public BigDecimal getReturnOrderAmount() {
		return returnOrderAmount;
	}
	public void setReturnOrderAmount(BigDecimal returnOrderAmount) {
		this.returnOrderAmount = returnOrderAmount;
	}
	public BigDecimal getPayableAmount() {
		return payableAmount;
	}
	public void setPayableAmount(BigDecimal payableAmount) {
		this.payableAmount = payableAmount;
	}
	
}
