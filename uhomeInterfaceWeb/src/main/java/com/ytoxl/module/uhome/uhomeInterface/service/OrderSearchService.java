package com.ytoxl.module.uhome.uhomeInterface.service;

import java.util.List;
import java.util.Map;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomeInterface.model.duomai.OrderBean;
import com.ytoxl.module.uhome.uhomeInterface.model.duomai.OrderContent;
import com.ytoxl.module.uhome.uhomebase.dataobject.Partner;

/**订单查询 service
 * @author zhiming.zeng
 *
 */
public interface OrderSearchService {
	
	/**通过unionId得到 Parenter对象
	 * @param unionId
	 * @return
	 */
	public Partner getPartnerModelByUnionId(String unionId);
	
	/**通过unionId 开始时间 结束时间查询得到订单对象
	 * @param map
	 * @return
	 */
	public List<OrderContent> searchOrderContentByUnionIdSEtime(Map<String,Object> map);
	

	/**通过unionId 开始时间 结束时间查询得到order_cps总条数
	 * @param map
	 * @return
	 */
	public Integer searchOrderContentByUnionIdSEtimeCount(Map<String, Object> map);

	/**通过 一些判断参数 获得orderbean
	 * @param union_id
	 * @param current_num
	 * @param stime
	 * @param etime
	 * @return
	 */
	public OrderBean searchOrderContent(BasePagination<OrderBean> orderPage);
	
}	
