package com.ytoxl.module.uhome.uhomecontent.dataobject.tbl;

import java.util.Date;

public class SpecialTopicTemplateTbl {
	
	protected Integer specialTopicTempletId;
	protected String name;
	protected Date createTime;
	protected Date updateTime;
	protected Short isPublish;
	protected Date startTime;
	protected Date endTime;
	public Integer getSpecialTopicTempletId() {
		return specialTopicTempletId;
	}
	public void setSpecialTopicTempletId(Integer specialTopicTempletId) {
		this.specialTopicTempletId = specialTopicTempletId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Short getIsPublish() {
		return isPublish;
	}
	public void setIsPublish(Short isPublish) {
		this.isPublish = isPublish;
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
	
	
	
}
