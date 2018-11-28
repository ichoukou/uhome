package com.ytoxl.module.uhome.uhomecontent.dataobject.tbl;

/**
 * 邮件订阅模板
 * @author guoxinzhi
 *
 */
public class MailTemplateTbl {
	protected Integer mailTemplateId;
	protected String title;
	protected String content;
	protected Short type;        // 订阅类型，1=特卖，2=秒杀，3=品牌预约，4=全部（根据类别选择相应模版生成最终发送内容）
	
	public Integer getMailTemplateId() {
		return mailTemplateId;
	}
	public void setMailTemplateId(Integer mailTemplateId) {
		this.mailTemplateId = mailTemplateId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	}
	
}
