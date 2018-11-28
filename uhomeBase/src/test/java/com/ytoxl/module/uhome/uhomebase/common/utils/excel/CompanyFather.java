package com.ytoxl.module.uhome.uhomebase.common.utils.excel;

import java.io.Serializable;

public class CompanyFather implements Serializable{
	private static final long serialVersionUID = 1L;
	@ExcelField(filedName="主键",sort=3)
	protected String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
