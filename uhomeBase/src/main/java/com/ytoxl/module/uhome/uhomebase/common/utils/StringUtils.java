package com.ytoxl.module.uhome.uhomebase.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang.StringUtils {
	public static String insertWbr(String str){
		if(null == str){
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<str.length();i++){
			sb.append(str.charAt(i)).append("<wbr />");
		}
		return sb.toString();
	}
	
	public static String getMidStr(String str, String startStr, String endStr) {
		if (str == null) {
			return null;
		}
		int a = str.indexOf(startStr);

		if (a != -1) {
			str = str.substring(a + startStr.length());
			int b = str.indexOf(endStr);
			if (b != -1) {
				str = str.substring(0, b);
				return str;
			}
		}
		return null;
	}
	
	/**
	 * 单个运单号
	 * @param str
	 * @return
	 */
	public static boolean isSingleMail(String str){
		Pattern pattern = Pattern.compile("(0|1|2|3|4|5|6|7|8|9|E|D|F|G|V|W|e|d|f|g|v|w)[0-9]{9}");
		Matcher matcher = pattern.matcher(str.trim());
		if (matcher.matches()){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否是运单号
	 * @param str
	 * @return
	 */
	public static boolean isMailNo(String str){
	    if(StringUtils.isNotEmpty(str)){
	        if(str.contains("/") && str.length() > 1){
	            String [] tempArray = str.split("/");
	            for(String tempStr : tempArray){
	                if(!isSingleMail(tempStr)){
	                    return false;
	                }
	            }
	            return true;
	        }else if(str.indexOf(" ")>1){
	        	String[] tempArray = str.split(" ");
	        	for(String tempStr : tempArray){
	                if(!isSingleMail(tempStr)){
	                    return false;
	                }
	             }
	        	return true;
	        }else if(str.indexOf("\n")>1){
	        	String[] tempArray = str.split("\n");
	        	for(String tempStr : tempArray){
	                if(!isSingleMail(tempStr)){
	                    return false;
	                }
	             }
	        	return true;
	        }
	        else{
	            return isSingleMail(str);
	        }
	    }

	    return false;
	}
	
	/**
	 * 返回目标字符串的字节长度
	 * @param str
	 * @return
	 */
	public static int getLength(String str){
		try {
			return str.getBytes("Unicode").length;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return 0;
		}
	}
	
	/**
	 * 按字节长度截取字符串，可以是中英文混排
	 * @param s 要截取的目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String multiSubStr(String s, int len) {
		if (s == null) {
			return "";
		}
		try {
			if (s.getBytes("Unicode").length <= len) {
				return s;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return multiSubstring2(s, len) + "...";
	}

	/**
	 * 按字节长度截取字符串
	 * @param s 要截取的目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String multiSubstring2(String s, int length) {
		String s1 = s;
		try{
		byte[] bytes = s.getBytes("Unicode");
		int n = 0; // 表示当前的字节数
		int i = 2; // 要截取的字节数，从第3个字节开始

		for (; i < bytes.length && n < length; i++) {
			// 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
			if (i % 2 == 1) {
				n++; // 在UCS2第二个字节时n加1
			} else {
				// 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
				if (bytes[i] != 0) {
					n++;
				}
			}
		}

		// 如果i为奇数时，处理成偶数
		if (i % 2 == 1) {
			// 该UCS2字符是汉字时，去掉这个截一半的汉字
			if (bytes[i - 1] != 0) {
				i = i - 1;
			}
			// 该UCS2字符是字母或数字，则保留该字符
			else {
				i = i + 1;
			}
		}
		
		s1 =  new String(bytes, 0, i, "Unicode");
		}catch(UnsupportedEncodingException u){
			u.printStackTrace();
		}
		return s1;
	}
	
	/**
	 * 检查指定的字符串列表是否不为空。
	 */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}
	
	/**
	 * 根据正则表达式 获取想要的字符串数组
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static String[] findStringByPattern(String str,String regex){
		String[] strArr;
		int index = 0;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		strArr = new String[pattern.split(str).length];
		while(matcher.find()){
			strArr[index++] = matcher.group();
		}
		return strArr;
	}
	
}
