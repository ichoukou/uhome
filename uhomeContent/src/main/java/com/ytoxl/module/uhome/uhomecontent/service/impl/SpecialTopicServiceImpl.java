package com.ytoxl.module.uhome.uhomecontent.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ytoxl.module.core.common.constant.CommonConstants;
import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.SpecialTopicTemplate;
import com.ytoxl.module.uhome.uhomecontent.dataobject.SpecialtopicAdvPosition;
import com.ytoxl.module.uhome.uhomecontent.dataobject.SpecialtopicAdvertisement;
import com.ytoxl.module.uhome.uhomecontent.dataobject.resultmap.SpecialtopicAdvertisementProduct;
import com.ytoxl.module.uhome.uhomecontent.mapper.SpecialTopicTemplateMapper;
import com.ytoxl.module.uhome.uhomecontent.mapper.SpecialtopicAdvPositionMapper;
import com.ytoxl.module.uhome.uhomecontent.mapper.SpecialtopicAdvertisementMapper;
import com.ytoxl.module.uhome.uhomecontent.service.SpecialTopicService;

@Service
public class SpecialTopicServiceImpl implements SpecialTopicService {
	@Autowired
	private SpecialTopicTemplateMapper specialTopicTemplateMapper;
	@Autowired
	private SpecialtopicAdvertisementMapper<SpecialtopicAdvertisement> advMapper;
	@Autowired
	private SpecialtopicAdvPositionMapper<SpecialtopicAdvPosition> advPositionMapper;

	@Override
	public void searchSpecialTopic(
			BasePagination<SpecialTopicTemplate> specialTopicPage)
			throws UhomeStoreException {
		Map<String, Object> paramsMap = specialTopicPage.getSearchParamsMap();
		if (specialTopicPage.isNeedSetTotal()) {
			Integer total = specialTopicTemplateMapper
					.searchSpecialTopicTemplateCount(paramsMap);
			specialTopicPage.setTotal(total);
		}
		Collection<SpecialTopicTemplate> specialTopicTemplateList = specialTopicTemplateMapper
				.searchSpecialTopicTemplate(paramsMap);
		specialTopicPage.setResult(specialTopicTemplateList);
	}

	@Override
	public void addSpecialTopicTemplate(
			SpecialTopicTemplate specialTopicTemplate)
			throws UhomeStoreException {
		specialTopicTemplateMapper.add(specialTopicTemplate);
	}

	@Override
	public void updateSpecialTopicTemplateById(
			SpecialTopicTemplate specialTopicTemplate)
			throws UhomeStoreException {
		specialTopicTemplateMapper.update(specialTopicTemplate);
	}

	/**
	 * 保存广告信息
	 */
	@Override
	public Integer saveAdvertisement(SpecialtopicAdvertisement advertisement)
			throws UhomeStoreException {
		// 更新广告信息
		if (advertisement.getSpecialTopicAdvId() != null) {
			return advMapper.update(advertisement);
		} else {// 新增广告信息
			return advMapper.add(advertisement);
		}
	}

	/**
	 * 分页查询广告信息
	 */
	@Override
	public void searchAdvertisement(
			BasePagination<SpecialtopicAdvertisement> advertisementPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = advertisementPage
				.getSearchParamsMap();
		searchParams.put(CommonConstants.PAGE_DIR, "desc");
		if (advertisementPage.isNeedSetTotal()) {
			Integer total = advMapper.getAdvertisementCount(searchParams);
			advertisementPage.setTotal(total);
		}
		Collection<SpecialtopicAdvertisement> result = advMapper
				.searchAdvertisement(searchParams);
		advertisementPage.setResult(result);
	}

	/**
	 * 根据广告ID，查询广告信息
	 */
	@Override
	public SpecialtopicAdvertisement getAdvertisementById(
			Integer specialTopicAdvId) throws UhomeStoreException {
		return advMapper.get(specialTopicAdvId);
	}

	/**
	 * 列出所有广告位
	 */
	@Override
	public List<SpecialtopicAdvPosition> listAdvPositions()
			throws UhomeStoreException {
		return advPositionMapper.listAdvPositions();
	}

