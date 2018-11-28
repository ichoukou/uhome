package com.ytoxl.module.uhome.uhomebase.dataobject;

import java.util.List;

import com.ytoxl.module.user.dataobject.Uresource;
import com.ytoxl.module.user.dataobject.tbl.UroleTbl;

public class MyUrole extends UroleTbl{
	public static final int STATUS_UNABLE=0;//0：停用
	public static final int STATUS_ABLE=1;//1：激活
	public static final int[] STATUS=new int[]{STATUS_UNABLE,STATUS_ABLE}; 
	/**
	 * 角色对应所有资源
	 */
	public List<Uresource> uresourceList;
	/**
	 * 已分配帐号名
	 */
	public String names;
	public List<Uresource> getUresourceList() {
		return uresourceList;
	}
	public void setUresourceList(List<Uresource> uresourceList) {
		this.uresourceList = uresourceList;
	}
	public String getNames() {
		return names;
	}
	public void setNames(String names) {
		this.names = names;
	}
	
	
}
