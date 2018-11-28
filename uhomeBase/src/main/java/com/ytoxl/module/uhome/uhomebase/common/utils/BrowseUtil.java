package com.ytoxl.module.uhome.uhomebase.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 浏览器类型判断工具类
 * @author 蔡扶保
 * 2013年2月21日
 */
public class BrowseUtil {
	
	/*正则字符串开始*/
	private final static String IE9="MSIE 9.0";
	private final static String IE8="MSIE 8.0";
	private final static String IE7="MSIE 7.0";
	private final static String IE6="MSIE 6.0";
	private final static String MAXTHON="Maxthon";
	private final static String QQ="QQBrowser";
	private final static String GREEN="GreenBrowser";
	private final static String SE360="360SE";
	private final static String FIREFOX="Firefox";
	private final static String OPERA="Opera";
	private final static String CHROME="Chrome";
	private final static String SAFARI="Safari";
	private final static String OTHER="other";
	/*正则字符串结束*/
	
	/**
	 * 判断并返回浏览器类型,调用方法
	 * @param userAgent 浏览器的ua(USER-AGENT)
	 * @return 指定浏览器的标识符
	 */
	public static String checkBrowse(String userAgent){
		if(regex(OPERA, userAgent))return "opera";
		if(regex(CHROME, userAgent))return "chrome";
		if(regex(FIREFOX, userAgent))return "firefox";
		if(regex(SAFARI, userAgent))return "Safari";
		if(regex(SE360, userAgent))return "360se";
		if(regex(GREEN,userAgent))return "greenBrowser";
		if(regex(QQ,userAgent))return "qqBrowser";
		if(regex(MAXTHON, userAgent))return "maxthon";
		if(regex(IE9,userAgent))return "ie9";
		if(regex(IE8,userAgent))return "ie8";
		if(regex(IE7,userAgent))return "ie7";
		if(regex(IE6,userAgent))return "ie6";
		return OTHER;
	}
	
	/**
	 * 根据正则判断是否是指定浏览器类型
	 * @param regex 正则表达式
	 * @param str 浏览器的ua
	 * @return 是否是指定浏览器
	 */
	private static boolean regex(String regex,String str){
		Pattern p =Pattern.compile(regex,Pattern.MULTILINE);
		Matcher m=p.matcher(str);
		return m.find();
	}
}