package com.ytoxl.uhomefront.web.action.partner;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.ytoxl.module.core.common.utils.StringUtils;
import com.ytoxl.module.uhome.uhomebase.common.CommonConstants;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.common.utils.CookieUtils;
import com.ytoxl.module.uhome.uhomebase.dataobject.Partner;
import com.ytoxl.module.uhome.uhomebase.service.PartnerService;
import com.ytoxl.uhomefront.web.action.BaseAction;

public class CpsAction extends BaseAction {

	private static final long serialVersionUID = -2248469650801573715L;
	private static Logger logger = LoggerFactory.getLogger(CpsAction.class);
	private String union_id;
	private String feedback;
	private String mid;
	private String to;
	@Value("${jspvar._ctxPath}")
	private String defaultTo;
	@Value("${jspvar.cps_time}")
	private Integer cpsTime;
	@Autowired
	private PartnerService partnerService;
	
	public void accessCps(){
		HttpServletResponse  response = ServletActionContext.getResponse();
		try {
			Partner partner = partnerService.getPartnerById(Partner.CPS_DUOMAI_ID);
			if(partner!=null){
				String uid = partner.getUnionId();
				if(!StringUtils.isBlank(uid)&&!StringUtils.isBlank(union_id)&&union_id.equals(uid)){
					CookieUtils.addCookie(response, CommonConstants.COOKIE_UNIONID,union_id,cpsTime);
					CookieUtils.addCookie(response, CommonConstants.COOKIE_FEEDBACK,feedback,cpsTime);
					CookieUtils.addCookie(response, CommonConstants.COOKIE_MID,mid,cpsTime);
					CookieUtils.addCookie(response, CommonConstants.COOKIE_TO,to,cpsTime);
				}else{
					logger.error("身份标示不匹配：partner_uid="+uid+"传入身份标示：union_id)");
				}
			}else{
				logger.error("partner is null");
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		} 
		if(to==null||"".equals(to.trim())){
			to =defaultTo;
		}
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		response.setHeader("Location",to);
	}
	public String getUnion_id() {
		return union_id;
	}
	public void setUnion_id(String union_id) {
		this.union_id = union_id;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
	
	
}
