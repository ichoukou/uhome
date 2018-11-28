package com.ytoxl.uhomemanage.web.action.content;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.MailSubscribe;
import com.ytoxl.module.uhome.uhomecontent.service.MailSubscribeService;
import com.ytoxl.uhomemanage.web.action.BaseAction;

public class MailSubscribeAction extends BaseAction  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1563032240216719019L;
	private static Logger logger = LoggerFactory.getLogger(MailSubscribeAction.class);
	private static final String SEARCH_MAILSUBS = "searchMailSubs";
	private static final String NEXT_ACTION="nextAction";

	private BasePagination<MailSubscribe> mailSubPage;
	private MailSubscribe mailSubscribe;
	private String nextAction;
	
	@Autowired
	private MailSubscribeService mailSubService;

	public BasePagination<MailSubscribe> getMailSubPage() {
		return mailSubPage;
	}

	public void setMailSubPage(BasePagination<MailSubscribe> mailSubPage) {
		this.mailSubPage = mailSubPage;
	}

	public MailSubscribeService getMailSubService() {
		return mailSubService;
	}

	public void setMailSubService(MailSubscribeService mailSubService) {
		this.mailSubService = mailSubService;
	}

	public MailSubscribe getMailSubscribe() {
		return mailSubscribe;
	}

	public void setMailSubscribe(MailSubscribe mailSubscribe) {
		this.mailSubscribe = mailSubscribe;
	}
	

	public String getNextAction() {
		return nextAction;
	}

	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}

	public String searchMailSubs(){
		if (mailSubPage == null) {
			mailSubPage=new BasePagination<MailSubscribe>();
		}
		try {
			mailSubService.searchMailSubBrands(mailSubPage);
		} catch (UhomeStoreException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return SEARCH_MAILSUBS;
	}
	
	public String updateStatus(){
		try {
			mailSubService.updateMailSubscribeStatus(mailSubscribe);
		} catch (UhomeStoreException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		this.nextAction="mailsub-searchMailSubs.htm";
		return NEXT_ACTION;
	}

	
	

	
	
}
