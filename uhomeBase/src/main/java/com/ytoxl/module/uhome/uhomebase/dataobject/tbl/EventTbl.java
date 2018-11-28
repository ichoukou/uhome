package com.ytoxl.module.uhome.uhomebase.dataobject.tbl;

import java.util.Date;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;

/**
 * 活动
 * @author anqian
 *
 */
public class EventTbl {
	/**
	 * 活动ID
	 */
	protected Integer eventId;
	/**
	 * 活动名称
	 */
	@ExcelField(filedName="活动名称",sort=0)
	protected String eventName;
	/**
	 * 优惠券数量
	 */
	protected Integer eventCount;
	/**
	 * 活动类型(0：普通活动	1：微信活动	2:短信推广活动)
	 */
	protected Short type;
	/**
	 * 活动状态(0：正常，1：已禁用，2：已删除)
	 */
	protected Short status;
	/**
	 * 激活码
	 */
	protected String activeCode;
	/**
	 * 激活次数
	 */
	protected Integer activeNum;
	/**
	 * 创建时间
	 */
	protected Date createTime;
	/**
	 * 修改时间
	 */
	protected Date updateTime;
	
	
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public Integer getEventCount() {
		return eventCount;
	}
	public void setEventCount(Integer eventCount) {
		this.eventCount = eventCount;
	}
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public String getActiveCode() {
		return activeCode;
	}
	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}
	public Integer getActiveNum() {
		return activeNum;
	}
	public void setActiveNum(Integer activeNum) {
		this.activeNum = activeNum;
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
	
	
}
