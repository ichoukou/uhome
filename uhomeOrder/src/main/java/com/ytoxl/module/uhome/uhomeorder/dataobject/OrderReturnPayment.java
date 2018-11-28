package com.ytoxl.module.uhome.uhomeorder.dataobject;

import com.ytoxl.module.uhome.uhomeorder.dataobject.tbl.OrderReturnPaymentTbl;

/**
 * @author wangguoqing
 *
 */
public class OrderReturnPayment extends OrderReturnPaymentTbl {
	
	public static final Short STATUS_WAITREFUND=0;    //0=待退款
	public static final Short STATUS_REFUND=1;    //1=已退款

}
