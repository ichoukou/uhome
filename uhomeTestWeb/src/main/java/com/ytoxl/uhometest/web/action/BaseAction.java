package com.ytoxl.uhometest.web.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.ytoxl.uhometest.web.bean.Message;

public class BaseAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private Message message;
	private static final Log log = LogFactory.getLog(BaseAction.class);
	public static final String JSONMSG = "jsonMsg";
	
	private String menuFlag;
	
	public Message getMessage() {
		return message;
	}

	public void setMessage(String code, String info, String[] infoValues) {
		message = new Message(code, info, infoValues);
	}

	public void setMessage(String info, String[] infoValues) {
		setMessage(null, info, infoValues);
	}

	public void setMessage(String code, String info) {
		setMessage(code, info, null);
	}

	public void setMessage(String info) {
		setMessage(null, info, null);
	}

	public String getMenuFlag() {
		return menuFlag;
	}

	public void setMenuFlag(String menuFlag) {
		this.menuFlag = menuFlag;
	}
	
	

}
