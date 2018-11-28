package com.ytoxl.module.uhome.uhomebase.common.utils;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.NoSuchMessageException;

import com.ytoxl.module.core.common.utils.SpringContextUtils;
import com.ytoxl.module.core.common.utils.StringUtils;

public class PropertyUtil {
	private static Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
	/**
	 * 根据key获取配置文件中的值
	 * @param messageOrCode
	 * @param errorValues
	 * @return
	 */
	public static String getPropertyValue(String messageOrCode,String[] errorValues, Locale locale){
		String result = null;
		if(StringUtils.isNotEmpty(messageOrCode)){
			try {
				result = SpringContextUtils.getContext().getMessage(messageOrCode, errorValues, locale);
			} catch (NoSuchMessageException e) {	
				logger.error(e.getMessage());
			}				
		}
		return result;
	}
	/**
	 * 获取对应KEY配置文件中文国际化信息
	 * @param messageOrCode
	 * @param errorValues
	 * @return
	 */
	public static String getPropertyValue(String messageOrCode,String[] errorValues){
		String result = null;
		if(StringUtils.isNotEmpty(messageOrCode)){
			try {
				result = SpringContextUtils.getContext().getMessage(messageOrCode, errorValues, Locale.SIMPLIFIED_CHINESE);
			} catch (NoSuchMessageException e) {	
				logger.error(e.getMessage());
			}				
		}
		return result;
	}
}
