package com.ytoxl.module.uhome.uhomeorder.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Seller;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderHead;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderPayment;
import com.ytoxl.module.uhome.uhomeorder.dataobject.resultmap.AliPayment;
import com.ytoxl.module.uhome.uhomeorder.dataobject.resultmap.CartItem;
import com.ytoxl.module.uhome.uhomeorder.dataobject.resultmap.ShoppingCart;

public interface OrderService {
	
	/**
	 * 将cookie json 转换成 ShoppingCart
	 * @param request
	 * @return
	 */
	public ShoppingCart getShoppingCart(HttpServletRequest request,HttpServletResponse response) throws UhomeStoreException;
	/**
	 * 获取购物车中的产品,并按照卖家分开显示。
	 * 如果skuIds不为null，则查询出购物车确定页面所选中的商品
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Seller> getCartProducts(ShoppingCart shoppingCart, List<Integer> skuIds) throws UhomeStoreException;
	
	/**
	 * 获取秒杀商品信息
	 * @param cart
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Seller> getSecKillProducts(CartItem cart) throws UhomeStoreException;
	
	/**
	 * 添加商品到购物车
	 * @param response
	 * @param cart
	 * @throws UhomeStoreException
	 */
	public void addProductToCart(HttpServletRequest request, HttpServletResponse response, CartItem cart) throws UhomeStoreException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
	
	/**
	 * 根据产品id删除购物车中的产品
	 * @param request
	 * @param response
	 * @param productSkuId
	 * @throws UhomeStoreException
	 */
	public ShoppingCart deleteProductFromCart(HttpServletRequest request, HttpServletResponse response, Integer[] productSkuId) throws UhomeStoreException;
	
	/**
	 * 结算，根据不同的卖家生成不同的订单
	 * @param orders
	 * @throws UhomeStoreException
	 */
	public List<Integer> addOrders (List<OrderHead> orders,OrderPayment orderPayment,Short type) throws UhomeStoreException;

	/**
	 * 生成支付宝跳转url
	 * @param aliPay
	 * @return
	 * @throws UhomeStoreException
	 */
	public String dealOrderPayment(AliPayment aliPay) throws UhomeStoreException;
	
	/**
	 * 支付宝 回调用平台返回参数params 
	 * @param params
	 * @throws UhomeStoreException
	 */
	public void alipayReturn(Map<String,String> params) throws UhomeStoreException;
	
	/**
	 * 支付返回 调用易通(异步) params 
	 * @param params
	 * @throws UhomeStoreException
	 */
	public void alipayNotify(Map<String, String> params) throws UhomeStoreException; 
	
	/**
	 * 查找我的订单
	 * @return
	 * @throws UhomeStoreException
	 */
	public void searchOrders(BasePagination<OrderHead> orderPage) throws UhomeStoreException; 
	
	/**
	 * 前台我的订单
	 * @return
	 * @throws UhomeStoreException
	 */
	public void searchOrders4Front(BasePagination<OrderHead> orderPage) throws UhomeStoreException; 
	/**
	 * 前台我的订单 待付款
	 * @return
	 * @throws UhomeStoreException
	 */
	public void searchOrders4FrontMyOrder(BasePagination<OrderHead> orderPage) throws UhomeStoreException;
	
	/**
	 * 根据id查找订单
	 * @param orderId
	 * @throws UhomeStoreException
	 */
	public OrderHead getOrderById(Integer orderId) throws UhomeStoreException;

	/**
	 * 多个订单好用|隔开
	 * 
	 * @param orderIds
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<OrderHead> getOrderByIds(String orderIds) throws UhomeStoreException;
	
	/**
	 * 根据orderPage 查找当前用户下面所有的发货订单
	 * @param orderPage
	 * @throws UhomeStoreException
	 */
	void getdeliveredOrders(BasePagination<OrderHead> orderPage) throws UhomeStoreException; 
	
	/**
	 * 调整购物车中商品数量
	 * @param request
	 * @param response
	 * @param productSkuId
	 * @param quantiy
	 * @throws UhomeStoreException
	 */
	public void adjustCartItem(HttpServletRequest request, HttpServletResponse response, Integer productSkuId, Integer quantiy) throws UhomeStoreException;
	
	/**
	 * 根据订单号查询积分
	 * @param orderNo
	 * @throws UhomeStoreException
	 */
	public Integer getOrderPointByOrderNo(String orderNo) throws UhomeStoreException;
	
    /**
     * 取消订单
     * @param orderId
     * @throws UhomeStoreException
     */
	public void cancelOrder(Integer orderId) throws UhomeStoreException;
	
	/**
     * 使用优惠券 (多个)取消订单
     * @param orderId
     * @throws UhomeStoreException
     */
	public void cancelOrders(String orderIds) throws UhomeStoreException;
	
	/**
	 * 根据订单id获取支付信息
	 * @param orderId
	 * @return
	 * @throws UhomeStoreException
	 */
	public AliPayment getOrderPayment(List<Integer> orderIds) throws UhomeStoreException;
	
	/**
	 * 根据订单id批量收货
	 * @param orderIds
	 * @throws UhomeStoreException
	 */
	public void receiveOrder(List<Integer> orderIds) throws UhomeStoreException;
	
	/**
	 * 判断订单退货状态，部分退货还是完全退货
	 * @param orderHead
	 * @return
	 * @throws UhomeStoreException
	 */
	public Short judgeOrderReturnStatus(OrderHead orderHead) throws UhomeStoreException;
	
	/**
	 * 获取购物车中当前存在的商品，防止sku删除后购物车中数据不准，数据包括不在排期内的数字
	 * @param request
	 * @return
	 * @throws UhomeStoreException
	 */
	public Integer getValidCartCount(HttpServletRequest request,HttpServletResponse response) throws UhomeStoreException;
	
	/**
	 * 对订单使用优惠券进行抵扣
	 * @param orders
	 * @param orderCouponId
	 * @throws UhomeStoreException
	 */
	public void reduceOrderPayment(List<OrderHead> orders, Integer orderCouponId) throws UhomeStoreException;
	
	/**
	 * 判断当前操作的订单和当前用户是否匹配
	 * @param order
	 * @return
	 * @throws UhomeStoreException
	 */
	public boolean checkOrderIsCurrentUser(OrderHead order) throws UhomeStoreException;
	
	/**
	 * 删除订单
	 * @param order
	 * @throws UhomeStoreException
	 */
	public void deleteOrder(OrderHead order) throws UhomeStoreException;
	
	/**
	 * 根据orderNos获取支付信息
	 * @param orderNos
	 * @throws UhomeStoreException
	 */
	public AliPayment getOrderIdsByOrderNos(String orderNos[]) throws UhomeStoreException; 
	
	/**
	 * 将shoppingCart按商家分组
	 * @param shoppingcart
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Seller> shoppingCartToSeller(ShoppingCart shoppingcart) throws UhomeStoreException;
	
	/**
	 * 购物车删除商品，重新计算邮费
	 * @param shoppingcart
	 * @return
	 * @throws UhomeStoreException
	 */
	public String repeatCalcPostage(ShoppingCart shoppingcart)  throws UhomeStoreException;
}
