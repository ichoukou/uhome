package com.ytoxl.module.uhome.uhomebase.dataobject;

import java.util.List;

import com.ytoxl.module.uhome.uhomebase.dataobject.tbl.BrandTbl;

public class Brand extends BrandTbl {
	public static final Short ISHOTSELLER_HOT_SALE=1; //热卖品牌
	public static final Short ISHOTSELLER_NO_HOT_SALE=2;//不是热卖品牌
	
	public static final Short ISFORBIDDEN_NO_FORBIDDEN=0;//不禁用
	public static final Short ISFORBIDDEN_FORBIDDEN=1;//禁用
	
	protected ProductCategory productCategory;
	protected List<Product> products;
	//记录该商家是否选择了该品牌
	protected Boolean isChecked;
	protected Seller seller;
	//作为界面div的id，用于分类动态生成品牌列表
	protected String firstLetter;
	protected String productCategoryName;
	
	//品牌介绍  是否有历史的热卖记录
	protected Boolean isHistoryRecord;
	//是否显示在首页
	protected boolean isCheck = false;
	
	public String getProductCategoryName() {
		return productCategoryName;
	}

	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public String getFirstLetter() {
		return firstLetter;
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}

	public Boolean getIsHistoryRecord() {
		return isHistoryRecord;
	}

	public void setIsHistoryRecord(Boolean isHistoryRecord) {
		this.isHistoryRecord = isHistoryRecord;
	}

	
	
	public boolean getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	@Override
	public String toString() {
		return this.brandId+"-->"+this.name;
	}
	
	@Override
	public int hashCode() {
		return brandId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(null == obj){
			return false;
		}
		if(obj == this){
			return true;
		}
		if(obj instanceof Brand){
			return this.brandId.equals(((Brand)obj).brandId);
		}
		return false;
	}
}
