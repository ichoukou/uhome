package com.ytoxl.uhomemanage.web.action.order;

import com.ytoxl.uhomemanage.web.action.BaseAction;

public class IndexAction extends BaseAction {

	private static final long serialVersionUID = -1683456422079577697L;
	
	private static final String SELLER="seller";
	private static final String ADMIN="admin";
	
	public String index()throws Exception{
		return SUCCESS;
	}
	
	
	public String seller(){
		return SELLER;
	}
	
	public String admin(){
		return ADMIN;
	}
}
