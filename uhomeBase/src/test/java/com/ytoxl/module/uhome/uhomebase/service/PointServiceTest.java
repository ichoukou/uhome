package com.ytoxl.module.uhome.uhomebase.service;

import java.math.BigDecimal;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.BaseTest;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Point;

/**
 * @author wangguoqing
 *
 */
public class PointServiceTest extends BaseTest {
	
	@Autowired
	PointService pointService;
	
	@Test
	public void getPointByUserId() throws UhomeStoreException{
		
		Point point = pointService.getPointByUserId(100013);
	}
	
	@Test
	public void updatePointByUserId() throws UhomeStoreException{
		
		pointService.updatePointByUserId(100013, BigDecimal.valueOf(2.4), Point.TYPE_MINUS, Point.POINTSOURCE_RETURNORDER);
	}
	
	@Test
	public void testcomputerPoint() throws UhomeStoreException{
		Integer pointNum = pointService.computerPoint(new BigDecimal(1.5789));
		log.info("--------------------pointNum------------------------>>"+pointNum);
	}
	
}
