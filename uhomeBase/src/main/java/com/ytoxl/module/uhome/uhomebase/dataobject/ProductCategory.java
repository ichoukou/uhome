package com.ytoxl.module.uhome.uhomebase.dataobject;

import com.ytoxl.module.uhome.uhomebase.dataobject.tbl.ProductCategoryTbl;

public class ProductCategory extends ProductCategoryTbl {

	protected Integer planNum; //排期个数

	public Integer getPlanNum() {
		return planNum;
	}

	public void setPlanNum(Integer planNum) {
		this.planNum = planNum;
	}

}
