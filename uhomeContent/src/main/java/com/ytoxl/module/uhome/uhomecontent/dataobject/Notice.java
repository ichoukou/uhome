package com.ytoxl.module.uhome.uhomecontent.dataobject;

import com.ytoxl.module.uhome.uhomecontent.dataobject.tbl.NoticeTbl;

/**
 * 公告
 * @author xupf
 *
 */
public class Notice extends NoticeTbl {

	public static final Short TYPE_REBATE = 1;	//打拆公告
	public static final Short TYPE_NETWORK = 2;	//网站公告
	public static final Short CHECKED_NO = 0;	//未审核
	public static final Short CHECKED_YES = 1;	//已审核
	public static final Short TOP_NO = 0;		//未置顶
	public static final Short TOP_YES = 1;	//已置顶
	public static final String DEFAULT_TIME = "0000-00-00 00:00:00"; //默认时间
	private String userName;     //用户名
	private String imgUrl;	 //图片地址
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	
}
