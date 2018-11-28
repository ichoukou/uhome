package com.ytoxl.uhomeInterface.web.action.duomai;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.taobao.api.internal.util.WebUtils;
import com.ytoxl.module.uhome.uhomeInterface.model.duomai.OrderPush;
import com.ytoxl.module.uhome.uhomeInterface.service.OrderPushService;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderCps;
import com.ytoxl.uhomeInterface.web.action.BaseAction;


public class OrderPushAction extends BaseAction{

	private static final long serialVersionUID = 2823749430302589022L;
	private static Logger logger = LoggerFactory.getLogger(OrderPushAction.class);
	@Autowired
	private OrderPushService orderPushService;
	/**订单编号	 */
	private String orderNo;
	private OrderPush orderPush;
	private Map<String,String> map = new HashMap<String,String>();
	private String result;
	public String getOrderContentByOrderNo(){
		try {
			orderPush = orderPushService.getOrderPushByOrderNo(orderNo);
			try {
				map=orderPushService.toHashMap(orderPush);
				map.put("test", "1");
				try {
					result = WebUtils.doPost("http://www.duomai.com/api/order.php", map, 0, 0);
					logger.info(result+"推送地址"+map.get("hash"));
				} catch (IOException e) {
					logger.error("推送失败0");
				}
			} catch (IntrospectionException e) {
				logger.error("推送失败1"+e.getMessage());
			} catch (IllegalAccessException e) {
				logger.error("推送失败2"+e.getMessage());
			} catch (InvocationTargetException e) {
				logger.error("推送失败3"+e.getMessage());
			} 
		} catch (UhomeStoreException e) {
			logger.error("推送失败4"+e.getMessage());
		}
		return "success";
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public OrderPush getOrderPush() {
		return orderPush;
	}
	public void setOrderPush(OrderPush orderPush) {
		this.orderPush = orderPush;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
	
	
}
