package com.ytoxl.module.uhome.uhomecontent.service;

import java.util.List;
import java.util.Map;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.SpecialTopicTemplate;
import com.ytoxl.module.uhome.uhomecontent.dataobject.SpecialtopicAdvPosition;
import com.ytoxl.module.uhome.uhomecontent.dataobject.SpecialtopicAdvertisement;
import com.ytoxl.module.uhome.uhomecontent.dataobject.resultmap.SpecialtopicAdvertisementProduct;

/**
 * 专题模板接口信息
 *
 */
public interface SpecialTopicService {
	
	public void searchSpecialTopic(BasePagination<SpecialTopicTemplate> specialTopicPage) throws UhomeStoreException;
	
	/**
	 * 保存广告信息
	 * 
	 * @param advertisement
	 * @throws UhomeStoreException
	 */
	public Integer saveAdvertisement(SpecialtopicAdvertisement advertisement) throws UhomeStoreException;
	
	/**
	 * 分页查询广告信息
	 * 
	 * @param specialTopicPage
	 * @throws UhomeStoreException
	 */
	public void searchAdvertisement(BasePagination<SpecialtopicAdvertisement> advertisementPage) throws UhomeStoreException;
	
	/**
	 * 根据广告ID查询广告信息
	 * 
	 * @param specialTopicAdvId
	 * @return
	 * @throws UhomeStoreException
	 */
	public SpecialtopicAdvertisement getAdvertisementById(Integer specialTopicAdvId) throws UhomeStoreException;
	
	/**
	 * 列出所有广告位信息
	 * 
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<SpecialtopicAdvPosition> listAdvPositions() throws UhomeStoreException;

	public void addSpecialTopicTemplate(SpecialTopicTemplate specialTopicTemplate) throws UhomeStoreException;

	public void updateSpecialTopicTemplateById(SpecialTopicTemplate specialTopicTemplate) throws UhomeStoreException;
	
	/**
	 * 根据专题ID，查询广告信息
	 */
	public Map<String,SpecialtopicAdvertisement> getAdvertisementMapById(
			Integer templetId) throws UhomeStoreException;
	
	/**
	 * 根据专题模板ID查询专题模板信息
	 * 
	 * @return
	 * @throws UhomeStoreException
	 */
	public SpecialTopicTemplate getTemplateById(Integer specialTopicTempletId) throws UhomeStoreException;
	
	/**
	 * 根据专题模板ID查询专题是否过期
	 * 
	 * @return
	 * @throws UhomeStoreException
	 */
	public boolean isOverdue(Integer specialTopicTempletId) throws UhomeStoreException;
	
	
	/**
	 * 根据专题模板ID，查询尚未使用的广告位
	 * 
	 * @param templateId
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<SpecialtopicAdvPosition> listAdvPositionsByTemplateId(Integer templateId) throws UhomeStoreException;
	
	/**
	 * 通过产品ID，查询广告产品信息
	 * 
	 * @param ids
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<SpecialtopicAdvertisementProduct> findProductByIds(Integer... ids) throws UhomeStoreException;
	
	/**
	 * 根据ID，获取广告位
	 * 
	 * @param advPositionId
	 * @return
	 * @throws UhomeStoreException
	 */
	public SpecialtopicAdvPosition getAdvPositionById(Integer advPositionId) throws UhomeStoreException;
}
