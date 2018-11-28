package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.List;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Region;
import com.ytoxl.module.uhome.uhomebase.dataobject.resultmap.RegionModel;

/**
 * 地址库
 * @author user
 *
 * @param <T>
 */
public interface RegionMapper<T extends Region> extends BaseSqlMapper<T> {
	
	/**
	 * 根据地址库编码查询地址库信息
	 * @param regionCode
	 * @return
	 */
	public Region getRegionByCode(String postCode);
	
	/**
	 * 根据ID查询省市区信息
	 * @param sellerId
	 * @return
	 */
	public Region getDetailInfoByRegionId(Integer regionId);
	
	/**
	 * 根据regionCode查询省市区信息
	 * @param sellerId
	 * @return
	 */
	public Region getDetailInfoByRegionCode(Integer regionCode);
	
	/**
	 * 根据regionId查询省市区code信息
	 * @param sellerId
	 * @return
	 */
	public Region getRegionCodesByRegionId(Integer regionId);
	/**
	 * 查询出所有的省
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<RegionModel> getProvince() throws UhomeStoreException;
	
	
	/**
	 * 根据省份id查询下面所有的城市
	 * @param pId
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<RegionModel> getCityOrArea(String pcId) throws UhomeStoreException;
	
	/**
	 * 通过id查询 省、市、区的regionId
	 * @param id
	 * @return
	 * @throws UhomeStoreException
	 */
	public RegionModel getRegionIdsByRegionId(Integer regionId) throws UhomeStoreException;

}
