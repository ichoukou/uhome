package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.dataobject.EventRange;

public interface EventRangeMapper<T extends EventRange> extends BaseSqlMapper<T> {
	
	/**
	 * 添加一组数据
	 * @param eventId
	 * @param list
	 */
	public void addBatch(@Param("eventId") Integer eventId, @Param("list") List<EventRange> list) throws DataAccessException;
	
	/**
	 * 根所eventId查询适用范围 
	 * @param eventId
	 * @return
	 */
	public List<EventRange> listEventRangesByEventId(Integer eventId) throws DataAccessException;
	
}
