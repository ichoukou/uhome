package com.ytoxl.module.uhome.uhomebase.common.utils.excel;

public class Address {
	@ExcelField(filedName="公司地址",sort=5)
	private String addr;
	@ExcelField(filedName="公司门号")
	private String no;
	@ExcelField(type=Type.OBJECT)
	private AddressSon addressSon;
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public AddressSon getAddressSon() {
		return addressSon;
	}
	public void setAddressSon(AddressSon addressSon) {
		this.addressSon = addressSon;
	}
	
	
}
