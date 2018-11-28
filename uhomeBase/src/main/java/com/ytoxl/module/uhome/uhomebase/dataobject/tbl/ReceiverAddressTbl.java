package com.ytoxl.module.uhome.uhomebase.dataobject.tbl;

import java.util.Date;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;

public class ReceiverAddressTbl {
	protected Integer receiverAddressId;
	protected Integer userId;
	protected Integer regionId;
	protected String regionCodes; //下拉菜单初始化值
	@ExcelField(filedName="联系手机",sort=12)
	protected String mobile;
	@ExcelField(filedName="联系电话 ",sort=11)
	protected String telephone;
	@ExcelField(filedName="邮编",sort=10)
	protected String postCode;
	@ExcelField(filedName="收货人姓名",sort=8)
	protected String receiverName; // 收货人姓名
	@ExcelField(filedName="收获地址",sort=9)
	protected String receiveAddress;
	protected Short isDefault;	//是否是默认地址：0=默认地址，1=不是默认地址
	protected Date createTime;
	protected Date updateTime;

	public Integer getReceiverAddressId() {
		return receiverAddressId;
	}

	public void setReceiverAddressId(Integer receiverAddressId) {
		this.receiverAddressId = receiverAddressId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public Short getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Short isDefault) {
		this.isDefault = isDefault;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public String getRegionCodes() {
		return regionCodes;
	}

	public void setRegionCodes(String regionCodes) {
		this.regionCodes = regionCodes;
	}

}
