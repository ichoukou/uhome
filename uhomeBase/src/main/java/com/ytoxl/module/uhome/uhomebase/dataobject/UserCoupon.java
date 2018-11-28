package com.ytoxl.module.uhome.uhomebase.dataobject;

import java.math.BigDecimal;
import java.util.Date;

import com.ytoxl.module.uhome.uhomebase.dataobject.tbl.UserCouponTbl;

public class UserCoupon extends UserCouponTbl {
	
	/**
	 * 优惠券状态：未使用
	 */
	public final static Short COUPON_STATUS_0 = 0;
	/**
	 * 优惠券状态：已使用
	 */
	public final static Short COUPON_STATUS_1 = 1;
	/**
	 * 优惠券状态：已过期
	 */
	public final static Short COUPON_STATUS_2 = 2;
	/**
	 * 优惠券状态：未知状态
	 */
	public final static Short COUPON_STATUS_3 = 3;
	/**
	 * 优惠券状态：活动已禁止
	 */
	public final static Short COUPON_STATUS_4 = 4;
	/**
	 * 优惠券状态：活动已删除
	 */
	public final static Short COUPON_STATUS_5 = 5;
	/**
	 * 优惠券状态：未到使用日期
	 */
	public final static Short COUPON_STATUS_6 = 6;
	/**
	 * 优惠券状态：已删除
	 */
	public final static Short COUPON_STATUS_7 = 7;
	/**
	 * 活动信息
	 */
	protected Event event;

	/**真实优惠金额*/
	protected BigDecimal preferentialMoney;
	/**优惠劵使用范围*/
	protected String couponUseScope;
	/**
	 * 活动配置信息
	 */
	protected CouponConfig couponConfig;

	protected Short userCouponStatusShow;
	
	
	public BigDecimal getPreferentialMoney() {
		return preferentialMoney;
	}
	public void setPreferentialMoney(BigDecimal preferentialMoney) {
		this.preferentialMoney = preferentialMoney;
	}
	
	
	public String getCouponUseScope() {
		return couponUseScope;
	}
	public void setCouponUseScope(String couponUseScope) {
		this.couponUseScope = couponUseScope;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public CouponConfig getCouponConfig() {
		return couponConfig;
	}
	public void setCouponConfig(CouponConfig couponConfig) {
		this.couponConfig = couponConfig;
	}
	public Short getUserCouponStatusShow() {
		if(couponStatus!=0&&couponStatus!=1){
			userCouponStatusShow = COUPON_STATUS_3;
		}else{
			//结束时间
			long endTime = couponConfig.getEndTime().getTime();
			//开始时间
			long startTime = couponConfig.getStartTime().getTime();
			//当前时间
			long nowTime = new Date().getTime();
			//开始时间大于当前时间
			if(startTime>nowTime){
				userCouponStatusShow = COUPON_STATUS_6;
			}else if(startTime<=nowTime&&nowTime<=endTime){
				switch(super.couponStatus){
				case 0:
					userCouponStatusShow = COUPON_STATUS_0;
					break;
				case 1:
					userCouponStatusShow = COUPON_STATUS_1;
					break;
				default:
					userCouponStatusShow = COUPON_STATUS_3;
				}
			}else
			//如果结束时间小于当前时间
			if(endTime<nowTime){
				if(super.couponStatus==1)
					userCouponStatusShow = COUPON_STATUS_1;
				else
					userCouponStatusShow = COUPON_STATUS_2;
			}
		}
		return userCouponStatusShow;
	}
	
}
