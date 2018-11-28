package com.ytoxl.uhomemanage.web.action.content;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.AdvPosition;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Advertisement;
import com.ytoxl.module.uhome.uhomecontent.service.AdvService;
import com.ytoxl.uhomemanage.web.action.BaseAction;

public class AdvAction extends BaseAction  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1563032240216719019L;
	private static Logger logger = LoggerFactory.getLogger(AdvAction.class);
	private static final String SEARCH_ADVS = "searchAdvs";
	private static final String ADD_ADV = "addAdv";
	private String nextAction;

	private BasePagination<Advertisement> advPage;
	private List<AdvPosition> advPositions;
	private Advertisement adv;
	
	@Autowired
	private AdvService advService;

	public AdvService getAdvService() {
		return advService;
	}

	public void setAdvService(AdvService advService) {
		this.advService = advService;
	}

	public BasePagination<Advertisement> getAdvPage() {
		return advPage;
	}

	public void setAdvPage(BasePagination<Advertisement> advPage) {
		this.advPage = advPage;
	}

	public List<AdvPosition> getAdvPositions() {
		return advPositions;
	}

	public void setAdvPositions(List<AdvPosition> advPositions) {
		this.advPositions = advPositions;
	}
	
	public Advertisement getAdv() {
		return adv;
	}

	public void setAdv(Advertisement adv) {
		this.adv = adv;
	}

	public String getNextAction() {
		return nextAction;
	}

	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}

	public String searchAdvs(){
		if (advPage == null) {
			advPage=new BasePagination<Advertisement>();
		}
		advPage.setSort("advertisementId");
		advPage.setDir("desc");
		try {
			advService.searchAdvs(advPage);
			advPositions=advService.listAdvPositons();
		} catch (UhomeStoreException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return SEARCH_ADVS;
	}
	
	public String addAdv(){
		try {
			advPositions=advService.listAdvPositons();
			if(adv!=null && adv.getAdvertisementId()!=null && !adv.getAdvertisementId().equals("")){
				adv=advService.getAdvById(adv.getAdvertisementId());
			}
		} catch (UhomeStoreException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return ADD_ADV;
	}
	
	
	public String saveAdv(){
		//判断是否为默认广告
		if(adv.getIsDefault()==null || adv.getIsDefault().equals("")){
			adv.setIsDefault(Advertisement.ISDEFAULT_FALSE);
		}else{
			adv.setIsDefault(Advertisement.ISDEFAULT_TRUE);
		}
		//判断是否需要验证登录
		if(adv.getIsLogin()!=Advertisement.ISLOGIN_FLASE&&adv.getIsLogin()!=Advertisement.ISLOGIN_TRUE){
			adv.setIsLogin(Advertisement.ISLOGIN_FLASE);//直接设为不需要
		}
		try {
			if(adv.getAdvertisementId()!=null && !adv.getAdvertisementId().equals("")){
				advService.updateAdvById(adv);
			}else{
				advService.addAdv(adv);
			}
		} catch (UhomeStoreException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		this.nextAction="adv_searchAdvs.htm";
		return "saveAdv";
	}
	
	
}
