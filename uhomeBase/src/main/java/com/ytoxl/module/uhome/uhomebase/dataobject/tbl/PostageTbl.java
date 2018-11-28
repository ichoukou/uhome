package com.ytoxl.module.uhome.uhomebase.dataobject.tbl;

import java.math.BigDecimal;
import java.util.Date;

public class PostageTbl {
	protected Integer postageId;
	protected Integer outId;
	protected BigDecimal postage;
	protected Date startTime;
	protected Date endTime;
	protected Short type;
	protected Date createTime;
	protected Date updateTime;

	public Integer getPostageId() {
		return postageId;
	}

	public void setPostageId(Integer postageId) {
		this.postageId = postageId;
	}

	public Integer getOutId() {
		return outId;
	}

	public void setOutId(Integer outId) {
		this.outId = outId;
	}

	public BigDecimal getPostage() {
		return postage;
	}

	public void setPostage(BigDecimal postage) {
		this.postage = postage;
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

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
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
