package com.ytoxl.uhomemanage.web.action.content;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Suggest;
import com.ytoxl.module.uhome.uhomecontent.service.SuggestService;
import com.ytoxl.uhomemanage.web.action.BaseAction;

public class SuggestAction extends BaseAction  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1563032240216719019L;
	private static Logger logger = LoggerFactory.getLogger(SuggestAction.class);
	private static final String SEARCH_SUGGESTS = "searchSuggests";
	private static final String NEXT_ACTION="redirectSuggests";

	private BasePagination<Suggest> suggestPage;
	private Integer suggestId;
	private String nextAction;
	
	@Autowired
	private SuggestService suggestService;

	public BasePagination<Suggest> getSuggestPage() {
		return suggestPage;
	}

	public void setSuggestPage(BasePagination<Suggest> suggestPage) {
		this.suggestPage = suggestPage;
	}

	public SuggestService getSuggestService() {
		return suggestService;
	}

	public void setSuggestService(SuggestService suggestService) {
		this.suggestService = suggestService;
	}

	public Integer getSuggestId() {
		return suggestId;
	}

	public void setSuggestId(Integer suggestId) {
		this.suggestId = suggestId;
	}

	public String getNextAction() {
		return nextAction;
	}

	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}

	public String searchSuggests(){
		if (suggestPage == null) {
			suggestPage=new BasePagination<Suggest>();
		}
		try {
			suggestService.searchSuggests(suggestPage);
		} catch (UhomeStoreException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return SEARCH_SUGGESTS;
	}
	
	public String deleteSuggest(){
		try {
			suggestService.deleteSuggestById(this.suggestId);
		} catch (UhomeStoreException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		this.setNextAction("suggest-searchSuggests.htm");
		return NEXT_ACTION;
	}
	
	public String updateSuggestStatus(){
		try {
			Suggest suggest=new Suggest();
			suggest.setSuggestId(this.getSuggestId());
			suggest.setStatus(Suggest.STATUS_READ);
			suggestService.updateSuggestStatusById(suggest);
			setMessage(Boolean.TRUE.toString(), "更新成功");
		} catch (UhomeStoreException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), "更新失败");
		}
		return JSONMSG;
	}
	
	

	
	
}
