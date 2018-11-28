package com.ytoxl.uhomefront.web.action.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.TokenHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.core.common.utils.DateUtil;
import com.ytoxl.module.uhome.uhomebase.common.CodeConstants;
import com.ytoxl.module.uhome.uhomebase.common.CommonConstants;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.common.utils.CookieUtils;
import com.ytoxl.module.uhome.uhomebase.common.utils.PropertyUtil;
import com.ytoxl.module.uhome.uhomebase.common.utils.SystemUtils;
import com.ytoxl.module.uhome.uhomebase.dataobject.Partner;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku;
import com.ytoxl.module.uhome.uhomebase.dataobject.ReceiverAddress;
import com.ytoxl.module.uhome.uhomebase.dataobject.Region;
import com.ytoxl.module.uhome.uhomebase.dataobject.Seller;
import com.ytoxl.module.uhome.uhomebase.dataobject.UserInfo;
import com.ytoxl.module.uhome.uhomebase.service.PartnerService;
import com.ytoxl.module.uhome.uhomebase.service.RegionService;
import com.ytoxl.module.uhome.uhomebase.service.UserInfoService;
import com.ytoxl.module.uhome.uhomebase.service.UserRegisterService;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderHead;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderItem;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderPayment;
import com.ytoxl.module.uhome.uhomeorder.dataobject.resultmap.AliPayment;
import com.ytoxl.module.uhome.uhomeorder.dataobject.resultmap.CartItem;
import com.ytoxl.module.uhome.uhomeorder.dataobject.resultmap.ShoppingCart;
import com.ytoxl.module.uhome.uhomeorder.service.OrderCpsService;
import com.ytoxl.module.uhome.uhomeorder.service.OrderPaymentService;
import com.ytoxl.module.uhome.uhomeorder.service.OrderService;
import com.ytoxl.module.uhome.uhomeorder.service.PostageCalService;
import com.ytoxl.module.user.common.exception.YtoxlUserException;
import com.ytoxl.module.user.common.utils.Md5EncryptionUtils;
import com.ytoxl.module.user.dataobject.Urole;
import com.ytoxl.module.user.security.CustomUserDetails;
import com.ytoxl.module.user.service.UserService;
import com.ytoxl.uhomefront.web.action.BaseAction;

