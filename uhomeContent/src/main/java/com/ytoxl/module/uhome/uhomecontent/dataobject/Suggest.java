package com.ytoxl.module.uhome.uhomecontent.dataobject;

import com.ytoxl.module.uhome.uhomecontent.dataobject.tbl.SuggestTbl;
import com.ytoxl.module.user.dataobject.User;

public class Suggest extends SuggestTbl {
	public static final Short STATUS_UNREAD=1;   //未读
	public static final Short STATUS_READ=2;   //已读
	
	public static final Short TYPE_SITE_SUGGEST=1;   //对网站的建议
	public static final Short TYPE_BRAND_SUGGEST=2;   //想要品牌的建议
	
	protected User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
