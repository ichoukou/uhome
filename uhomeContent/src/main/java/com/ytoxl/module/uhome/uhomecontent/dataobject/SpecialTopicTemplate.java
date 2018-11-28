package com.ytoxl.module.uhome.uhomecontent.dataobject;

import com.ytoxl.module.uhome.uhomecontent.dataobject.tbl.SpecialTopicTemplateTbl;

public class SpecialTopicTemplate extends SpecialTopicTemplateTbl {
	public static final Short PUBLISH_NO = 1;	//未发布
	public static final Short PUBLISH_YES = 2; //已发布
	
	private   Integer overdue ;

	public Integer getOverdue() {
		return overdue;
	}

	public void setOverdue(Integer overdue) {
		this.overdue = overdue;
	}
	
	
}
