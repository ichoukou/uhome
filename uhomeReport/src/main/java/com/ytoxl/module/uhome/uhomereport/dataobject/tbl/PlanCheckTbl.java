package com.ytoxl.module.uhome.uhomereport.dataobject.tbl;

import java.util.Date;

/**
 * 结算管理
 * 
 * @author shengjianming
 * 
 */
public class PlanCheckTbl {
	protected Integer planCheckId;
	protected Integer planId;  
	protected Integer sellerId;
	protected Integer feedbackCount;  
	protected Date feedbackTime;
	protected String feedbackStatus;  
	protected String userId; 
	protected Date createTime; // 申请时间
	protected Short isConfirm; // 确认收款
	public Integer getPlanCheckId() {
		return planCheckId;
	}
	public void setPlanCheckId(Integer planCheckId) {
		this.planCheckId = planCheckId;
	}
	public Integer getPlanId() {
		return planId;
	}
	public void setPlanId(Integer planId) {
		this.planId = planId;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public Integer getFeedbackCount() {
		return feedbackCount;
	}
	public void setFeedbackCount(Integer feedbackCount) {
		this.feedbackCount = feedbackCount;
	}
	public Date getFeedbackTime() {
		return feedbackTime;
	}
	public void setFeedbackTime(Date feedbackTime) {
		this.feedbackTime = feedbackTime;
	}
	public String getFeedbackStatus() {
		return feedbackStatus;
	}
	public void setFeedbackStatus(String feedbackStatus) {
		this.feedbackStatus = feedbackStatus;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Short getIsConfirm() {
		return isConfirm;
	}
	public void setIsConfirm(Short isConfirm) {
		this.isConfirm = isConfirm;
	}
}
