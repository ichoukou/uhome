package com.ytoxl.module.uhome.uhomeorder.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.core.common.utils.DateUtil;
import com.ytoxl.module.uhome.uhomebase.common.CodeConstants;
import com.ytoxl.module.uhome.uhomebase.common.CommonConstants;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.common.utils.CookieUtils;
import com.ytoxl.module.uhome.uhomebase.common.utils.PropertyUtil;
import com.ytoxl.module.uhome.uhomebase.common.utils.StringUtils;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.Event;
import com.ytoxl.module.uhome.uhomebase.dataobject.Express;
import com.ytoxl.module.uhome.uhomebase.dataobject.GlobalLog;
import com.ytoxl.module.uhome.uhomebase.dataobject.Point;
import com.ytoxl.module.uhome.uhomebase.dataobject.Product;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSkuOptionValue;
import com.ytoxl.module.uhome.uhomebase.dataobject.ReceiverAddress;
import com.ytoxl.module.uhome.uhomebase.dataobject.Region;
import com.ytoxl.module.uhome.uhomebase.dataobject.Seller;
import com.ytoxl.module.uhome.uhomebase.dataobject.UserCoupon;
import com.ytoxl.module.uhome.uhomebase.dataobject.UserInfo;
import com.ytoxl.module.uhome.uhomebase.dataobject.resultmap.ExpressMessage;
import com.ytoxl.module.uhome.uhomebase.mapper.EventMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.ProductMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.ProductSkuMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.ProductSkuOptionValueMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.RegionMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.UserCouponMapper;
import com.ytoxl.module.uhome.uhomebase.service.DocNumSystemService;
import com.ytoxl.module.uhome.uhomebase.service.ExpressService;
import com.ytoxl.module.uhome.uhomebase.service.GlobalLogService;
import com.ytoxl.module.uhome.uhomebase.service.PointService;
import com.ytoxl.module.uhome.uhomebase.service.ProductService;
import com.ytoxl.module.uhome.uhomebase.service.UserCouponService;
import com.ytoxl.module.uhome.uhomebase.service.UserInfoService;
import com.ytoxl.module.uhome.uhomeorder.alipay.config.AlipayConfig;
import com.ytoxl.module.uhome.uhomeorder.alipay.util.AlipayNotify;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderExpress;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderHead;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderItem;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderPayment;
import com.ytoxl.module.uhome.uhomeorder.dataobject.resultmap.AliPayment;
import com.ytoxl.module.uhome.uhomeorder.dataobject.resultmap.CartItem;
import com.ytoxl.module.uhome.uhomeorder.dataobject.resultmap.ShoppingCart;
import com.ytoxl.module.uhome.uhomeorder.dataobject.tbl.OrderCoupon;
import com.ytoxl.module.uhome.uhomeorder.dataobject.tbl.OrderPostage;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderCouponMapper;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderExpressMapper;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderHeadMapper;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderItemMapper;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderPaymentMapper;
import com.ytoxl.module.uhome.uhomeorder.mapper.OrderPostageMapper;
import com.ytoxl.module.uhome.uhomeorder.service.AlipayService;
import com.ytoxl.module.uhome.uhomeorder.service.OrderCouponService;
import com.ytoxl.module.uhome.uhomeorder.service.OrderPaymentService;
import com.ytoxl.module.uhome.uhomeorder.service.OrderService;
import com.ytoxl.module.uhome.uhomeorder.service.PostageCalService;
import com.ytoxl.module.user.common.exception.YtoxlUserException;
import com.ytoxl.module.user.dataobject.Urole;
import com.ytoxl.module.user.security.CustomUserDetails;
import com.ytoxl.module.user.service.UserService;

@Service
public class OrderServiceImpl implements OrderService {
	protected static Logger log = LoggerFactory.getLogger(OrderService.class);
	
	protected String filterParamReg = "[\\<\\>\\\"\\']";//正则过滤参数里面的非法字符
	
	@Autowired
	private ProductMapper<Product> productMapper;
	@Autowired
	private ProductSkuMapper<ProductSku> productSkuMapper;
	@Autowired
	private OrderHeadMapper<OrderHead> orderHeadMapper;
	@Autowired
	private OrderItemMapper<OrderItem> orderItemMapper;
	@Autowired
	private OrderPaymentMapper<OrderPayment> orderPaymentMapper;
	@Autowired
	private OrderExpressMapper<OrderExpress> orderExpressMapper;
	@Autowired
	private RegionMapper<Region> regionMapper;
	@Autowired
	private OrderPostageMapper<OrderPostage> orderPostageMapper;
	@Autowired
	private EventMapper<Event> eventMapper;
	@Autowired
	private DocNumSystemService docNumSystemService;
	@Autowired
	private ProductSkuOptionValueMapper<ProductSkuOptionValue> productSkuOptionValueMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private AlipayService alipayService;
	@Autowired
	private ProductService productService;
	@Autowired
	private PointService pointService;
	@Autowired
	private OrderPaymentService orderPaymentService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserCouponMapper<UserCoupon> userCouponMapper;
	@Autowired
	private OrderCouponService orderCouponService;
	@Autowired
	private OrderCouponMapper<OrderCoupon> orderCouponMapper;
	@Autowired
	private UserCouponService userCouponService;
	@Autowired
	GlobalLogService globalLogService;
	@Autowired
	private PostageCalService postageCalService;
	@Autowired
	private ExpressService expressService;
	
	@Value("${trade_no_pattern}")
	private String tradeNoPattern;
	@Value("${order_no_pattern}")
	private String orderNoPattern;
	@Value("${cart_default_expire_time}")
	private Integer cartDefaultExpireTime;//购物车过期时间
	
	@Override
	public ShoppingCart getShoppingCart(HttpServletRequest request,HttpServletResponse response) throws UhomeStoreException  {
		String products = CookieUtils.getCookie(request, CommonConstants.COOKIE_SHOPPINGCART);
		ShoppingCart shoppingCart = null; 
		if(StringUtils.isNotEmpty(products.trim())){
			JSONObject object = JSONObject.fromObject(products);
			shoppingCart = (ShoppingCart)JSONObject.toBean(object, ShoppingCart.class);
			//如果购物车cookie超时，删除cookie
			if(shoppingCart.getCartExpireTime()!=null&&System.currentTimeMillis()>shoppingCart.getCartExpireTime()){
				shoppingCart = null;
				CookieUtils.removeCookie(request, response, CommonConstants.COOKIE_SHOPPINGCART);     
			}
		}
		return shoppingCart;
	}
	
