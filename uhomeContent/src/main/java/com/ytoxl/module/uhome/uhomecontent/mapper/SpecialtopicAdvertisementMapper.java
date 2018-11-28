package com.ytoxl.module.uhome.uhomecontent.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomecontent.dataobject.SpecialtopicAdvertisement;
import com.ytoxl.module.uhome.uhomecontent.dataobject.resultmap.SpecialtopicAdvertisementProduct;

public interface SpecialtopicAdvertisementMapper<T extends SpecialtopicAdvertisement> extends BaseSqlMapper<T>{

	/**
	 * 根据位置获取广告信息
	 * 
	 * @param advertisement
	 * @return
	 * @throws DataAccessException
	 */
	public List<SpecialtopicAdvertisement> listAdvertisementByTopic(SpecialtopicAdvertisement advertisement) throws DataAccessException;
	
	/**
	 * 分页查询广告信息
	 * 
	 * @param searchParams
	 * @return
	 * @throws DataAccessException
	 */
	public List<SpecialtopicAdvertisement> searchAdvertisement(Map<String, Object> searchParams) throws DataAccessException;
	
	/**
	 * 查询广告信息记录数
	 * 
	 * @param searchParams
	 * @return
	 * @throws DataAccessException
	 */
	public Integer getAdvertisementCount(Map<String, Object> searchParams) throws DataAccessException;
	
	/**
	 * 查询广告信息
	 * 
	 * @param searchParams
	 * @return
	 * @throws DataAccessException
	 */
	public List<SpecialtopicAdvertisement> listAdvertisementById(
			Integer templetId) throws DataAccessException;
	
	/**
	 * 通过产品ID，查询广告产品信息
	 * 
	 * @param productId
	 * @return
	 * @throws DataAccessException
	 */
	public List<SpecialtopicAdvertisementProduct> findProductByIds(Integer... ids) throws DataAccessException;
}
