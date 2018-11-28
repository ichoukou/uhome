package com.ytoxl.uhomefront.web.action.content;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.code.jcaptcha4struts2.core.validation.JCaptchaValidator;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.service.UserInfoService;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Suggest;
import com.ytoxl.module.uhome.uhomecontent.service.SuggestService;
import com.ytoxl.module.user.security.CustomUserDetails;
import com.ytoxl.uhomefront.web.action.BaseAction;

public class SuggestAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(SuggestAction.class);
	
	@Autowired
	private SuggestService suggestService;
	@Autowired
	private UserInfoService userInfoService;
	
	private Suggest suggest;
	/**
	 * 显示添加建议页面
	 * @return
	 */
	public String editSuggest(){
		return SUCCESS;
	}
	/**
	 * 保存建议
	 * @return
	 */
	public String saveSuggest(){
		boolean flag = JCaptchaValidator.validate();
		if(flag){
			//判断用户是否登录
			try {
				CustomUserDetails customUserDetail = userInfoService.checkUserIsLogin();
				if(customUserDetail != null){
					suggest.setUserId(customUserDetail.getUserId());
				}
			} catch (UhomeStoreException e) {
				logger.error(e.getMessage());
			}
			try {
				suggestService.addSuggest(suggest);
				setMessage(Boolean.TRUE.toString(), "恭喜你，提交成功！");
			} catch (UhomeStoreException e) {
				logger.error(e.getMessage());
				setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
			}
		}else{
			setMessage("captchaError");//输入的验证码不正确
		}
		return JSONMSG;
	}
	
	public void setSuggest(Suggest suggest) {
		this.suggest = suggest;
	}
	public Suggest getSuggest() {
		return suggest;
	}
}
