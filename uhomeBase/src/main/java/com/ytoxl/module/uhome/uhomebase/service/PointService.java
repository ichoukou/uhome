package com.ytoxl.module.uhome.uhomebase.service;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Point;

public interface PointService {
	
	/**
	 * 获取用户积分信息
	 * @return
	 */
	public Point getPointByUserId(Integer userId) throws UhomeStoreException;
	
	/**
	 * 增减用户积分
	 * @param userId
	 * @param money
	 * @param type
	 */
	public void updatePointByUserId(Integer userId, BigDecimal money, Short type, Short pointSource) throws UhomeStoreException;

	/**
	 * 根据费用计算积分数，这里抽出来便于以后算法变化后只需要修改一处
	 * @param money
	 * @return
	 * @throws UhomeStoreException
	 */
	public Integer computerPoint(BigDecimal money) throws UhomeStoreException;
	
}
