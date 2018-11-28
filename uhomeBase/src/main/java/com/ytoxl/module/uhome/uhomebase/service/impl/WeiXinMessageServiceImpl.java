package com.ytoxl.module.uhome.uhomebase.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Event;
import com.ytoxl.module.uhome.uhomebase.mapper.EventMapper;
import com.ytoxl.module.uhome.uhomebase.service.CouponService;
import com.ytoxl.module.weixin.domain.BaseMessage;
import com.ytoxl.module.weixin.request.EventReqMessage;
import com.ytoxl.module.weixin.request.ImageReqMessage;
import com.ytoxl.module.weixin.request.LinkReqMessage;
import com.ytoxl.module.weixin.request.LocationReqMessage;
import com.ytoxl.module.weixin.request.TextReqMessage;
import com.ytoxl.module.weixin.response.TextResMessage;
import com.ytoxl.module.weixin.service.impl.AbstractMessageServiceImpl;

@Service
public class WeiXinMessageServiceImpl extends AbstractMessageServiceImpl {
	private static Logger logger = LoggerFactory.getLogger(WeiXinMessageServiceImpl.class);
	
	@Autowired
	private EventMapper<Event> eventMapper;
	@Autowired
	private CouponService couponService;
	
	@Value("${weixin_token}")
	private String token;
	@Value("${weixin_attention}")
	private String attention;
	@Value("${weixin_default}")
	private String weixinDefault;
	
	@Override
	public String doMessage(String reqMessageStr, String timestamp,String nonce, String signature) {
		//TODO 验证指纹是否正确，不正确则记录日志，返回null
		String[] str = new String[]{token,timestamp,nonce};
		Arrays.sort(str);
		String total = "";
		for(String s : str){
			total += s;
		}
		String codeStr = DigestUtils.shaHex(total.getBytes());
		if(signature.equals(codeStr)){
			return super.doMessage(reqMessageStr, timestamp, nonce, signature);
		}
		logger.error("非法url访问!"+reqMessageStr);
		return null;
	}

	@Override
	protected BaseMessage doTextMessage(TextReqMessage textReqMessage) {
		// TODO 
		return null;
	}

	@Override
	protected BaseMessage doImageMessage(ImageReqMessage imageReqMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BaseMessage doLocationMessage(
			LocationReqMessage locationReqMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BaseMessage doLinkMessage(LinkReqMessage linkReqMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BaseMessage doEventMessage(EventReqMessage eventReqMessage) {
		//取消关注
		if("unsubscribe".equalsIgnoreCase(eventReqMessage.getEvent())){
			return null;
		}
		//1.查询有没有微信活动正在进行
		List<Event> events = eventMapper.listEventsByTypeAndTime(Event.TYPE_WECHAT);
		if(null == events || events.size() == 0){
			return getRequestTextMessage(eventReqMessage, weixinDefault);
		}
		//2.有微信活动生成优惠券号码 TODO
		List<String> couponNos = null;
		Integer eventId = events.get(0).getEventId();
		BigDecimal couponAmount;
		try {
			//如果有多个活动只给一个 TODO
			couponNos = couponService.createCoupon4Weixin(eventId,eventReqMessage.getFromUserName());
			couponAmount = couponService.getCouponsAmount(eventId);
		} catch (UhomeStoreException e) {
			logger.error("生成微信优惠券出错!"+e.getMessage());
//			return null;
			return getRequestTextMessage(eventReqMessage, weixinDefault);
		}
		if(couponNos != null && couponNos.size()>0){
			String attention_tmp = attention;
			attention_tmp = attention_tmp.replaceAll("200", couponAmount.toString());
			String cNos = couponNos.get(0);
			attention_tmp = attention_tmp.replaceAll("XXXX", cNos);
//			attention = attention.replaceAll(tmp, sb.toString());
//			reqTextMessage.setContent(attention_tmp);
//			reqTextMessage.setCreateTime(System.currentTimeMillis());
//			reqTextMessage.setFromUserName(eventReqMessage.getToUserName());
//			reqTextMessage.setToUserName(eventReqMessage.getFromUserName());
//			reqTextMessage.setFuncFlag("0");
			//3.发送给用户
			return getRequestTextMessage(eventReqMessage,attention_tmp);
		}
		return getRequestTextMessage(eventReqMessage, weixinDefault);
	}
	
	//生成返回xml
	private TextResMessage getRequestTextMessage(EventReqMessage eventReqMessage,String attention){
		TextResMessage reqTextMessage = new TextResMessage();
		reqTextMessage.setContent(attention);
		reqTextMessage.setCreateTime(System.currentTimeMillis());
		reqTextMessage.setFromUserName(eventReqMessage.getToUserName());
		reqTextMessage.setToUserName(eventReqMessage.getFromUserName());
		reqTextMessage.setFuncFlag("0");
		return reqTextMessage;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
