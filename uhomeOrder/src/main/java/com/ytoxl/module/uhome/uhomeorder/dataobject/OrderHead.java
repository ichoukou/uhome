package com.ytoxl.module.uhome.uhomeorder.dataobject;

import java.util.List;
import java.util.Map;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;
import com.ytoxl.module.uhome.uhomebase.common.utils.excel.Type;
import com.ytoxl.module.uhome.uhomebase.dataobject.ReceiverAddress;
import com.ytoxl.module.uhome.uhomebase.dataobject.resultmap.ExpressMessage;
import com.ytoxl.module.uhome.uhomeorder.dataobject.tbl.OrderHeadTbl;
import com.ytoxl.module.uhome.uhomeorder.dataobject.tbl.OrderPostage;

public class OrderHead extends OrderHeadTbl {
	public static final Short STATUS_NEW=1;    //1=新建
	public static final Short STATUS_WAITSEND=2;    //2=待发货
	public static final Short STATUS_SEND=3;    //3=已发货
	public static final Short STATUS_FINISHED=4;    //4=已完成（默认15天后自动完成，定时器跑任务）
	public static final Short STATUS_CANCEL=5;    //5=已取消
	public static final Short STATUS_RETURN=6;    //6=部分退货
	public static final Short STATUS_ALLRETURN=7;  //7=全部退货
	public static final Short[] STATUSES=new Short[]{STATUS_NEW,STATUS_WAITSEND,STATUS_SEND,STATUS_FINISHED,STATUS_CANCEL,STATUS_RETURN,STATUS_ALLRETURN};
	
	//常量15天退货
	public static final Short RETRUN_DATE=15;
	
	//是否运行退货状态
	public static final Short STATUS_FORBID_RETURN = 0; //不允许退货
	public static final Short STATUS_ALLOW_RETURN = 1; //允许退货
	@ExcelField(type=Type.OBJECT)
	private ReceiverAddress address;
	@ExcelField(type=Type.OBJECT)
	private OrderPayment payment;
	private OrderExpress orderExpress;
	private List<OrderItem> items;
	private OrderItem item;
	private OrderReturn orderReturn;
	private OrderPostage orderPostage;
	private Integer sellerId;
	
	private Map detailExpress;
	
	private ExpressMessage expressMessage;

	@ExcelField(filedName="订单状态",sort=1)
	protected String statusChar;
	
	@ExcelField(filedName="商品名称",sort=2)
	protected String productName;
	
	@ExcelField(filedName="商品个数",sort=3)
	protected String productcategoryNum;
	
	@ExcelField(filedName="总金额",sort=4)
	protected String totalMoney;
	
	//标识是否能退货
	protected Short allowReturn;
	
	//用于使用了同一张优惠券的订单  
	protected List<OrderHead> childOrders;
	//id用字符串拼接起来 用|区分
	protected String childOrderIds;
	
	protected List<OrderReturn> orderReturns;
	
	
	public OrderItem getItem() {
		return item;
	}
	public void setItem(OrderItem item) {
		this.item = item;
	}
	public ReceiverAddress getAddress() {
		return address;
	}
	public void setAddress(ReceiverAddress address) {
		this.address = address;
	}
	public OrderPayment getPayment() {
		return payment;
	}
	public void setPayment(OrderPayment payment) {
		this.payment = payment;
	}
	public List<OrderItem> getItems() {
		return items;
	}
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	public OrderExpress getOrderExpress() {
		return orderExpress;
	}
	public void setOrderExpress(OrderExpress orderExpress) {
		this.orderExpress = orderExpress;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getProductcategoryNum() {
		return productcategoryNum;
	}
	public void setProductcategoryNum(String productcategoryNum) {
		this.productcategoryNum = productcategoryNum;
	}
	public String getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}
	public String getStatusChar() {
		return statusChar;
	}
	public void setStatusChar(String statusChar) {
		this.statusChar = statusChar;
	}
	public OrderReturn getOrderReturn() {
		return orderReturn;
	}
	public void setOrderReturn(OrderReturn orderReturn) {
		this.orderReturn = orderReturn;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public Map getDetailExpress() {
		return detailExpress;
	}
	public void setDetailExpress(Map detailExpress) {
		this.detailExpress = detailExpress;
	}
	public Short getAllowReturn() {
		return allowReturn;
	}
	public void setAllowReturn(Short allowReturn) {
		this.allowReturn = allowReturn;
	}
	
	public List<OrderHead> getChildOrders() {
		return childOrders;
	}
	public void setChildOrders(List<OrderHead> childOrders) {
		this.childOrders = childOrders;
	}
	public String getChildOrderIds() {
		return childOrderIds;
	}
	public void setChildOrderIds(String childOrderIds) {
		this.childOrderIds = childOrderIds;
	}
	
	public List<OrderReturn> getOrderReturns() {
		return orderReturns;
	}
	public void setOrderReturns(List<OrderReturn> orderReturns) {
		this.orderReturns = orderReturns;
	}
	public ExpressMessage getExpressMessage() {
		return expressMessage;
	}
	public void setExpressMessage(ExpressMessage expressMessage) {
		this.expressMessage = expressMessage;
	}
	@Override
	public int hashCode() {
		return this.orderId;
	}
	
	@Override
	public String toString() {
		return "orderNo:"+this.orderNo;
	}
	public OrderPostage getOrderPostage() {
		return orderPostage;
	}
	public void setOrderPostage(OrderPostage orderPostage) {
		this.orderPostage = orderPostage;
	}
	
}
