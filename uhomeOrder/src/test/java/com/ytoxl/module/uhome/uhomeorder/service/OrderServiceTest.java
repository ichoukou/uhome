package com.ytoxl.module.uhome.uhomeorder.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.utils.DateUtil;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.service.ProductService;
import com.ytoxl.module.uhome.uhomeorder.BaseTest;
import com.ytoxl.module.uhome.uhomeorder.alipay.util.AlipayCore;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderHead;
import com.ytoxl.module.uhome.uhomeorder.dataobject.resultmap.ShoppingCart;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderHeadMapper;

public class OrderServiceTest extends BaseTest {
	@Autowired
	private OrderService4Timer orderService4Timer;
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderHeadMapper<OrderHead> orderHeadMapper;
	@Test
	public void testaddToShopCart() {
/*		CartItem cart = new CartItem();
		cart.setProductSkuId(10000);
		cart.setNum(1);
		CartItem cart1 = new CartItem();
		cart1.setProductSkuId(20000);
		cart1.setNum(2);
		List<CartItem> list = new ArrayList<CartItem>();
		list.add(cart);
		list.add(cart1);*/
		JSONObject map = new JSONObject();
		map.put(2012, 5);
		map.put(2013, 6);
		ShoppingCart carts = new ShoppingCart();
//		carts.setCartItems(map);
//		carts.setCartItems(list);
		JSON json = JSONSerializer.toJSON(carts);
		log.info(json.toString());
		
	}
	@Test
	public void testgetCartProduct(){
		String json = "{\"cartItems\":{\"100000\":4},\"totalNum\":4}";
		JSONObject object = JSONObject.fromObject(json);
		ShoppingCart cart = (ShoppingCart)JSONObject.toBean(object, ShoppingCart.class);
//		if(cart.getCartItems().containsKey("100000")){
//			log.info("================-----------------==================");
//		}
//		log.info("获取购物车里的产品数量：============="+cart.getCartItems().keySet());
	}
	
	@Test
	public void testUpdateStatusToCanceled(){
		Integer count = 0;
		try {
			count = orderService4Timer.updateStatusToCanceled();
		} catch (UhomeStoreException e) {
			log.info("更新订单数量：============="+count);
		}
	}
	
	@Test
	public void testIsPlanByProductSkuId(){
		boolean active;
		try {
			active = productService.isPlanByProductSkuId(100013);
			log.info("当前sku状态：============="+active);
		} catch (UhomeStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		String orderIds = "1|2|3";
		String[] strArr = orderIds.split("\\|");
		System.out.println(Arrays.toString(strArr));
		
		Map<String,String> params = new HashMap<String,String>();
		String str = "buyer_id=2088002078953505, trade_no=2013081927166050, body=玉髓手镯 平步青云(2)orderNo:[13081900007], use_coupon=N, notify_time=2013-08-19 11:17:43, subject=特卖商品支付, sign_type=MD5, is_total_fee_adjust=N, notify_type=trade_status_sync, out_trade_no=QR+EcZdQY1F1PCG27K43IA==, gmt_payment=2013-08-19 11:13:35, trade_status=TRADE_SUCCESS, discount=0.00, sign=bd788754d79d9085d7ac230462e6f16f, gmt_create=2013-08-19 11:12:45, buyer_email=sss30002003@126.com, price=1.00, total_fee=1.00, seller_id=2088701753830358, quantity=1, seller_email=ytoxl@ytoxl.com, notify_id=7b874a6b7cfbecefe9cc5b3b5385a7a34s, payment_type=1";
		String arr[]  = str.split(",");
		for(int i=0;i<arr.length;i++){
			String arrTemp[] = arr[i].split("=");
			System.out.println(arrTemp[0].trim()+"======"+arrTemp[1].trim());
			params.put(arrTemp[0].trim(), arrTemp[1].trim());
		}
		System.out.println(AlipayCore.buildMysign(AlipayCore.paraFilter(params))+"======="+params.get("sign")+"==="+arr.length);
	}

	@Test
	public void testCouponNo(){
		String couponNo;
		try {
			couponNo = Long.valueOf(String.valueOf(System.currentTimeMillis())).toString();
			log.info("当前号码状态：============="+couponNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testAliNotice(){
		
		String trade_no = DateUtil.toShortDay(new Date())+System.currentTimeMillis();
		String orderNo = "13081600002";//要付款的订单号13081200001|13081200002
		String orderNos[] = orderNo.split("\\|");
		Double tempValue = 0d;
		for(String no:orderNos){
			OrderHead order = orderHeadMapper.getOrderHeadByOrderNo(no);
			tempValue += order.getTotalPrice().doubleValue();
		}
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("trade_no",trade_no); // 支付宝交易号
		params.put("out_trade_no",""); // 获取订单号
		params.put("total_fee",String.valueOf(tempValue)); // 获取总金额
		params.put("subject","");// 商品名称、订单名称
		params.put("body","xxxxx商品orderNo:["+orderNo+"]");// 商品描述、订单备注、描述
		params.put("buyer_email","payment@ytoxl.com"); // 买家支付宝账号
		params.put("buyer_id","2088888888888888"); // 买家支付宝账号
		params.put("trade_status","TRADE_SUCCESS"); // 交易状态
		
		params.put("sign", AlipayCore.buildMysign(AlipayCore.paraFilter(params)));//设置验证
		
		try {
			orderService.alipayNotify(params);
			System.out.println("success");
		} catch (UhomeStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
