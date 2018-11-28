package com.ytoxl.module.uhome.uhomeInterface.service.impl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.taobao.api.internal.util.WebUtils;
import com.ytoxl.module.core.common.utils.DateUtil;
import com.ytoxl.module.uhome.uhomeInterface.model.duomai.OrderProduct;
import com.ytoxl.module.uhome.uhomeInterface.model.duomai.OrderPush;
import com.ytoxl.module.uhome.uhomeInterface.service.OrderPushService;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderCps;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderHead;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderItem;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderCpsMapper;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderHeadMapper;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderItemMapper;

@Service
public class OrderPushServiceImpl implements OrderPushService{

	private static Logger logger = LoggerFactory.getLogger(OrderPushServiceImpl.class);
	@Autowired
	private OrderHeadMapper<OrderHead> orderHeadMapper;
	@Autowired
	private OrderItemMapper<OrderItem> orderItemMapper;
	@Autowired
	private OrderCpsMapper<OrderCps> orderCpsMapper;
	
	@Value("${everytime_push_order_num}")
	private Integer num;
	@Override
	public OrderPush getOrderPushByOrderNo(String orderNo)throws UhomeStoreException {
		OrderPush orderPush = new OrderPush();
		if(StringUtils.isNotEmpty(orderNo)){
			OrderHead orderHead = orderHeadMapper.getOrderHeadByOrderNo(orderNo);
			if(orderHead!=null){
				orderPush.setOrder_sn(orderNo);
				orderPush.setOrder_time(orderHead.getCreateTime());
				orderPush.setOrders_price(orderHead.getTotalPrice());
				orderPush.setStatus(orderHead.getStatus().toString());
				OrderCps orderCps = orderCpsMapper.getOrderCpsByOrderId(orderHead.getOrderId());
				if(orderCps!=null){
					orderPush.setEuid(orderCps.getFeedback());
					orderPush.setCommission(orderCps.getCommission());
					orderPush.setFeedback(orderCps.getFeedback());
//					orderPush.setIs_new_custom(orderCps.getIsNewCustom()==null? 0 :orderCps.getIsNewCustom().intValue());
					orderPush.setMid(orderCps.getMid().toString());
					orderPush.setHash(orderCps.getHash());
					
				}else{
					logger.error("orderCps为空");
					orderPush.setEuid(null);
					orderPush.setCommission(null);
				}
				List<OrderItem> orderItemList = orderItemMapper.getOrderItemByOrderIdForPush(orderHead.getOrderId());
				if(orderItemList.size()>0){
					List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();
					for(int i=0;i<orderItemList.size();i++){
						OrderProduct orderProduct = new OrderProduct();
						OrderItem orderItem = orderItemList.get(i);
					    if(orderItem!=null){
					    	orderProduct.setGoods_name(orderItem.getProductName());
					    	orderProduct.setGoods_price(orderItem.getClosingCost());
					    	orderProduct.setGoods_ta(orderItem.getNum());
					    	orderProduct.setGoods_id(orderItem.getProductSkuId().toString());
						}else{
							logger.error("orderItem为空");
							throw new UhomeStoreException("orderItem未获取到值");
						}
					    orderProductList.add(orderProduct);
					}
					orderPush.setDetails(orderProductList);
			}else{
				orderPush.setDetails(null);
				logger.error("没有找到订单相对应的数据");
				throw new UhomeStoreException("没有找到订单相对应的数据");
			}
			}
		}
		return orderPush;
	}
	public static Map<String,String> convertBean(Object bean)
         	throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Class type = bean.getClass();
		Map<String, String> returnMap = new HashMap<String, String>();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (null != result) {
					returnMap.put(propertyName, result.toString());
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}
 
    public  Map<String,String> toHashMap(OrderPush bean)throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap = convertBean(bean);
		returnMap.remove("details");
		String orderTime = DateUtil.toSeconds(bean.getOrder_time());
		returnMap.put("order_time", orderTime);
		List<OrderProduct> list = new ArrayList<OrderProduct>();
		list = bean.getDetails();
		for (int i = 0; i < list.size(); i++) {
			OrderProduct orderProduct = list.get(i);
			Map<String, String> map = convertBean(orderProduct);
			Set set = map.keySet();
			if (set != null) {
				Iterator iterator = set.iterator();
				while (iterator.hasNext()) {
					String key = iterator.next().toString();
					Object value = map.get(key);
					if (returnMap.containsKey(key)) {
						value = returnMap.get(key) + "|" + value;
						returnMap.put(key, value.toString());
					} else {
						returnMap.put(key, value.toString());
					}
				}
			}
		}
		return returnMap;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public void orderPushTimer() throws UhomeStoreException {
		int orderCpsCount = orderCpsMapper.getOrderCpsByStatusCount();
		while(orderCpsCount>0){
			int orderCpsNum = 0;
			List<OrderCps> listOrdercps = orderCpsMapper.listOrderCpsByStatus(num);
			if(listOrdercps.size()>0){
				orderCpsNum = listOrdercps.size();
				for (int i = 0; i < listOrdercps.size(); i++) {
					OrderCps orderCps = listOrdercps.get(i);
					String orderNo = orderHeadMapper.getOrderById(orderCps.getOrderId()).getOrderNo();
					OrderPush orderPush = getOrderPushByOrderNo(orderNo);
					//orderPush.setHash(orderCps.getHash());
					try {
						Map<String, String> map = toHashMap(orderPush);
						try {
							String result = WebUtils.doPost(orderCps.getPushLink(), map, 0, 0);
							if(StringUtils.isNotEmpty(result)){
								orderCpsMapper.updateOrderCpsStatusByOrderCpsId(orderCps.getOrderCpsId(),OrderCps.STATUS_SUCCESS);
								logger.error("推送成功，订单为"+orderNo+"返回结果："+result);
							}else{
								orderCpsMapper.updateOrderCpsStatusByOrderCpsId(orderCps.getOrderCpsId(),OrderCps.STATUS_FAIL);
								logger.error("推送失败，订单为"+orderNo);
								orderCpsNum--;
								continue;
							}
						} catch (IOException e) {
							logger.error(e.getMessage());
						}
					} catch (IntrospectionException e) {
						logger.error(e.getMessage());
					} catch (IllegalAccessException e) {
						logger.error(e.getMessage());
					} catch (InvocationTargetException e) {
						logger.error(e.getMessage());
					}
				}
			}
			orderCpsCount -=num;
			//logger.error("推送成功:"+(num+orderCpsCount)+"条");
		}
	}
}
