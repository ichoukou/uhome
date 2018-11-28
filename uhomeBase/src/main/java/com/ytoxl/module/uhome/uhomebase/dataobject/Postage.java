package com.ytoxl.module.uhome.uhomebase.dataobject;

import com.ytoxl.module.uhome.uhomebase.dataobject.tbl.PostageTbl;

public class Postage extends PostageTbl {
	public static final Short TYPE_PRODUCT = 1;
	public static final Short TYPE_PLAN = 2;
	
	public static final Short OPTION_FREE = 0;
	public static final Short OPTION_TEN = 1;
	public static final Short[] OPTIONS = {OPTION_FREE, OPTION_TEN};

	protected Short option;

	public Short getOption() {
		return option;
	}

	public void setOption(Short option) {
		this.option = option;
	}
	
	
}
