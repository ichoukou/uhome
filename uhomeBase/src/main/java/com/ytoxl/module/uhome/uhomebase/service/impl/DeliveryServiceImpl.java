package com.ytoxl.module.uhome.uhomebase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Region;
import com.ytoxl.module.uhome.uhomebase.mapper.RegionMapper;
import com.ytoxl.module.uhome.uhomebase.service.DeliveryService;

@Service
public class DeliveryServiceImpl implements DeliveryService {

	@Autowired
	private RegionMapper<Region> regionMapper;

	@Override
	public Region getRegionByCode(String regionCode) throws UhomeStoreException {
		Region region = regionMapper.getRegionByCode(regionCode);
		return region;
	}
	

	
}
