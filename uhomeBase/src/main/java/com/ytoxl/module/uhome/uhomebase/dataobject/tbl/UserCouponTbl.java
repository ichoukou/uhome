package com.ytoxl.module.uhome.uhomebase.dataobject.tbl;
/**
 * 用户优惠券关联表
 * @author anqian
 *
 */
public class UserCouponTbl {
	/**
	 * ID
	 */
	protected Integer userCouponId;
	/**
	 * 用户ID
	 */
	protected Integer userId;
	/**
	 * 用户名
	 */
	protected String username;
	/**
	 * 优惠券号
	 */
	protected String couponNo;
	/**
	 * 优惠券状态	0：未使用	1：已使用	2:已过期
	 */
	protected Short couponStatus;
	/**
	 * 优惠券明细ID
	 */
	protected Integer couponId;
	/**
	 * 配置ID
	 */
	protected Integer couponConfigId;
	
	/**
	 * 激活码
	 */
	protected String activeCode;
	public Integer getUserCouponId() {
		return userCouponId;
	}
	public void setUserCouponId(Integer userCouponId) {
		this.userCouponId = userCouponId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCouponNo() {
		return couponNo;
	}
	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}
	public Short getCouponStatus() {
		return couponStatus;
	}
	public void setCouponStatus(Short couponStatus) {
		this.couponStatus = couponStatus;
	}
	public Integer getCouponId() {
		return couponId;
	}
	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}
	public Integer getCouponConfigId() {
		return couponConfigId;
	}
	public void setCouponConfigId(Integer couponConfigId) {
		this.couponConfigId = couponConfigId;
	}
	public String getActiveCode() {
		return activeCode;
	}
	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}
	
}
