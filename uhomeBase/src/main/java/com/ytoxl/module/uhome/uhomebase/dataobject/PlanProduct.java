package com.ytoxl.module.uhome.uhomebase.dataobject;

import com.ytoxl.module.uhome.uhomebase.dataobject.tbl.PlanProductTbl;

public class PlanProduct extends PlanProductTbl {

	protected Product product;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
