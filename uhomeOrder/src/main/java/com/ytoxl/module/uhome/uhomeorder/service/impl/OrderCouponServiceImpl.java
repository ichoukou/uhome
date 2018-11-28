package com.ytoxl.module.uhome.uhomeorder.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.CouponConfig;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku;
import com.ytoxl.module.uhome.uhomebase.dataobject.UserCoupon;
import com.ytoxl.module.uhome.uhomebase.service.UserCouponService;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderHead;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderItem;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderPayment;
import com.ytoxl.module.uhome.uhomeorder.dataobject.tbl.OrderPostage;
import com.ytoxl.module.uhome.uhomeorder.service.OrderCouponService;

@Service
public class OrderCouponServiceImpl implements OrderCouponService {

	@Autowired
	private UserCouponService userCouponService;
	
	@Override
	public Boolean getActualCost(UserCoupon userCoupon, List<OrderHead> orders) throws UhomeStoreException {
		boolean isAllCoupon = false;//是否全部用优惠券支付
		
		//TODO:现在修改订单生成逻辑，后期实现折扣算法
		BigDecimal paymentAmount = new BigDecimal(0); //订单总额
		BigDecimal actualReduceMoney = new BigDecimal(0); //抵扣优惠券后的总额
		BigDecimal zeroMoney = new BigDecimal(0);
		Integer size = orders.size();
		//计算总金额
		for(OrderHead orderHead : orders){
			OrderPayment payment = orderHead.getPayment();
			OrderPostage orderPostage = orderHead.getOrderPostage();
			BigDecimal totlePriceNotContentPostage = payment.getClosingCostAmount().subtract(orderPostage.getPostage());
			paymentAmount = paymentAmount.add(totlePriceNotContentPostage);
		}
		//最低消费金额为0 或者 订单总额大于最低消费金额
		if(userCoupon.getCouponConfig().getLimitCharge().compareTo(zeroMoney)<=0 || paymentAmount.compareTo(userCoupon.getCouponConfig().getLimitCharge())  >=0){
			actualReduceMoney = paymentAmount.subtract(userCoupon.getCouponConfig().getAllowance());
		}else{
			throw new UhomeStoreException("消费金额小于最低使用额度！");
		}
		//计算每个订单平摊后的实际金额
		BigDecimal dutchTotalMoney = new BigDecimal(0);
		BigDecimal dutchMoney = new BigDecimal(0);
		for(int i = 0 ; i < size ;i++){
			OrderHead order = orders.get(i);
			OrderPostage postage = order.getOrderPostage();
			OrderPayment curOP = order.getPayment();
			BigDecimal notContentPostage = curOP.getClosingCostAmount().subtract(postage.getPostage());
			//如果抵扣优惠券后的总额小于=0 
			if(actualReduceMoney.compareTo(zeroMoney)<=0){
				curOP.setPaymentAmount(zeroMoney);
				isAllCoupon = true;
			}else{
				MathContext mc = new MathContext(5, RoundingMode.HALF_UP); 
				//当前子订单占总额比例
				BigDecimal rate = ((notContentPostage).divide(paymentAmount,mc));
				//判断是否最后一个订单支付信息，最后一个使用做差求均摊,防止金额因为除不尽丢失
				if(i ==  size - 1){
					BigDecimal last = actualReduceMoney.subtract(dutchTotalMoney);
					last = last.add(postage.getPostage());//加上运费
					curOP.setPaymentAmount(last);
				}else{
					//当前子订单应减去多少钱
					dutchMoney = actualReduceMoney.multiply(rate).setScale(2,BigDecimal.ROUND_HALF_UP);
					dutchTotalMoney = dutchTotalMoney.add(dutchMoney);
					dutchMoney = dutchMoney.add(postage.getPostage());//加上运费
					curOP.setPaymentAmount(dutchMoney);
				}
			}
		}
		
		return isAllCoupon;
	}

