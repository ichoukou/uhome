package com.ytoxl.module.uhome.uhomebase.common.utils.excel;

import java.io.Serializable;

public class Phone implements Serializable{
	@ExcelField(filedName="移动电话",sort=7)
	private String mobilePhone;
	@ExcelField(filedName="固定电话",sort=0)
	private String gudingPhone;
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getGudingPhone() {
		return gudingPhone;
	}
	public void setGudingPhone(String gudingPhone) {
		this.gudingPhone = gudingPhone;
	}
	
	
}
