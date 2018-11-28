package com.ytoxl.zebrawms.web.bean;

import org.junit.Test;

import com.ytoxl.uhome.web.BaseTest;
import com.ytoxl.uhomemanage.web.bean.Message;

public class MessageTest extends BaseTest{
	@Test
	public void testBean(){
		System.out.println(new Message("true","I_ORDERNO_CORRECT",null).getInfo());
		System.out.println(new Message("true","I_C",null).getInfo());
	}
}
