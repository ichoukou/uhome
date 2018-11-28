package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.dataobject.Event;

public interface EventMapper<T extends Event> extends BaseSqlMapper<T> {
	
	/**
	 * 根据活动类型查询活动列表
	 * @param Type
	 * @return
	 */
	public List<Event> listEvents(Short type) throws DataAccessException;
	
	/**
	 * 根据类型和当前时间查询活动
	 * @param type
	 * @return
	 * @throws DataAccessException
	 */
	public List<Event> listEventsByTypeAndTime(Short type) throws DataAccessException;
	
	/**
	 * 根据激活码查询记录个数
	 * @param activeCode
	 * @return
	 */
	public Integer listCountByActiveCode(String activeCode) throws DataAccessException;
	/**
	 * 根据激活码查询event
	 * @param activeCode
	 * @return
	 * @throws DataAccessException
	 */
	public Event getEventByActiveCode(String activeCode) throws DataAccessException; 
	
	/**
	 * 更新已激活次数
	 * @param eventId
	 * @return
	 * @throws DataAccessException
	 */
	public Integer updateActiveNumById(Integer eventId) throws DataAccessException; 
	
	/**
	 * 根据商品productSkuId查询该商品可参与的活动(不包括全场)
	 * @param productSkuId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Event> getEventByProductSkuId(Integer productSkuId) throws DataAccessException;
	
	/**
	 * 获得当前时间里全场的优惠活动
	 * @param productSkuId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Event> getCurrentEventByAllPlans() throws DataAccessException;
	
}
