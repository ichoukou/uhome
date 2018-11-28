package com.ytoxl.module.uhome.uhomebase.dataobject.tbl;

import java.math.BigDecimal;
import java.util.Date;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;


/**
 * 优惠券配置
 * @author anqian
 *
 */
public class CouponConfigTbl {
	/**
	 * 配置ID
	 */
	protected Integer couponConfigId;
	/**
	 * 抵扣金额
	 */
	@ExcelField(filedName="金额(元)",sort=2)
	protected BigDecimal allowance;
	/**
	 * 最低消费金额
	 */
	@ExcelField(filedName="最低消费金额(元)",sort=3)
	protected BigDecimal limitCharge;
	/**
	 * 活动开始时间
	 */
	protected Date startTime;
	/**
	 * 活动结束时间
	 */
	protected Date endTime;
	/**
	 * 单个用户优惠券数量
	 */
	protected Integer couponCount;
	/**
	 * 活动ID
	 */
	protected Integer eventId;
	
	
	public Integer getCouponConfigId() {
		return couponConfigId;
	}
	public void setCouponConfigId(Integer couponConfigId) {
		this.couponConfigId = couponConfigId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getCouponCount() {
		return couponCount;
	}
	public void setCouponCount(Integer couponCount) {
		this.couponCount = couponCount;
	}
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public BigDecimal getAllowance() {
		return allowance;
	}
	public void setAllowance(BigDecimal allowance) {
		this.allowance = allowance;
	}
	public BigDecimal getLimitCharge() {
		return limitCharge;
	}
	public void setLimitCharge(BigDecimal limitCharge) {
		this.limitCharge = limitCharge;
	}
	
}
