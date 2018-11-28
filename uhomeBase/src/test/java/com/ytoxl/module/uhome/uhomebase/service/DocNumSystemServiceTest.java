package com.ytoxl.module.uhome.uhomebase.service;

import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.BaseTest;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;

public class DocNumSystemServiceTest extends BaseTest {
	
	@Autowired
	private DocNumSystemService docNumSystemService;
	
	@Test
	public void testGetNum() {
		String msg;
		try {
			msg = docNumSystemService.getOrderNum();
			log.error(" --------================本次生成单号：==============----------"+ msg);
		} catch (UhomeStoreException e) {
		}
		
	}

	@Test
	public void testgetCouponNum() {
		try {
			List<String> list = docNumSystemService.getCouponNos(5, "asdasdasdasd");
			log.info("-----------list-------------" + list.size());
			log.info(list.toString());
		} catch (UhomeStoreException e) {
		}
	}
	
	@Test
	public void testgetCouponActiveCode(){
		try {
			String activeCode = docNumSystemService.getCouponActiveCode();
			log.info(activeCode);
		} catch (UhomeStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
