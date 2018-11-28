package com.ytoxl.module.uhome.uhomereport.dataobject.resultmap;

import java.util.Date;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;
import com.ytoxl.module.uhome.uhomebase.common.utils.excel.FormatType;


public class OrderDetailReport {
 
	private Integer orderId;
	@ExcelField(filedName="订单号", sort=1)
	private String orderNo;
	@ExcelField(filedName="商品品名", sort=2)
	private String productName ;
	@ExcelField(filedName="商品数量", sort=3)
	private Integer num;
	@ExcelField(filedName="订单金额", sort=4)
	private Double closingCost;
	
	private Integer  status;
	
	@ExcelField(filedName="订单状态", sort=5)
	private String orderStatus;
	
	@ExcelField(filedName="订单日期", sort=6 , formatType=FormatType.DATE,pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;

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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Double getClosingCost() {
		return closingCost;
	}

	public void setClosingCost(Double closingCost) {
		this.closingCost = closingCost;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	 
	
}
