package com.ytoxl.module.uhome.uhomeorder.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.CommonConstants;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.common.utils.PropertyUtil;
import com.ytoxl.module.uhome.uhomebase.dataobject.Point;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSkuOptionValue;
import com.ytoxl.module.uhome.uhomebase.dataobject.Region;
import com.ytoxl.module.uhome.uhomebase.mapper.ProductSkuMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.ProductSkuOptionValueMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.RegionMapper;
import com.ytoxl.module.uhome.uhomebase.service.PointService;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderExpress;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderHead;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderItem;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderPayment;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturn;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturnExport;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturnItem;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturnPayment;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderExpressMapper;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderHeadMapper;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderItemMapper;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderReturnItemMapper;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderReturnMapper;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderReturnPaymentMapper;
import com.ytoxl.module.uhome.uhomeorder.service.OrderService;
import com.ytoxl.module.uhome.uhomeorder.service.OrderService4Manage;

@Service
public class OrderService4ManageImpl implements OrderService4Manage {
	protected static Logger log = LoggerFactory.getLogger(OrderService.class);
	
	@Autowired
	private ProductSkuMapper<ProductSku> productSkuMapper;
	@Autowired
	private OrderHeadMapper<OrderHead> orderHeadMapper;
	@Autowired
	private OrderItemMapper<OrderItem> orderItemMapper;
	@Autowired
	private OrderExpressMapper<OrderExpress> orderExpressMapper;
	@Autowired
	private RegionMapper<Region> regionMapper;
	@Autowired
	private ProductSkuOptionValueMapper<ProductSkuOptionValue> productSkuOptionValueMapper;
	@Autowired
	private OrderReturnMapper<OrderReturn> orderReturnMapper;
	@Autowired
	private PointService pointService;
	@Autowired
	private OrderReturnItemMapper<OrderReturnItem> orderReturnItemMapper;
	@Autowired
	private OrderReturnPaymentMapper<OrderReturnPayment> orderReturnPaymentMapper;
	

	@Override
	public OrderHead getOrderById(Integer orderId) throws UhomeStoreException {
		OrderHead order = orderHeadMapper.getOrderById(orderId);
		if(order == null){
			throw new UhomeStoreException("订单相关数据不存在");
		}
		//获取订单发运信息
		OrderExpress express = orderExpressMapper.getOrderExpressById(orderId);
		/*//如果地址已经被二货用户删除
		if(null == order.getAddress()){
			ReceiverAddress address = new ReceiverAddress();
			BeanUtils.copyProperties(express, address);
			order.setAddress(address);
		}*/
		order.setOrderExpress(express);
		Region region = regionMapper.getDetailInfoByRegionId(order.getAddress().getRegionId());
		order.getAddress().setRegion(region);
		// 获取订单明细
		List<OrderItem> orderItems = orderItemMapper.listOrderItemsByOrderId(order.getOrderId());
		if(orderItems != null){
			order.setItems(orderItems);
			for(OrderItem item : orderItems){
				ProductSku sku = productSkuMapper.getProductSkuById(item.getProductSkuId());
				//添加产品ProductSkuOptionValue
				List<ProductSkuOptionValue> productSkuOptionValues = productSkuOptionValueMapper.listProductSkuOptionValuesByProductSkuId(item.getProductSkuId());
				sku.setProductSkuOptionValues(productSkuOptionValues);
			    item.setProductSku(sku);
			}   
		}
		return order;
	}

	/**
	 * 查找订单
	 * @return
	 * @throws UhomeStoreException
	 */
	@Override
	public void searchOrders(BasePagination<OrderHead> orderPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = orderPage.getSearchParamsMap();
		searchParams.put(CommonConstants.PAGE_SORT, "head.createTime");
		searchParams.put(CommonConstants.PAGE_DIR, "desc");
		
		if (orderPage.isNeedSetTotal()) {
			Integer total = orderHeadMapper.searchOrdersCount(searchParams);
			orderPage.setTotal(total);
		}
		Collection<OrderHead> result = orderHeadMapper.searchOrders(searchParams);
		if (result != null) {
			for(OrderHead order:result){
				// 获取订单明细
				List<OrderItem> orderItems = orderItemMapper.listOrderItemsByOrderId(order.getOrderId());
				if(orderItems != null){
					order.setItems(orderItems);
				}
			}
		}
		orderPage.setResult(result);
	}
	
