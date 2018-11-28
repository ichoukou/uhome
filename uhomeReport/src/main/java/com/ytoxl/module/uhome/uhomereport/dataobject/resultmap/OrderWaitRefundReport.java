package com.ytoxl.module.uhome.uhomereport.dataobject.resultmap;

import java.math.BigDecimal;
import java.util.Date;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;
import com.ytoxl.module.uhome.uhomebase.common.utils.excel.FormatType;

/**
 * 商家销售管理明细
 * @author hwx
 *
 */
public class OrderWaitRefundReport {
	
	/** 退款人姓名  */
	@ExcelField(filedName="退款人姓名",sort=1)
	private String userName;
	
	/** 退款账号  */
	@ExcelField(filedName="退款账号",sort=2)
	private String account;
	
	/** 退款金额 */
	@ExcelField(filedName="退款金额",sort=3)
	private BigDecimal refundAmount;
	
	/** 退款申请时间 */
	@ExcelField(filedName="退款申请时间",sort=4,formatType=FormatType.DATE,pattern="yyyy-MM-dd HH:mm:ss")
	private Date refundTime;
	
	/** 订单创建时间 */
	@ExcelField(filedName="订单创建时间",sort=5,formatType=FormatType.DATE,pattern="yyyy-MM-dd HH:mm:ss")
	private Date orderCreateTime;
	
	/** 订单支付时间 */
	@ExcelField(filedName="订单支付时间",sort=6,formatType=FormatType.DATE,pattern="yyyy-MM-dd HH:mm:ss")
	private Date orderPayTime;
	
	/** 订单号 */
	@ExcelField(filedName="订单号",sort=7)
	private String orderNo;
	
	/** 订单状态 */
	@ExcelField(filedName="订单状态",sort=8)
	private String orderStatus;
	
	/** 国际码 */
	@ExcelField(filedName="国际码",sort=9)
	private String internationalCode;
	
	/** SKU CODE */
	@ExcelField(filedName="SKU编码",sort=10)
	private String skuCode;
	
	/** 商品名称 */
	@ExcelField(filedName="商品名称",sort=11)
	private String productName;
	
	/** 颜色 */
	@ExcelField(filedName="颜色",sort=12)
	private String colour;
	
	/** 尺寸/规格 */
	@ExcelField(filedName="尺寸/规格",sort=13)
	private String size;
	
	/** 购买商品数量 */
	@ExcelField(filedName="购买商品数量",sort=14)
	private Integer num;
	
	/** 商品单价 */
	@ExcelField(filedName="商品单价",sort=15)
	private BigDecimal salePrice;
	
	/** 优惠类型 */
	@ExcelField(filedName="优惠类型",sort=16)
	private String preferentialType;
	
	/** 优惠金额 */
	@ExcelField(filedName="优惠金额",sort=17)
	private BigDecimal rebatePrice;
	
	/** 运费 */
	@ExcelField(filedName="运费",sort=18)
	private BigDecimal postage;
	
	/** 买家实际支付金额 */
	@ExcelField(filedName="买家实际支付金额",sort=19)
	private BigDecimal paymentAmount;
	
	/** 商家名称 */
	@ExcelField(filedName="商家名称",sort=20)
	private String companyName;
	
	/** 品牌名称 */
	@ExcelField(filedName="品牌名称",sort=21)
	private String brandName;
	
	private String productSkuId;
	
	private String skuOptionId;
	
	private String skuOptionValue;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public Date getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public Date getOrderPayTime() {
		return orderPayTime;
	}

	public void setOrderPayTime(Date orderPayTime) {
		this.orderPayTime = orderPayTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getInternationalCode() {
		return internationalCode;
	}

	public void setInternationalCode(String internationalCode) {
		this.internationalCode = internationalCode;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public String getPreferentialType() {
		return preferentialType;
	}

	public void setPreferentialType(String preferentialType) {
		this.preferentialType = preferentialType;
	}

	public BigDecimal getRebatePrice() {
		return rebatePrice;
	}

	public void setRebatePrice(BigDecimal rebatePrice) {
		this.rebatePrice = rebatePrice;
	}

	public BigDecimal getPostage() {
		return postage;
	}

	public void setPostage(BigDecimal postage) {
		this.postage = postage;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(String productSkuId) {
		this.productSkuId = productSkuId;
	}

	public String getSkuOptionId() {
		return skuOptionId;
	}

	public void setSkuOptionId(String skuOptionId) {
		this.skuOptionId = skuOptionId;
	}

	public String getSkuOptionValue() {
		return skuOptionValue;
	}

	public void setSkuOptionValue(String skuOptionValue) {
		this.skuOptionValue = skuOptionValue;
	}

	
	
}
