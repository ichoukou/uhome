package com.ytoxl.module.uhome.uhomebase.dataobject;

import com.ytoxl.module.user.dataobject.tbl.UserTbl;

public class MyUser extends UserTbl{
	public static final int STATUS_UNABLE=0;//0：停用
	public static final int STATUS_ABLE=1;//1：激活
	public static final int[] STATUS=new int[]{STATUS_UNABLE,STATUS_ABLE}; 
	public MyUrole urole;//用户对应角色
	public MyUrole getUrole() {
		return urole;
	}
	public void setUrole(MyUrole urole) {
		this.urole = urole;
	}
	
}
