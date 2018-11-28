package com.ytoxl.module.uhome.uhomebase.common.utils.excel;

import java.util.Arrays;

import org.junit.Test;

import com.ytoxl.module.uhome.uhomebase.common.utils.StringUtils;

public class StringUtilsTest {

	@Test
	public void findStringByPatterntest(){
		String str = "body=12(1)orderNo:[13070800018]";
		str = "透气赤足仿生大底休闲商务男鞋(1)orderNo:[13080500014]";
		String regex = "orderNo:\\[(.*?)\\]";
		String[] strArr = StringUtils.findStringByPattern(str, regex);
		System.out.println(Arrays.toString(strArr));
		regex = "(\\d{1}\\|?){1,}";
		for(String str2:strArr){
			System.out.println(Arrays.toString(StringUtils.findStringByPattern(str2,regex)));
		}
		
	}
	
	@Test
	public void indexOfTest(){
		String str = "0.123";
		System.out.println(str.indexOf("."));
	}
}
