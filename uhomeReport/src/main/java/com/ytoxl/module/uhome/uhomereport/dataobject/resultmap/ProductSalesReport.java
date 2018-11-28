package com.ytoxl.module.uhome.uhomereport.dataobject.resultmap;

import java.math.BigDecimal;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;

/**
 * 商品销售报表
 * 
 * @author xu
 *
 */
public class ProductSalesReport {

	/** 统计日期 */
	@ExcelField(filedName="日期",sort=1)
	protected String date;
	
	/** 销售金额 */
	@ExcelField(filedName="销售金额",sort=2)
	protected BigDecimal orderAmount;
	
	/** 订单数 */
	@ExcelField(filedName="订单数",sort=3)
	protected Integer orderNum;
	
	/** 客单价 (客单价=销售金额/订单数) */
	@ExcelField(filedName="客单价",sort=4)
	protected BigDecimal unitPrice;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	
}
