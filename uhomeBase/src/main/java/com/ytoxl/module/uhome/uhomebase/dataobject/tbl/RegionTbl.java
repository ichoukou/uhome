package com.ytoxl.module.uhome.uhomebase.dataobject.tbl;

/**
 * 
 * @author wangguoqing
 *
 */
public class RegionTbl {
	protected Integer regionId;
	protected Integer parentRegionId; // 父区域Id
	protected String regionName; // 区域名称
	protected String regionCode; // 区域代码
	protected Integer rank;// 排序
	
	public Integer getRegionId() {
		return regionId;
	}
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	public Integer getParentRegionId() {
		return parentRegionId;
	}
	public void setParentRegionId(Integer parentRegionId) {
		this.parentRegionId = parentRegionId;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}

}
