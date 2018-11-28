package com.ytoxl.module.uhome.uhomebase.dataobject.tbl;

import java.util.Date;

/**
 * 积分明细
 * 
 * @author wangguoqing
 * 
 */
public class PointDetailTbl {
	protected Integer pointDetailId;
	protected Integer pointId;
	protected Short pointSource; // 积分来源
	protected Integer point;
	protected Date createTime;

	public Integer getPointDetailId() {
		return pointDetailId;
	}

	public void setPointDetailId(Integer pointDetailId) {
		this.pointDetailId = pointDetailId;
	}

	public Integer getPointId() {
		return pointId;
	}

	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}

	public Short getPointSource() {
		return pointSource;
	}

	public void setPointSource(Short pointSource) {
		this.pointSource = pointSource;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
