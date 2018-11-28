package com.ytoxl.module.uhome.uhomebase.common.utils.excel;

public class AddressSon {
	@ExcelField(filedName="地址SonName",sort=8)
	private String addSonName;
	
	@ExcelField(filedName="地址SonName2",sort=10)
	private String addSonName2;

	public String getAddSonName() {
		return addSonName;
	}

	public void setAddSonName(String addSonName) {
		this.addSonName = addSonName;
	}

	public String getAddSonName2() {
		return addSonName2;
	}

	public void setAddSonName2(String addSonName2) {
		this.addSonName2 = addSonName2;
	}
	
	
}
