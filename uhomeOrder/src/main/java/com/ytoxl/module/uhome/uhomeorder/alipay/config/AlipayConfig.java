package com.ytoxl.module.uhome.uhomeorder.alipay.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class AlipayConfig {
	
	public static String partner = "";
	
	// 交易安全检验码，由数字和字母组成的32位字符串
	public static String key = "";
	
	// 签约支付宝账号或卖家收款支付宝帐户
	public static String seller_email = "";
	
	public static String notify_url = "http://www.xlbuy365.com/order/alipayNotify.action";
	
	public static String return_url = "http://www.xlbuy365.com/order/alipayReturn.action";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	// 调试用，创建TXT日志路径
	//public static String log_path = "D:\\alipay_log_" + System.currentTimeMillis()+".txt";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "MD5";
	
	public static String show_parameter = "";
	
	//以下是让配置文件参数和当前静态常量参数同步
	@Autowired(required = true)  
    public void setPartner(@Value("${alipay_partner}") String partner) {  
        AlipayConfig.partner = partner;
        show_parameter += "&parter="+AlipayConfig.partner;
    }
	@Autowired(required = true)  
    public void setKey(@Value("${alipay_key}") String key) {  
        AlipayConfig.key = key;
        show_parameter += "&key="+AlipayConfig.key;
    }  
	@Autowired(required = true)  
    public void setSellerEmail(@Value("${alipay_seller_email}") String sellerEmail) {  
        AlipayConfig.seller_email = sellerEmail;
        show_parameter += "&seller_email="+AlipayConfig.seller_email;
    }  
	@Autowired(required = true)  
    public void setNotifyUrl(@Value("${notify_url}") String notifyUrl) {  
        AlipayConfig.notify_url = notifyUrl;  
    } 
	@Autowired(required = true)  
    public void setReturnUrl(@Value("${return_url}") String returnUrl) {  
        AlipayConfig.return_url = returnUrl;  
    }
	@Autowired(required = true)  
    public void setInputCharset(@Value("${alipay_input_charset}") String inputCharset) {  
        AlipayConfig.input_charset = inputCharset;  
    }
	
}
