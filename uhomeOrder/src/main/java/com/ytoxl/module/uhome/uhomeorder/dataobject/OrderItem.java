package com.ytoxl.module.uhome.uhomeorder.dataobject;

import java.util.Date;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;
import com.ytoxl.module.uhome.uhomebase.common.utils.excel.FormatType;
import com.ytoxl.module.uhome.uhomebase.common.utils.excel.Type;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku;
import com.ytoxl.module.uhome.uhomebase.dataobject.ReceiverAddress;
import com.ytoxl.module.uhome.uhomebase.dataobject.Seller;
import com.ytoxl.module.uhome.uhomeorder.dataobject.tbl.OrderItemTbl;

public class OrderItem extends OrderItemTbl {
	public static final Short ORDERSOURCE_SPECIAL_SELLER=2;    //2=特卖
	public static final Short ORDERSOURCE_SEC_KILL=1;    //1=秒杀
	protected ProductSku productSku;
	protected Brand brand;
	protected OrderReturn orderReturn;
	protected OrderExpress express;//发件公司和物流编号
	
	protected Integer ableReturnNum;//可退货数量
	
	//卖家
	protected Seller seller;
	
	@ExcelField(type=Type.OBJECT)
	private ReceiverAddress address;
	
	@ExcelField(type=Type.OBJECT)
	private OrderPayment payment;
	
	@ExcelField(filedName="订单号",sort=0)
	protected String orderNo;
	
	//@ExcelField(filedName="商品属性",sort=3)
	protected String productProperty;
	
	@ExcelField(filedName="订单状态",sort=1)
	protected String orderStatus;
	
	@ExcelField(filedName="国际码",sort=14)
	protected String internationalCode; // 国际码
	
	@ExcelField(filedName="SKU编码",sort=15)
	protected String skuCode; // sku编码
	
	@ExcelField(filedName="商家名称",sort=2)
	protected String sellerName;
	
	@ExcelField(filedName="品牌名称",sort=3)
	protected String brandName;
	
	@ExcelField(filedName="订单创建时间",sort=16,formatType=FormatType.DATE,pattern="yyyy-MM-dd HH:mm:ss")
	protected Date orderCreateTime;
	
	public String getProductProperty() {
		return productProperty;
	}
	public void setProductProperty(String productProperty) {
		this.productProperty = productProperty;
	}
	
	public OrderExpress getExpress() {
		return express;
	}

	public void setExpress(OrderExpress express) {
		this.express = express;
	}
	public OrderReturn getOrderReturn() {
		return orderReturn;
	}
	public void setOrderReturn(OrderReturn orderReturn) {
		this.orderReturn = orderReturn;
	}
	public ProductSku getProductSku() {
		return productSku;
	}
	public void setProductSku(ProductSku productSku) {
		this.productSku = productSku;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
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
	public Integer getAbleReturnNum() {
		return ableReturnNum;
	}
	public void setAbleReturnNum(Integer ableReturnNum) {
		this.ableReturnNum = ableReturnNum;
	}
	public ReceiverAddress getAddress() {
		return address;
	}
	public void setAddress(ReceiverAddress address) {
		this.address = address;
	}
	public OrderPayment getPayment() {
		return payment;
	}
	public void setPayment(OrderPayment payment) {
		this.payment = payment;
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
	
}
