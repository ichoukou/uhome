package com.ytoxl.module.uhome.uhomebase.mapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.dataobject.Point;

/**
 * 积分
 * @author huayucai
 *
 * @param <T>
 */
public interface PointMapper<T extends Point> extends BaseSqlMapper<T> {
	
	/**
	 * 根据用户ID查询积分
	 * @param userId	用户ID
	 * @return			会员积分
	 */
	public Point getPointByUserId(Integer userId) throws DataAccessException;
	
	/**
	 * 增减用户积分
	 * @param userId
	 * @param num
	 * @param type
	 */
	public void updatePointByUserId(@Param("userId") Integer userId, @Param("num") Integer num, @Param("type") Short type) throws DataAccessException;
}