	@Override
	public List<Seller> getCartProducts(ShoppingCart shoppingCart, List<Integer> skuIds) throws UhomeStoreException {
		List<Seller> sellerProducts = null;
		if (shoppingCart!=null) {
			Map<String,Integer>  items = shoppingCart.getCartItems();
			List<Integer> ids = null;
			//如果传过来的skuIds为空，则显示购物车中所有货物
			if(skuIds == null){
				ids = new ArrayList<Integer>();
				for(String id :items.keySet()){
					ids.add(Integer.parseInt(id));
				}
			} else {
				ids = skuIds;
			}
			if(ids==null||ids.size()<=0)//针对{"cartItems":{},"totalNum":0}的情况进行处理
				return sellerProducts;
			List<ProductSku> productSkus = productSkuMapper.listShoppingCartProducts(ids);
			//转换成相应的list返回
			Map<String, List<ProductSku>> skuMap = new HashMap<String, List<ProductSku>>();
			for(ProductSku sku : productSkus){
				//判断商品当前状态是activity的
				boolean flag = productService.isPlanByProductSkuId(sku.getProductSkuId());
				sku.setIsActivity(flag);
				
				String company = sku.getProduct().getSeller().getCompanyName();
				sku.setNum(items.get(sku.getProductSkuId().toString()));
				
				//商品关联当前优惠的活动
				List<Event> events = eventMapper.getEventByProductSkuId(sku.getProductSkuId());
				sku.setEvents(events);
				
				if(skuMap.containsKey(company)){
					skuMap.get(company).add(sku);
				} else {
					List<ProductSku> skus = new ArrayList<ProductSku>();
					skus.add(sku);
					skuMap.put(company, skus);
				}
				//添加产品ProductSkuOptionValue
				List<ProductSkuOptionValue> productSkuOptionValues = productSkuOptionValueMapper.listProductSkuOptionValuesByProductSkuId(sku.getProductSkuId());
				sku.setProductSkuOptionValues(productSkuOptionValues);
				//添加品牌名称
				ProductSku ps = productSkuMapper.getProductSkuById(sku.getProductSkuId());
				Brand brand = sku.getProduct().getBrand();
				if(null != brand){
					sku.getProduct().getBrand().setName(ps.getProduct().getBrand().getName());
				}else{
					brand = new Brand();
					brand.setName(ps.getProduct().getBrand().getName());
					sku.getProduct().setBrand(brand);
				}
			}
			sellerProducts = new ArrayList<Seller>();
			for(String company : skuMap.keySet()){
				Seller seller = new Seller();
				seller.setCompanyName(company);
				seller.setProductSkus(skuMap.get(company));
				sellerProducts.add(seller);
			}
         }
		return sellerProducts;
	}

	@Override
	public void addProductToCart(HttpServletRequest request, HttpServletResponse response, CartItem cart) throws UhomeStoreException {
		//判断cart是否有效
		if(cart.getNum() <= 0){
			throw new UhomeStoreException("添加商品数量无效！");
		}
		//获取当前cookie里已经添加的商品
		ShoppingCart shoppingCart = getShoppingCart(request,response);
		if(shoppingCart!=null){
			//遍历之前的list，看是否存在相同的的商品，存在则数量相加
			Map<String,Integer>  map = shoppingCart.getCartItems();
			Integer skuId = cart.getProductSkuId();
			if(map.containsKey(skuId.toString())){
				//查询sku库存是否大于当前购买的数量 TODO
				Integer inventory = productSkuMapper.getProductSkuInventoryByProductSkuId(skuId);
				if(inventory<=0){
					throw new UhomeStoreException("该商品已售罄！");
				}
				Integer buyInventory = map.get(skuId.toString()) + cart.getNum();
				if(buyInventory.intValue() >= inventory.intValue()){
					buyInventory = inventory;
				}
				Integer totalNum = shoppingCart.getTotalNum() - map.get(skuId.toString()) + buyInventory;
				map.put(cart.getProductSkuId().toString(), buyInventory);
				shoppingCart.setTotalNum(totalNum);
			} else {
				map.put(cart.getProductSkuId().toString(), cart.getNum());
				shoppingCart.setTotalNum(shoppingCart.getTotalNum() + cart.getNum());
			}
		} else {
			shoppingCart = new ShoppingCart();
			JSONObject map = new JSONObject();
			map.put(cart.getProductSkuId(), cart.getNum());
			shoppingCart.setCartItems(map);
			shoppingCart.setTotalNum(cart.getNum());
		}
		shoppingCart.setCartExpireTime(com.ytoxl.module.uhome.uhomebase.common.utils.DateUtil.add(new Date(),Calendar.SECOND, cartDefaultExpireTime).getTime());
		JSONObject json = JSONObject.fromObject(shoppingCart);
		CookieUtils.addCookie(response, CommonConstants.COOKIE_SHOPPINGCART, json.toString());
	}

	@Override
	public ShoppingCart deleteProductFromCart(HttpServletRequest request, HttpServletResponse response, Integer[] productSkuId) throws UhomeStoreException {
		ShoppingCart shoppingCart = getShoppingCart(request,response);
		if(shoppingCart!=null){
			Map<String,Integer>  items = shoppingCart.getCartItems();
			//循环删除
			for(Integer skuId :productSkuId){
				if(items.containsKey(skuId.toString())){
					shoppingCart.setTotalNum(shoppingCart.getTotalNum() - items.get(skuId.toString()));
					items.remove(skuId.toString());
				} else {
					throw new UhomeStoreException("此商品已在订单中失效！");
				}
			}
			//修改本地cookie
			if(shoppingCart.getTotalNum()==0){
				shoppingCart = null;
				CookieUtils.removeCookie(request, response,  CommonConstants.COOKIE_SHOPPINGCART);
			}else{
				JSONObject json = JSONObject.fromObject(shoppingCart);
				CookieUtils.addCookie(response, CommonConstants.COOKIE_SHOPPINGCART, json.toString());
			}
		}
		return shoppingCart;
	}

