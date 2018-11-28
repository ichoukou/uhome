package com.ytoxl.module.uhome.uhomebase.dataobject.tbl;

import java.util.Date;

/**
 * 名牌排序
 * 
 * @author shengjianming	
 * 
 */
public class BrandSortTbl {
	protected Integer bandSortId;
	protected Integer brandId;
	protected Integer sort; 
	protected Date updateTime;
	protected Date createTime;
	protected Integer type;
	public Integer getBandSortId() {
		return bandSortId;
	}
	public void setBandSortId(Integer bandSortId) {
		this.bandSortId = bandSortId;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	} 
}
