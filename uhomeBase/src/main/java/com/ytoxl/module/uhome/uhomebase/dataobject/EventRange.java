package com.ytoxl.module.uhome.uhomebase.dataobject;

import java.util.List;

import com.ytoxl.module.uhome.uhomebase.dataobject.tbl.EventRangeTbl;

public class EventRange extends EventRangeTbl {

	public final static Short TYPE_ALLPLAN = 0;
	public final static Short TYPE_PLAN = 1;

	protected String name;
	protected List<EventRange> childrenEventRanges;

	public EventRange() {
	}
	
	public EventRange(Integer outId, String name, Short type) {
		super.outId = outId;
		super.type  = type;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<EventRange> getChildrenEventRanges() {
		return childrenEventRanges;
	}

	public void setChildrenEventRanges(List<EventRange> childrenEventRanges) {
		this.childrenEventRanges = childrenEventRanges;
	}

}