	@Override
	public List<Integer> addOrders(List<OrderHead> orders,OrderPayment orderPayment, Short type) throws UhomeStoreException {
		if(orders == null || orders.size() == 0){
			throw new UhomeStoreException(CodeConstants.PARAMETER_ERROR, "提交数据有误，请核对后再提交！");
		}
		//获取当前用户
		CustomUserDetails detail = null;
		try { 
			detail = userService.getCurrentUser();
		} catch (YtoxlUserException e) {
			throw new UhomeStoreException(CodeConstants.LOGIN_TIMEOUT_ERROR, "登陆状态失效");
		}
		if(detail == null){
			throw new UhomeStoreException(CodeConstants.UNLOGIN_ERROR, "请先登陆然后购买");
		}
		//当前卖家不是管理员和卖家
		List<Urole> uroles = detail.getUroles();
		for(Urole u : uroles){
			//如果当前用户是管理员或者卖家抛出异常
			if(UserInfo.USER_ROLE_ADMIN.equals(u) || UserInfo.USER_ROLE_SELLER.equals(u)){
				throw new UhomeStoreException(CodeConstants.UNLOGIN_ERROR, "请登陆买家帐号后购买");
			}
		}
		List<Integer> orderIds = new ArrayList<Integer>();
		//循环生成订单
		Integer size = orders.size();
		for(int i = 0 ; i < size ;i++){
			OrderHead order = orders.get(i);
			//判断收货地址是否存在
			if(order.getReceiverAddressId() == null || order.getReceiverAddressId().intValue() == 0){
				throw new UhomeStoreException(CodeConstants.ORDER_ADDRESS_ERROR, "订单地址不存在");
			}
			order.setStatus(OrderHead.STATUS_NEW);
			order.setSkuNum(order.getItems().size());
			order.setUserId(detail.getUserId());
			String orderNo = docNumSystemService.getOrderNum();//生成订单号
			order.setOrderNo(orderNo);
			orderHeadMapper.add(order);
			Integer orderId = order.getOrderId();
			orderIds.add(orderId);
			BigDecimal paymentAmount = new BigDecimal(0);//包含邮费 
			BigDecimal totalPriceAmount = new BigDecimal(0);//不包含邮费，未使用优惠劵的价格 
			for(OrderItem item : order.getItems()){
				item.setOrderSource(type);
				ProductSku productSku = productSkuMapper.getProductSkuById(item.getProductSkuId());
				if(productSku != null){
					Product product = productSku.getProduct();
					//判断商品当前状态是activity的
					boolean flag = productService.isPlanByProductSkuId(item.getProductSkuId());
					if(!flag){
						throw new UhomeStoreException(CodeConstants.PRODUCT_NOTACTIVITY_ERROR, "订单中商品不在销售内，请返回购物车修改");
					}
					//判断是否能够扣减库存，如果不能扣减库存则回滚
					Integer count = productSkuMapper.changeProductSkuInventory(item.getOrderSource(), item.getProductSkuId(), item.getNum());
					if(count <= 0){
						throw new UhomeStoreException(CodeConstants.PRODUCT_UNDERSTOCK_ERROR, "订单中商品库存不足，请重新调整商品数量");
					}
					//后台去取当前商品的金额，防止非法用在提交时候在金额上面做手脚。
					item.setMarketPrice(product.getMarketPrice());
					switch (type){
						case com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku.TYPE_SEC_KILL: 
							item.setClosingCost(product.getSecKillPrice());
						break;
						case com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku.TYPE_SPECIAL_SELLER: 
							item.setClosingCost(product.getSalePrice());
						break;
					}
					item.setOrderId(orderId);
					item.setProductName(product.getName());
					item.setSellerId(product.getSellerId());
					item.setProductSku(productSku);
					orderItemMapper.add(item);
					
					BigDecimal total = item.getClosingCost().multiply(new BigDecimal(item.getNum()));
					totalPriceAmount = totalPriceAmount.add(total);
					paymentAmount = paymentAmount.add(total);
				}else{
					throw new UhomeStoreException(CodeConstants.PARAMETER_ERROR, "提交数据有误，请核对后再提交！");
				}
			}
			order.setTotalPrice(totalPriceAmount);
			orderHeadMapper.update(order);//更新总价
			//设置收货地址
			OrderExpress express = new OrderExpress();
			ReceiverAddress address = userInfoService.getReceiverAddressById(order.getReceiverAddressId());
			BeanUtils.copyProperties(address, express);
			express.setOrderId(orderId);
			orderExpressMapper.add(express);
			//设置邮费
			OrderPostage orderPostage = new OrderPostage();
			orderPostage.setOrderId(orderId);
			orderPostage.setPostage(postageCalService.getPostage(order, CommonConstants.POSTAGE_PLAN));
			orderPostageMapper.add(orderPostage);
			paymentAmount = paymentAmount.add(orderPostage.getPostage());
			order.setOrderPostage(orderPostage);
			//设置应付款信息
			OrderPayment payment = new OrderPayment();
			BeanUtils.copyProperties(orderPayment, payment);
			payment.setOrderId(orderId);
			payment.setDocumentType(OrderPayment.DOCUMENTTYPE_RECEIPT);
			payment.setOrderPaymentId(null);
			payment.setClosingCostAmount(paymentAmount);
			payment.setPaymentAmount(paymentAmount);
			orderPaymentMapper.add(payment);
			order.setPayment(payment);
		}
		return orderIds;
	}

	@Override
	public List<Seller> getSecKillProducts(CartItem cart) throws UhomeStoreException {
		List<Seller> sellerProducts = null;
		List<Integer> ProductIds = new ArrayList<Integer>();
		ProductIds.add(cart.getProductSkuId());
		List<ProductSku> productSkus = productSkuMapper.listShoppingCartProducts(ProductIds);
		//转换成相应的list返回
		Map<String, List<ProductSku>> skuMap = new HashMap<String, List<ProductSku>>();
		for(ProductSku sku : productSkus){
			String company = sku.getProduct().getSeller().getCompanyName();
			sku.setNum(cart.getNum());
			if(skuMap.containsKey(company)){
				skuMap.get(company).add(sku);
			} else {
				List<ProductSku> skus = new ArrayList<ProductSku>();
				skus.add(sku);
				skuMap.put(company, skus);
			}
			//添加产品ProductSkuOptionValue
			List<ProductSkuOptionValue> productSkuOptionValues = productSkuOptionValueMapper.listProductSkuOptionValuesByProductSkuId(sku.getProductSkuId());
			sku.setProductSkuOptionValues(productSkuOptionValues);
		}
		sellerProducts = new ArrayList<Seller>();
		for(String company : skuMap.keySet()){
			Seller seller = new Seller();
			seller.setCompanyName(company);
			seller.setProductSkus(skuMap.get(company));
			sellerProducts.add(seller);
		}
		return sellerProducts;
	}
	
	/**
	 * @return 生成请求URL
	 */
	@Override
	public String dealOrderPayment(AliPayment aliPay) throws UhomeStoreException {
		//调用支付宝 (生成请求URL)，把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("out_trade_no", aliPay.getOutTradeNo());
		sParaTemp.put("subject", aliPay.getSubject());
		sParaTemp.put("total_fee", aliPay.getTotalFee().toString());
		sParaTemp.put("body", aliPay.getBody());
	
		String sHtmlText="";
		//生成请求URL
		try{
			sHtmlText =alipayService.createDirectPayByUser(sParaTemp);
			log.error("调用支付宝的路径: "+sHtmlText);
		}catch(Exception e){
			log.error("生成支付宝URL出错:"+sHtmlText);
		}
		//返回URL(action 跳转)
		return sHtmlText;
	}
	
