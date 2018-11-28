package com.ytoxl.module.uhome.uhomebase.dataobject;

import com.ytoxl.module.uhome.uhomebase.dataobject.tbl.BrandSortTbl;

public class BrandSort extends BrandSortTbl {
	protected Brand brand;
	public static final Short BRABDSORT_TYPE_FSXB=1; //服饰箱包
	public static final Short BRABDSORT_TYPE_MYYP=2; //母婴用品
	public static final Short BRABDSORT_TYPE_JJYP=3; //家具用品
	public static final Short BRABDSORT_TYPE_MRHF=4; //美容护肤

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}
}
