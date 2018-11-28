package com.ytoxl.module.uhome.uhomeorder.dataobject;

import com.ytoxl.module.uhome.uhomebase.dataobject.Express;
import com.ytoxl.module.uhome.uhomeorder.dataobject.tbl.OrderExpressTbl;

public class OrderExpress extends OrderExpressTbl {
	private Express express;

	public Express getExpress() {
		return express;
	}

	public void setExpress(Express express) {
		this.express = express;
	}
}
