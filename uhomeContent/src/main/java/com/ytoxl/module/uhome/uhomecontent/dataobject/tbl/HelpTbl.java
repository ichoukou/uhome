package com.ytoxl.module.uhome.uhomecontent.dataobject.tbl;

/**
 * 网站帮助内容
 * @author guoxinzhi
 *
 */
public class HelpTbl {
	protected Integer helpId;
	protected Integer helpCategoryId;
	protected String content;
	
	public Integer getHelpId() {
		return helpId;
	}
	public void setHelpId(Integer helpId) {
		this.helpId = helpId;
	}
	public Integer getHelpCategoryId() {
		return helpCategoryId;
	}
	public void setHelpCategoryId(Integer helpCategoryId) {
		this.helpCategoryId = helpCategoryId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
