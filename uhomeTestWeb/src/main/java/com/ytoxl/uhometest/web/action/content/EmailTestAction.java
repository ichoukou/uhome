package com.ytoxl.uhometest.web.action.content;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.MailSubscribe;
import com.ytoxl.module.uhome.uhomecontent.service.MailSubscribe4TestService;
import com.ytoxl.uhometest.web.action.BaseAction;

/**
 * 邮箱测试action
 * @author 
 *
 */

public class EmailTestAction extends BaseAction {
	private static final long serialVersionUID = -3234630103530025949L;
	private static Logger logger = LoggerFactory.getLogger(EmailTestAction.class);
	
	@Autowired
	private MailSubscribe4TestService mailSubscribeService;
	private String email;// 邮箱地址
	private List<Short> typelist;// 订阅类型集合

	/**
	 * 发送测试
	 */
	public String send() {
		try {
			mailSubscribeService.testSendEmail(typelist, email);
			setMessage(Boolean.TRUE.toString(), "发送成功");
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), "发送失败");
		}
		return JSONMSG;
	}
	
	/**
	 * 进入测试页面
	 * @return String
	 * 
	 */
	public String gototest() {
		return "gototest";
	}
	
	/**
	 * 获取邮件订阅类型
	 * @return Short[]
	 */
	public Short[] getTypes(){
		return MailSubscribe.TYPES;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Short> getTypelist() {
		return typelist;
	}

	public void setTypelist(List<Short> typelist) {
		this.typelist = typelist;
	}
}