	/*
	 * 注：支付宝 回调用平台返回参数params 的参数包括：
	 * trade_no（支付宝交易号） 
	 * out_trade_no（获取订单号）说明此订单号与交易（支付）明细表 的id 对应 
	 * total_fee（获取总金额） 
	 * subject（商品名称、订单名称） 
	 * body（商品描述、订单备注、描述）
	 * buyer_email（买家支付宝账号） 
	 * trade_status（交易状态）	
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public void alipayReturn(Map<String,String> params) throws UhomeStoreException {		
		boolean verify_result=AlipayNotify.verify(params);		
		if(verify_result){//验证成功	
			String trade_no = params.get("trade_no"); // 支付宝交易号
			String order_no = params.get("out_trade_no"); // 获取订单号
			String total_fee = params.get("total_fee"); // 获取总金额
			String subject = params.get("subject");// 商品名称、订单名称
			String body = params.get("body");// 商品描述、订单备注、描述(放值的时候存了dserviceId)
			String buyer_email = params.get("buyer_email"); // 买家支付宝账号
			String buyer_id = params.get("buyer_id"); // 买家支付宝账号
			String trade_status = params.get("trade_status"); // 交易状态
			if("TRADE_FINISHED".equals(trade_status) || "TRADE_SUCCESS".equals(trade_status)){
				String[] orderNos = null;
				String[] tradeNo = StringUtils.findStringByPattern(params.get("body"), tradeNoPattern);
				if(tradeNo != null && tradeNo.length > 0){
					orderNos = StringUtils.findStringByPattern(tradeNo[0], orderNoPattern)[0].split("\\|");
				}
				if(orderNos != null && orderNos.length > 0){
					//更新payment状态
					for(String orderNo : orderNos){
						OrderHead order = orderHeadMapper.getOrderHeadByOrderNo(orderNo);
						if(order==null){
							try {
								globalLogService.recordLog("订单不存在：订单号"+orderNo+"接收参数为:"+params.toString(), GlobalLog.ALIPAY_CALLBACK, GlobalLog.LEVEL6);
							} catch (Exception e) {
								log.error("订单不存在：订单号"+orderNo+"接收参数为:"+params.toString());
							}
							throw new UhomeStoreException("订单不存在：订单号"+orderNo);
						}
						OrderPayment payment = orderPaymentMapper.getOrderPaymentByOrderId(order.getOrderId());
						if(payment!=null){
							//判断是否已经支付过，防止重复提交。
							if(payment.getStatus() == null){
								payment.setAccount(buyer_email);
								payment.setAccountInfo(buyer_id);
								payment.setDocumentNo(trade_no);
//								payment.setPaymentAmount(new BigDecimal(total_fee));
								payment.setPayTime(new Date());
								payment.setStatus(OrderPayment.STATUS_PAYED);
								orderPaymentMapper.update(payment);
								//更新订单状态
								order.setStatus(OrderHead.STATUS_WAITSEND);
								orderHeadMapper.updateStatus(order);
								//添加积分记录
								pointService.updatePointByUserId(order.getUserId(), payment.getPaymentAmount(), Point.TYPE_ADD, Point.POINTSOURCE_ADDORDER); 
							 }else if(payment.getStatus().equals(OrderPayment.STATUS_PAYED)) {
								log.error("支付宝异步返回已经处理(无需在处理),支付宝交易号:" + trade_no + "订单号:"
											+ order_no + "获取总金额:" + total_fee + "商品名称、订单名称:"
											+ subject + "商品描述、订单备注、描述:" + body + "买家支付宝账号:"
											+ buyer_email);
							 }else{
								 try {
										globalLogService.recordLog("付款状态错误：状态值为"+payment.getStatus(), GlobalLog.ALIPAY_CALLBACK, GlobalLog.LEVEL5);
								 } catch (Exception e) {
										log.error("付款状态错误：状态值为"+payment.getStatus());
								 }
								 throw new UhomeStoreException("付款状态错误:" + params.toString());
							 }
						}else{
							try {
								globalLogService.recordLog("订单不存在:" + params.toString(), GlobalLog.ALIPAY_CALLBACK,GlobalLog.LEVEL4);
							} catch (Exception e) {
								log.error("订单不存在:" + params.toString());
							}
							throw new UhomeStoreException("订单不存在:" + params.toString());
						}
					}
				}else{
					try {
						globalLogService.recordLog("订单号未解析成功:"  + params.toString(), GlobalLog.ALIPAY_CALLBACK, GlobalLog.LEVEL3);
					} catch (Exception e) {
						log.error("订单号未解析成功:"  + params.toString());
					}
					throw new UhomeStoreException("订单号未解析成功" + params.toString());
				}
			}else{
				try {
					globalLogService.recordLog("参数传递错误:"  + params.toString(), GlobalLog.ALIPAY_CALLBACK, GlobalLog.LEVEL2);
				} catch (Exception e) {
					log.error("参数传递错误:"  + params.toString());
				}
				throw new UhomeStoreException("参数传递错误" + params.toString());
			}
		}else{
			try {
				globalLogService.recordLog("验证失败:"  + params.toString()+ "配置参数："+AlipayConfig.show_parameter, GlobalLog.ALIPAY_CALLBACK, GlobalLog.LEVEL1);
			} catch (Exception e) {
				log.error("验证失败:"  + params.toString()+ "配置参数："+AlipayConfig.show_parameter);
			}
			throw new UhomeStoreException("验证失败" + params.toString());
		}
	}

	/**
	 * 注：支付返回 调用易通(异步) params 的参数包括： 
	 * trade_no（支付宝交易号） 
	 * out_trade_no（获取订单号）说明此订单号与交易（支付）明细表 的id 对应 
	 * total_fee（获取总金额）
	 * subject（商品名称、订单名称） 
	 * body（商品描述、订单备注、描述）
	 * buyer_email（买家支付宝账号） 
	 * trade_status（交易状态）
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public void alipayNotify(Map<String, String> params) throws UhomeStoreException {
		boolean verify_result=AlipayNotify.verify(params);
		if(verify_result){//验证成功
			String trade_no = params.get("trade_no"); // 支付宝交易号
			String order_no = params.get("out_trade_no"); // 获取订单号
			String total_fee = params.get("total_fee"); // 获取总金额
			String subject = params.get("subject");// 商品名称、订单名称
			String body = params.get("body");// 商品描述、订单备注、描述
			String buyer_email = params.get("buyer_email"); // 买家支付宝账号
			String buyer_id = params.get("buyer_id"); // 买家支付宝账号
			String trade_status = params.get("trade_status"); // 交易状态
			if("TRADE_FINISHED".equals(trade_status) || "TRADE_SUCCESS".equals(trade_status)){
				String[] orderNos = null;
				String[] tradeNo = StringUtils.findStringByPattern(params.get("body"), tradeNoPattern);
				if(tradeNo != null && tradeNo.length > 0){
					orderNos = StringUtils.findStringByPattern(tradeNo[0], orderNoPattern)[0].split("\\|");
				}
				if(orderNos != null && orderNos.length > 0){
					//更新payment状态
					for(String orderNo : orderNos){
						OrderHead order = orderHeadMapper.getOrderHeadByOrderNo(orderNo);
						if(order==null){
							try {
								globalLogService.recordLog("订单不存在：订单号"+orderNo+"接收参数为:"+params.toString(), GlobalLog.ALIPAY_CALLBACK, GlobalLog.LEVEL6);
							} catch (Exception e) {
								log.error("订单不存在：订单号"+orderNo+"接收参数为:"+params.toString());
							}
							throw new UhomeStoreException("订单不存在：订单号"+orderNo);
						}
						OrderPayment payment = orderPaymentMapper.getOrderPaymentByOrderId(order.getOrderId());
						
						if(payment!=null){
							//判断是否已经支付过，防止重复提交。
							if(payment.getStatus() == null){
								payment.setAccount(buyer_email);
							    payment.setAccountInfo(buyer_id);
								payment.setDocumentNo(trade_no);
//								payment.setPaymentAmount(new BigDecimal(total_fee));
								payment.setPayTime(new Date());
								payment.setStatus(OrderPayment.STATUS_PAYED);
								orderPaymentMapper.update(payment);
								//更新订单状态
								order.setStatus(OrderHead.STATUS_WAITSEND);
								orderHeadMapper.updateStatus(order);
								//添加积分记录
								pointService.updatePointByUserId(order.getUserId(), payment.getPaymentAmount(), Point.TYPE_ADD, Point.POINTSOURCE_ADDORDER);
							}else if(payment.getStatus().equals(OrderPayment.STATUS_PAYED)) {
								log.error("支付宝异步返回已经处理(无需再处理),支付宝交易号:" + trade_no 
										+ "订单号:"+ order_no + "获取总金额:" + total_fee 
										+ "商品名称、订单名称:" + subject + "商品描述、订单备注、描述:" + body 
										+ "买家支付宝账号:" + buyer_email);
							}else{
								try {
									globalLogService.recordLog("付款状态错误：状态值为"+payment.getStatus(), GlobalLog.ALIPAY_CALLBACK, GlobalLog.LEVEL5);
								} catch (Exception e) {
									log.error("付款状态错误：状态值为"+payment.getStatus());
								}
								throw new UhomeStoreException("付款状态错误:" + params.toString());
							}
						}else{
							try {
								globalLogService.recordLog("订单不存在:" + params.toString(), GlobalLog.ALIPAY_CALLBACK, GlobalLog.LEVEL4);
							} catch (Exception e) {
								log.error("订单不存在:" + params.toString());
							}
							throw new UhomeStoreException("订单不存在:" + params.toString());
						}
					}
				}else{
					try {
						globalLogService.recordLog("订单号未解析成功:"  + params.toString(), GlobalLog.ALIPAY_CALLBACK, GlobalLog.LEVEL3);
					} catch (Exception e) {
						log.error("订单号未解析成功:"  + params.toString());
					}
					throw new UhomeStoreException("订单号未解析成功" + params.toString());
				}
			}else{
				try {
					globalLogService.recordLog("参数传递错误:"  + params.toString(), GlobalLog.ALIPAY_CALLBACK, GlobalLog.LEVEL2);
				} catch (Exception e) {
					log.error("参数传递错误:"  + params.toString());
				}
				throw new UhomeStoreException("参数传递错误" + params.toString());
			}
		}else{
			try {
				globalLogService.recordLog("验证失败:"  + params.toString()+"配置参数："+AlipayConfig.show_parameter, GlobalLog.ALIPAY_CALLBACK, GlobalLog.LEVEL1);
			} catch (Exception e) {
				log.error("验证失败:"  + params.toString()+ "配置参数："+AlipayConfig.show_parameter);
			}
			throw new UhomeStoreException("验证失败" + params.toString());
		}
	}

	@Override
	public void searchOrders(BasePagination<OrderHead> orderPage) throws UhomeStoreException {
		orderPage.setFilterParamReg(filterParamReg);
		Map<String, Object> searchParams = orderPage.getSearchParamsMap();
		searchParams.put(CommonConstants.PAGE_SORT, "head.createTime");
		searchParams.put(CommonConstants.PAGE_DIR, "desc");
		//如果是根据状态执行另外一个查询 TODO
		if (orderPage.isNeedSetTotal()) {
			Integer total = orderHeadMapper.searchOrdersCount(searchParams);
			orderPage.setTotal(total);
		}
		Collection<OrderHead> result = orderHeadMapper.searchOrders(searchParams);
		if (result != null) {
			for(OrderHead order:result){
				//区分使用优惠券和没有使用优惠券的订单
				if(null == order.getParentOrderId() && null != order.getStatus()){
					getOrderDetail(order);
				}else if(null == order.getParentOrderId() && null == order.getStatus()){
					//使用了优惠券的订单parentOrderId TODO
					//1.找到使用同一个优惠券的所有订单  封装成一个list
					Object obj = searchParams.get("userId");
					Integer buyerId = obj == null ? null : Integer.parseInt(obj.toString());
					String status = (String)searchParams.get("status");
					List<OrderHead> list = orderHeadMapper.listOrderByParentOrderId(order.getOrderId(), buyerId, status);
					if(null == list || list.size() == 0){
						continue;
					}
					//2.为每个订单封装item
					String orderIds = "";
					for(OrderHead oh : list){
						getOrderDetail(oh);
						orderIds += oh.getOrderId().intValue()+"|";
					}
					order.setChildOrderIds(orderIds.substring(0, orderIds.length()-1));
					order.setChildOrders(list);
					//3.移除result使用同一张优惠券的所有订单
					//4.统计总订单数
				}
			}
		}
		orderPage.setResult(result);
	}
	
	@Override
	public void searchOrders4Front(BasePagination<OrderHead> orderPage) throws UhomeStoreException {
		orderPage.setFilterParamReg(filterParamReg);
		Map<String, Object> searchParams = orderPage.getSearchParamsMap();
		searchParams.put(CommonConstants.PAGE_SORT, "head.createTime");
		searchParams.put(CommonConstants.PAGE_DIR, "desc");
		//如果是根据状态执行另外一个查询 TODO
		if (orderPage.isNeedSetTotal()) {
			Integer total = orderHeadMapper.searchOrders4FrontCount(searchParams);
			orderPage.setTotal(total);
		}
		Collection<OrderHead> result = orderHeadMapper.searchOrders4Front(searchParams);
		if (result != null) {
			for(OrderHead order:result){
				//区分使用优惠券和没有使用优惠券的订单
				//if(null == order.getParentOrderId() && null != order.getStatus()){
					getOrderDetail(order);
				//}
			}
		}
		orderPage.setResult(result);
	}
	
	@Override
	public void searchOrders4FrontMyOrder(BasePagination<OrderHead> orderPage) throws UhomeStoreException {
		orderPage.setFilterParamReg(filterParamReg);
		Map<String, Object> searchParams = orderPage.getSearchParamsMap();
		searchParams.put(CommonConstants.PAGE_SORT, "head.createTime");
		searchParams.put(CommonConstants.PAGE_DIR, "desc");
		//如果是根据状态执行另外一个查询 TODO
		if (orderPage.isNeedSetTotal()) {
			Integer total = orderHeadMapper.searchOrders4FrontMyOrdersCount(searchParams);
			orderPage.setTotal(total);
		}
		Collection<OrderHead> result = orderHeadMapper.searchOrders4FrontMyOrders(searchParams);
		if (result != null) {
			for(OrderHead order:result){
				//区分使用优惠券和没有使用优惠券的订单
				if(null == order.getParentOrderId() && null != order.getStatus()){
					getOrderDetail(order);
				}else if(null == order.getParentOrderId() && null == order.getStatus()){
					//使用了优惠券的订单parentOrderId TODO
					//1.找到使用同一个优惠券的所有订单  封装成一个list
					Object obj = searchParams.get("userId");
					Integer buyerId = obj == null ? null : Integer.parseInt(obj.toString());
					String status = (String)searchParams.get("status");
					List<OrderHead> list = orderHeadMapper.listOrderByParentOrderId(order.getOrderId(), buyerId, status);
					if(null == list || list.size() == 0){
						continue;
					}
					//2.为每个订单封装item
					String orderIds = "";
					for(OrderHead oh : list){
						getOrderDetail(oh);
						orderIds += oh.getOrderId().intValue()+"|";
					}
					order.setChildOrderIds(orderIds.substring(0, orderIds.length()-1));
					order.setChildOrders(list);
					//3.移除result使用同一张优惠券的所有订单
					//4.统计总订单数
				}
			}
		}
		orderPage.setResult(result);
	}

	@Override
	public OrderHead getOrderById(Integer orderId) throws UhomeStoreException {
		OrderHead order = orderHeadMapper.getOrderById(orderId);
		OrderExpress orderExpress = orderExpressMapper.getOrderExpressById(orderId);
		/*//如果地址已经被二货用户删除
		if(order.getAddress() == null){
			ReceiverAddress address = new ReceiverAddress();
			BeanUtils.copyProperties(express, address);
			order.setAddress(address);
		}*/
		order.setOrderExpress(orderExpress);
		Region region = regionMapper.getDetailInfoByRegionId(order.getAddress().getRegionId());
		order.getAddress().setRegion(region);
		// 获取订单明细
		List<OrderItem> orderItems = orderItemMapper.listOrderItemsByOrderId4Front(order.getOrderId());
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
		//查询快递信息
		Express express = orderExpress.getExpress();
		if(express != null){
			//快递单号
			String mailNo = orderExpress.getMailNo();
			//快递公司代码
			String companyCode = express.getCompanyCode();
			ExpressMessage msg = expressService.getExpressDetailInfoFromKuaidi100(companyCode, mailNo);
			order.setExpressMessage(msg);
		}
		return order;
	}
	
	@Override
	public List<OrderHead> getOrderByIds(String orderIds) throws UhomeStoreException {
		List<OrderHead> orders = new ArrayList<OrderHead>();
		//将订单号转换成Integer
		String[] strArr = orderIds.split("\\|");
		//查询订单信息
		for(String orderId : strArr){
			Integer id = Integer.parseInt(orderId);
			OrderHead oh = getOrderById(id);
			//添加到orders中
			orders.add(oh);
			//计算总金额 TODO
		}
		return orders;
	}
	
	@Override
	public void getdeliveredOrders(BasePagination<OrderHead> orderPage) throws UhomeStoreException {
		Map<String, Object> searchParams = orderPage.getSearchParamsMap();
		searchParams.put(CommonConstants.PAGE_SORT, "head.updateTime");
		searchParams.put(CommonConstants.PAGE_DIR, "desc");
		String endTime = (String)searchParams.get(CommonConstants.ENDTIME);
		if(endTime!=null){
			searchParams.put(CommonConstants.ENDTIME,endTime+CommonConstants.ENDTIME_VALUE);
		}
		Collection<OrderHead> result = orderHeadMapper.searchDeliveredOrders(searchParams);
		if (orderPage.isNeedSetTotal()) {
			Integer total = orderHeadMapper.searchDeliveredOrdersCount(searchParams);
			orderPage.setTotal(total);
		}
		if (result != null) {
			for(OrderHead order:result){
				// 获取订单明细
				List<OrderItem> orderItems = orderItemMapper.listItemsOrderId(order.getOrderId());
				if(orderItems != null){
//					Integer totalCount = 0;  //记录已经完成条数
					Iterator<OrderItem> it = orderItems.iterator();
//					for(int i = 0; i < orderItems.size(); i++)
					while(it.hasNext()){
//						OrderItem item = orderItems.get(i);
						OrderItem item = it.next();
						//查询此订单明细是否已经退过货了 TODO
						int num = orderItemMapper.getOrderItemReturnNumById(item.getOrderItemId());
						//此订单项全部退完了
						if(0 == num){
//							orderItems.remove(i);
							it.remove();
							continue;
						}
						ProductSku sku = productSkuMapper.getProductSkuById(item.getProductSkuId());
						//添加产品ProductSkuOptionValue
						List<ProductSkuOptionValue> productSkuOptionValues = productSkuOptionValueMapper.listProductSkuOptionValuesByProductSkuId(item.getProductSkuId());
						sku.setProductSkuOptionValues(productSkuOptionValues);
					    item.setProductSku(sku);
					    //判断当前商品是否能退货
//					    int num = orderItemMapper.getOrderItemReturnNumById(item.getOrderItemId());
//						if(num == 0) totalCount++;
					}
//					if(totalCount < orderItems.size()){
//						status = OrderHead.STATUS_ALLOW_RETURN;
//					}
//					if(totalCount == orderItems.size()){
//						status = OrderHead.STATUS_FORBID_RETURN;
//					}
					order.setItems(orderItems);
				}
			}
		}
		orderPage.setResult(result);
	}

	@Override
	public void adjustCartItem(HttpServletRequest request,HttpServletResponse response, Integer productSkuId, Integer quantiy) throws UhomeStoreException {
		ShoppingCart shoppingCart = getShoppingCart(request,response); 
		if (shoppingCart!=null) {
			boolean isAdd = true;//判断是加还是减
			Map<String,Integer>  items = shoppingCart.getCartItems();
			//修改数量
			if(items.containsKey(productSkuId.toString())){
				shoppingCart.setTotalNum(shoppingCart.getTotalNum() - items.get(productSkuId.toString()) + quantiy);
				if(items.get(productSkuId.toString())>quantiy){
					isAdd = false;
				}
				items.put(productSkuId.toString(), quantiy);
			} else {
				throw new UhomeStoreException("此商品已在订单中失效！");
			}
			//修改本地cookie
			Long tempCartExpireTime = 0l;
			if(isAdd){
				shoppingCart.setCartExpireTime(com.ytoxl.module.uhome.uhomebase.common.utils.DateUtil.add(new Date(),Calendar.SECOND, cartDefaultExpireTime).getTime());
				tempCartExpireTime = cartDefaultExpireTime*1000l;
			}else{
				Long temNum = shoppingCart.getCartExpireTime()-System.currentTimeMillis();
				if(temNum>0){
					tempCartExpireTime = temNum;
				}
				
			}
			JSONObject json = JSONObject.fromObject(shoppingCart);
			CookieUtils.addCookie(response, CommonConstants.COOKIE_SHOPPINGCART, json.toString());
		}
	}

	@Override
	public Integer getOrderPointByOrderNo(String orderNo) throws UhomeStoreException {
		String[] orderNos = orderNo.split("\\|");
		BigDecimal paymentAmount = new BigDecimal(0); 
		if(orderNos != null && orderNos.length > 0){
			for(String num : orderNos){
				OrderHead order = orderHeadMapper.getOrderHeadByOrderNo(num);
				OrderPayment payment = orderPaymentMapper.getOrderPaymentByOrderId(order.getOrderId());
				paymentAmount = paymentAmount.add(payment.getPaymentAmount());
			}
		}
		return pointService.computerPoint(paymentAmount);
		
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public void cancelOrder(Integer orderId) throws UhomeStoreException {
		OrderHead order = orderHeadMapper.getOrderById(orderId);
		if(order == null){
			throw new UhomeStoreException("当前订单不存在！");
		}
		if(checkOrderIsCurrentUser(order)){
			order.setStatus(OrderHead.STATUS_CANCEL);
			orderHeadMapper.updateStatus(order);
			//归还库存
			List<OrderItem> items = orderItemMapper.listItemsOrderId(orderId);
			for(OrderItem item : items){
				productSkuMapper.revertProductSkuInventory(item.getOrderSource(), item.getProductSkuId(), item.getNum());
			}
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public void cancelOrders(String orderIds) throws UhomeStoreException {
		//TODO
		//将订单号转换成Integer
		String[] strArr = orderIds.split("\\|");
		//查询订单信息
		for(String orderId : strArr){
			Integer id = Integer.parseInt(orderId);
			OrderHead order = orderHeadMapper.getOrderById(id);
			if(order == null){
				throw new UhomeStoreException("当前订单不存在！");
			}
			if(!checkOrderIsCurrentUser(order)){
				//如果不是当前用户  因为使用了优惠券的订单  取消要同时取消  所以如果有一个不是当前登录用户 抛异常
				throw new UhomeStoreException("用户没有登录,当前用户只能操作当前用户的订单");
			}
			order.setStatus(OrderHead.STATUS_CANCEL);
			orderHeadMapper.updateStatus(order);
			//归还库存
			List<OrderItem> items = orderItemMapper.listItemsOrderId(id);
			for(OrderItem item : items){
				productSkuMapper.revertProductSkuInventory(item.getOrderSource(), item.getProductSkuId(), item.getNum());
			}
		}
	}

	@Override
	public AliPayment getOrderPayment(List<Integer> orderIds) throws UhomeStoreException {
		StringBuilder outTradeNo = new StringBuilder();
		BigDecimal totalFee = new BigDecimal(0); 
		StringBuilder body = new StringBuilder();
		Short type = null;
		Integer size = orderIds.size();
		for(int i = 0 ; i < size ;i++){
			Integer orderId = orderIds.get(i);
			OrderHead order = this.getOrderById(orderId);
			if(order == null){
				throw new UhomeStoreException("当前订单不存在！");
			}
			//判断订单状态，如果是已经取消，已付款等则不能继续操作。
			if(!OrderHead.STATUS_NEW.equals(order.getStatus())&&!OrderHead.STATUS_WAITSEND.equals(order.getStatus())){
				throw new UhomeStoreException("订单已经失效！");
			}
			//判断收货地址是否存在
			if(order.getReceiverAddressId() == null){
				throw new UhomeStoreException(CodeConstants.ORDER_ADDRESS_ERROR, "订单地址不存在");
			}
			//订单异常，没有生成payment
			OrderPayment  payment = orderPaymentService.getPaymentByOrderId(orderId);
			if(payment == null || payment.getPaymentAmount() == null || payment.getPaymentAmount().doubleValue() < 0){
				throw new UhomeStoreException("订单存在异常！~");
			}
			outTradeNo.append(order.getOrderNo());
			if (i != size - 1) {//拼接时，不包括最后一个|字符
			  outTradeNo.append("|");
			}
			totalFee = totalFee.add(payment.getPaymentAmount());
			for(OrderItem item : order.getItems()){
				if(type == null){
					type = item.getOrderSource();
				}
				//判断商品当前状态是activity的
				boolean flag = productService.isPlanByProductSkuId(item.getProductSkuId());
				if(!flag){
					throw new UhomeStoreException(CodeConstants.PRODUCT_NOTACTIVITY_ERROR, "订单中商品不在销售内，请重新选购");
				}
				body.append(String.format("%1$s(%2$s)", item.getProductName(), item.getNum() ));
			}
		}
		
		AliPayment aliPay = new AliPayment();
		String key = String.format("order.source.%1$s", type);
		String orderSource = PropertyUtil.getPropertyValue(key, null);
		aliPay.setSubject(String.format("%1$s商品支付", orderSource));
		aliPay.setOutTradeNo(outTradeNo.toString());
		aliPay.setBody(body.append(String.format("orderNo:[%1$s]", outTradeNo.toString())).toString());
		aliPay.setTotalFee(totalFee);
		return aliPay;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public void receiveOrder(List<Integer> orderIds) throws UhomeStoreException {
		for(Integer orderId : orderIds){
			OrderHead order = orderHeadMapper.getOrderById(orderId);
			if(order == null){
				throw new UhomeStoreException("当前商品不存在！");
			}
			if(!OrderHead.STATUS_SEND.equals(order.getStatus()))
				continue;
			order.setStatus(OrderHead.STATUS_FINISHED);
			orderHeadMapper.updateStatus(order);
			//更新收货字段时间
			orderHeadMapper.updateReceiveProductTime(order.getOrderId());
		}
	}

	@Override
	public Short judgeOrderReturnStatus(OrderHead orderHead) throws UhomeStoreException {
		Short status = OrderHead.STATUS_FORBID_RETURN;
		//判断已完成 不是已发货 、已完成、退货  TODO 2013-10-29 17:33:09
		if( !(OrderHead.STATUS_SEND.equals(orderHead.getStatus())|| OrderHead.STATUS_FINISHED.equals(orderHead.getStatus()) || OrderHead.STATUS_RETURN.equals(orderHead.getStatus()))){
//			throw new UhomeStoreException("当前商品状态不符合退货要求！");
			return status = OrderHead.STATUS_FORBID_RETURN;
		}
		//上一步判断已经过滤掉非完成和退货状态，能够到这一步已经是完成或者退货状态，这一步判断是否超过15天  
		//OrderHead.STATUS_FINISHED.equals(orderHead.getStatus()) &&
//		if(DateUtil.dayInterval(DateUtil.roundToDay(new Date()), orderHead.getUpdateTime()) >= OrderHead.RETRUN_DATE){
		//如果收货时间为空则用updateTime TODO
		/*Date receiveProductTime = orderHead.getReceiveProductTime();
		if(null == receiveProductTime){
			receiveProductTime = orderHead.getUpdateTime();
		}*/
		
		Date sendProductTime = orderHead.getSendProductTime();
		if(null == sendProductTime){
			sendProductTime = orderHead.getUpdateTime();
		}
		if(DateUtil.dayInterval(DateUtil.valueOfStandard(DateUtil.toDay(new Date())), DateUtil.valueOfStandard(DateUtil.toDay(sendProductTime))) >= OrderHead.RETRUN_DATE){
//			throw new UhomeStoreException("已经超过退货期限！");
			return status = OrderHead.STATUS_FORBID_RETURN;
		}
		
		//遍历item，判断是否完全退货
		List<OrderItem> orderItems = orderHead.getItems();
		Integer totalCount = 0;  //记录已经完成条数
		if(orderItems != null){
			for(OrderItem item : orderItems){
				int num = orderItemMapper.getOrderItemReturnNumById(item.getOrderItemId());
				if(num == 0) totalCount++;
			}  
			if(totalCount < orderItems.size()){
				status = OrderHead.STATUS_ALLOW_RETURN;
			}
			if(totalCount == orderItems.size()){
				status = OrderHead.STATUS_FORBID_RETURN;
			}
		}
		return status;
	}

	@Override
	public Integer getValidCartCount(HttpServletRequest request,HttpServletResponse response) throws UhomeStoreException {
		Integer count = 0;
		ShoppingCart shoppingCart = getShoppingCart(request,response); 
		if (shoppingCart!=null) {
			Map<String,Integer>  items = shoppingCart.getCartItems();
			List<String> removeIds = new ArrayList<String>();
			//如果传过来的skuIds为空，则显示购物车中所有货物
			for(String id :items.keySet()){
				ProductSku  productSku = productSkuMapper.get(Integer.parseInt(id));
				if(productSku != null){
					count += items.get(productSku.getProductSkuId().toString());
				} else {
					removeIds.add(id);
					
				}
			}
			//清除掉sku被删除的数据
			for(String id :removeIds){
				items.remove(id);
			}
			shoppingCart.setTotalNum(count);
			//修改本地cookie
			JSONObject json = JSONObject.fromObject(shoppingCart);
			CookieUtils.addCookie(response, CommonConstants.COOKIE_SHOPPINGCART, json.toString());
		}
		return count;
	}
	
	private void getOrderDetail(OrderHead order) throws UhomeStoreException{
		/*//如果地址已经被二货用户删除
		if(null == order.getAddress()){
			OrderExpress express = orderExpressMapper.getOrderExpressById(order.getOrderId());
			ReceiverAddress address = new ReceiverAddress();
			BeanUtils.copyProperties(express, address);
			order.setAddress(address);
		}*/
		// 获取订单明细
		List<OrderItem> orderItems = orderItemMapper.listOrderItemsByOrderId4Front(order.getOrderId());
		if(orderItems != null){
			order.setItems(orderItems);
			for(OrderItem item : orderItems){
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
			//判断是否还能退货,并判断是否部分退货
			Short status = judgeOrderReturnStatus(order);
			order.setAllowReturn(status);
		/*	if(OrderHead.STATUS_RETURN.equals(order.getStatus()) && OrderHead.STATUS_ALLOW_RETURN.equals(status)){
				order.setStatus(OrderHead.STATUS_RETURN);
			}*/
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public void reduceOrderPayment(List<OrderHead> orders, Integer userCouponId) throws UhomeStoreException {
		//判断优惠券信息是否存在，如果不存在直接return
		UserCoupon userCoupon = userCouponMapper.getUserCouponById(userCouponId);
		if(userCoupon == null){
			throw new UhomeStoreException("优惠券不存在！");
		}
		//判断优惠券是否可用
		Short status = userCouponService.getUserCouponStatus(userCoupon.getCouponNo());
		if(!UserCoupon.COUPON_STATUS_0.equals(status)){
			throw new UhomeStoreException("优惠券不可用！");
		}
		//生成父订单信息
		OrderHead order = new OrderHead();
		if(orders==null||orders.size()<1){
			throw new UhomeStoreException("订单异常！");
		}
		order.setUserId(orders.get(0).getUserId());
		orderHeadMapper.add(order);
		
		//均摊payment isAllCoupon:true 全部用优惠券支付
		//isAllCoupon = orderCouponService.getActualCost(userCoupon, orders);
		orderCouponService.calCouponForOrders(orders,userCoupon);
		//保存OrderCoupon
		OrderCoupon orderCoupon = new OrderCoupon();
		orderCoupon.setCouponNo(userCoupon.getCouponNo());
		orderCoupon.setAllowance(userCoupon.getCouponConfig().getAllowance());
		orderCoupon.setLimitCharge(userCoupon.getCouponConfig().getLimitCharge());
		orderCoupon.setOrderId(order.getOrderId());
		orderCouponMapper.add(orderCoupon);
		//均摊sku
		for(OrderHead orderHead: orders){
			//orderHead.setOrderId(order.getOrderId());
			orderHead.setParentOrderId(order.getOrderId());
			//orderCouponService.getOrderItemActualPrice(orderHead);
			//更新子订单里面的父订单id
			orderHeadMapper.updateOrderParentId(orderHead.getOrderId(), order.getOrderId());
			//如果全部用优惠券支付的，更新订单状态为待发货
			if(orderHead.getPayment().getPaymentAmount().compareTo(new BigDecimal(0))==0){
				orderHead.setStatus(OrderHead.STATUS_WAITSEND);
				orderHeadMapper.updateStatus(orderHead);
			}
			//更新实际支付金额
			orderPaymentMapper.updateOrderActualPayment(orderHead.getPayment());
			for(OrderItem item: orderHead.getItems()){
				orderItemMapper.updateOrderItemReducedPrice(item);
			}
		}
		//插入一条空的payment信息
		OrderPayment orderPay = new OrderPayment();
		orderPay.setOrderId(order.getOrderId());
		orderPaymentMapper.add(orderPay);
		//更新优惠券状态为已使用
		userCouponService.updateUserCouponStatus(userCouponId, UserCoupon.COUPON_STATUS_1);
	}

	@Override
	public boolean checkOrderIsCurrentUser(OrderHead order) throws UhomeStoreException {
		boolean b = userInfoService.checkIsLogin();
		CustomUserDetails customUserDetail = null;
		if(b && null != order.getUserId()){
			try {
				customUserDetail = userService.getCurrentUser();
			} catch (YtoxlUserException e) {
				log.error("当前用户没有登录:"+e.getMessage());
				throw new UhomeStoreException("当前用户没有登录");
			}
			if(customUserDetail.getUserId().equals(order.getUserId())){
				return true;
			}
		}
		return false;
	}

	@Override
	public void deleteOrder(OrderHead order) throws UhomeStoreException {
		//删除订单 要删除 order_head最后上传父订单  order_item  order_payment最后删除父订单  order_express  order_conpon最后删除
		Integer orderId = order.getOrderId();
		//1.order_item
		orderItemMapper.deleteOrderItemByOrderId(orderId);
		//2.order_express
		orderExpressMapper.deleteOrderExpressByOrderId(orderId);
		//判断是否是使用优惠券的订单 如果是检查是否是最后一个订单  是 同时删除父订单
		OrderHead orderHead = orderHeadMapper.get(orderId);
		Integer parentOrderId = orderHead.getParentOrderId();
		if(null != parentOrderId){
			Integer pCounts = orderHeadMapper.getOrderHeadCountByOrderParentId(parentOrderId);
			if(1 == pCounts){
				//3.order_payment
				orderPaymentMapper.deleteOrderPaymentByOrderId(orderId);
				orderPaymentMapper.deleteOrderPaymentByOrderId(parentOrderId);
				//4.order_head
				orderHeadMapper.deleteOrderHeadByOrderId(orderId);
				orderHeadMapper.deleteOrderHeadByOrderId(parentOrderId);
				//5.order_coupon  父订单id
				orderCouponMapper.deleteOrderCouponByOrderId(parentOrderId);
				return;
			}
		}
		orderPaymentMapper.deleteOrderPaymentByOrderId(orderId);
		orderHeadMapper.deleteOrderHeadByOrderId(orderId);
		orderCouponMapper.deleteOrderCouponByOrderId(orderId);
	}

	@Override
	public AliPayment getOrderIdsByOrderNos(String orderNos[]) throws UhomeStoreException {
		List<Integer> orderIds = new ArrayList<Integer>();
		for(String orderNo : orderNos){
			OrderHead order = orderHeadMapper.getOrderHeadByOrderNo(orderNo);
			if(order!=null){
				orderIds.add(order.getOrderId());
			}
		}
		return getOrderPayment(orderIds);
	}
	@Override
	public  List<Seller> shoppingCartToSeller(ShoppingCart shoppingCart) throws UhomeStoreException {
		Map<String,Integer>  items = shoppingCart.getCartItems();
		Map<Integer,Seller> sellerMap = new HashMap<Integer,Seller>();
		List<Integer> ids = new ArrayList<Integer>();
		for(String id :items.keySet()){
			ids.add(Integer.parseInt(id));
		}
		List<ProductSku> productSkus = productSkuMapper.listShoppingCartProducts(ids);
		for(ProductSku productSku:productSkus){
			Integer sellerId = productSku.getProduct().getSeller().getSellerId();
			if(sellerMap.containsKey(sellerId)){
				sellerMap.get(sellerId).getProductSkus().add(productSku);
			}else{
				Seller seller = productSku.getProduct().getSeller();
				List<ProductSku> productSkuList = new ArrayList<ProductSku>();
				productSkuList.add(productSku);
				seller.setProductSkus(productSkuList);
				sellerMap.put(seller.getSellerId(), seller);
			}
		}
		List<Seller> listSeller = new ArrayList<Seller>();
		for(Iterator<Integer> it =sellerMap.keySet().iterator();it.hasNext();){
			listSeller.add(sellerMap.get(it.next()));
		}
		return listSeller;
	}

	@Override
	public String repeatCalcPostage(ShoppingCart shoppingcart)throws UhomeStoreException {
		List<Seller> listSeller  = shoppingCartToSeller(shoppingcart);
		Map<String,String> sellerPostageMap = new HashMap<String,String>();
		for(Seller seller:listSeller){
			BigDecimal posage = postageCalService.getPostage(seller, CommonConstants.POSTAGE_PLAN);
			sellerPostageMap.put(String.valueOf(seller.getSellerId()), posage.toString());
		}
		JSONObject json = JSONObject.fromObject(sellerPostageMap);
		return json.toString();
	}
}
