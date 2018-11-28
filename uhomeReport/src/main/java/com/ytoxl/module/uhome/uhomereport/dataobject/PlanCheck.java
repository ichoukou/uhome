package com.ytoxl.module.uhome.uhomereport.dataobject;

import java.util.Date;

import com.ytoxl.module.uhome.uhomereport.dataobject.tbl.PlanCheckTbl;


/**
 * 结算管理
 * 
 * @author shengjianming
 * 
 */
public class PlanCheck extends PlanCheckTbl {
	
 	public static final Short STATUS_FEEDBACKCCOUNT_ONE_CHECK = 1; // 待一次对账
	public static final Short STATUS_FEEDBACKCCOUNT_TWO_CHECK = 2; // 待二次对账
	
	public static final Short FEEDBACKCCOUNT_ONE = 1;   //第一次对账
	public static final Short FEEDBACKCCOUNT_TWO = 2;   //第二次对账
	public static final Short FEEDBACKCCOUNT_FINISH = 3; //完成对账
	
	public static final Short STATUS_FEEDBACKCSTATUS_NO  = 0; // 未完成
	public static final Short STATUS_FEEDBACKCSTATUS_FINISH = 1; // 已完成
	public static final Short PAYMENT_ISCONFIRM = 1;//确认支付
 
	private Integer tempPlanId;
	
	private String name;    //特卖名称
	
	private Date startTime;
	
	private Date endTime;
	
	private String companyName;
	
	private Integer sellerNum;

	private Date oneFeedbackTime;
	
	public Integer getTempPlanId() {
		return tempPlanId;
	}

	public void setTempPlanId(Integer tempPlanId) {
		this.tempPlanId = tempPlanId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		
		this.startTime = startTime ;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setContactName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getSellerNum() {
		return sellerNum;
	}

	public void setSellerNum(Integer sellerNum) {
		this.sellerNum = sellerNum;
	}

	public Date getOneFeedbackTime() {
		return oneFeedbackTime;
	}

	public void setOneFeedbackTime(Date oneFeedbackTime) {
		this.oneFeedbackTime = oneFeedbackTime;
	}
	
	 
	
}
