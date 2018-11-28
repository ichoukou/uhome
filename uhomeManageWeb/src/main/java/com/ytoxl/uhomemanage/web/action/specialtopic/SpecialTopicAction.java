package com.ytoxl.uhomemanage.web.action.specialtopic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.common.utils.StringUtils;
import com.ytoxl.module.uhome.uhomecontent.dataobject.SpecialTopicTemplate;
import com.ytoxl.module.uhome.uhomecontent.dataobject.SpecialtopicAdvPosition;
import com.ytoxl.module.uhome.uhomecontent.dataobject.SpecialtopicAdvertisement;
import com.ytoxl.module.uhome.uhomecontent.dataobject.resultmap.SpecialtopicAdvertisementProduct;
import com.ytoxl.module.uhome.uhomecontent.service.SpecialTopicService;
import com.ytoxl.uhomemanage.web.action.BaseAction;

@SuppressWarnings("serial")
public class SpecialTopicAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(SpecialTopicAction.class);
	@Autowired
	private SpecialTopicService specialTopicService;
	private SpecialTopicTemplate specialTopicTemplate;
	private BasePagination<SpecialTopicTemplate> specialTopicPage;
	/** 可用广告位 for ajax */
	private List<SpecialtopicAdvPosition> availableAdvPositions;
	/** 查看广告分页 */
	private BasePagination<SpecialtopicAdvertisement> advertisementPage;
	/** 广告实体 */
	private SpecialtopicAdvertisement advertisement;
	/** 广告ID */
	private Integer specialTopicAdvId;
	/** 下一步action路径 */
	private String nextAction;
	/** 产品 */
	private List<SpecialtopicAdvertisementProduct> advProducts;
	
	public String searcheSecialTopicTemplate(){
		if(specialTopicPage == null){
			specialTopicPage = new BasePagination<SpecialTopicTemplate>();
		}
		try {
			specialTopicService.searchSpecialTopic(specialTopicPage);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "specialTopicPage";
	}
	
	public String addSpecialTopicTemplate(){
		try {
			specialTopicService.addSpecialTopicTemplate(specialTopicTemplate);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "turnAction";
	}
	
	public String updateSpecialTopicTemplate(){
		try {
			specialTopicService.updateSpecialTopicTemplateById(specialTopicTemplate);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "turnAction";
	}

	/**
	 * 分页查询广告
	 * 
	 * @return
	 */
	public String searchAdv(){
		if(advertisementPage == null){
			advertisementPage = new BasePagination<SpecialtopicAdvertisement>();
		}
		try {
			if(advertisementPage.getParams() ==null || StringUtils.isEmpty(advertisementPage.getParams().get("specialTopicTempletId"))){
				return NONE;
			}
			specialTopicService.searchAdvertisement(advertisementPage);
			//第一次访问，设置专题名称
			if(advertisementPage.getParams().get("templateName") == null){
				Integer specialTopicTempletId = Integer.valueOf(advertisementPage.getParams().get("specialTopicTempletId"));
				SpecialTopicTemplate template = specialTopicService.getTemplateById(specialTopicTempletId);
				if(template !=null){
					advertisementPage.getParams().put("templateName", template.getName());
					//设置专题发布状态:1-未发布   2-已发布
					advertisementPage.getParams().put("templateIsPublish", String.valueOf(template.getIsPublish()));
				}
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "advertisement";
	}
	
	/**
	 * 获取广告
	 * @return
	 */
	public String getAdv(){
		if(specialTopicAdvId !=null && specialTopicAdvId>0){//修改广告
			try{
				advertisement = specialTopicService.getAdvertisementById(specialTopicAdvId);
				SpecialtopicAdvPosition advPosition = specialTopicService
						.getAdvPositionById(advertisement
								.getSpecialTopicAdvPositionId());
				availableAdvPositions = new ArrayList<SpecialtopicAdvPosition>();
				availableAdvPositions.add(advPosition);
			}catch (UhomeStoreException e) {
				logger.error(e.getMessage());
			}
		}else{//新增广告
			try{
				availableAdvPositions = specialTopicService
						.listAdvPositionsByTemplateId(Integer.valueOf(
								advertisementPage.getParams()
								.get("specialTopicTempletId")));
				advertisement = new SpecialtopicAdvertisement();
			}catch (UhomeStoreException e) {
				logger.error(e.getMessage());
			}
		}
		return "editAdv";
	}
	
	/**
	 * 保存广告
	 * 
	 * @return
	 */
	public String saveAdv(){
		try {
			if(advertisementPage == null){
				advertisementPage = new BasePagination<SpecialtopicAdvertisement>();
			}
			//为分页对象设置专题模板ID
			HashMap<String, String> searchParams = new HashMap<String, String>();
			searchParams.put("specialTopicTempletId", advertisement.getSpecialTopicTempletId()+"");
			advertisementPage.setParams(searchParams);
			if(advertisement.getSpecialTopicAdvId() == null){ //新增广告
				//先验证该专题的广告位是否已有广告
				advertisementPage.getParams().put("specialTopicAdvPositionId", advertisement.getSpecialTopicAdvPositionId()+"");
				specialTopicService.searchAdvertisement(advertisementPage);
				//若当前广告位已有广告，则进行广告的更新操作
				if(advertisementPage.getResult().size()>0){
					List<SpecialtopicAdvertisement> list = (List<SpecialtopicAdvertisement>)advertisementPage.getResult();
					advertisement.setSpecialTopicAdvId(list.get(0).getSpecialTopicAdvId());
				}
			}
			specialTopicService.saveAdvertisement(advertisement);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		this.setNextAction("specialTopic_searchAdv.htm");
		return "saveAdv";
	}
		
	/**
	 * 所有广告位列表
	 * @return
	 */
	public List<SpecialtopicAdvPosition> getAdvPositions(){
		List<SpecialtopicAdvPosition> advPositions = new ArrayList<SpecialtopicAdvPosition>();
		try {
			advPositions = specialTopicService.listAdvPositions();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return advPositions;
	}
	
	/**
	 * 查询可用广告位（for ajax）
	 * @return
	 */
	public String listAvailableAdvPosition(){
		if(specialTopicTemplate.getSpecialTopicTempletId()!=null){
			try {
				availableAdvPositions = specialTopicService.listAdvPositionsByTemplateId(specialTopicTemplate.getSpecialTopicTempletId());
			} catch (UhomeStoreException e) {
				logger.error(e.getMessage());
			} 
		}
		return SUCCESS;
	}
	
	/**
	 * 根据商品编码，查询商品默认图片路径
	 * @return
	 */
	public String listProductImgUrls(){
		if(advertisement.getProductIds() != null){
			try {
				String productIds[] = advertisement.getProductIds().split(",");
				advertisement = null; //清空，减少ajax返回时的数据量
				advProducts = new ArrayList<SpecialtopicAdvertisementProduct>();
				//将String数组，转换为Integer数组
				Integer ids[] = new Integer[productIds.length];
				for(int i=0; i<productIds.length; i++){
					ids[i] = Integer.valueOf(productIds[i]);
				}
				//查询商品
				advProducts= specialTopicService.findProductByIds(ids);
				//处理结果集
				for(SpecialtopicAdvertisementProduct p :advProducts){
					String defaultImage = p.getDefaultImage(); //商品图片
					p.setPreviewImage(defaultImage);//设置返回给前台的值
					p.setImageUrls(null);//减少ajax返回时的数据量
				}
				
			} catch (UhomeStoreException e) {
				logger.error(e.getMessage());
			}
		}
		return SUCCESS;
	}
	
	public BasePagination<SpecialTopicTemplate> getSpecialTopicPage() {
		return specialTopicPage;
	}

	public void setSpecialTopicPage(
			BasePagination<SpecialTopicTemplate> specialTopicPage) {
		this.specialTopicPage = specialTopicPage;
	}

	public SpecialTopicTemplate getSpecialTopicTemplate() {
		return specialTopicTemplate;
	}

	public void setSpecialTopicTemplate(SpecialTopicTemplate specialTopicTemplate) {
		this.specialTopicTemplate = specialTopicTemplate;
	}

	public BasePagination<SpecialtopicAdvertisement> getAdvertisementPage() {
		return advertisementPage;
	}

	public void setAdvertisementPage(
			BasePagination<SpecialtopicAdvertisement> advertisementPage) {
		this.advertisementPage = advertisementPage;
	}

	public SpecialtopicAdvertisement getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(SpecialtopicAdvertisement advertisement) {
		this.advertisement = advertisement;
	}

	public Integer getSpecialTopicAdvId() {
		return specialTopicAdvId;
	}

	public void setSpecialTopicAdvId(Integer specialTopicAdvId) {
		this.specialTopicAdvId = specialTopicAdvId;
	}

	public String getNextAction() {
		return nextAction;
	}

	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}

	public List<SpecialtopicAdvPosition> getAvailableAdvPositions() {
		return availableAdvPositions;
	}

	public void setAvailableAdvPositions(
			List<SpecialtopicAdvPosition> availableAdvPositions) {
		this.availableAdvPositions = availableAdvPositions;
	}

	public List<SpecialtopicAdvertisementProduct> getAdvProducts() {
		return advProducts;
	}

	public void setAdvProducts(List<SpecialtopicAdvertisementProduct> advProducts) {
		this.advProducts = advProducts;
	}

}
