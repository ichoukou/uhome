package com.ytoxl.module.uhome.uhomecontent.dataobject.tbl;

/**
 * 专题模板广告位表
 * 
 * @author xu
 *
 */
public class SpecialtopicAdvPositionTbl {

	protected Integer specialTopicAdvPositionId; //主键ID
	protected String name; //广告位置名称
	protected String positionCode; //广告前台位置对应标示
	
	public Integer getSpecialTopicAdvPositionId() {
		return specialTopicAdvPositionId;
	}
	public void setSpecialTopicAdvPositionId(Integer specialTopicAdvPositionId) {
		this.specialTopicAdvPositionId = specialTopicAdvPositionId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPositionCode() {
		return positionCode;
	}
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	
}
