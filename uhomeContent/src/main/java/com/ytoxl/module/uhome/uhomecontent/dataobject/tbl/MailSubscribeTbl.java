package com.ytoxl.module.uhome.uhomecontent.dataobject.tbl;

import java.util.Date;
/**
 * 邮件订阅列表
 * @author guoxinzhi
 *
 */
public class MailSubscribeTbl {
	protected Integer mailSubscribeId;
	protected Integer msgTemplateId;
	protected Integer brandId;
	protected String email;        //订阅邮箱
	protected Date createTime;
	protected Short type;        // 订阅类型，1=特卖，2=秒杀，3=品牌预约，4=全部 ,7=即将上线预约（根据类别选择相应模版生成最终发送内容）
	protected Short status;      //0表示取消，1表示订阅
	protected Integer planId;//订阅的排期id
	
	
	public Integer getPlanId() {
		return planId;
	}
	public void setPlanId(Integer planId) {
		this.planId = planId;
	}
	public Integer getMailSubscribeId() {
		return mailSubscribeId;
	}
	public void setMailSubscribeId(Integer mailSubscribeId) {
		this.mailSubscribeId = mailSubscribeId;
	}
	public Integer getMsgTemplateId() {
		return msgTemplateId;
	}
	public void setMsgTemplateId(Integer msgTemplateId) {
		this.msgTemplateId = msgTemplateId;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	
}
