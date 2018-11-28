package com.ytoxl.module.uhome.uhomeorder.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ytoxl.module.uhome.uhomeorder.alipay.config.AlipayConfig;
import com.ytoxl.module.uhome.uhomeorder.alipay.util.AlipaySubmit;
import com.ytoxl.module.uhome.uhomeorder.service.AlipayService;

/* *
 *类名：AlipayService
 *功能：支付宝各接口构造类
 *详细：构造支付宝各接口请求参数
 */
@Service
public class AlipayServiceImpl implements AlipayService {
	@Value("${alipay_partner}")
    private String alipayPartner;//合作身份者ID，以2088开头由16位纯数字组成的字符串
	@Value("${alipay_key}")
	private String alipayKey;//交易安全检验码，由数字和字母组成的32位字符串
	@Value("${alipay_seller_email}")
	private String alipaySellerEmail;//签约支付宝账号或卖家收款支付宝帐户
	@Value("${notify_url}")
	private String notifyUrl;//支付宝服务器通知的路径[必须保证能访问到,不带自定义参数]
	@Value("${return_url}")
	private String returnUrl;//支付宝服务器异步通知路径[必须保证能访问到,不带自定义参数]
	@Value("${alipay_input_charset}")
	private String alipayInputCharset;//网站的编码方式
    
	/**
	 * 支付宝提供给商户的服务接入网关URL(新)
	 */
	private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";

	@Override
	public  String createDirectPayByUser(Map<String, String> sParaTemp) {
		// 从配置文件中读取配置替换系统默认的配置
		if (sParaTemp != null && sParaTemp.size() >0) {
			AlipayConfig.partner = alipayPartner;
			AlipayConfig.seller_email = alipaySellerEmail;
			AlipayConfig.input_charset = alipayInputCharset;
			AlipayConfig.return_url = returnUrl;
			AlipayConfig.notify_url = notifyUrl;
			AlipayConfig.key = alipayKey;
		}
		
		//把请求参数打包成数组
		sParaTemp.put("service", "create_direct_pay_by_user");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("return_url", AlipayConfig.return_url); 
		sParaTemp.put("notify_url", AlipayConfig.notify_url);
		sParaTemp.put("seller_email", AlipayConfig.seller_email);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", "1");
		String strButtonName = "确认";

		return AlipaySubmit.buildForm(sParaTemp, ALIPAY_GATEWAY_NEW, "get", strButtonName);
	}

	@Override
	public  String query_timestamp() throws MalformedURLException, DocumentException, IOException {
		// 构造访问query_timestamp接口的URL串
		String strUrl = ALIPAY_GATEWAY_NEW + "service=query_timestamp&partner=" + AlipayConfig.partner;
		StringBuffer result = new StringBuffer();

		SAXReader reader = new SAXReader();
		Document doc = reader.read(new URL(strUrl).openStream());
		List<Node> nodeList = doc.selectNodes("//alipay/*");

		for (Node node : nodeList) {
			// 截取部分不需要解析的信息
			if (node.getName().equals("is_success") && node.getText().equals("T")) {
				// 判断是否有成功标示
				List<Node> nodeList1 = doc.selectNodes("//response/timestamp/*");
				for (Node node1 : nodeList1) {
					result.append(node1.getText());
				}
			}
		}
		return result.toString();
	}
}
