package com.ytoxl.module.uhome.uhomecontent.dataobject.tbl;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 邮件发送队列表
 * @author guoxinzhi
 */
public class MailQueueTbl {
	protected Integer mailQueueId;
	protected String title;
	protected String sender; // 发件人
	protected String receiver; // 收件人
	protected String content;
	protected Short status;
	protected Date sendTime;
	protected Date createTime;
	protected Date updateTime;
	protected Short type;  // 订阅类型，1=特卖，2=秒杀，3=品牌预约，4=全部 ,7=即将上线预约（根据类别选择相应模版生成最终发送内容）
	
   
	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Integer getMailQueueId() {
		return mailQueueId;
	}

	public void setMailQueueId(Integer mailQueueId) {
		this.mailQueueId = mailQueueId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
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
