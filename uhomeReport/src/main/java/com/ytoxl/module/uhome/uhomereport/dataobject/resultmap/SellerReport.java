package com.ytoxl.module.uhome.uhomereport.dataobject.resultmap;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;

/**
 * 卖家信息
 * @author shengjianming
 *
 */
public class SellerReport  {
	/**
	 * 
	 */
 
	@ExcelField(filedName="商家", sort=1)
	private String username;
	
	@ExcelField(filedName="业务联系人", sort=2)
	private String contactName;    //联系人
	
	@ExcelField(filedName="固定电话", sort=4)
	private String tel;            //电话
	
	@ExcelField(filedName="手机号码", sort=3)
	private String mobile;          //手机号码
 
	@ExcelField(filedName="邮箱", sort=5)
	private String email;          //邮箱
    
	@ExcelField(filedName="支付宝账号", sort=6)
	private String alipayNo;            //支付宝账号

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAlipayNo() {
		return alipayNo;
	}

	public void setAlipayNo(String alipayNo) {
		this.alipayNo = alipayNo;
	}
	 

	
}