	@Override
	@Deprecated
	public void getOrderItemActualPrice(OrderPayment orderPayment, List<OrderItem> orderItems) throws UhomeStoreException {
		//计算订单中每个sku平摊后的实际总金额，ps：不是折扣后的单价哦，亲
		BigDecimal dutchTotalMoney = new BigDecimal(0);
		BigDecimal dutchMoney = new BigDecimal(0);
		BigDecimal zeroMoney = new BigDecimal(0);
		//实际支付金额
		BigDecimal paymentAmount = orderPayment.getPaymentAmount();
		Integer size = orderItems.size();
		for(int i = 0 ; i < size ;i++){
			//如果实际支付总额小于=0 
			if(paymentAmount.compareTo(zeroMoney)<=0){
				orderItems.get(i).setRebatePrice(zeroMoney);
			}else{
				MathContext mc = new MathContext(5, RoundingMode.HALF_UP); 
				BigDecimal rate = orderItems.get(i).getClosingCost().multiply(new BigDecimal(orderItems.get(i).getNum())).divide(orderPayment.getClosingCostAmount(),mc);
				//判断是否最后一个订单支付信息，最后一个使用做差求均摊,防止金额因为除不尽丢失
				if(i ==  size - 1){
					//orderItems.get(i).setRebatePrice(dutchTotalMoney.subtract(dutchMoney));
					orderItems.get(i).setRebatePrice(paymentAmount.subtract(dutchTotalMoney));
				}else{
					dutchMoney = paymentAmount.multiply(rate).setScale(2,BigDecimal.ROUND_HALF_UP);;
					dutchTotalMoney = dutchTotalMoney.add(dutchMoney);
					orderItems.get(i).setRebatePrice(dutchMoney);
				}
			}
		}
	}

	@Override
	public void getOrderItemActualPrice(OrderHead orderHead) throws UhomeStoreException {
		//计算订单中每个sku平摊后的实际总金额，ps：不是折扣后的单价哦，亲
		OrderPayment orderPayment = orderHead.getPayment();
		List<OrderItem> orderItems = orderHead.getItems();
		OrderPostage orderPostage = orderHead.getOrderPostage();
		BigDecimal dutchTotalMoney = new BigDecimal(0);
		BigDecimal dutchMoney = new BigDecimal(0);
		BigDecimal zeroMoney = new BigDecimal(0);
		//实际支付金额
		BigDecimal paymentAmount = orderPayment.getPaymentAmount();
		BigDecimal paymentAmountNotContentPostage = paymentAmount.subtract(orderPostage.getPostage());//订单总额不包含运费部分
		BigDecimal closingCostAmountNotContentPostage  = orderPayment.getClosingCostAmount().subtract(orderPostage.getPostage());
		Integer size = orderItems.size();
		for(int i = 0 ; i < size ;i++){
			OrderItem orderItem = orderItems.get(i);
			//如果实际支付总额小于=0 
			if(paymentAmount.compareTo(zeroMoney)<=0){
				orderItem.setRebatePrice(zeroMoney);
			}else{
				MathContext mc = new MathContext(5, RoundingMode.HALF_UP); 
				BigDecimal rate = orderItem.getClosingCost().multiply(new BigDecimal(orderItem.getNum())).divide(closingCostAmountNotContentPostage,mc);
				//判断是否最后一个订单支付信息，最后一个使用做差求均摊,防止金额因为除不尽丢失
				if(i ==  size - 1){
					//orderItems.get(i).setRebatePrice(dutchTotalMoney.subtract(dutchMoney));
					orderItems.get(i).setRebatePrice(paymentAmountNotContentPostage.subtract(dutchTotalMoney));
				}else{
					dutchMoney = paymentAmountNotContentPostage.multiply(rate).setScale(2,BigDecimal.ROUND_HALF_UP);;
					dutchTotalMoney = dutchTotalMoney.add(dutchMoney);
					orderItems.get(i).setRebatePrice(dutchMoney);
				}
			}
		}
	}