	/**导出退货订单
	 * @param orderPage
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<OrderReturnExport> listReturnOrders(BasePagination<OrderHead> orderPage) throws UhomeStoreException{
		Map<String, Object> searchParams = orderPage.getSearchParamsMap();
		if(null != searchParams){//如果查询参数不为null
			//如果选择 退货  则显示 全部退货 和 部分退货的订单
			Object obj = searchParams.get("status");
			Object objReturn = searchParams.get("returnStatus");//得到退货订单明细的状态
			if(null != obj  && obj.toString().length() > 0){
				Short status = Short.parseShort(obj.toString());
				if(status == OrderHead.STATUS_RETURN){
					//为后台查询拼接sql条件 TODO
					searchParams.put("status", OrderHead.STATUS_RETURN+","+OrderHead.STATUS_ALLRETURN);
				}
			}
			String endTime = (String) searchParams.get(CommonConstants.ENDTIME);
			if(StringUtils.isNotEmpty(endTime)){
				searchParams.put(CommonConstants.ENDTIME,endTime+CommonConstants.ENDTIME_VALUE);
			}
			if(objReturn==null||com.ytoxl.module.core.common.utils.StringUtils.isEmpty(objReturn.toString())){//如果状态为空表示查全部
				searchParams.put("returnStatus",OrderReturnItem.STATUS_ALL);
			}else{
				Integer index = Integer.parseInt(objReturn.toString());//0 全部 1待审核 2待退货 3待退款4未通过  5已完成
					switch (index) {
				case 1:searchParams.put("index",OrderReturnItem.STATUS_NOTAUDIT);break;
				case 2:searchParams.put("index",OrderReturnItem.STATUS_ACCEPT);break;
				case 3:searchParams.put("index",OrderReturnItem.STATUS_TAKE_GOODS);break;
				case 4:searchParams.put("index",OrderReturnItem.STATUS_REFUSEED);break;
				case 5:searchParams.put("index",OrderReturnItem.STATUS_TAKE_GOODS);break;
				default:break;
				}
			}
		}
		List<OrderReturnExport> orders=orderHeadMapper.listReturnOrders(searchParams);
		if(orders!=null&&orders.size()>0){//如果查询来的退货订单部位空
			Iterator<OrderReturnExport> ite = orders.iterator();//遍历整个退货订单
			while(ite.hasNext()){
				OrderReturnExport head = ite.next();
				//移除父订单 TODO
				if(null == head.getOrderNo()){//订单号不存在就移除
					ite.remove();
					continue;
				}
				//订单明细 只能查询一个 根据itemId去查
				OrderItem items = orderItemMapper.getOrderItemByItemId(head.getItem().getOrderItemId());
				if(items!=null){
					head.setItem(items);
				}
				if(Integer.parseInt(searchParams.get("returnStatus").toString())==0){//全部
					if(head.getOrderReturn().getItem().getStatus()==OrderReturnItem.STATUS_TAKE_GOODS){//如果是已完成
						if(head.getOrderReturn().getOrderReturnPayment().getStatus()==OrderReturnPayment.STATUS_REFUND){
							head.setStatusChar(PropertyUtil.getPropertyValue("order.return.item.status."+OrderReturnItem.STATUS_FINISH, null));//已退款
						}else{
							head.setStatusChar(PropertyUtil.getPropertyValue("order.return.item.status."+OrderReturnItem.STATUS_REFUSEED, null));//未退款
						}
					}else{
						if(head.getOrderReturn().getItem().getStatus()!=3){
							head.setStatusChar(PropertyUtil.getPropertyValue("order.return.item.status."+head.getOrderReturn().getItem().getStatus(), null));//订单状态输出
						}else{
							head.setStatusChar(PropertyUtil.getPropertyValue("order.return.item.status.4", null));//订单状态输出
						}
						
					}
				}else{//指定的状态
					head.setStatusChar(PropertyUtil.getPropertyValue("order.return.item.status."+searchParams.get("returnStatus"), null));//订单状态输出
				}
				if(null != head.getPayment()){
					head.setTotalMoney(head.getPayment().getPaymentAmount().toString());
				}
				OrderExpress express = orderExpressMapper.getOrderExpressById(head.getOrderId());//订单地址
				Region region = regionMapper.getDetailInfoByRegionId(head.getAddress().getRegionId());
				if(null != region){
					StringBuffer sb=new StringBuffer();
					sb.append(region.getProvince());
					sb.append(region.getCity());
					sb.append(region.getCounty());
					sb.append(head.getAddress().getReceiveAddress());
					head.getAddress().setReceiveAddress(sb.toString());
				}
				//设置发票
				if( null != head.getPayment() && OrderPayment.HASINVOICE_ASK.equals( head.getPayment().getHasInvoice())){
					//如果title为空则为个人
					if(StringUtils.isEmpty(head.getPayment().getInvoiceTitle())){
						head.getPayment().setInvoiceTitle("个人");
					}
				}
			}
		}
		return orders;
	}
	
	
	
	
	/**
	 * 导出订单
	 * @return
	 * @throws UhomeStoreException
	 */
	@Override
	public List<OrderHead> listOrders(BasePagination<OrderHead> orderPage) throws UhomeStoreException {
		Map<String, Object> searchParams = orderPage.getSearchParamsMap();
		if(null != searchParams){
			//如果选择 退货  则显示 全部退货 和 部分退货的订单
			Object obj = searchParams.get("status");
			if(null != obj  && obj.toString().length() > 0){
				Short status = Short.parseShort(obj.toString());
				if(status == OrderHead.STATUS_RETURN){
					//为后台查询拼接sql条件 TODO
					searchParams.put("status", OrderHead.STATUS_RETURN+","+OrderHead.STATUS_ALLRETURN);
				}
			}
			String endTime = (String) searchParams.get(CommonConstants.ENDTIME);
			if(StringUtils.isNotEmpty(endTime)){
				searchParams.put(CommonConstants.ENDTIME,endTime+CommonConstants.ENDTIME_VALUE);
			}
		}
		
//		List<OrderHead> orders=orderHeadMapper.listOrders(orderPage.getSearchParamsMap());
		
		List<OrderHead> orders=orderHeadMapper.listOrders(searchParams);
		
		//如果订单头不为空 设置订单明细
		if(orders != null &&orders.size()>0){
			Iterator<OrderHead> ite = orders.iterator();
			while(ite.hasNext()){
//			for(OrderHead head : orders){
				OrderHead head = ite.next();
				//移除父订单 TODO
				if(null == head.getOrderNo()){
					ite.remove();
					continue;
				}
				//订单明细
				List<OrderItem> items = orderItemMapper.getOrderItemByOrderId(head.getOrderId());
//				String pName = "";
//				int index = 0;
//				for(OrderItem item : items){
//					//获取设置 商品属性 TODO
//					pName += item.getProductName()+",";
//					index ++;
//					//订单状态
//					item.setOrderStatus(PropertyUtil.getPropertyValue("order.status."+head.getStatus(), null));
//				}
				//设置订单明细
				head.setItems(items);
				//订单所有的商品名称用,隔开
				//head.setProductName(pName.substring(0, pName.length()-1));
//				head.setProductName(pName);
				//订单状态
				head.setStatusChar(PropertyUtil.getPropertyValue("order.status."+head.getStatus(), null));
				//如果订单为新建设置为 待付款
//				if(OrderHead.STATUS_NEW.equals(head.getStatus())){
//					head.setStatusChar("待付款");
//				}
//				head.setProductcategoryNum(index+"");
				if(null != head.getPayment()){
					head.setTotalMoney(head.getPayment().getPaymentAmount().toString());
				}
				//保存收获地址
				/*if(null != head.getAddress()){
					Region region = regionMapper.getDetailInfoByRegionId(head.getAddress().getRegionId());
					if(null != region){
						StringBuffer sb=new StringBuffer();
						sb.append(region.getProvince());
						sb.append(region.getCity());
						sb.append(region.getCounty());
						sb.append(head.getAddress().getReceiveAddress());
						head.getAddress().setReceiveAddress(sb.toString());
					}
				}*/
				
				//获取订单发运信息
				OrderExpress express = orderExpressMapper.getOrderExpressById(head.getOrderId());
				/*//如果地址已经被二货用户删除
				if(null == head.getAddress()){
					ReceiverAddress address = new ReceiverAddress();
					BeanUtils.copyProperties(express, address);
					head.setAddress(address);
				}*/
				Region region = regionMapper.getDetailInfoByRegionId(head.getAddress().getRegionId());
				if(null != region){
					StringBuffer sb=new StringBuffer();
					sb.append(region.getProvince());
					sb.append(region.getCity());
					sb.append(region.getCounty());
					sb.append(head.getAddress().getReceiveAddress());
					head.getAddress().setReceiveAddress(sb.toString());
				}
				
				//设置发票
				if( null != head.getPayment() && OrderPayment.HASINVOICE_ASK.equals( head.getPayment().getHasInvoice())){
					//如果title为空则为个人
					if(StringUtils.isEmpty(head.getPayment().getInvoiceTitle())){
						head.getPayment().setInvoiceTitle("个人");
					}
				}
			}
		}
		return orders;
	}
	/**
	 * 批量发货
	 * @return
	 * @throws UhomeStoreException
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public void batchUpload(List<String[]> excels, Integer userId) throws UhomeStoreException {
		// TODO Auto-generated method stub
		for (Iterator iterator = excels.iterator(); iterator.hasNext();) {
			String[] strings = (String[]) iterator.next();
			if(strings.length!=3){
				throw new UhomeStoreException(strings[0]+"订单数据不完整");
			}else{
				OrderExpress oe=new OrderExpress();
				OrderHead oh=new OrderHead();
				oh.setSellerId(userId);
				
				oh.setOrderNo(strings[0]);
				
				oe.setExpressName(strings[1]);
				oe.setMailNo(strings[2]);
				
				OrderHead dboh=orderHeadMapper.getOrderHead(oh);
				if(dboh==null){
					throw new UhomeStoreException("订单号 "+strings[0]+" 不存在");
				}
				oe.setOrderId(dboh.getOrderId());
				if(dboh.getStatus()!=OrderHead.STATUS_WAITSEND){
					throw new UhomeStoreException("请确认订单 "+strings[0]+" 为待发货");
				}
				try{
					//orderExpressMapper.update(oe);
					orderExpressMapper.updateOrderExpressByOrderId(oe);
					dboh.setStatus(OrderHead.STATUS_SEND);
					orderHeadMapper.updateStatus(dboh);
					//修改销售量
					List<OrderItem> items = orderItemMapper.listItemsOrderId(oe.getOrderId());
					for(OrderItem item : items){
						Integer productSkuId = item.getProductSkuId();
						Integer num = item.getNum();
						//修改销售量
						productSkuMapper.updateSaleQualtity(productSkuId, num);
					}
				}catch(Exception e){
					throw new UhomeStoreException(strings[0]+"订单数据有问题");
				}
			}
			
		}
	}
	
	/**
	 * 确认发货
	 * @return
	 * @throws UhomeStoreException
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public String confirmSendProduct(OrderExpress orderExpress)
			throws UhomeStoreException {
		// TODO Auto-generated method stub
		OrderHead oh=orderHeadMapper.getOrderById(orderExpress.getOrderId());
		if(oh==null){
			throw new UhomeStoreException("该订单已被删除");
		}
		if(oh.getStatus()!=OrderHead.STATUS_WAITSEND){
			throw new UhomeStoreException("请确认该订单未发货");
		}
		oh.setStatus(OrderHead.STATUS_SEND);
//		orderHeadMapper.updateStatus(oh);
		orderHeadMapper.updateSendProductTimeAndOrderStatus(oh);
		//orderExpressMapper.update(orderExpress);
		orderExpressMapper.updateOrderExpressByOrderId(orderExpress);
		//TODO
		List<OrderItem> items = orderItemMapper.listItemsOrderId(orderExpress.getOrderId());
		for(OrderItem item : items){
			Integer productSkuId = item.getProductSkuId();
			Integer num = item.getNum();
			//修改销售量
			productSkuMapper.updateSaleQualtity(productSkuId, num);
		}
		return PropertyUtil.getPropertyValue("order.status."+OrderHead.STATUS_SEND, null);
	}
	
	/**
	 * 审核
	 * @return
	 * @throws UhomeStoreException
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public String audit(OrderReturnItem orderReturnItem) throws UhomeStoreException {
		// TODO Auto-generated method stub
		OrderReturnItem item = orderReturnItemMapper.get(orderReturnItem.getOrderReturnItemId());
		if(item==null){
			throw new UhomeStoreException("该退货订单项已被删除");
		}
		if( item.getStatus() != OrderReturnItem.STATUS_NOTAUDIT){
			throw new UhomeStoreException("请确认该订单未审核");
		}
		//orderReturnMapper.updateStatus(orderReturn);
		orderReturnItemMapper.updateStatus(orderReturnItem);
		return PropertyUtil.getPropertyValue("order.return.status."+orderReturnItem.getStatus(), null);
	}
	/**
	 * 同意退款
	 * @return
	 * @throws UhomeStoreException
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public String agreePayment(OrderReturnItem orderReturnItem) throws UhomeStoreException {
		orderReturnItem = orderReturnItemMapper.get(orderReturnItem.getOrderReturnItemId());
		//通过orderReturnId 查询退货付款表中是否有 记录  若有且状态是未退款则更改退款金额
		OrderReturnPayment orp = orderReturnPaymentMapper.getOrderReturnPaymentByOrderReturnId(orderReturnItem.getOrderReturnId());
		//根据orderItemId  查询当前退货订单的价格  计算 退款小计
		OrderItem orderItem = orderItemMapper.get(orderReturnItem.getOrderItemId());
		BigDecimal num = new BigDecimal(orderReturnItem.getNum());
		//成交价格
		BigDecimal closingCost = new BigDecimal(orderItem.getClosingCost().doubleValue());
		//使用优惠券后的折扣价格 TODO  要计算每件商品多少价格 ？？？
		//BigDecimal everyRebatePrice = new BigDecimal(orderItem.getRebatePrice().doubleValue());
		//BigDecimal everyRebatePrice =  getEveryRebatePrice(orderItem,orderReturnItem);
		// orp.setPaymentAmount(closingCost.multiply(num));
		
		if(null == orp){
			//没有记录  插入记录 
			//待退款
			orp = new OrderReturnPayment();
			orp.setPaymentAmount(orderReturnItem.getRefundAmount());
			orp.setClosingCostAmount(closingCost.multiply(num));
			orp.setOrderReturnId(orderReturnItem.getOrderReturnId());
			orp.setStatus(OrderReturnPayment.STATUS_WAITREFUND);
			orderReturnPaymentMapper.add(orp);
			//同时更新 order_return_item表
			//更新状态已收货 
			orderReturnItem.setStatus(OrderReturnItem.STATUS_TAKE_GOODS);
			orderReturnItemMapper.updateStatus(orderReturnItem);
		}else if(orp.getStatus().equals(OrderReturnPayment.STATUS_WAITREFUND)){
			//有记录且状态是未退款则更改退款金额 
			//查询金额
			BigDecimal paymentAmount = orp.getPaymentAmount();
			BigDecimal closingCostAmount = orp.getClosingCostAmount();
			//将退款金额 相加
			paymentAmount = paymentAmount.add(orderReturnItem.getRefundAmount());
			closingCostAmount = closingCostAmount.add(closingCost.multiply(num)); 
			orp.setClosingCostAmount(closingCostAmount);
			orp.setPaymentAmount(paymentAmount);
			orderReturnPaymentMapper.updateOrderReturnPayAmount(orp);
			//同时更新 order_return_item表
			//已经收到货了 更新成
			orderReturnItem.setStatus(OrderReturnItem.STATUS_TAKE_GOODS);
			orderReturnItemMapper.updateStatus(orderReturnItem);
		}else if(orp.getStatus().equals(OrderReturnPayment.STATUS_REFUND)){
			//有记录 状态不是未退款
			throw new UhomeStoreException("请确认该退货订单是否已经退款");
		}
		return "同意退款";
	}
	
	/**
	 * 通过orderItem计算每一个商品的价格  rebatePrice/num  最后一次退货用减法
	 * @param orderItem
	 * @return
	 */
	@Override
	public BigDecimal getEveryRebatePrice(OrderItem orderItem,OrderReturnItem orderReturnItem) throws UhomeStoreException{
		if(orderItem.getRebatePrice() == null){
			//未使用优惠券时，直接返回成交价
			return orderItem.getClosingCost();
		}
		BigDecimal everyRebatePrice = new BigDecimal(0);
		//1.通过 orderItemId 查询此orderItem已经退货的数量rNum
		Integer rNum = orderReturnItemMapper.getOrderReturnItemsByOrderItemId(orderItem.getOrderItemId());
//		Integer rNum = new Integer(0);
//		for(OrderReturnItem item : rItems){
//			rNum += item.getNum();
//		}
		
		MathContext mc = new MathContext(5, RoundingMode.HALF_UP);
		if(rNum.equals(orderItem.getNum())){
			//3.若是最后一次退货
			BigDecimal rate = orderItem.getRebatePrice().divide(new BigDecimal(orderItem.getNum()), mc);
			//其它退货订单项商品数量
			BigDecimal num = new BigDecimal(orderItem.getNum() - orderReturnItem.getNum());
			//该退货订单项商品数量
			BigDecimal currReturnNum = new BigDecimal(orderReturnItem.getNum());
			BigDecimal currRebatePrice = orderItem.getRebatePrice().subtract(rate.multiply(num));
			everyRebatePrice = currRebatePrice.divide(currReturnNum, mc);
		}else{
			//4.若不是最后一个退货 
			everyRebatePrice = orderItem.getRebatePrice().divide(new BigDecimal(orderItem.getNum()), mc);
		}
		
		return everyRebatePrice;
	}
	
