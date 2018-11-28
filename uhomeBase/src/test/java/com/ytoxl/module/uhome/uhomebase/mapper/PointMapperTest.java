package com.ytoxl.module.uhome.uhomebase.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.BaseTest;
import com.ytoxl.module.uhome.uhomebase.dataobject.Plan;
import com.ytoxl.module.uhome.uhomebase.dataobject.Point;

/**
 * @author wangguoqing
 *
 */
public class PointMapperTest extends BaseTest {
	
	@Autowired
	public PointMapper pointMapper;
	
	@Test
	public void updatePointByUserId() {
//		pointMapper.updatePointByUserId(100013, 20, Point.TYPE_ADD);
		
		pointMapper.updatePointByUserId(100013, 20, Point.TYPE_MINUS);
		
	}
	
	
}
