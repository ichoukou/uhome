package com.ytoxl.module.uhome.uhomeInterface.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomeInterface.model.duomai.OrderContent;

/**订单查询 mapper
 * @author zhiming.zeng
 *
 * @param <T>
 */
public interface OrderSearchMapper <T extends OrderContent> extends BaseSqlMapper<T>{
	
	

	/**通过unionId 开始时间 结束时间查询得到订单对象
	 * @param map
	 * @return
	 */
	public List<OrderContent> searchOrderContentByUnionIdSEtime(Map<String, Object> map)throws DataAccessException;;
	
	/**通过unionId 开始时间 结束时间查询得到order_cps总条数
	 * @param map
	 * @return
	 */
	public Integer searchOrderContentByUnionIdSEtimeCount(Map<String, Object> map)throws DataAccessException;;
}
