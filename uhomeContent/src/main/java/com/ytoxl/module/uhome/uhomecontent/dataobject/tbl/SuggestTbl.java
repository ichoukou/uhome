package com.ytoxl.module.uhome.uhomecontent.dataobject.tbl;

import java.util.Date;

/**
 * 建议内容
 * 
 * @author guoxinzhi
 * 
 */
public class SuggestTbl {
	protected Integer suggestId;
	protected Short type; // 1=对网站的建议，2=想要的品牌建议
	protected String title;
	protected String content;
	protected Short status; // 1=未读，2=已读
	protected Date createTime;
	protected Integer userId;

	public Integer getSuggestId() {
		return suggestId;
	}

	public void setSuggestId(Integer suggestId) {
		this.suggestId = suggestId;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
