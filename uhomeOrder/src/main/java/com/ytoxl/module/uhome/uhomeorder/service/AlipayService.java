package com.ytoxl.module.uhome.uhomeorder.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import org.dom4j.DocumentException;

public interface AlipayService {

	/**
	 * 构造即时到帐接口
	 * 
	 * @param sParaTemp
	 *            请求参数集合
	 * @return 表单提交HTML信息
	 */
	public abstract String createDirectPayByUser(Map<String, String> sParaTemp);

	/**
	 * 用于防钓鱼，调用接口query_timestamp来获取时间戳的处理函数 注意：远程解析XML出错，与服务器是否支持SSL等配置有关
	 * 
	 * @return 时间戳字符串
	 * @throws IOException
	 * @throws DocumentException
	 * @throws MalformedURLException
	 */
	public abstract String query_timestamp() throws MalformedURLException, DocumentException, IOException;

}