package com.ytoxl.module.uhome.uhomebase.service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Region;

public interface RegionService {
	/**
	 * 根据ID查询省市区信息
	 * @param sellerId
	 * @return
	 */
	public Region getDetailInfoByRegionId(Integer regionId)throws UhomeStoreException;

	/**
	 * 查询出所有的省json返回
	 * @return
	 * @throws UhomeStoreException
	 */
	public String getProvince() throws UhomeStoreException;
	
	
	/**
	 * 根据省份id查询下面所有的城市json返回
	 * @param pId
	 * @return
	 * @throws UhomeStoreException
	 */
	public String getCity(String pId) throws UhomeStoreException;
	
	/**
	 * 根据城市id查询下面所有的地区json返回
	 * @param cId
	 * @return
	 * @throws UhomeStoreException
	 */
	
	public String getArea(String cId) throws UhomeStoreException;
}
