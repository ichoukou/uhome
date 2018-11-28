package com.ytoxl.module.uhome.uhomeorder.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ytoxl.module.uhome.uhomebase.common.CommonConstants;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.common.utils.StringUtils;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderCps;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderPayment;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderCpsMapper;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderPaymentMapper;
import com.ytoxl.module.uhome.uhomeorder.service.OrderCpsService;

@Service
public class OrderCpsServiceImpl implements OrderCpsService {
	
	@Autowired
	private OrderCpsMapper<OrderCps> orderCpsMapper;
	@Autowired
	private OrderPaymentMapper<OrderPayment> orderPaymentMapper;

	/**
	 * 保存cps推广来的订单信息
	 * @param request
	 * @param orderIds
	 */
	@Override
	public void addOrderCps(HttpServletRequest request, List<Integer> orderIds)
			throws UhomeStoreException {
		String unionId = "";
		String feedback = "";
		String mid = "";
		String link = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(CommonConstants.COOKIE_UNIONID)) {
					unionId = cookie.getValue();
				}else if (cookie.getName().equals(CommonConstants.COOKIE_FEEDBACK)) {
					feedback = cookie.getValue();
				}else if (cookie.getName().equals(CommonConstants.COOKIE_MID)) {
					mid = cookie.getValue();
				}else if (cookie.getName().equals(CommonConstants.COOKIE_TO)) {
					link = cookie.getValue();
				}
			}
		}
		
		if(StringUtils.areNotEmpty(unionId, feedback, mid, link)){
			if(orderIds != null && orderIds.size() > 0){
				for(Integer orderId : orderIds){
					OrderCps orderCps = new OrderCps();
					orderCps.setOrderId(orderId);
					orderCps.setUnionId(unionId);
					orderCps.setFeedback(feedback);
					orderCps.setMid(mid);
					orderCps.setLink(link);
					//佣金计算
					BigDecimal commission = getCommission(orderId);
					orderCps.setCommission(commission);
					orderCpsMapper.add(orderCps);
				}
			}
		}
	}

	/**
	 * 计算佣金
	 * @param orderId
	 * @return
	 */
	@Override
	public BigDecimal getCommission(Integer orderId) throws UhomeStoreException {
		OrderPayment orderPayment = orderPaymentMapper.getOrderPaymentByOrderId(orderId);
		BigDecimal payAmount = orderPayment.getPaymentAmount();
		BigDecimal commission = payAmount.multiply(CommonConstants.COMMISSION_REBATE).setScale(2,BigDecimal.ROUND_HALF_UP);
		return commission;
	}

}
