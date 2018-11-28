package com.ytoxl.module.uhome.uhomebase.dataobject;

import com.ytoxl.module.uhome.uhomebase.dataobject.tbl.ProductSkuOptionValueTbl;

public class ProductSkuOptionValue extends ProductSkuOptionValueTbl {

	protected SkuOptionValue skuOptionValue;// 关联SKU选项值

	public SkuOptionValue getSkuOptionValue() {
		return skuOptionValue;
	}

	public void setSkuOptionValue(SkuOptionValue skuOptionValue) {
		this.skuOptionValue = skuOptionValue;
	}

	@Override
	public String toString() {
		return "productSkuOptionValueId:"+this.productSkuOptionValueId+"-->"+"skuOptionValue:"+this.skuOptionValue
		+"-->overrideSkuOptionValue:"+this.overrideSkuOptionValue;
	}
	
	@Override
	public int hashCode() {
		return this.productSkuOptionValueId;
	}

}
