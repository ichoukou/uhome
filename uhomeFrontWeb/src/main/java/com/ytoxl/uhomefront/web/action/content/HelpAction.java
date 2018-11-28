package com.ytoxl.uhomefront.web.action.content;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Help;
import com.ytoxl.module.uhome.uhomecontent.dataobject.HelpCategory;
import com.ytoxl.module.uhome.uhomecontent.service.HelpService;
import com.ytoxl.uhomefront.web.action.BaseAction;

public class HelpAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(HelpAction.class);
	private static final String VIEW_HELP="viewHelp"; 
	
	private List<HelpCategory> helpCategorys;
	private Help help;
	
	@Autowired
	private HelpService helpService;
	
	/**
	 * 查看帮助信息
	 * @return
	 */
	public String viewHelp(){		
		try {
			this.helpCategorys=helpService.listHelpCategorys();
			if(this.help!=null && this.help.getHelpId()!=null){
				this.help=helpService.getContentByHelpId(this.help.getHelpId());
			}
		} catch (UhomeStoreException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return VIEW_HELP;
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
	
	
}
