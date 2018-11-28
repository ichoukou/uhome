package com.ytoxl.module.uhome.uhomeInterface.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomeInterface.mapper.OrderSearchMapper;
import com.ytoxl.module.uhome.uhomeInterface.model.duomai.OrderBean;
import com.ytoxl.module.uhome.uhomeInterface.model.duomai.OrderContent;
import com.ytoxl.module.uhome.uhomeInterface.service.OrderSearchService;
import com.ytoxl.module.uhome.uhomebase.dataobject.Partner;
import com.ytoxl.module.uhome.uhomebase.mapper.PartnerMapper;

/**
 * 订单查询 serivce 实体
 * 
 * @author user
 * 
 */
@Service
public class OrderSearchServiceImpl implements OrderSearchService {

	private static Logger logger = LoggerFactory
			.getLogger(OrderSearchServiceImpl.class);

	@Autowired
	private OrderSearchMapper<OrderContent> orderSearcherMapper;

	@Autowired
	private PartnerMapper<Partner> partnerMapper;

	/**
	 * 通过unionId得到 Parenter对象
	 * 
	 * @param unionId
	 *            系统对于联盟的唯一标识
	 * @return
	 */
	@Override
	public Partner getPartnerModelByUnionId(String unionId) {
		return partnerMapper.getPartnerModelByUnionId(unionId);
	}

	/**
	 * 通过unionId 开始时间 结束时间查询得到订单对象
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public List<OrderContent> searchOrderContentByUnionIdSEtime(
			Map<String, Object> map) {
		return orderSearcherMapper.searchOrderContentByUnionIdSEtime(map);
	}

	/**
	 * 通过unionId 开始时间 结束时间查询得到order_cps总条数
	 * 
	 * @param map
	 * @return
	 */
	public Integer searchOrderContentByUnionIdSEtimeCount(
			Map<String, Object> map) {
		return orderSearcherMapper.searchOrderContentByUnionIdSEtimeCount(map);
	}

	/**
	 * 通过 一些判断参数 获得orderbean
	 * 
	 * @param union_id
	 * @param current_num
	 * @param stime
	 * @param etime
	 * @return
	 */
	public OrderBean searchOrderContent(BasePagination<OrderBean> orderPage) {
		Integer current_num = null;
		try {
			String union_id = orderPage.getParams().get("unionId");
			current_num = Integer.parseInt(orderPage.getParams().get("current_num"));
			Partner p = this.getPartnerModelByUnionId(union_id);
			if (p == null) {// 非本系统合作联盟商
				logger.error("非本系统联盟商!");
				return new OrderBean(0, "非本系统联盟商!", null,current_num, false);
			}
			if (current_num < 0) {// 小于1
				logger.error("页码不正确!");
				return new OrderBean(0, "页码不正确!", null, current_num, false);
			}
			Integer totalSize = this.searchOrderContentByUnionIdSEtimeCount(orderPage.getSearchParamsMap());// 得到总条数
			orderPage.setTotal(totalSize);
			orderPage.setCurrentPage(current_num);
			List<OrderContent> order = this.searchOrderContentByUnionIdSEtime(orderPage.getSearchParamsMap());
			if (orderPage.getTotalPage() <= current_num) {// 如果是最后一页
				return orderPage.getTotalPage()==0?new OrderBean(1, null, order,orderPage.getTotalPage(), true): new OrderBean(1, null, order,orderPage.getTotalPage()-1, true);
			}
			return new OrderBean(1, null, order,current_num, false);
		} catch (Exception e) {
			logger.error("查询失败:" + e.getMessage());
			return new OrderBean(0, "查询失败!", null,current_num, false);
		}

	}
}
