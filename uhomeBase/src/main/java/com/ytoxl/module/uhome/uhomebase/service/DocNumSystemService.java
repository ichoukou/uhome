package com.ytoxl.module.uhome.uhomebase.service;

import java.util.List;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;

public interface DocNumSystemService {
	
	/**
	 * 各种单据号码生成
	 * @throws UhomeStoreException
	 */
	public String getOrderNum () throws UhomeStoreException;
	
	/**
	 * 生成优惠券号
	 * @param count
	 * @param prefix
	 * @throws UhomeStoreException
	 */
	public List<String> getCouponNos(Integer count, String prefix) throws UhomeStoreException;
	
	/**
	 * 生成优惠券礼包激活码
	 * @return
	 * @throws UhomeStoreException
	 */
	public String getCouponActiveCode() throws UhomeStoreException;
}
