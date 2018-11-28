package com.ytoxl.module.uhome.uhomebase.dataobject.tbl;

import java.util.Date;

public class FormNumberTbl {
  
	protected Integer formNumberId;
	protected String  formPrefix;
	protected Integer  formIndex;
	protected Date   updateTime;
	
	
	public Integer getFormNumberId() {
		return formNumberId;
	}
	public void setFormNumberId(Integer formNumberId) {
		this.formNumberId = formNumberId;
	}
	public Integer getFormIndex() {
		return formIndex;
	}
	public void setFormIndex(Integer formIndex) {
		this.formIndex = formIndex;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getFormPrefix() {
		return formPrefix;
	}
	public void setFormPrefix(String formPrefix) {
		this.formPrefix = formPrefix;
	}

	
}
