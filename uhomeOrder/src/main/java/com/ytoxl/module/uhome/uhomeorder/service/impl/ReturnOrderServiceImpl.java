package com.ytoxl.module.uhome.uhomeorder.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.CommonConstants;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSkuOptionValue;
import com.ytoxl.module.uhome.uhomebase.dataobject.ReceiverAddress;
import com.ytoxl.module.uhome.uhomebase.dataobject.Region;
import com.ytoxl.module.uhome.uhomebase.mapper.ProductSkuMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.ProductSkuOptionValueMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.RegionMapper;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderExpress;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderHead;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderItem;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderPayment;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturn;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturnItem;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderExpressMapper;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderHeadMapper;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderItemMapper;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderPaymentMapper;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderReturnItemMapper;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderReturnMapper;
import com.ytoxl.module.uhome.uhomeorder.service.OrderService;
import com.ytoxl.module.uhome.uhomeorder.service.ReturnOrderService;
import com.ytoxl.module.user.common.exception.YtoxlUserException;
import com.ytoxl.module.user.service.UserService;

@Service
	public class ReturnOrderServiceImpl implements ReturnOrderService {
		protected static Logger logger = LoggerFactory.getLogger(ReturnOrderServiceImpl.class);
		@Autowired
		private OrderReturnMapper<OrderReturn> orderReturnMapper;
		@Autowired
		private OrderReturnItemMapper<OrderReturnItem> orderReturnItemMapper;
		@Autowired
		private OrderItemMapper<OrderItem> orderItemMapper;
		@Autowired
		private ProductSkuOptionValueMapper<ProductSkuOptionValue> productSkuOptionValueMapper;
		@Autowired
		private RegionMapper<Region> regionMapper;
		@Autowired
		private ProductSkuMapper<ProductSku> productSkuMapper;
		@Autowired
		private OrderExpressMapper<OrderExpress> orderExpressMapper;
		@Autowired
		private OrderHeadMapper<OrderHead> orderHeadMapper;
		@Autowired
		private OrderService orderService;
		@Autowired
		private UserService userService;
		@Autowired
		private OrderPaymentMapper<OrderPayment> orderPaymentMapper;
		
		public OrderExpressMapper<OrderExpress> getOrderExpressMapper() {
			return orderExpressMapper;
		}
	
		public void setOrderExpressMapper(OrderExpressMapper<OrderExpress> orderExpressMapper) {
			this.orderExpressMapper = orderExpressMapper;
		}
	
		/**
		 * 根据订单id获取退货订单信息
		 * @param orderId
		 * @return
		 * @throws UhomeStoreException
		 */
		@Override
		public List<OrderReturn> getOrderReturnById(Integer orderId) throws UhomeStoreException {
			// TODO 多次退货怎么解决
			List<OrderReturn> orderReturns=orderReturnMapper.getOrderReturnById(orderId);
			if(orderReturns!=null && orderReturns.size() > 0){
				for(OrderReturn or:orderReturns){
					BigDecimal refundAmount = new BigDecimal(0);//应退金额
					List<OrderReturnItem> items=orderReturnMapper.listOrderReturnItems(or.getOrderReturnId());
					List<Short> itemStatuses = new ArrayList<Short>();
					for(OrderReturnItem returnItem : items){
						itemStatuses.add(returnItem.getStatus());
						/*if(returnItem.getStatus().equals(OrderReturnItem.STATUS_TAKE_GOODS)){
							//如果一个退货订单中  有一个退货订单项是 未审核 或者 一个审核通过的  且退货付款表中  退款状态是 未退款 后台 管理员 确认退货按钮不显示
							or.setReturnDisplay(true);
						}*/
						OrderItem item = returnItem.getOrderItem();
						ProductSku sku = productSkuMapper.getProductSkuById(item.getProductSkuId());
						//添加产品ProductSkuOptionValue
						List<ProductSkuOptionValue> productSkuOptionValues = productSkuOptionValueMapper.listProductSkuOptionValuesByProductSkuId(item.getProductSkuId());
						sku.setProductSkuOptionValues(productSkuOptionValues);
					    item.setProductSku(sku);
					    //如果不是审核未通过，合并应退金额
					    if(!returnItem.getStatus().equals(OrderReturnItem.STATUS_REFUSEED)){
					    	refundAmount = refundAmount.add(returnItem.getRefundAmount());
					    }
					}
					or.setRefundAmount(refundAmount.toString());
					or.setReturnItems(items);
					
					//判断管理员后台订单页面是否显示确认退款按扭
					if(itemStatuses.contains(OrderReturnItem.STATUS_TAKE_GOODS) 
							&& !itemStatuses.contains(OrderReturnItem.STATUS_NOTAUDIT)
							&& !itemStatuses.contains(OrderReturnItem.STATUS_ACCEPT)){
						or.setReturnDisplay(true);
					}
				}
			}
			return orderReturns;
		}
		
		
		/**根据orderPage 查询得到退货订单明细
		 * @param orderPage
		 * @return
		 * @throws UhomeStoreException
		 */
		public List<OrderReturn> getOrderReturnById(BasePagination<OrderHead> orderPage) throws UhomeStoreException {
			Map<String, Object> searchParams = orderPage.getSearchParamsMap();
			// TODO 多次退货怎么解决
			Object obj = searchParams.get("status").toString();
			Integer status = 0 ;
			if(obj!=null&&!obj.equals("")){
				status = Integer.parseInt(obj.toString());//订单状态
			}
			Integer orderId = Integer.parseInt(searchParams.get("orderId").toString());
			if(status!=6){//如果不是退货 
				return getOrderReturnById(orderId);
			}else{
				Integer returnStatus = 0;
				Object obj1 = searchParams.get("returnStatus").toString();
				if(obj1!=null&&!obj1.equals("")){
					returnStatus = Integer.parseInt(obj1.toString());
				}
				switch (returnStatus) {
					case 1:searchParams.put("index",OrderReturnItem.STATUS_NOTAUDIT);break;
					case 2:searchParams.put("index",OrderReturnItem.STATUS_ACCEPT);break;
					case 3:searchParams.put("index",OrderReturnItem.STATUS_TAKE_GOODS);break;
					case 4:searchParams.put("index",OrderReturnItem.STATUS_REFUSEED);break;
					case 5:searchParams.put("index",OrderReturnItem.STATUS_TAKE_GOODS);break;
					default:break;
				}
				//退货订单也要根据退货订单状态来获取
				List<OrderReturn> orderReturns=orderReturnMapper.getOrderReturn4Manager(searchParams);
				if(orderReturns!=null && orderReturns.size() > 0){
					for(OrderReturn or:orderReturns){
						BigDecimal refundAmount = new BigDecimal(0);//应退金额
						searchParams.put("orderReturnId", or.getOrderReturnId());
						List<OrderReturnItem> items=orderReturnMapper.listOrderReturnItems4Manager(searchParams);//更改这一步 只取根据退货订单状态的数据
						List<Short> itemStatuses = new ArrayList<Short>();
						for(OrderReturnItem returnItem : items){
							itemStatuses.add(returnItem.getStatus());
							OrderItem item = returnItem.getOrderItem();
							ProductSku sku = productSkuMapper.getProductSkuById(item.getProductSkuId());
							//添加产品ProductSkuOptionValue
							List<ProductSkuOptionValue> productSkuOptionValues = productSkuOptionValueMapper.listProductSkuOptionValuesByProductSkuId(item.getProductSkuId());
							sku.setProductSkuOptionValues(productSkuOptionValues);
						    item.setProductSku(sku);
						    //如果不是审核未通过，合并应退金额
						    if(!returnItem.getStatus().equals(OrderReturnItem.STATUS_REFUSEED)){
						    	refundAmount = refundAmount.add(returnItem.getRefundAmount());
						    }
						}
						or.setRefundAmount(refundAmount.toString());
						or.setReturnItems(items);
						//判断管理员后台订单页面是否显示确认退款按扭
						if(itemStatuses.contains(OrderReturnItem.STATUS_TAKE_GOODS) 
								&& !itemStatuses.contains(OrderReturnItem.STATUS_NOTAUDIT)
								&& !itemStatuses.contains(OrderReturnItem.STATUS_ACCEPT)){
							or.setReturnDisplay(true);
						}
					}
				}	
				return orderReturns;
			}
		}
		/**
		 * 修改退货订单状态
		 * @param orderReturn
		 * @return
		 * @throws UhomeStoreException
		 */
		public Integer updateStatus(OrderReturn orderReturn)  throws UhomeStoreException{
			
			return orderReturnMapper.updateStatus(orderReturn);
		}
	
	/**
	 * 根据orderReturn 添加相应的退货评价
	 */
		@Override
		public void saveOrderReturn(OrderReturn orderReturn)throws UhomeStoreException {
			orderReturnMapper.add(orderReturn);
		}
	/***
	 * 查找当前用户底下所有的已退货的退货物品
	 */
	@Override
	public void getMyReturnOrders(BasePagination<OrderReturn> orderHead ) throws UhomeStoreException {
		Map<String, Object> searchParams = orderHead.getSearchParamsMap();
		searchParams.put(CommonConstants.PAGE_SORT, "ordr.createTime");
		searchParams.put(CommonConstants.PAGE_DIR, "desc");
		Object endTime = searchParams.get(CommonConstants.ENDTIME);
		if(endTime!=null){
			searchParams.put(CommonConstants.ENDTIME,endTime.toString()+CommonConstants.ENDTIME_VALUE);
		}
		Collection<OrderReturn> result = orderReturnMapper.searchReturnOrders(searchParams);
		if (orderHead.isNeedSetTotal()) {
			Integer total = orderReturnMapper.searchReturnOrdersCount(searchParams);
			orderHead.setTotal(total);
		}
		if (result != null) {
			for(OrderReturn order:result){
				// 获取退货订单明细
				List<OrderReturnItem> orderItems = orderReturnItemMapper.listOrderReturnItemsByOrderReturnId(order.getOrderReturnId());
				if(orderItems != null){
					order.setReturnItems(orderItems);
					for(OrderReturnItem item : orderItems){
						if(OrderReturnItem.STATUS_ACCEPT.equals(item.getStatus())){
							order.setIsAgree("1");
						}
						OrderItem oitem = item.getOrderItem();
						ProductSku sku = productSkuMapper.getProductSkuById(oitem.getProductSkuId());
						//添加产品ProductSkuOptionValue
						List<ProductSkuOptionValue> productSkuOptionValues = productSkuOptionValueMapper.listProductSkuOptionValuesByProductSkuId(oitem.getProductSkuId());
						sku.setProductSkuOptionValues(productSkuOptionValues);
						oitem.setProductSku(sku);
					}   
				}
			}
		}
		orderHead.setResult(result);
	}
	
	@Override
	public OrderReturn getOrderReturnByOrderReturnId(Integer orderReturnId) throws UhomeStoreException {
			OrderReturn orderReturn = orderReturnMapper.getOrderReturnByReturnId(orderReturnId);
			if(orderReturn==null){
				logger.error("===getOrderReturnByOrderReturnId()===退货信息不存在！退货单ID：{}",orderReturnId);
				throw new UhomeStoreException("退货信息不存在！");
			}
			Integer userId = null;
			try{
			 userId = userService.getCurrentUser().getUserId();
			 if(!userId.equals(orderReturn.getOrderHead().getUserId())){
				logger.error("===getOrderReturnByOrderReturnId()===查询的订单不属于当前登录用户！订单号：{}",orderReturn.getOrderHead().getOrderNo());
				throw new UhomeStoreException("查询退货信息异常！");
			 }
			}catch(YtoxlUserException e){
				logger.error("===getOrderReturnByOrderReturnId()===获取当前用户信息异常：{}",e.getMessage());
				throw new UhomeStoreException("获取登录用户信息异常！");
			}
			ReceiverAddress address = orderReturn.getAddress();
			//获取订单发运信息
			OrderExpress express = orderExpressMapper.getOrderExpressById(orderReturn.getOrderId());
			//如果地址已经被二货用户删除
			if(null == address){
				address = new ReceiverAddress();
				BeanUtils.copyProperties(express, address);
				orderReturn.setAddress(address);
			}
			Region region = null;
			if(address!=null){
				region = regionMapper.getDetailInfoByRegionId(address.getRegionId());
				address.setRegion(region);
			}
			// 获取退货订单明细
			List<OrderReturnItem> orderItems = orderReturnItemMapper.listOrderReturnItemsByOrderReturnId(orderReturn.getOrderReturnId());
			if(orderItems != null){
				orderReturn.setReturnItems(orderItems);
				for(OrderReturnItem item : orderItems){
					OrderItem oitem = item.getOrderItem();
					ProductSku sku = productSkuMapper.getProductSkuById(oitem.getProductSkuId());
					//添加产品ProductSkuOptionValue
					List<ProductSkuOptionValue> productSkuOptionValues = productSkuOptionValueMapper.listProductSkuOptionValuesByProductSkuId(oitem.getProductSkuId());
					sku.setProductSkuOptionValues(productSkuOptionValues);
					oitem.setProductSku(sku);
				}   
			}
			return orderReturn;
	}
	
	@Override
	public List<OrderItem> getOrderReturnByOrderId(Integer orderId)
			throws UhomeStoreException {
		return null;
	}
	
	
	@Override
	public OrderReturn getOrderItemReturn(Integer orderItemId)
			throws UhomeStoreException {
		return orderReturnMapper.getOrderItemReturn(orderItemId);
	}
	
	@Override
	public void saveOrderReturnItem(OrderReturnItem OrderReturnItem) throws UhomeStoreException {
		orderReturnItemMapper.add(OrderReturnItem);
	}
	
	//添加退货信息
	@Override
	public void saveOrderReturnModel(OrderReturn orderReturn) throws UhomeStoreException{
		List<OrderReturnItem> oriList =  orderReturn.getReturnItems();
		if(oriList == null||oriList.size()<1){
			logger.error("===saveOrderReturnModel()===退货商品不存在！");
			throw new UhomeStoreException("请选择退货商品！");
		}
		Integer userId = null;
		try{
		 userId = userService.getCurrentUser().getUserId();
		}catch(YtoxlUserException e){
			logger.error("===saveOrderReturnModel()===获取当前用户信息异常：{}",e.getMessage());
			throw new UhomeStoreException("获取登录用户信息异常！");
		}
		//检查具体退货信息是否合理
		for(OrderReturnItem ori:oriList){
			Integer orderItemId = orderItemMapper.checkOrderItem(userId, ori.getOrderItemId());
			if(orderItemId==null){
				logger.error("===saveOrderReturnModel()===检查退货信息与用户不匹配！");
				throw new UhomeStoreException("订单异常！");
			}
			//可退数量
			Integer ableReturnNum = orderItemMapper.getOrderItemReturnNumById(orderItemId);
			if(ableReturnNum==null||ableReturnNum<ori.getNum()){
				logger.error("===saveOrderReturnModel()===订单数量异常！");
				throw new UhomeStoreException("订单数量异常！");
			}
		}
		
		saveOrderReturn(orderReturn);
		for(OrderReturnItem returnItem : oriList){
			returnItem.setOrderReturnId(orderReturn.getOrderReturnId());
			if(returnItem.getRefundAmount() == null)
				returnItem.setRefundAmount(getRefundAmount(orderItemMapper.get(returnItem.getOrderItemId()), returnItem.getNum()));
			saveOrderReturnItem(returnItem);
		}
		updateStatusByOrderId(orderReturn.getOrderId());
	}
	
	@Override
	public BigDecimal getRefundAmount(OrderItem orderItem,Integer returnNum) throws UhomeStoreException{
		if(orderItem==null||returnNum==null){
			logger.error("===getRefundAmount()===参数异常！");
			throw new UhomeStoreException("参数异常！");
		}
		BigDecimal refundAmount = new BigDecimal(0);
		BigDecimal newReturnNum = new BigDecimal(returnNum);//实退数量
		//1.通过 orderItemId 查询此orderItem已经退货的数量rNum
		Integer rNum = orderReturnItemMapper.getOrderReturnItemsByOrderItemId(orderItem.getOrderItemId());
		MathContext mc = new MathContext(5, RoundingMode.HALF_UP); 
		//2.判断rNum==orderItem.num 最后一次退货
		if(orderItem.getNum().equals(rNum+returnNum)){
			//未使用优惠券
			if(orderItem.getRebatePrice() == null){
				refundAmount = orderItem.getClosingCost().multiply(newReturnNum);
			}else{
				//该orderitem已经退过的钱的数量
				BigDecimal returnedPaymentAmount = orderReturnItemMapper.getReturnedOrderItemSumPaymentAmount(orderItem.getOrderItemId());
				if(returnedPaymentAmount!=null){
					refundAmount =orderItem.getRebatePrice().subtract(returnedPaymentAmount);
				}else{//第一次全不退货
					refundAmount =orderItem.getRebatePrice();
				}
			}
		}else{
			//未使用优惠券时，直接返回成交价*数量
			if(orderItem.getRebatePrice() == null){
				refundAmount = orderItem.getClosingCost().multiply(newReturnNum);
			}else{
				refundAmount = orderItem.getRebatePrice().divide(new BigDecimal(orderItem.getNum()), mc).multiply(newReturnNum);
			}
		}
		return refundAmount;
	}
	
	@Override
	public void updateStatusByOrderId(Integer orderId) throws UhomeStoreException {
		//根据订单来更新状态 updateStatus
		OrderHead order = orderService.getOrderById(orderId);
		Short status = orderService.judgeOrderReturnStatus(order);
		if((OrderHead.STATUS_RETURN.equals(order.getStatus()) ||OrderHead.STATUS_FINISHED.equals(order.getStatus())||OrderHead.STATUS_SEND.equals(order.getStatus()) ) && OrderHead.STATUS_ALLOW_RETURN.equals(status)){
			order.setStatus(OrderHead.STATUS_RETURN);
		}
		if((OrderHead.STATUS_RETURN.equals(order.getStatus()) ||OrderHead.STATUS_FINISHED.equals(order.getStatus())||OrderHead.STATUS_SEND.equals(order.getStatus()) ) && OrderHead.STATUS_FORBID_RETURN.equals(status)){
			order.setStatus(OrderHead.STATUS_ALLRETURN);
		}
		orderHeadMapper.updateStatus(order);
	}
	
	@Override
	public void addmail(OrderExpress oderexExpress)  throws UhomeStoreException{
		//重复提交数据 TODO  如果重复提交分2中情况  1.故意提交2次  ，2.一次退2件商品
		OrderExpress oe = orderExpressMapper.getOrderExpressByOrderReturnId(oderexExpress.getOrderReturnId());
		if(null == oe){
			orderExpressMapper.add(oderexExpress);
		}else{
			//update 
			orderExpressMapper.update(oderexExpress);
		}
		
	}
	
	@Override
	public OrderReturnItem getOrderReturnItemById(Integer orderReturnItemId)throws UhomeStoreException {
		return orderReturnItemMapper.get(orderReturnItemId);
	}
	
	@Override
	public OrderHead getReturnOrder(Integer userId,Integer orderId, List<Integer> orderItemIds) throws UhomeStoreException {
		OrderHead order = new OrderHead();
		order.setUserId(userId);
		order.setOrderId(orderId);
		order = orderHeadMapper.checkUserOrder(order);
		if(order == null){
			logger.error("===getReturnOrder()===获取退货订单异常！");
			throw new UhomeStoreException("获取退货订单异常！");
		}
		order.setPayment(orderPaymentMapper.getOrderPaymentByOrderId(orderId));
		//TODO:添加对此订单是否能退货的判断。
		// 获取选中的订单明细
		List<OrderItem> orderItems = orderItemMapper.listOrderItemsByIds(orderId, orderItemIds);
		if(orderItems != null){
			order.setItems(orderItems);
			for(OrderItem item : orderItems){
				//能够退的数量
				int num = orderItemMapper.getOrderItemReturnNumById(item.getOrderItemId());
				item.setAbleReturnNum(num);
				if(item.getProductSkuId() == null)
					continue;
				ProductSku sku = productSkuMapper.getProductSkuById(item.getProductSkuId());
				if(sku == null)
					continue;
				//添加产品ProductSkuOptionValue
				List<ProductSkuOptionValue> productSkuOptionValues = productSkuOptionValueMapper.listProductSkuOptionValuesByProductSkuId(item.getProductSkuId());
				sku.setProductSkuOptionValues(productSkuOptionValues);
			    item.setProductSku(sku);
			}   
		}
		return order;
	}
	
}
