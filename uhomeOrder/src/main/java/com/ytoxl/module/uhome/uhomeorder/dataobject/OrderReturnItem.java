package com.ytoxl.module.uhome.uhomeorder.dataobject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ytoxl.module.uhome.uhomeorder.dataobject.tbl.OrderReturnItemTbl;

public class OrderReturnItem extends OrderReturnItemTbl {
	public static final Short STATUS_ALL=0;//全部
	public static final Short STATUS_NOTAUDIT=1;    //1=未审核
	public static final Short STATUS_ACCEPT=2;    	//2=审核通过
	public static final Short STATUS_REFUSEED=3;    //3=审核不通过
	public static final Short STATUS_TAKE_GOODS=4;  //已经收货
	public static final Short STATUS_FINISH=5;//已经完成
	public static final Short[] ITEM_STATUSES=new Short[]{STATUS_ALL,STATUS_NOTAUDIT,STATUS_ACCEPT,STATUS_REFUSEED,STATUS_TAKE_GOODS,STATUS_FINISH};
	//图片集合
	protected List<String> imageList;
	public List<String> getImageList() {
		List<String> list = new ArrayList<String>();
		String[] str = imageUrls.split(",");
		list = Arrays.asList(str);
		return list;
	}
	
	protected OrderItem  orderItem;//唯一对应的订单项

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

}