	/**
	 * 根据专题ID，查询广告信息
	 */
	@Override
	public Map<String, SpecialtopicAdvertisement> getAdvertisementMapById(
			Integer templetId) throws UhomeStoreException {
		List<SpecialtopicAdvertisement> list = advMapper
				.listAdvertisementById(templetId);
		Map<String, SpecialtopicAdvertisement> map = new HashMap<String, SpecialtopicAdvertisement>();
		for (SpecialtopicAdvertisement sa : list) {
			if (sa.getAdvPosition() != null
					&& sa.getAdvPosition().getPositionCode() != null) {
				if (sa.getProductIds() != null
						&& !sa.getProductIds().equals("")) {
					String productIds[] = sa.getProductIds().split(",");
					Integer ids[] = new Integer[productIds.length];
					for (int i = 0; i < productIds.length; i++) {
						ids[i] = Integer.valueOf(productIds[i]);
					}
					List<SpecialtopicAdvertisementProduct> sapList = advMapper
							.findProductByIds(ids);
					String names = "";
					String salePrices = "";
					String rebates = "";
					String marketPrices = "";
					if (sapList != null && sapList.size() != ids.length) {
						Map<Integer, SpecialtopicAdvertisementProduct> sapMap = new HashMap<Integer, SpecialtopicAdvertisementProduct>();
						for (SpecialtopicAdvertisementProduct sap : sapList) {
							sapMap.put(sap.getProductId(), sap);
						}
						for (Integer i : ids) {
							SpecialtopicAdvertisementProduct sap = sapMap
									.get(i);
							if (sap != null) {
								names = names + sap.getName() + ",";
								salePrices = salePrices + sap.getSalePrice()
										+ ",";
								rebates = rebates + sap.getRebate() + ",";
								marketPrices = marketPrices
										+ sap.getMarketPrice() + ",";
							}else{
								names = names + " " + ",";
								salePrices = salePrices + " "
										+ ",";
								rebates = rebates + " " + ",";
								marketPrices = marketPrices
										+ " " + ",";
							}
						}
					} else {
						for (SpecialtopicAdvertisementProduct sap : sapList) {
							if (sap != null) {
								names = names + sap.getName() + ",";
								salePrices = salePrices + sap.getSalePrice()
										+ ",";
								rebates = rebates + sap.getRebate() + ",";
								marketPrices = marketPrices
										+ sap.getMarketPrice() + ",";
							}else{
								names = names + " " + ",";
								salePrices = salePrices + " "
										+ ",";
								rebates = rebates + " " + ",";
								marketPrices = marketPrices
										+ " " + ",";
							}
						}
					}
					sa.setProductNames(names.substring(0, names.length() - 1));
					sa.setProductSalePrices(salePrices.substring(0,
							salePrices.length() - 1));
					sa.setProductRebates(rebates.substring(0,
							rebates.length() - 1));
					sa.setProductMarketPrices(marketPrices.substring(0,
							marketPrices.length() - 1));
				}
				map.put(sa.getAdvPosition().getPositionCode(), sa);
			}
		}
		return map;
	}

	/**
	 * 根据专题模板ID查询专题模板信息 xupf
	 */
	@Override
	public SpecialTopicTemplate getTemplateById(Integer specialTopicTempletId)
			throws UhomeStoreException {
		return specialTopicTemplateMapper.get(specialTopicTempletId);
	}

	/**
	 * 查询还未使用的广告位 xupf
	 */
	@Override
	public List<SpecialtopicAdvPosition> listAdvPositionsByTemplateId(
			Integer templateId) throws UhomeStoreException {
		List<SpecialtopicAdvPosition> advPositions = advPositionMapper
				.listAdvPositionsByTemplateId(templateId);
		return advPositions;
	}

	/**
	 * 根据商品ID，查询广告商品
	 */
	@Override
	public List<SpecialtopicAdvertisementProduct> findProductByIds(
			Integer... ids) throws UhomeStoreException {
		return advMapper.findProductByIds(ids);
	}

	/**
	 * 查询广告是否在有效期内
	 */
	@Override
	public boolean isOverdue(Integer specialTopicTempletId)
			throws UhomeStoreException {
		int count = specialTopicTemplateMapper
				.searchOverdueSpecialTopicTemplateCount(specialTopicTempletId);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public SpecialtopicAdvPosition getAdvPositionById(Integer advPositionId)
			throws UhomeStoreException {
		return advPositionMapper.get(advPositionId);
	}

}
