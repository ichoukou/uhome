package com.ytoxl.module.uhome.uhomebase.service.impl;

import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Region;
import com.ytoxl.module.uhome.uhomebase.dataobject.resultmap.RegionModel;
import com.ytoxl.module.uhome.uhomebase.mapper.RegionMapper;
import com.ytoxl.module.uhome.uhomebase.service.RegionService;
@Service
public class RegionServiceImpl implements RegionService {
	@Autowired
	private RegionMapper<Region> regionMapper;

	/**
	 * 根据ID查询省市区信息
	 * @param sellerId
	 * @return
	 */
	@Override
	public Region getDetailInfoByRegionId(Integer regionId)
			throws UhomeStoreException {
		// TODO Auto-generated method stub
		return regionMapper.getDetailInfoByRegionId(regionId);
	}


	@Override
	public String getProvince() throws UhomeStoreException {
		List<RegionModel> regions = regionMapper.getProvince();
		JSONArray json = JSONArray.fromObject(regions);
		return json.toString();
	}

	@Override
	public String getCity(String pId) throws UhomeStoreException {
		List<RegionModel> regions = regionMapper.getCityOrArea(pId);
		JSONArray json = JSONArray.fromObject(regions);
		return json.toString();
	}

	@Override
	public String getArea(String cId) throws UhomeStoreException {
		List<RegionModel> regions = regionMapper.getCityOrArea(cId);
		JSONArray json = JSONArray.fromObject(regions);
		return json.toString();
	}
}
