package com.ytoxl.module.uhome.uhomebase.dataobject.tbl;

import java.util.Date;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;

/**
 * 优惠券明细
 * @author anqian
 *
 */
public class CouponTbl {
	/**
	 * 明细ID
	 */
	protected Integer couponId;
	/**
	 * 优惠券号
	 */
	@ExcelField(filedName="优惠券号",sort=1)
	protected String couponNo;
	/**
	 * 激活状态	0：未激活	1：已激活
	 */
	protected Short isActive;
	/**
	 * 激活时间
	 */
	protected Date activeTime;
	/**
	 * 微信激活用户唯一标识
	 */
	protected String wxuFlag;
	/**
	 * 微信包ID
	 */
	protected Integer packageId;
	/**
	 * 优惠券配置ID
	 */
	protected Integer couponConfigId;
	
	public Integer getCouponId() {
		return couponId;
	}
	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}
	public String getCouponNo() {
		return couponNo;
	}
	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}
	public Short getIsActive() {
		return isActive;
	}
	public void setIsActive(Short isActive) {
		this.isActive = isActive;
	}
	public Date getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}
	public String getWxuFlag() {
		return wxuFlag;
	}
	public void setWxuFlag(String wxuFlag) {
		this.wxuFlag = wxuFlag;
	}
	public Integer getCouponConfigId() {
		return couponConfigId;
	}
	public void setCouponConfigId(Integer couponConfigId) {
		this.couponConfigId = couponConfigId;
	}
	public Integer getPackageId() {
		return packageId;
	}
	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}
	
}