	@Override
	public void calCouponForOrders(List<OrderHead> orders,UserCoupon userCoupon) throws UhomeStoreException {
		List<ProductSku> allProducSkuList= new ArrayList<ProductSku>();
		List<ProductSku> joinCalProducSkuList= new ArrayList<ProductSku>();
		List<OrderItem> joinCalOrderItemList= new ArrayList<OrderItem>();
		Map<Integer,OrderItem> productSkuIdOrderItemMap = new HashMap<Integer,OrderItem>();
		for(OrderHead orderHead:orders){
			if(orderHead!=null){
				List<OrderItem> orderItemList = orderHead.getItems();
				for(OrderItem orderItem:orderItemList){
					if(orderItem!=null){
						ProductSku productSku = orderItem.getProductSku();
						if(productSku!=null){
							allProducSkuList.add(productSku);
							productSkuIdOrderItemMap.put(productSku.getProductSkuId(), orderItem);
						}
					}
				}
			}
		}
		Map<CouponConfig,List<ProductSku>> couponConfigProductSkuMap = userCouponService.getCouponContentProductSku(allProducSkuList);
		for(Iterator<CouponConfig> it=couponConfigProductSkuMap.keySet().iterator();it.hasNext();){
			CouponConfig couponConfig = it.next();
			if(couponConfig!=null){
				if(userCoupon.getCouponConfigId().intValue()==couponConfig.getCouponConfigId().intValue()){
					joinCalProducSkuList = couponConfigProductSkuMap.get(couponConfig);
				}
			}
		}
		BigDecimal allMount = new BigDecimal(0);//可以参与优惠商品的总价格
		for(ProductSku productSku:joinCalProducSkuList){
			OrderItem orderItem = productSkuIdOrderItemMap.get(productSku.getProductSkuId());
			if(orderItem!=null){
				allMount = allMount.add(orderItem.getClosingCost().multiply(new BigDecimal(orderItem.getNum())));
				joinCalOrderItemList.add(productSkuIdOrderItemMap.get(productSku.getProductSkuId()));
			}
		}
		BigDecimal couponMount = userCoupon.getCouponConfig().getAllowance();//可优惠的总价格
		BigDecimal usedTotalMoney = new BigDecimal(0);//已优惠价格
		BigDecimal tempCouponMoney = new BigDecimal(0);//优惠价格
		BigDecimal zeroMoney = new BigDecimal(0);
		Integer size = joinCalOrderItemList.size();
		for(int i = 0 ; i < size ;i++){
			OrderItem orderItem = joinCalOrderItemList.get(i);
			BigDecimal orderItemMount = orderItem.getClosingCost().multiply(new BigDecimal(orderItem.getNum()));
			MathContext mc = new MathContext(5, RoundingMode.HALF_UP); 
			BigDecimal rate = orderItemMount.divide(allMount,mc);
			BigDecimal afterCouponOrderItemMoney = new BigDecimal(0);//使用优惠劵过后的价格
			if(i ==  size - 1){
				afterCouponOrderItemMoney = orderItemMount.subtract(couponMount.subtract(usedTotalMoney));
			}else{
				tempCouponMoney = couponMount.multiply(rate).setScale(2,BigDecimal.ROUND_HALF_UP);
				afterCouponOrderItemMoney = orderItemMount.subtract(tempCouponMoney);
				usedTotalMoney = usedTotalMoney.add(tempCouponMoney);
			}
			if(afterCouponOrderItemMoney.compareTo(zeroMoney)<=0){
				orderItem.setRebatePrice(zeroMoney);
			}else{
				orderItem.setRebatePrice(afterCouponOrderItemMoney);
			}
		}
		for(OrderHead orderHead:orders){
			BigDecimal orderMount = new BigDecimal(0).add(orderHead.getOrderPostage().getPostage());
			for(OrderItem orderItem:orderHead.getItems()){
				if(orderItem.getRebatePrice()!=null){
					orderMount = orderMount.add(orderItem.getRebatePrice());
				}else{
					orderMount = orderMount.add(orderItem.getClosingCost().multiply(new BigDecimal(orderItem.getNum())));
				}
			}
			OrderPayment payment = orderHead.getPayment();
			payment.setPaymentAmount(payment.getPaymentAmount().subtract(payment.getPaymentAmount().subtract(orderMount)));
			
		}
	}
	
	
}