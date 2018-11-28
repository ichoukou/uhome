package com.ytoxl.module.uhome.uhomebase.dataobject;

import java.util.List;

import com.ytoxl.module.uhome.uhomebase.dataobject.tbl.SkuOptionTbl;

public class SkuOption extends SkuOptionTbl {
	
	//前台商品详细信息展示  颜色和规格
	public static String SKUOPTION_COLOR = "skuOptionColor";
	public static String SKUOPTION_SPECIFICATION="skuOptionSpecification";
	
	protected List<SkuOptionValue> skuOptionValues;
	
	public List<SkuOptionValue> getSkuOptionValues() {
		return skuOptionValues;
	}

	public void setSkuOptionValues(List<SkuOptionValue> skuOptionValues) {
		this.skuOptionValues = skuOptionValues;
	}

}
