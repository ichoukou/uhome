package com.ytoxl.module.uhome.uhomebase.service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Region;

/**
 * 发货信息 地区 承运公司 收获地址
 * @author wangguoqing
 *
 */
public interface DeliveryService {
	
	public Region getRegionByCode(String regionCode) throws UhomeStoreException;

}
