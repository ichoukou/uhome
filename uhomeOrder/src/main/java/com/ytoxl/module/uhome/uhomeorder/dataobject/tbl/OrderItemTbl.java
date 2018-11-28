package com.ytoxl.module.uhome.uhomeorder.dataobject.tbl;

import java.math.BigDecimal;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;

public class OrderItemTbl {

	protected Integer orderItemId;
	protected Integer orderId;
	protected Integer sellerId;
	protected Integer productSkuId;
	protected Short orderSource; // 订单来源，1=特卖，2=秒杀
	@ExcelField(filedName="购买商品数量",sort=5)
	protected Integer num;
	@ExcelField(filedName="商品单价",sort=6)
	protected BigDecimal closingCost; // 成交价
	protected BigDecimal marketPrice;
	@ExcelField(filedName="商品名称",sort=4)
	protected String productName;
	@ExcelField(filedName="买家实际支付金额",sort=7)
	protected BigDecimal rebatePrice; //使用优惠券后的折扣金额

	public Integer getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
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

	public Integer getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(Integer productSkuId) {
		this.productSkuId = productSkuId;
	}

	public Short getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(Short orderSource) {
		this.orderSource = orderSource;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public BigDecimal getClosingCost() {
		return closingCost;
	}

	public void setClosingCost(BigDecimal closingCost) {
		this.closingCost = closingCost;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getRebatePrice() {
		return rebatePrice;
	}

	public void setRebatePrice(BigDecimal rebatePrice) {
		this.rebatePrice = rebatePrice;
	}

}
