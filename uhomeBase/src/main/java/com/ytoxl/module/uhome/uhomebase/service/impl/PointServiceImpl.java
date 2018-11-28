package com.ytoxl.module.uhome.uhomebase.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Point;
import com.ytoxl.module.uhome.uhomebase.dataobject.PointDetail;
import com.ytoxl.module.uhome.uhomebase.mapper.PointDetailMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.PointMapper;
import com.ytoxl.module.uhome.uhomebase.service.PointService;

@Service
public class PointServiceImpl implements PointService {

	@Autowired
	public PointMapper<Point> pointMapper; 
	@Autowired
	public PointDetailMapper<PointDetail> pointDetailMapper;
	
	/**
	 * 获取用户积分信息
	 * @return
	 */
	@Override
	public Point getPointByUserId(Integer userId) throws UhomeStoreException {
		Point point = pointMapper.getPointByUserId(userId);
		if(point != null){
			List<PointDetail> pointDetails = pointDetailMapper.listPointDetailsByPointId(point.getPointId());
			point.setPointDetails(pointDetails);
		}
		return point;
	}

	/**
	 * 增减用户积分
	 * @param userId
	 * @param money
	 * @param type
	 */
	@Override
	public void updatePointByUserId(Integer userId, BigDecimal money, Short type, Short pointSource)
			throws UhomeStoreException {
		Integer num = computerPoint(money);
		Point point = pointMapper.getPointByUserId(userId);
		if(point == null){
			point = new Point();
			point.setUserId(userId);
			point.setTotal(num);
			pointMapper.add(point);
		} else {
			pointMapper.updatePointByUserId(userId, num, type);
		}
		
		//积分明细
		Integer pointId = point.getPointId();
		PointDetail pointDetail = new PointDetail();
		pointDetail.setPointId(pointId);
		pointDetail.setPointSource(pointSource);
		pointDetail.setPoint(num);
		pointDetailMapper.add(pointDetail);
	}

	/**
	 * 根据费用计算积分数
	 * @param money
	 * @return
	 * @throws UhomeStoreException
	 */
	@Override
	public Integer computerPoint(BigDecimal money) throws UhomeStoreException {
		return getPoint(money);
	}

	public static Integer computerPoint4Front(BigDecimal money){
		return getPoint(money);
	}
	
	/**
	 * 计算积分
	 * @param money
	 * @return
	 */
	private static Integer getPoint(BigDecimal money){
		return money.setScale(0, RoundingMode.DOWN).intValue();
	}
	
}
