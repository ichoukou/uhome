package com.ytoxl.module.uhome.uhomereport.dataobject.resultmap;

import java.math.BigDecimal;
import java.util.Date;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;
import com.ytoxl.module.uhome.uhomebase.common.utils.excel.FormatType;

/**
 * 每日新增待发货订单
 * 
 * @author xu
 *
 */
public class OrderWaitSendReport {
	
	public static final String STATUS_WAITSEND = "待发货";
	
	public static final String REBATE_TYPE_COUPON = "优惠券";
	
	public static final String INVOICE_TITLE_PERSON = "个人";
	
	public static final Integer HAS_INVOICE = 1;
	
	/** sku记录ID */
	protected Integer productSkuId;
	
	/** 是否有发票：0-没有；1-有 */
	protected Integer hasInvoice;

	/* 供应商及品牌 */
	/** 商家名称 */
	@ExcelField(filedName="商家名称", sort=1)
	protected String sellerName;
	
	/** 品牌名称 */
	@ExcelField(filedName="品牌名称", sort=2)
	protected String brandName;
	
	/* 订单信息 */
	/** 订单创建时间 */
	@ExcelField(filedName="订单创建时间", sort=3,formatType=FormatType.DATE,pattern="yyyy-MM-dd HH:mm:ss")
	protected Date orderCreateTime;
	
	/** 订单支付时间 */
	@ExcelField(filedName="订单支付时间", sort=4,formatType=FormatType.DATE,pattern="yyyy-MM-dd HH:mm:ss")
	protected Date orderPayTime;
	
	/** 订单号 */
	@ExcelField(filedName="订单号", sort=5)
	protected String orderNo;
	
	/** 订单状态 */
	@ExcelField(filedName="订单状态", sort=6)
	protected String orderStatus;
	
	/** 国际码 */
	@ExcelField(filedName="国际码", sort=7)
	protected String internationalCode;
	
	/** SKU编码 */
	@ExcelField(filedName="SKU编码", sort=8)
	protected String skuCode;
	
	/** 商品名称 */
	@ExcelField(filedName="商品名称", sort=9)
	protected String productName;
	
	/** 颜色 */
	@ExcelField(filedName="颜色", sort=10)
	protected String color;
	
	/** 尺码/规格 */
	@ExcelField(filedName="尺码/规格", sort=11)
	protected String specification;
	
	/** 购买商品数量 */
	@ExcelField(filedName="购买商品数量", sort=12)
	protected Integer buyNum;
	
	/* 交易信息 */
	/** 商品单价 */
	@ExcelField(filedName="商品单价", sort=13)
	protected BigDecimal unitPrice;
	
	/** 优惠类型 */
	@ExcelField(filedName="优惠类型", sort=14)
	protected String rebateType;
	
	/** 优惠金额 */
	@ExcelField(filedName="优惠金额", sort=15)
	protected BigDecimal rebatePrice;
	
	/** 运费 */
	@ExcelField(filedName="运费", sort=16)
	protected BigDecimal postage;
	
	/** 买家实际支付金额 */
	@ExcelField(filedName="买家实际支付金额", sort=17)
	protected BigDecimal paymentAmount;
	
	/*发货信息*/
	/** 收货人姓名 */
	@ExcelField(filedName="收货人姓名", sort=18)
	protected String receiver;
	
	/** 第三级地址ID */
	protected Integer regionId;
	
	/** 收货地址 */
	@ExcelField(filedName="收货地址", sort=19)
	protected String receiveAddress;
	
	/** 邮编 */
	@ExcelField(filedName="邮编", sort=20)
	protected String postCode;
	
	/** 联系电话 */
	@ExcelField(filedName="联系电话", sort=21)
	protected String telephone;
	
	/** 联系手机 */
	@ExcelField(filedName="联系手机", sort=22)
	protected String mobile;
	
	/** 发票抬头 */
	@ExcelField(filedName="发票抬头", sort=23)
	protected String invoiceTitle;
	
	/*供应商填写*/
	/** 物流公司 */
	@ExcelField(filedName="物流公司", sort=24)
	protected String mailCompany;
	/** 物流单号 */
	@ExcelField(filedName="物流单号", sort=25)
	protected String mailNo;
	
	public Integer getProductSkuId() {
		return productSkuId;
	}
	public void setProductSkuId(Integer productSkuId) {
		this.productSkuId = productSkuId;
	}
	public Integer getHasInvoice() {
		return hasInvoice;
	}
	public void setHasInvoice(Integer hasInvoice) {
		this.hasInvoice = hasInvoice;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
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
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public Integer getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getRebateType() {
		return rebateType;
	}
	public void setRebateType(String rebateType) {
		this.rebateType = rebateType;
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
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public Integer getRegionId() {
		return regionId;
	}
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	public String getReceiveAddress() {
		return receiveAddress;
	}
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public String getMailCompany() {
		return mailCompany;
	}
	public void setMailCompany(String mailCompany) {
		this.mailCompany = mailCompany;
	}
	public String getMailNo() {
		return mailNo;
	}
	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}
	
	
}
