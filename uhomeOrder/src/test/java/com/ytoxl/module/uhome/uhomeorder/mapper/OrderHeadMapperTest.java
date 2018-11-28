package com.ytoxl.module.uhome.uhomeorder.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomeorder.BaseTest;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderHead;

public class OrderHeadMapperTest extends BaseTest {
	
	@Autowired
	private OrderHeadMapper<OrderHead> orderHeadMapper;
	
	@Test
	public void updateReceiveProductTime(){
		orderHeadMapper.updateReceiveProductTime(new Integer(1));
	}
}
