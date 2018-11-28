package com.ytoxl.uhomemanage.web.action.content;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Help;
import com.ytoxl.module.uhome.uhomecontent.dataobject.HelpCategory;
import com.ytoxl.module.uhome.uhomecontent.service.HelpService;
import com.ytoxl.uhomemanage.web.action.BaseAction;

public class HelpAction extends BaseAction  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1563032240216719019L;
	private static Logger logger = LoggerFactory.getLogger(HelpAction.class);
	private static final String LIST_HELPS = "listHelps";
	
	private List<HelpCategory> helpCategorys;
	private Help help;
	@Autowired
	private HelpService helpService;

	public List<HelpCategory> getHelpCategorys() {
		return helpCategorys;
	}

	public void setHelpCategorys(List<HelpCategory> helpCategorys) {
		this.helpCategorys = helpCategorys;
	}

	public Help getHelp() {
		return help;
	}

	public void setHelp(Help help) {
		this.help = help;
	}

	public String listHelps(){
		try {
			this.helpCategorys=helpService.listHelpCategorys();
		} catch (UhomeStoreException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return LIST_HELPS;
	}
	
	public String updateContent(){
		try {
			helpService.updateContentById(this.help);
			setMessage(Boolean.TRUE.toString(), "更新成功");
		} catch (UhomeStoreException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), "更新失败");
		}
		return JSONMSG;
	}
	
	public String getContentByHelpId(){
		try {
			setMessage(Boolean.TRUE.toString(), helpService.getContentByHelpId(this.getHelp().getHelpId()).getContent());
		} catch (UhomeStoreException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), "");
		}
		return JSONMSG;
	}
	
}
