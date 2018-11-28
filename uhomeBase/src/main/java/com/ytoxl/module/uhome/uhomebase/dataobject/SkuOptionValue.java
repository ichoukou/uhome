package com.ytoxl.module.uhome.uhomebase.dataobject;

import com.ytoxl.module.uhome.uhomebase.dataobject.tbl.SkuOptionValueTbl;

public class SkuOptionValue extends SkuOptionValueTbl {

	protected SkuOption skuOption;// 关联SKU选项
	protected String overrideFlag;//修改标志("0"表示未修改，"1"表示修改过)
	
	public SkuOption getSkuOption() {
		return skuOption;
	}

	public void setSkuOption(SkuOption skuOption) {
		this.skuOption = skuOption;
	}
	
	public String getOverrideFlag() {
		return overrideFlag;
	}

	public void setOverrideFlag(String overrideFlag) {
		this.overrideFlag = overrideFlag;
	}

	@Override
	public String toString() {
		return "skuOptionValueId:"+this.skuOptionValueId+"-->>"+"skuOptionValue:"+this.skuOptionValue;
	}
	
	@Override
	public int hashCode() {
		return this.skuOptionValueId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		if(obj == this){
			return true;
		}
		if(obj instanceof SkuOptionValue){
			return ((SkuOptionValue)obj).skuOptionValueId.equals(this.skuOptionValueId);
		}
		return false;
	}

}
