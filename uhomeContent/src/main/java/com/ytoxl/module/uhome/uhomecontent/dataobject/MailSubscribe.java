package com.ytoxl.module.uhome.uhomecontent.dataobject;

import com.ytoxl.module.uhome.uhomecontent.dataobject.tbl.MailSubscribeTbl;

public class MailSubscribe extends MailSubscribeTbl {
	public static final Short TYPE_SPECIAL_SELLER=1;   //特卖
	public static final Short TYPE_SEC_KILL=2;  //秒杀
	public static final Short TYPE_BRAND_SUBSCRIBE=3;  //品牌预约
	public static final Short TYPE_ALL=4;                //所有
	public static final Short TYPE_TOBEONTHELINE=7;                //即将上线的订阅
	public static final Short STATUS_CANCEL=0;         //表示取消
	public static final Short STATUS_SUBSCRIBE=1;      //表示订阅
	//品牌集中营订阅多个品牌时 记录品牌id 用  , 隔开
	protected String brandIds;
	//品牌集中营订阅多个即将上线时 记录品牌id 用  , 隔开
	protected String planIds;
	protected String brandNames;
	public static final Short[] TYPES = new Short[] { TYPE_SPECIAL_SELLER, TYPE_BRAND_SUBSCRIBE, TYPE_TOBEONTHELINE };
	public String getBrandNames() {
		return brandNames;
	}

	public void setBrandNames(String brandNames) {
		this.brandNames = brandNames;
	}

	public String getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(String brandIds) {
		this.brandIds = brandIds;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.mailSubscribeId+"-->"+this.email;
	}

}