@SuppressWarnings("serial")
public class OrderAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(OrderAction.class);
	private static final String LISTCARTITEM = "listCartItem";
	private static final String SHOPPINGCART = "shoppingCart";
	private static final String CHECKORDER = "checkOrder";
	private static final String EDITADDRESS = "editAddress";
	private static final String SHOWORDERS = "showOrders";
	private List<Seller> sellers;
	private CartItem item;
	private Short type;
	private String orderSource;
	private List<ReceiverAddress> address;
	private ReceiverAddress addr;
	private List<Integer> skuIds;
	private List<Integer> ordIds;
	private List<OrderHead> orders;
	private OrderPayment payment;
	private AliPayment aliPay;
	private BasePagination<OrderHead> orderPage;
	private OrderHead orderHead;
	private String orderNo;
	private String orderIds;
	private Integer point;
	private Integer userCouponId;
	private Date cartExpireTime;
	
	@Value("${trade_no_token}")
    private String tradeNoToken;
	@Value("${trade_no_pattern}")
	private String tradeNoPattern;
	@Value("${order_no_pattern}")
	private String orderNoPattern;


	@Autowired
	private OrderService orderService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRegisterService userRegisterService;
	@Autowired
	private OrderPaymentService orderPaymentService;
	@Autowired
	private RegionService regionServiceImpl;
	@Autowired
	private OrderCpsService orderCpsService;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private PostageCalService postageCalService;

	@Value("${default_password}")
	private String defaultPass;
	
	/**
	 * 首页显示购物车物品列表
	 * @return
	 */
	public String listCartItem(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			ShoppingCart cart =  orderService.getShoppingCart(request,response);
			if(cart!=null){
				cartExpireTime = com.ytoxl.module.uhome.uhomebase.common.utils.DateUtil.getDateByMillis(cart.getCartExpireTime());
			}
			sellers = orderService.getCartProducts(cart, null);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return LISTCARTITEM;
	}
	
	/**
	 * 跳转到购物车页面
	 * @return
	 */
	public String shoppingCart(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		  //订单提交页面禁止缓存
    	response.setHeader("Expires","0");
        response.setHeader("Cache-Control","no-store");
        response.setHeader("Pragrma","no-cache");
        response.setDateHeader("Expires",0);
		try {
			ShoppingCart cart =  orderService.getShoppingCart(request,response);
			if(cart!=null){
				cartExpireTime = com.ytoxl.module.uhome.uhomebase.common.utils.DateUtil.getDateByMillis(cart.getCartExpireTime());
			}
			sellers = orderService.getCartProducts(cart, null);
			if(sellers!=null&&sellers.size()>0){
				//设置邮费
				for(Seller seller:sellers){
					seller.setPostage(postageCalService.getPostage(seller, CommonConstants.POSTAGE_PLAN));
				}
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return SHOPPINGCART;
	}

	/**
	 * 添加商品到购物车
	 * @return
	 */
    public String addCartProduct(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	HttpServletResponse response = ServletActionContext.getResponse();
    	try {
			orderService.addProductToCart(request, response, item);
			setMessage(Boolean.TRUE.toString(),"已经加入购物车");
		} catch (Exception e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
    	return JSONMSG;
    }
	
    /**
     * 从购物车中删除商品
     * @return
     */
    public String delCartProduct(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	HttpServletResponse response = ServletActionContext.getResponse();
    	try {
    		orderService.deleteProductFromCart(request, response, (Integer[])skuIds.toArray(new Integer[skuIds.size()]));
    		setMessage(Boolean.TRUE.toString(),"删除商品成功");
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
    	return JSONMSG;
    }
    
    /**
     * 清空购物车
     * @return
     */
    public String clearShoppingCart(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	HttpServletResponse response = ServletActionContext.getResponse();
    	try {
    		String products = CookieUtils.getCookie(request, CommonConstants.COOKIE_SHOPPINGCART);
    		if(StringUtils.isNotEmpty(products.trim())){
    			CookieUtils.removeCookie(request, response, CommonConstants.COOKIE_SHOPPINGCART);
    			setMessage(Boolean.TRUE.toString(),"",null);
    		}
		} catch (Exception e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
    	return JSONMSG;
    }
    /**
     * 从购物车中商品数量
     * @return
     */
    public String adjustCartItem(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	HttpServletResponse response = ServletActionContext.getResponse();
    	try {
    		orderService.adjustCartItem(request, response, item.getProductSkuId(), item.getNum());
    		setMessage(Boolean.TRUE.toString(),"修改商品数量成功");
    	} catch (UhomeStoreException e) {
    		logger.error(e.getMessage());
    		setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
    	}
    	return JSONMSG;
    }
    
    /**
     * 跳转到订单审核，地址新增页面
     * @return
     */
    public String checkOrder(){
    	HttpServletRequest request = ServletActionContext.getRequest();
        //订单提交页面禁止缓存
		HttpServletResponse response = ServletActionContext.getResponse();
    	response.setHeader("Expires","0");
        response.setHeader("Cache-Control","no-store");
        response.setHeader("Pragrma","no-cache");
        response.setDateHeader("Expires",0);
    	try {
    		if(type == null && (item == null || skuIds == null)){
    			return shoppingCart();
    		}
			CustomUserDetails detail = getCurrentUser();
			if(detail != null){
				//如果detail这个角色是买家 则查询地址  如果是同一个浏览器管理员登录了  不现实管理员的地址包括卖家  目前是一个用户一个角色
				List<Urole> uroles = detail.getUroles();
				for(Urole urole : uroles){
					if(urole.getUroleId().intValue() == UserInfo.USER_ROLE_BUYER){
						address = userInfoService.getReceiverAddressList(detail.getUserId());
					}
				}
			}
			ShoppingCart cart =  orderService.getShoppingCart(request,response);
			if(cart!=null){
				cartExpireTime = com.ytoxl.module.uhome.uhomebase.common.utils.DateUtil.getDateByMillis(cart.getCartExpireTime());
			}
			//根据购买类型显示商品信息
			switch (type){
				case com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku.TYPE_SEC_KILL: 
					sellers = orderService.getSecKillProducts(item);
				break;
				case com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku.TYPE_SPECIAL_SELLER: 
					sellers = orderService.getCartProducts(cart, skuIds);
				break;
			}
			String key = String.format("order.source.%1$s", type);
			orderSource = PropertyUtil.getPropertyValue(key, null);
			if(sellers!=null&&sellers.size()>0){
				//设置邮费
				for(Seller seller:sellers){
					seller.setPostage(postageCalService.getPostage(seller, CommonConstants.POSTAGE_PLAN));
				}
			}
		
		}catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
    	return CHECKORDER;
    }
    
    
    /**
     * 新增或者编辑收货地址
     * @return
     */
    public String editAddress(){
    	if(addr != null && addr.getReceiverAddressId() != null){
    		try {
    			addr = userInfoService.getReceiverAddressById(addr.getReceiverAddressId());
			} catch (UhomeStoreException e) {
				logger.error(e.getMessage());
			}
    	}
    	return EDITADDRESS;
    }
    
    /**
     * 保存收货地址
     * @return
     */
    public String saveAddress(){
    	try {
    	    //快速购买暂时不支持
    	/*	if(!userService.repeatEmail(addr.getEmail())){
   			 setMessage(Boolean.FALSE.toString(), "用户邮箱重复!");//用户邮箱重复
   			 return JSONMSG;
   		    }*/
    		//如果该用户还没有登陆或者注册，则注册帐号
    		CustomUserDetails detail = getCurrentUser();
    		Integer userId = null;
//    		String json = null;
			if(detail == null){
				/*User user = new User();
	    		user.setUsername(addr.getEmail());
	    		user.setPassword(defaultPass);
	    		user.setTel(addr.getMobile());
	    		user.setOperateName(addr.getReceiverName());
	    		user.setEmail(addr.getEmail());
	    		user.setStatus(com.ytoxl.module.user.dataobject.User.STATUS_ABLE);
	    		userRegisterService.addRegisterUser(user);
	    		//添加userInfo数据
	    		UserInfo userInfo = new UserInfo();
	    		userInfo.setUser(user);
	    		userInfo.setName(addr.getReceiverName());
	    		userInfo.setMobile(addr.getMobile());
	    		userInfo.setTelephone(addr.getTelephone());
	    		userInfo.setAddress(addr.getReceiveAddress());
	    		userInfo.setEmail(addr.getEmail());
	    		userInfo.setRegionId(addr.getRegionId());
	    		userInfo.setOrderCount(0);
				userId = userInfoService.addBuyer(userInfo);
	    		userId = user.getUserId();
	    		json = JSONObject.fromObject(user).toString();*/
				setMessage(Boolean.FALSE.toString(), "请先登陆!");
	   			return JSONMSG;
			}
    		userId = detail.getUserId();
			addr.setUserId(userId);
			userInfoService.addReceiverAddress(addr);
			
			//返回字符串数组
			Region region = regionServiceImpl.getDetailInfoByRegionId(addr.getRegionId());
			String jsonAddr = "{\"receiverAddressId\":\""+addr.getReceiverAddressId()+"\",\"province\":\""+region.getProvince()+"\",\"city\":\""+region.getCity()+"\",\"county\":\""+region.getCounty()+"\",\"receiveAddress\":\""+addr.getReceiveAddress()+"\",\"receiverName\":\""+addr.getReceiverName()+"\",\"mobile\":\""+addr.getMobile()+"\"}";
			//将修改或者新增的地址json格式返回
//			addr.setRegion(region);
			
			//TODO:json lib 转换有问题，有空研究下
//			ReceiverAddress jsonBean = new ReceiverAddress();
//			BeanUtils.copyProperties(addr, jsonBean);
//			JsonConfig jsonConfig = new JsonConfig();  
//			jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));  
//
//			String jsonAddr = JSONObject.fromObject(jsonBean,jsonConfig).toString();
			
			setMessage(Boolean.TRUE.toString(), jsonAddr);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
    	
    	return JSONMSG;
    }
    
    /**
     * 提交订单信息并跳转到支付页面
     * @return
     */
    public String submitOrder(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	HttpServletResponse response = ServletActionContext.getResponse();
    	HttpSession session = request.getSession(true);
    	//订单提交页面禁止缓存
    	response.setHeader("Expires","0");
        response.setHeader("Cache-Control","no-store");
        response.setHeader("Pragrma","no-cache");
        response.setDateHeader("Expires",0);
        
        List<Integer> orderList = null;
    	try {
    		//判断是否重复提交
	        synchronized (session) {
	            if (!TokenHelper.validToken()) {
	            	throw new UhomeStoreException(CodeConstants.MULTIPLE_SUBMIT, "请不要重复提交订单！");
	            }
	        }
    		payment.setIpAddress(SystemUtils.getClientIpAddr(request));
    		orderList = orderService.addOrders(orders,payment,type);
    		//更新优惠券信息
    		if(userCouponId!=null){
    			orderService.reduceOrderPayment(orders, userCouponId);
    		}
    		StringBuilder orderIds = new StringBuilder();
    		//删除cookie里面已经购买的产品
    		Integer size = orders.size();
    		List<Integer> skuIds =new ArrayList<Integer>(); 
    		for(int i = 0 ; i < size ;i++){
    			OrderHead order = orders.get(i);
    			orderIds.append(order.getOrderId());
    			if (i != size - 1) {//拼接时，不包括最后一个|字符
    				orderIds.append("|");
    			}
    			for(OrderItem item : order.getItems()){
    				skuIds.add(item.getProductSkuId());
    			}
    		}
    		orderService.deleteProductFromCart(request, response,  skuIds.toArray(new Integer[skuIds.size()]));
    		setMessage(Boolean.TRUE.toString(), orderIds.toString());
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			//TODO:订单提交出错分秒杀和特卖很多情况周一好好考虑后添加.
			//防止重复提交
			if(e.getMessage().equals(CodeConstants.MULTIPLE_SUBMIT)){
				setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
				return JSONMSG;
			}
			//参数为空
			//购物车内商品为空
			if(e.getMessage().equals(CodeConstants.PARAMETER_ERROR)){
				setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
				return JSONMSG;
			}
			//登陆状态失败
			if(e.getMessage().equals(CodeConstants.LOGIN_TIMEOUT_ERROR) 
					|| e.getMessage().equals(CodeConstants.UNLOGIN_ERROR)){
				setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
				return JSONMSG;
			}
			//地址异常
			if(e.getMessage().equals(CodeConstants.ORDER_ADDRESS_ERROR)){
				setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
				return JSONMSG;
			}
			//商品不在排期内
			if(e.getMessage().equals(CodeConstants.PRODUCT_NOTACTIVITY_ERROR)){
				setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
				return JSONMSG;
			}
			//如果是秒杀库存不足，提示秒杀结束。
			if(e.getMessage().equals(CodeConstants.PRODUCT_UNDERSTOCK_ERROR) && type.equals(ProductSku.TYPE_SEC_KILL)){
				setMessage(Boolean.FALSE.toString(),  e.getLocalizedMessage());
				return JSONMSG;
			}
			//如果是特卖库存不足，跳转到购物车修改库存
			if(e.getMessage().equals(CodeConstants.PRODUCT_UNDERSTOCK_ERROR) && type.equals(ProductSku.TYPE_SPECIAL_SELLER)){
				setMessage(Boolean.FALSE.toString(),  e.getLocalizedMessage());
				return JSONMSG;
			}
		}
		//处理cps推广过来的订单
		try {
			String unionId = CookieUtils.getCookie(request,CommonConstants.COOKIE_UNIONID);
			if(!StringUtils.isBlank(unionId)){
				Partner partnerDuomai = partnerService.getPartnerById(Partner.CPS_DUOMAI_ID);
				if(unionId.equals(partnerDuomai.getUnionId())){
					//处理订单关联合作者业务
					if(orderList!=null&&orderList.size()>0){
						orderCpsService.addOrderCps(request, orderList);
					}
				}
			}
		} catch (Exception eu) {
			logger.error("合作者订单未关联成功"+eu.getMessage());
		}
		return JSONMSG;
    }
    
    /**
     * 显示订单生成的最终页面
     * @return
     */
    public String showOrders(){
		try {
	    	String[] Ids = orderIds.split("\\|");
    		List<Integer> orderIds = new ArrayList<Integer>();
	    	for(String id : Ids){
	    		orderIds.add(Integer.parseInt(id));
	    	}
			aliPay = orderService.getOrderPayment(orderIds);
			if(aliPay.getTotalFee().compareTo(new BigDecimal(0))==0){
				ServletActionContext.getRequest().setAttribute("allCouponFlag", "y");
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
    	return SHOWORDERS;
    }
    
    public String payit(){
    	HttpServletRequest request = ServletActionContext.getRequest();
		//调用支付宝支付
		String alipayURL = null;
		try {
			String[] orderNos = null;
			String[] tradeNo = com.ytoxl.module.uhome.uhomebase.common.utils.StringUtils.findStringByPattern(aliPay.getBody(), tradeNoPattern);
			if(tradeNo != null && tradeNo.length > 0){
				orderNos = com.ytoxl.module.uhome.uhomebase.common.utils.StringUtils.findStringByPattern(tradeNo[0], orderNoPattern)[0].split("\\|");
			}
			aliPay = orderService.getOrderIdsByOrderNos(orderNos);
			aliPay.setOutTradeNo(Md5EncryptionUtils.MD5SaltPassword(aliPay.getOutTradeNo(), tradeNoToken));
			alipayURL = orderService.dealOrderPayment(aliPay);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		request.setAttribute("alipayURL", alipayURL);
		return "alipay";
    }
    
    /**
     * 我的订单支付
     * @return
     */
    public String payMyOrder(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String alipayURL = null;
    	try {
//    		List<Integer> orderIds = new ArrayList<Integer>();
//    		orderIds.add(orderHead.getOrderId());
    		aliPay = orderService.getOrderPayment(ordIds);
    		//调用支付宝支付
    		aliPay.setOutTradeNo(Md5EncryptionUtils.MD5SaltPassword(aliPay.getOutTradeNo(), tradeNoToken));
    		alipayURL = orderService.dealOrderPayment(aliPay);
    	} catch (UhomeStoreException e) {
    		logger.error(e.getMessage());
    		setMessage(Boolean.FALSE.toString(),  e.getLocalizedMessage());
			return JSONMSG;
    	}
    	request.setAttribute("alipayURL", alipayURL);
    	return "alipay";
    }
    
    /**
     * 查找我的订单	
     * @return
     */
    public String myOrders(){
    	try {
    		if (orderPage == null) {
    			orderPage=new BasePagination<OrderHead>();
			}
    		CustomUserDetails detail = getCurrentUser();
    		if(detail != null){
        		Map<String, String> searchParams = orderPage.getParams();
        		if(searchParams == null){
        			searchParams =new HashMap<String, String>();
        			orderPage.setParams(searchParams);
        		}
        		//页面显示日期初始化
        		String beginTime = orderPage.getParams().get("beginTime");
        		String endTime = orderPage.getParams().get("endTime");
        		if(StringUtils.isEmpty(beginTime) && StringUtils.isEmpty(endTime)){
        			Date now = new Date();
        			searchParams.put("beginTime", DateUtil.toDay(DateUtil.previousSevenDate(now)));
        			searchParams.put("endTime", DateUtil.toDay(now));
        		}
        		//如果没有登陆，不显示列表
        		searchParams.put("userId", detail.getUserId().toString());
        		//如果没有选定选项卡  选择待付款
        		String status = orderPage.getParams().get("status");
        		if(StringUtils.isEmpty(status) || OrderHead.STATUS_NEW.toString().equals(status)){
        			searchParams.put("status",OrderHead.STATUS_NEW.toString());
        			//选择待付款执行
        			orderService.searchOrders4FrontMyOrder(orderPage);
        		}else{
        			//选择非 待付款执行
        			orderService.searchOrders4Front(orderPage);
        		}
        		
    		}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
    	return "myOrders";
    }
    
    /**
     * 获取订单明细
     * @return
     */
    public String getOrder(){
    	try {
    		orderHead = orderService.getOrderById(orderHead.getOrderId());
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
    	return "getOrder";
    }
    
    /**
     * 查看使用优惠券的(多个)订单明细   
     * @return
     */
    public String getOrderCoupons(){
    	try {
			this.orders = orderService.getOrderByIds(orderIds);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
    	return "getOrderCoupons";
    }
    
    /**
     * 支付结束跳转页面
     * @return
     */
    public String finishOrder(){
    	try {
    		point = orderService.getOrderPointByOrderNo(orderNo);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "finishOrder";
    }
    
    /**
     * 取消订单
     * @return
     */
    public String cancelOrder(){
    	try {
    		//验证是否登录 TODO
    		if(userInfoService.checkIsLogin()){
	    		orderService.cancelOrder(orderHead.getOrderId());
	    		setMessage(Boolean.TRUE.toString(), "取消订单成功！");
    		}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), "取消订单失败！");
		}
		return JSONMSG;
    }
    
    /**
     *使用优惠券 取消(多个)订单
     * @return
     */
    public String cancelOrders(){
    	try {
    		//验证是否登录 TODO
    		if(userInfoService.checkIsLogin()){
	    		orderService.cancelOrders(orderIds);
	    		setMessage(Boolean.TRUE.toString(), "取消订单成功！");
    		}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), "取消订单失败！");
		}
		return JSONMSG;
    }
    
    /**
     * 收到订单
     * @return
     */
    public String receiveOrder(){
    	try {
    		orderService.receiveOrder(ordIds);
    		setMessage(Boolean.TRUE.toString(), "收货成功！");
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), "收货失败！");
		}
		return JSONMSG;
    }
    
    /**
     * 删除订单
     * @return
     */
    public String deleteOrder(){
    	try {
			if(userInfoService.checkIsLogin()){
				orderService.deleteOrder(orderHead);
				setMessage(Boolean.TRUE.toString(), "删除订单成功！");
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), "删除订单失败！");
		}
    	return JSONMSG;
    }
    
	public Short[] getStatus() {
		return OrderHead.STATUSES;
	}
	
	public List<Seller> getSellers() {
		return sellers;
	}
	public void setSellers(List<Seller> sellers) {
		this.sellers = sellers;
	}

	public CartItem getItem() {
		return item;
	}

	public void setItem(CartItem item) {
		this.item = item;
	}

	public List<ReceiverAddress> getAddress() {
		return address;
	}

	public void setAddress(List<ReceiverAddress> address) {
		this.address = address;
	}
	
	public CustomUserDetails getCurrentUser() {
		CustomUserDetails detail = null;
		try {
			detail = userService.getCurrentUser();
		} catch (YtoxlUserException e) {
			logger.error(e.getMessage());
		}
		return detail;
	}

	public ReceiverAddress getAddr() {
		return addr;
	}

	public void setAddr(ReceiverAddress addr) {
		this.addr = addr;
	}

	public List<Integer> getSkuIds() {
		return skuIds;
	}

	public void setSkuIds(List<Integer> skuIds) {
		this.skuIds = skuIds;
	}

	public List<OrderHead> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderHead> orders) {
		this.orders = orders;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public AliPayment getAliPay() {
		return aliPay;
	}

	public void setAliPay(AliPayment aliPay) {
		this.aliPay = aliPay;
	}

	public BasePagination<OrderHead> getOrderPage() {
		return orderPage;
	}

	public void setOrderPage(BasePagination<OrderHead> orderPage) {
		this.orderPage = orderPage;
	}

	public OrderHead getOrderHead() {
		return orderHead;
	}

	public void setOrderHead(OrderHead orderHead) {
		this.orderHead = orderHead;
	}

	public OrderPayment getPayment() {
		return payment;
	}

	public void setPayment(OrderPayment payment) {
		this.payment = payment;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public String getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

	public List<Integer> getOrdIds() {
		return ordIds;
	}

	public void setOrdIds(List<Integer> ordIds) {
		this.ordIds = ordIds;
	}

	public Integer getUserCouponId() {
		return userCouponId;
	}

	public void setUserCouponId(Integer userCouponId) {
		this.userCouponId = userCouponId;
	}

	public Date getCartExpireTime() {
		return cartExpireTime;
	}

	public void setCartExpireTime(Date cartExpireTime) {
		this.cartExpireTime = cartExpireTime;
	}

	
	
}
