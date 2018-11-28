package com.ytoxl.module.uhome.uhomebase.dataobject.resultmap;

import java.util.HashMap;
import java.util.Map;

public class ProductCategoryModel {
	public final static Integer FSXB = 100000;  //服饰箱包
	public final static Integer MYYP = 100001;  //母婴用品
	public final static Integer JJYP = 100002;  //家居用品
	public final static Integer MRHF = 100003;  //美容护肤
	
	public final static Integer[] PCS = new Integer[]{FSXB,MYYP,JJYP,MRHF};
	
	@SuppressWarnings("serial")
	public static Map<String,Integer> PCSMAP = new HashMap<String,Integer>(){
		{
			put("fsxb",FSXB);
			put("myyp",MYYP);
			put("jjyp",JJYP);
			put("mrhf",MRHF);
		}
	};
	

}
