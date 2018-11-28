package com.ytoxl.module.uhome.uhomeorder.dataobject.resultmap;

import java.util.Map;

public class ShoppingCart {
	private Long cartExpireTime;
	private Integer totalNum;    //商品总数
	private Map<String, Integer>  cartItems;   //<productId,num>购物车里面的品项,json对象的key为string
	
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Map<String, Integer> getCartItems() {
		return cartItems;
	}
	public void setCartItems(Map<String, Integer> cartItems) {
		this.cartItems = cartItems;
	}
	public Long getCartExpireTime() {
		return cartExpireTime==null?0:cartExpireTime;
	}
	public void setCartExpireTime(Long cartExpireTime) {
		this.cartExpireTime = cartExpireTime;
	}
	
}

