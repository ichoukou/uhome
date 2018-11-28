package com.ytoxl.module.uhome.uhomeorder.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku;
import com.ytoxl.module.uhome.uhomebase.mapper.ProductSkuMapper;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderHead;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderItem;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderHeadMapper;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderItemMapper;
import com.ytoxl.module.uhome.uhomeorder.service.OrderService;
import com.ytoxl.module.uhome.uhomeorder.service.OrderService4Timer;

@Service
public class OrderService4TimerImpl implements OrderService4Timer {
	protected static Logger log = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private OrderHeadMapper<OrderHead> orderHeadMapper;
	@Autowired
	private ProductSkuMapper<ProductSku> productSkuMapper;
	@Autowired
	private OrderItemMapper<OrderItem> orderItemMapper;
	
	@Value("${defalut_order_complate_day}")
	private Integer defaultDay;
	
	@Value("${defalut_order_cancel_time}")
	private Integer defaultTime;
	
	@Value("${defalut_limit}")
	private Integer limit;
	
	@Override
	public Integer updateStatusToFinished() throws UhomeStoreException {
		return orderHeadMapper.updateStatusToFinished(defaultDay, limit);
	}

	@Override
	public Integer updateStatusToCanceled() throws UhomeStoreException {
		//30分钟内未支付的订单
		List<OrderHead> orders = orderHeadMapper.listNotPayOrders(defaultTime, limit);
		for(OrderHead order : orders){
			order.setStatus(OrderHead.STATUS_CANCEL);
			orderHeadMapper.updateStatus(order);
			//归还库存
			List<OrderItem> items = orderItemMapper.listItemsOrderId(order.getOrderId());
			for(OrderItem item : items){
				productSkuMapper.revertProductSkuInventory(item.getOrderSource(), item.getProductSkuId(), item.getNum());
			}
		}
		return orders.size();
	}
	
}
