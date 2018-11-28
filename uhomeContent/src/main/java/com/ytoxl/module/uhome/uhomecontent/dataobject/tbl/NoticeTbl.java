package com.ytoxl.module.uhome.uhomecontent.dataobject.tbl;

import java.util.Date;

/**
 * 公告表
 * @author xupf
 *
 */
public class NoticeTbl {
	private Integer noticeId;   //主键
	private String title; 		//标题 
	private Short type;       //类型：1-打折预报；2-网站公告；
	private String source;      //来源
	private String content;     //内容
	private Integer authorId;   //撰稿人ID
	private Short status;     //状态：1-未审核；2-已审核
	private Short isTop;      //是否置顶
	private Integer checkerId;  //审核人ID
	private Date topTime;    	//置顶时间（可反复置顶）
	private Date publishTime;   //发布时间（即审核通过时间）
	private Date createTime;    //创建时间
	private Date updateTime;	//更新时间
	
	public Integer getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public Short getIsTop() {
		return isTop;
	}
	public void setIsTop(Short isTop) {
		this.isTop = isTop;
	}
	public Integer getCheckerId() {
		return checkerId;
	}
	public void setCheckerId(Integer checkerId) {
		this.checkerId = checkerId;
	}
	public Date getTopTime() {
		return topTime;
	}
	public void setTopTime(Date topTime) {
		this.topTime = topTime;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
