package com.ytoxl.module.uhome.uhomeorder.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomeorder.BaseTest;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturn;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturnPayment;

/**
 * @author wangguoqing
 *
 */
public class OrderReturnPaymentMapperTest extends BaseTest {

	@Autowired 
	private OrderReturnPaymentMapper<OrderReturnPayment> orderReturnPaymentMapper;
	
	@Test
	public void getOrderReturnPaymentByOrderReturnId(){
		OrderReturnPayment orp = orderReturnPaymentMapper.getOrderReturnPaymentByOrderReturnId(2);
		//orderReturnMapper.get(90);
		log.info("~~~~~~~~~~~~~~~orp:");
	}
}
