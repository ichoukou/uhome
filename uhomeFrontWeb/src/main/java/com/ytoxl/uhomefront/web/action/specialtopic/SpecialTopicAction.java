package com.ytoxl.uhomefront.web.action.specialtopic;

import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.SpecialtopicAdvertisement;
import com.ytoxl.module.uhome.uhomecontent.service.SpecialTopicService;
import com.ytoxl.uhomefront.web.action.BaseAction;

/**
 * @author huangwenxuan
 *  
 */
@SuppressWarnings("serial")
public class SpecialTopicAction extends BaseAction {

	private static Logger logger = LoggerFactory
			.getLogger(SpecialTopicAction.class);

	@Autowired
	private SpecialTopicService specialTopicService;

	// 所有广告
	private Map<String, SpecialtopicAdvertisement> map;

	// 专题ID
	private Integer templetId;

	// 显示专题下的所有广告
	public String showSpecialTopic() {
		try {
			map = specialTopicService.getAdvertisementMapById(templetId);

			super.setSeoConfigByUrlKey(ServletActionContext.getRequest().getServletPath(), null);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
 
		if (map != null && map.size() != 0) {
			return "specialTopic";
		}
		return ERROR;
 	} 

	public Map<String, SpecialtopicAdvertisement> getMap() {
		return map;
	}

	public void setMap(Map<String, SpecialtopicAdvertisement> map) {
		this.map = map;
	}

	public Integer getTempletId() {
		return templetId;
	}

	public void setTempletId(Integer templetId) {
		this.templetId = templetId;
	}

}
