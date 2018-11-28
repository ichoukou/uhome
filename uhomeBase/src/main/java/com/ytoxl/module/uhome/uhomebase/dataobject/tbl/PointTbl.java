package com.ytoxl.module.uhome.uhomebase.dataobject.tbl;

import java.util.Date;

/**
 * 积分表
 * 
 * @author wangguoqing
 * 
 */
public class PointTbl {
	protected Integer pointId;
	protected Integer userId;
	protected Integer total;
	protected Date createTime;
	protected Date updateTime;

	public Integer getPointId() {
		return pointId;
	}

	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
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
