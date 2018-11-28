package com.ytoxl.module.uhome.uhomeorder.dataobject;

import com.ytoxl.module.uhome.uhomeorder.dataobject.tbl.OrderCpsTbl;

public class OrderCps extends OrderCpsTbl {
	public static final Short ISNEWCUSTOM_NO = 0;
	public static final Short ISNEWCUSTOM_YES = 1;
	
	public static final Short STATUS_DEFAULT = 0;
	public static final Short STATUS_SUCCESS = 1;
	public static final Short STATUS_FAIL = 2;
	
	protected String pushLink;
	protected String hash;


	public String getPushLink() {
		return pushLink;
	}

	public void setPushLink(String pushLink) {
		this.pushLink = pushLink;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
}
