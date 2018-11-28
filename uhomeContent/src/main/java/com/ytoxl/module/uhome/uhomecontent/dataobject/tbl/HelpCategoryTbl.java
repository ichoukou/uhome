package com.ytoxl.module.uhome.uhomecontent.dataobject.tbl;

/**
 * 
 * @author guoxinzhi
 *
 */
public class HelpCategoryTbl {
	protected Integer helpCategoryId;
	protected String name;
	protected Integer parentId;
	protected Integer rank;
	
	public Integer getHelpCategoryId() {
		return helpCategoryId;
	}
	public void setHelpCategoryId(Integer helpCategoryId) {
		this.helpCategoryId = helpCategoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	
}
