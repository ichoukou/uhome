package com.ytoxl.module.uhome.uhomereport.dataobject.resultmap;

import java.math.BigDecimal;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;

/**
 * 退货商品管理明细
 * @author xu
 *
 */
public class ProductReturnReport {

	/** 商家名称  */
	@ExcelField(filedName="商家",sort=1)
	protected String sellerName;
	
	/** 品牌名称  */
	@ExcelField(filedName="品牌",sort=2)
	protected String brandName;
	
	/** 品类名称 */
	@ExcelField(filedName="商品品类",sort=3)
	protected String categoryName;
	
	/** 商品名称 */
	@ExcelField(filedName="商品名称",sort=4)
	protected String productName;
	
	/** sku编码 */
	@ExcelField(filedName="SKU",sort=5)
	protected String skuCode;
	
	/** 退货数量 */
	@ExcelField(filedName="退货数量",sort=6)
	protected Integer retCount;
	
	/** 退货金额 */
	@ExcelField(filedName="退货金额",sort=7)
	protected BigDecimal retAmount;

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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public Integer getRetCount() {
		return retCount;
	}

	public void setRetCount(Integer retCount) {
		this.retCount = retCount;
	}

	public BigDecimal getRetAmount() {
		return retAmount;
	}

	public void setRetAmount(BigDecimal retAmount) {
		this.retAmount = retAmount;
	}
}
