package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.dataobject.PointDetail;

/**
 * 积分明细
 * @author huayucai
 *
 * @param <T>
 */
public interface PointDetailMapper<T extends PointDetail> extends BaseSqlMapper<T> {
	
	/**
	 * 根据积分ID查询积分明细
	 * @param userId	用户ID
	 * @return			积分明细集合
	 */
	public List<PointDetail> listPointDetailsByPointId(Integer pointId) throws DataAccessException;
	
}
