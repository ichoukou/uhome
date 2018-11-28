package com.ytoxl.uhomefront.web.action.content;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.opensymphony.oscache.util.StringUtil;
import com.ytoxl.module.core.common.utils.EncodeUtils;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.MailSubscribe;
import com.ytoxl.module.uhome.uhomecontent.service.MailSubscribeService;
import com.ytoxl.uhomefront.web.action.BaseAction;

/**
 * @author wangguoqing
 * 
 */
@SuppressWarnings("serial")
public class MailSubscribeAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(MailSubscribeAction.class);

	@Autowired
	private MailSubscribeService mailSubscribeService;
	
	private MailSubscribe mailSubscribe;
	private String email;//退订邮件的地址
	private String type;//退订邮件的类型
	private String originalEmail;
	private Short originalType;

	// 订阅邮件
	public String subscriptionEmail() {
		try {
			//判断邮箱长度
			if(null != mailSubscribe && mailSubscribe.getEmail().length() > 50){
				//setMessage("邮箱太长，邮件订阅失败！");
				setMessage("请输入正确的邮箱");
				return JSONMSG;
			}
			mailSubscribeService.addMailSubscribe(mailSubscribe);
			// TODO 判断邮件的格式以及一些验证 防止攻击
			setMessage("恭喜你，邮件订阅成功！");
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage("邮件订阅失败！");
		}
		return JSONMSG;
	}

	// 退订邮件
	public String unSubscriptionEmail() {
		logger.error("============================退订邮件请求============================");
		HttpServletRequest request = ServletActionContext.getRequest();
		String host = request.getHeader("Host");
		String referer = request.getHeader("Referer");
		logger.error("\nhost：" + host + "\nreferer：" + referer);
		
		if (StringUtil.isEmpty(email) || StringUtil.isEmpty(type)) {
			return "input";
		}
		try {
			int count = mailSubscribeService.listCountByEmailAndType(originalEmail, originalType);
			if(count > 0){
				boolean flag = mailSubscribeService.isUnsubscribe(originalEmail, originalType);
				if(flag){
					setMessage(Boolean.FALSE.toString(), "已取消订阅");
				}
			}else{
				setMessage(Boolean.FALSE.toString(), "不存在此邮件订阅");	
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "success";
	}
	
	public String unsubscribe(){
		try {
			mailSubscribeService.updateMailSubscribeStatus2Cancel(originalEmail, originalType);
			setMessage(Boolean.TRUE.toString(), "退订成功");	
		} catch (Exception e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), "退订失败");
		}
		return JSONMSG;
	}

	public MailSubscribe getMailSubscribe() {
		return mailSubscribe;
	}

	public void setMailSubscribe(MailSubscribe mailSubscribe) {
		this.mailSubscribe = mailSubscribe;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
		this.originalEmail = EncodeUtils.base64Decode(email);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		this.originalType = Short.valueOf(EncodeUtils.base64Decode(type));
	}

	public String getOriginalEmail() {
		return originalEmail;
	}

	public void setOriginalEmail(String originalEmail) {
		this.originalEmail = originalEmail;
	}

	public Short getOriginalType() {
		return originalType;
	}

	public void setOriginalType(Short originalType) {
		this.originalType = originalType;
	}

	
}
