package com.ytoxl.module.uhome.uhomecontent.dataobject;

import com.ytoxl.module.uhome.uhomecontent.dataobject.tbl.SupplierTbl;

public class Supplier extends SupplierTbl {
	/**
	 * 供应商类型：生产商
	 */
	public final static Short TYPE_PRODUCER = 1;
	
	/**
	 * 供应商类型：代理商
	 */
	public final static Short TYPE_AGENT = 2;
	
	/**
	 * 供应商类型：加盟商
	 */
	public final static Short TYPE_ALLIANCE_BUSINESS = 3;
	
	public static final Short[] TYPES = new Short[] { TYPE_PRODUCER, TYPE_AGENT, TYPE_ALLIANCE_BUSINESS };

}