	/**
	 * 确认退款
	 * @param orderPayment
	 * @return
	 * @throws UhomeStoreException
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public String confirmPayment (OrderReturnPayment orderReturnPayment) throws UhomeStoreException{
		OrderReturnPayment orp=orderReturnPaymentMapper.get(orderReturnPayment.getOrderReturnPaymentId());
		if(orp.getStatus()!=OrderReturnPayment.STATUS_WAITREFUND){
			throw new UhomeStoreException("请确认该订单是否已经退款");
		}
		orderReturnPaymentMapper.updateStatus(orderReturnPayment);
		
		orderReturnPayment = orderReturnPaymentMapper.get(orderReturnPayment.getOrderReturnPaymentId());
		//TODO 更新积分   获取买家userId  通过order_head 表
		OrderReturn orderReturn = orderReturnMapper.get(orderReturnPayment.getOrderReturnId());
		OrderHead orderHead = orderHeadMapper.get(orderReturn.getOrderId());
		pointService.updatePointByUserId(orderHead.getUserId(), orp.getPaymentAmount(), Point.TYPE_MINUS, Point.POINTSOURCE_RETURNORDER);
		return PropertyUtil.getPropertyValue("order.return.payment.status."+orderReturnPayment.getStatus(), null);
		
	}

	/* (non-Javadoc)
	 * @see com.ytoxl.module.uhome.uhomeorder.service.OrderService4Manage#searchOrders4Manager(com.ytoxl.module.core.common.pagination.BasePagination)
	 */
	@Override
	public void searchOrders4Manager(BasePagination<OrderHead> orderPage) throws UhomeStoreException {
		Map<String, Object> searchParams = orderPage.getSearchParamsMap();
		searchParams.put(CommonConstants.PAGE_SORT, "oh.createTime");
		searchParams.put(CommonConstants.PAGE_DIR, "desc");
		if(null != searchParams){
			//如果选择 退货  则显示 全部退货 和 部分退货的订单
			Object obj = searchParams.get("status");
			Object objReturn = searchParams.get("returnStatus");//退货订单状态
			if(null != obj  && obj.toString().length() > 0){
				Short status = Short.parseShort(obj.toString());
				if(status == OrderHead.STATUS_RETURN){//如果为退货
					//为后台查询拼接sql条件 TODO
					searchParams.put("status", OrderHead.STATUS_RETURN+","+OrderHead.STATUS_ALLRETURN);
					if(objReturn==null||com.ytoxl.module.core.common.utils.StringUtils.isEmpty(objReturn.toString())){//如果状态为空表示查全部
						searchParams.put("returnStatus",OrderReturnItem.STATUS_ALL);
					}else{
						Integer index = Integer.parseInt(objReturn.toString());//0 全部 1待审核 2待退货 3待退款4未通过  5已完成
 						switch (index) {
						case 1:searchParams.put("index",OrderReturnItem.STATUS_NOTAUDIT);break;
						case 2:searchParams.put("index",OrderReturnItem.STATUS_ACCEPT);break;
						case 3:searchParams.put("index",OrderReturnItem.STATUS_TAKE_GOODS);break;
						case 4:searchParams.put("index",OrderReturnItem.STATUS_REFUSEED);break;
						case 5:searchParams.put("index",OrderReturnItem.STATUS_TAKE_GOODS);break;
						default:break;
						}
					}
					if (orderPage.isNeedSetTotal()) {
						//首先我必须查询所有满足退货订单状态的 总数
						Integer total = orderReturnMapper.searchOrdersReturn4ManagerCount(searchParams);
						orderPage.setTotal(total);
					}
					//获得所有订单id集合  里面带有退货订单的退货id 
					Collection<OrderHead> result = orderReturnMapper.searchOrdersReturn4Manager(searchParams);
					orderPage.setResult(result);
					return;
				}
			}
		}
		if (orderPage.isNeedSetTotal()) {
			Integer total = orderHeadMapper.searchOrders4ManagerCount(searchParams);
			orderPage.setTotal(total);
		}
		//去重复
//		List<OrderHead> list = orderHeadMapper.searchOrders4Manager(searchParams);
//		HashSet<OrderHead> set = new HashSet<OrderHead>();
//		set.addAll(list);
		Collection<OrderHead> result = orderHeadMapper.searchOrders4Manager(searchParams);
		if (result != null) {
			for(OrderHead order:result){
				/*//如果地址已经被二货用户删除
				if(null == order.getAddress()){
					OrderExpress express = orderExpressMapper.getOrderExpressById(order.getOrderId());
					ReceiverAddress address = new ReceiverAddress();
					BeanUtils.copyProperties(express, address);
					order.setAddress(address);
				}*/
				
				//退货订单
				List<OrderReturn> orderReturns = orderReturnMapper.getOrderReturnById(order.getOrderId());
				for(OrderReturn or : orderReturns){
					//新增一个方法 根据退货订单状态与退货订单id查询 
					List<OrderReturnItem> items = orderReturnMapper.listOrderReturnItems(or.getOrderReturnId());
					or.setReturnItems(items);
				}
				order.setOrderReturns(orderReturns);
				
/*				//如果有退货订单 获取退货订单明细
				OrderReturn orderReturn = order.getOrderReturn(); 
				if(null != orderReturn){
					int orid = order.getOrderReturn().getOrderReturnId();
					orderReturn.setReturnItems(orderReturnItemMapper.listOrderReturnItemsByOrderReturnId(orid));
					for(OrderReturnItem item : orderReturn.getReturnItems()){
						if(item.getStatus() == OrderReturnItem.STATUS_NOTAUDIT){
							//如果有订单项未审核  此退货订单显示   退货审核
							orderReturn.setStatus(OrderReturn.STATUS_NOTAUDIT);
							break;
						}
//						if(item.getStatus() == OrderReturnItem.STATUS_REFUSEED){
//							//如果有订单项未通过审核  此退货订单显示   部分退货
//							orderReturn.setStatus(OrderReturn.STATUS_SOME_RETURN);
//							break;
//						}
//						if(item.getStatus() == OrderReturnItem.STATUS_REFUSEED){
//							//如果有订单项未通过审核  此退货订单显示   部分退货
//						}
					}
					//orderReturn.setStatus(orderService.judgeOrderReturnStatus(order));
				}*/
				// 获取订单明细
				List<OrderItem> orderItems = orderItemMapper.listOrderItemsByOrderId(order.getOrderId());
				if(orderItems != null){
					order.setItems(orderItems);
				}
			}
		}
		orderPage.setResult(result);
	}
	
}
