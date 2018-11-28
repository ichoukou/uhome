package com.ytoxl.uhomefront.web.action.order;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.ytoxl.module.uhome.uhomebase.common.utils.StringUtils;
import com.ytoxl.module.uhome.uhomeorder.service.OrderService;
import com.ytoxl.uhomefront.web.action.BaseAction;

/**
 * 支付宝相关的action
 */
@SuppressWarnings("all")
public class AlipayAction extends BaseAction {

	private static Logger logger = LoggerFactory.getLogger(AlipayAction.class);
	@Autowired
	private OrderService orderService;
	private String orderNo;
	@Value("${trade_no_pattern}")
	private String tradeNoPattern;
	@Value("${order_no_pattern}")
	private String orderNoPattern;
	/**
	 * 注：及时到账支付宝返回调用的方法
	 * @return
	 */
	public String alipayReturn() {
		logger.error("---------------==================及时到账支付宝返回信息处理开始=====================--------------------");
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String,String> params = getAliPayReturnMap(request);		
		try {
//			orderNo = params.get("out_trade_no");
			String[] orderNos = StringUtils.findStringByPattern(params.get("body"), tradeNoPattern);
			if(orderNos != null && orderNos.length > 0){
				orderNo = StringUtils.findStringByPattern(orderNos[0], orderNoPattern)[0];
			}
			orderService.alipayReturn(params);
			logger.error("支付宝返回调用参数:"+params.toString());
		} catch (Exception e) {
			logger.error("及时到账支付宝返回信息处理失败",e);
			 for (Entry<String, String> entry: params.entrySet()) {
	                logger.error(entry.getKey()+"="+entry.getValue());
	            }
		}
		return "alipayResult";
	}

	/**
	 * 注：及时到账支付宝返回调用的方法(异步),调用成功后需要反馈给支付报成功或者失败
	 * @return
	 */
	public void alipayNotify() throws IOException  {
		logger.error("---------------==================及时到账支付宝返回调用的方法(异步)处理开始==================--------------");
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String,String> params =getAliPayReturnMap(request);
		try {
			logger.error("支付宝返回调用参数:"+params.toString());
			orderService.alipayNotify(params);
			logger.error("及时到账(异步)支付宝返回信息处理success");
			print("success");
		} catch (Exception e) {			
			logger.error("及时到账(异步)支付宝返回信息处理fail");
			print("fail");
		}
	}
	
	// 获取支付宝的通知返回参数
	private Map<String, String> getAliPayReturnMap(HttpServletRequest request) {
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		return params;
	}
	
	/**
	 * 支付宝异步调用返回支付宝状态使用
	 */
	public void print(String string) throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		out.print(string);
		out.flush();
		out.close();
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


}
