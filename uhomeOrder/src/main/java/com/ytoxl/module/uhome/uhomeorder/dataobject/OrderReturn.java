package com.ytoxl.module.uhome.uhomeorder.dataobject;

import java.util.List;

import com.ytoxl.module.uhome.uhomebase.dataobject.ReceiverAddress;
import com.ytoxl.module.uhome.uhomeorder.dataobject.tbl.OrderReturnTbl;

public class OrderReturn extends OrderReturnTbl {
	protected OrderHead orderHead;
	protected ReceiverAddress  address;
	protected OrderExpress express;
	protected OrderPayment orderPayment;
	//退货订单  退款记录
	protected OrderReturnPayment orderReturnPayment;
	//退货明细
	protected List<OrderReturnItem> returnItems;
	protected OrderReturnItem item;
	public static Short STATUS_RETURN_SUCCESS=1;  //退货成功  全部审核通过并收货成功
	public static Short STATUS_RETURN_FAIL=2;     //退货失败  全部审核不通过
	public static Short STATUS_SOME_RETURN=3;	  //部分退货   只有部分审核通过并收货成功
	public static Short STATUS_NOTAUDIT = 4;      //退货订单中有订单项未审核
	
	//后台管理员 确认退货按钮是否显示  默认不显示
	protected boolean returnDisplay = false;
	
	//自定义
	protected Short status;
	//合计应退金额
	protected String refundAmount;
	//是否有审核通过的 0:未通过 1：通过
	protected String isAgree;

	
	
	public OrderReturnItem getItem() {
		return item;
	}

	public void setItem(OrderReturnItem item) {
		this.item = item;
	}

	public String getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(String isAgree) {
		this.isAgree = isAgree;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
	

	public OrderExpress getExpress() {
		return express;
	}

	public void setExpress(OrderExpress express) {
		this.express = express;
	}

	public ReceiverAddress getAddress() {
		return address;
	}

	public void setAddress(ReceiverAddress address) {
		this.address = address;
	}

	public OrderHead getOrderHead() {
		return orderHead;
	}

	public void setOrderHead(OrderHead orderHead) {
		this.orderHead = orderHead;
	}

	public OrderPayment getOrderPayment() {
		return orderPayment;
	}

	public void setOrderPayment(OrderPayment orderPayment) {
		this.orderPayment = orderPayment;
	}

	public List<OrderReturnItem> getReturnItems() {
		return returnItems;
	}

	public void setReturnItems(List<OrderReturnItem> returnItems) {
		this.returnItems = returnItems;
	}

	public OrderReturnPayment getOrderReturnPayment() {
		return orderReturnPayment;
	}

	public void setOrderReturnPayment(OrderReturnPayment orderReturnPayment) {
		this.orderReturnPayment = orderReturnPayment;
	}

	public boolean isReturnDisplay() {
		return returnDisplay;
	}

	public void setReturnDisplay(boolean returnDisplay) {
		this.returnDisplay = returnDisplay;
	}
}
