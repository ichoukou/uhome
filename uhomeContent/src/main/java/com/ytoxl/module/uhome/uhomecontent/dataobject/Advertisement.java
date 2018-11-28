package com.ytoxl.module.uhome.uhomecontent.dataobject;

import com.ytoxl.module.uhome.uhomecontent.dataobject.tbl.AdvertisementTbl;

public class Advertisement extends AdvertisementTbl {
	public static final Short ISDEFAULT_TRUE=1;  //备用广告
	public static final Short ISDEFAULT_FALSE=2;  //不是备用广告
	public static final Short ISLOGIN_FLASE=0;//不验证登录
	public static final Short ISLOGIN_TRUE=1;//验证登录
	private AdvPosition advPosition;

	public AdvPosition getAdvPosition() {
		return advPosition;
	}

	public void setAdvPosition(AdvPosition advPosition) {
		this.advPosition = advPosition;
	}
	
}
