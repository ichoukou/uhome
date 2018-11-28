package com.ytoxl.module.uhome.uhomeorder.dataobject;

import java.util.List;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;
import com.ytoxl.module.uhome.uhomebase.common.utils.excel.Type;
import com.ytoxl.module.uhome.uhomebase.dataobject.ReceiverAddress;
import com.ytoxl.module.uhome.uhomeorder.dataobject.tbl.OrderHeadTbl;

/**单独用于退货导出的
 * @author zhiming.zeng
 *
 */
public class OrderReturnExport extends OrderHeadTbl{
	
	/**收货地址	 */
	@ExcelField(type=Type.OBJECT)
	private ReceiverAddress address;
	/**订单金额	 */
	@ExcelField(type=Type.OBJECT)
	private OrderPayment payment;
	
	@ExcelField(filedName="订单状态",sort=1)
	protected String statusChar;
	
	@ExcelField(filedName="商品名称",sort=2)
	protected String productName;
	
	@ExcelField(filedName="商品个数",sort=3)
	protected String productcategoryNum;
	
	@ExcelField(filedName="总金额",sort=4)
	protected String totalMoney;
	
	private OrderItem item;
	
	/**	订单对应多个退货订单 */
	protected List<OrderReturn> orderReturns;
	/**	用于展示的时候 1对1 对应 */
	protected OrderReturn orderReturn;
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
	public String getStatusChar() {
		return statusChar;
	}
	public void setStatusChar(String statusChar) {
		this.statusChar = statusChar;
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
	public OrderItem getItem() {
		return item;
	}
	public void setItem(OrderItem item) {
		this.item = item;
	}
	public List<OrderReturn> getOrderReturns() {
		return orderReturns;
	}
	public void setOrderReturns(List<OrderReturn> orderReturns) {
		this.orderReturns = orderReturns;
	}
	public OrderReturn getOrderReturn() {
		return orderReturn;
	}
	public void setOrderReturn(OrderReturn orderReturn) {
		this.orderReturn = orderReturn;
	}
	
	
	
	
	
}
