package com.ytoxl.uhomefront.web.action.user;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.google.code.jcaptcha4struts2.core.validation.JCaptchaValidator;
import com.ytoxl.module.core.common.utils.EncodeUtils;
import com.ytoxl.module.uhome.uhomebase.common.CodeConstants;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.UserInfo;
import com.ytoxl.module.uhome.uhomebase.service.UserInfoService;
import com.ytoxl.module.uhome.uhomebase.service.UserRegisterService;
import com.ytoxl.module.uhome.uhomecontent.dataobject.MailTemplate;
import com.ytoxl.module.uhome.uhomecontent.service.SendMailService;
import com.ytoxl.module.user.common.exception.YtoxlUserException;
import com.ytoxl.module.user.common.utils.Md5EncryptionUtils;
import com.ytoxl.module.user.dataobject.Urole;
import com.ytoxl.module.user.dataobject.User;
import com.ytoxl.module.user.security.CustomUserDetails;
import com.ytoxl.module.user.service.UserService;
import com.ytoxl.uhomefront.web.action.BaseAction;
public class UserAction extends BaseAction {
	private static final long serialVersionUID = 7018414785767003877L;
	
	private static Logger logger = LoggerFactory.getLogger(UserAction.class);
	private static final String  user_stuas="0";
	private static final String  Status_deniy="1";//账号被禁用被禁用 、用户的账号重复
	private static final String  pass_img_deniy="3";//验证码不正确
	private static final String  pass_img_access="4";//验证码通过
	private static final String  pass_staus_deniy="5";//密码不正确、用户的邮箱不正确
	private static final String  pass_name_access="6";//验证全部通过 、重置密码成功
	private static final String  Status_deniy_lgoin="8";//禁止登陆
	private static final String  pass_name_excption="7";//注册异常
	
	
	private static final String JOINME = "joinMe";
	
	private  Integer opertNum;//次数记录数
	private String username;//用户名
	private  String jCaptchaResponse;//验证码
	public String password1;//密码
	public String password;
	private String  queryPassWord;//确认码
	private String email;//邮箱
	@Value("${password_salt}")
    private String passwordSaltAction;	
	@Value("${mailUrl}")//邮件地址
	private String mailUrl;
	@Value("${jspvar._domainName}")
	private String domainName;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRegisterService userRegisterService; 
	@Autowired
	private UserInfoService userInfoService; 
	@Autowired
	private  SendMailService sendEmailService;
	private UserInfo userinfo;
	private String  newpassword;
	private String urlBeforeLogin;
	
	private String userId ;
	
	//找回密码
	private String active;
	private String sendTime;
	@Value("${resetPasswordTime}")
	private String resetPasswordTime;
	
	//找回密码 提示信息
	private String findPasswordInfo;
	
	//登陆
	public String checkUserName(){
		if(opertNum>3){
			//ServletActionContext.getRequest().setAttribute("jCaptchaResponse", jCaptchaResponse);
			boolean flag = JCaptchaValidator.validate();
			if(flag){
				setMessage(pass_img_access);//验证码输入正确
			}else{
				setMessage(pass_img_deniy);//输入的验证码不正确
				return JSONMSG;
			}
		}
		User user = userService.getByName(username);
		if(user==null){
			setMessage(user_stuas);//账号不存在
			return JSONMSG;
		}
		if(user.getStatus().equals(Status_deniy)){
			setMessage(Status_deniy);//账号被禁用
			return JSONMSG;
		}
		if(user.getCreateByUserId()!=0){
			setMessage(Status_deniy_lgoin);//账号不能登录
			return JSONMSG;
		}
		if(user.getPassword()!=null&&!"".equals(user.getPassword())){
			if(!Md5EncryptionUtils.MD5SaltPassword(password, passwordSaltAction).equals(user.getPassword())){
				setMessage(pass_staus_deniy);//密码不正确
				return JSONMSG;
			}
		}
		setMessage(pass_name_access);
		return JSONMSG;
	}
	//检查Email
	public String registerCheck(){
		if(!userService.repeatEmail(username)||!userService.repeatUsername(username)){
			 setMessage(Status_deniy);//用户邮箱重复
			 return JSONMSG;
		}
		 setMessage(pass_name_access);//验证通过
		 return JSONMSG;
	}
	//注册
	public String userRegister(){
		boolean flag = JCaptchaValidator.validate();
		if(flag){//验证码
			if(!userService.repeatUsername(username)||!userService.repeatEmail(username)){
				 setMessage(Status_deniy);//用户账号重复
				 return JSONMSG;
			}else {
				User user = new User();
				user.setUsername(username);
				user.setPassword(Md5EncryptionUtils.MD5SaltPassword(password, passwordSaltAction));
	        	user.setEmail(username);
	        	try {
					userInfoService.addRegister(user);
					setMessage(pass_name_access);
				} catch (UhomeStoreException e) {
					setMessage(pass_name_excption);
				} catch (YtoxlUserException e) {
					setMessage(pass_name_excption);
				}
			}	
		}else{
			setMessage(pass_img_deniy);//输入的验证码不正确
			return JSONMSG;
		}
		return JSONMSG;
	}
	//找回密码首页
	public String findPassWord(){
		return "passWordstep1";
	}
	//找回密码操作
	public String passwordBackQueryMsg(){
		try{
			CustomUserDetails customUserDetail = null;
			try {
				customUserDetail = userService.getCurrentUser();
			} catch (YtoxlUserException e1) {
				logger.error(e1.getMessage());
			}
			//找回密码 第二步如果用户没有登录 执行登录流程
			if(customUserDetail == null){
				ServletActionContext.getRequest().setAttribute("jCaptchaResponse", jCaptchaResponse);
				boolean flag = JCaptchaValidator.validate();
				if(flag){
					if(!userService.repeatUsername(username)){
						 User user = null ;
						 user = userService.getByName(username);
						 email = user.getEmail();
//						 email = user.getUsername();
						 StringBuilder sb = new StringBuilder();
						 sb.append(mailUrl).append("/user/resetpassword.htm?username=").append(EncodeUtils.base64Encode(user.getUsername()));
						 //将用户的username和常量md5加密
						 sb.append("&").append("active=").append(EncodeUtils.base64Encode(Md5EncryptionUtils.MD5SaltPassword(user.getUsername(), passwordSaltAction)));
						 //添加时间戳
						 String timesConstant = System.currentTimeMillis()+":"+domainName;
						 sb.append("&").append("sendTime=").append(EncodeUtils.base64Encode(timesConstant));
						 String findPassUrl = sb.toString();
						 try {
							 Map<String, String> map = new HashMap<String, String>();
							 map.put("userName", username);
							 map.put("findPassUrl", findPassUrl);
							 map.put("domainName", domainName);
							 String text = sendEmailService.getMailContent(MailTemplate.TYPE_REPASSWORD, map);
							 sendEmailService.sendMail(user.getEmail(),"找回密码",text);
						} catch (UhomeStoreException e) {
							logger.error(e.getMessage());
						}
						return "mailmsg";//发送邮件跳转到邮件已发送页面
					}
					else{
						 return "accoutError";//账号不存在
					}
				}else{
					 return "verificationError";//验证码错误
				}
			}else{
				return SUCCESS;
			}
		}catch (Exception e) {
			return SUCCESS;
		}
	}
	
	/**
	 * 重置密码
	 * @return
	 */
	public String resetPassword(){
		if(!checkUrlVaildTime()){
			//过期
			findPasswordInfo = "url已经过期！";
			return "updateInfo";
		}
		return "passWordStep3";
	}
	
	//邮件跳转页面的提交
	public String updatePassword() throws YtoxlUserException{
		//获取用户名称、时间戳、加密字符串
		//1.判断时间是否过期
		//2.验证url是否正确
		//3.重置密码
		try{
//			if(StringUtils.isNotEmpty(sendTime)){
//				String sendEmailTime = EncodeUtils.base64Decode(sendTime).split(":")[0];
//				long sdTime = Long.parseLong(sendEmailTime);
//				long curTime = System.currentTimeMillis();
//				int pTime = Integer.parseInt(resetPasswordTime);
//				long passwordTime = sdTime + pTime * 60 * 60 * 1000;
				if(checkUrlVaildTime()){
					String uName = EncodeUtils.base64Decode(username);
					User u = userService.getByName(uName);
					String uActive = Md5EncryptionUtils.MD5SaltPassword(u.getUsername(),passwordSaltAction);
					active = EncodeUtils.base64Decode(active);
					if(null != u && uActive.equals(active)){
						//修改密码
						userService.updatePswByUserId(u.getUserId(), password);
						findPasswordInfo = "重置密码成功！";
						return "updateSuccess";
					}else{
						//Url被改了
						findPasswordInfo = "url被修改过！";
						return "updateInfo";
					}
				}else{
					//过期
					findPasswordInfo = "url已经过期！";
					return "updateInfo";
				}
//			}
//			findPasswordInfo = "修改密码失败！";
//			return "updateInfo";
		}catch (NumberFormatException n) {
			logger.error("修复密码是转换数据异常:"+n.getMessage());
			findPasswordInfo = "修改密码失败！";
			return "updateInfo";
		}catch (Exception e) {
			logger.error("密码修复失败:"+e.getMessage());
			findPasswordInfo = "修改密码失败！";
			return "updateInfo";
		}
		
	}
	
	/**
	 * 检查url有效的时间
	 * @return
	 */
	private boolean checkUrlVaildTime(){
		if(StringUtils.isNotEmpty(sendTime)){
			String sendEmailTime = EncodeUtils.base64Decode(sendTime).split(":")[0];
			long sdTime = Long.parseLong(sendEmailTime);
			long curTime = System.currentTimeMillis();
			int pTime = Integer.parseInt(resetPasswordTime);
			long passwordTime = sdTime + pTime * 60 * 60 * 1000;
			return curTime < passwordTime ;
		}
		return false;
	}
	
	//获取userInfo的信息
	public String getUerInfo(){
		try {
			userinfo = userInfoService.getUserByUserId(userService.getCurrentUser().getUserId());
			username = userService.getCurrentUser().getUsername();
		} catch (UhomeStoreException e) {
		} catch (YtoxlUserException e) {
		}
		return "userInfo";
	}
	//修改用户信息 和用户详细详细信息
	public String updateUserInfo(){
		try {
			// 判断当前用户是否登录  并且只能修改自己的信息
			CustomUserDetails customUserDetail = userService.getCurrentUser();
			if(customUserDetail != null && customUserDetail.getUserId().equals(userinfo.getUserId())){
				//当前角色是买家
				List<Urole> uroles = customUserDetail.getUroles();
				boolean isUpdate = true;
				for(Urole urole:uroles){
					if(!urole.getUroleId().equals(UserInfo.USER_ROLE_BUYER)){
						isUpdate = false;
					}
				}
				if(isUpdate){
					userInfoService.updateUserAndUserInfo(userinfo);
					setMessage("更新成功");
					return JSONMSG;
				}
			}
			setMessage("更新失败");
		} catch (UhomeStoreException e) {
			setMessage("更新失败,"+e.getMessage());
//			setMessage("更新失败");
		} catch (YtoxlUserException e) {
//			setMessage("更新失败,"+e.getMessage());
			setMessage("更新失败");
		}
		 return JSONMSG;
	}
	public String getRenewPassWord(){
		return "renewPass";
	}
	public String updatePassWord() throws UhomeStoreException{
		CustomUserDetails user=null;
		String userPassword = null;
		try {
			user=  userService.getCurrentUser();//修改密码后，当前对象没有更新，重新从数据库中查出来，进行设置，否则修改后没有效果
			User user2 = userInfoService.getUserById(userService.getCurrentUser().getUserId());
			user.setPassword(user2.getPassword());
			userPassword = user.getPassword();
		} catch (YtoxlUserException e) {
			e.printStackTrace();
		}
		if(!Md5EncryptionUtils.MD5SaltPassword(password, passwordSaltAction).equals(userPassword)){
			setMessage(pass_staus_deniy);//密码不正确
			return JSONMSG;
		}else{
			userService.updatePswByUserId(user.getAdminUserId(),newpassword);
			user.setPassword(newpassword);//及时更新密码
			setMessage(pass_name_access);//重置密码成功
		}
			return JSONMSG;
	}
	
	//邀请好友
	public String joinMe(){
		return JOINME;
	}
	//设置url
	public String setFormalUrl(){
		ServletActionContext.getRequest().getSession().setAttribute("url",urlBeforeLogin);
		return JSONMSG;
	}
	
	/**
	 * 判断用户是否登陆
	 */
	public String checkIsLogin(){
		//设置joinMe 要跳转的url spring security 跳转到 IndexAction index 方法取值并跳转到url页面
		ServletActionContext.getRequest().getSession().setAttribute("url",urlBeforeLogin);
		//获取当前用户
		CustomUserDetails detail = null;
		boolean isBuyer = false;
		try { 
			detail = userService.getCurrentUser();
			if(detail == null){
				setMessage(Boolean.FALSE.toString(), CodeConstants.UNLOGIN_ERROR);
				return JSONMSG;
			}
			List<Urole> roleList = detail.getUroles();
			for(Urole userRole : roleList){
				if(userRole.getUroleId().intValue()==UserInfo.USER_ROLE_BUYER.intValue()){
					isBuyer = true;
				}
			}
		} catch (YtoxlUserException e) {
			setMessage(Boolean.FALSE.toString(), CodeConstants.LOGIN_TIMEOUT_ERROR);
			return JSONMSG;
		}
		if(isBuyer){
			String jsonUser = JSONObject.fromObject(detail).toString();
			setMessage(Boolean.TRUE.toString(), jsonUser);
		}else{
			setMessage(Boolean.FALSE.toString(), CodeConstants.MANAGE_SELLER_FORBID);
		}
		return JSONMSG;
	}
	

	public String ajaxJCaptchaValidator(){
		boolean flag = JCaptchaValidator.validate();
		if(flag){
			setMessage(flag+"", "验证码正确");
		}else{
			setMessage(flag+"", "验证码错误");
		}
		return JSONMSG;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}
	public String getPassword1() {
		return password1;
	}
	
	/**
	 * 获取服务条款
	 * @return
	 */
	public String getServiceTerms(){
		return "getServiceTerms";
	}
	
	public String getUrlBeforeLogin() {
		return urlBeforeLogin;
	}
	public void setUrlBeforeLogin(String urlBeforeLogin) {
		this.urlBeforeLogin = urlBeforeLogin;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public UserInfo getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public UserRegisterService getUserRegisterService() {
		return userRegisterService;
	}
	public void setUserRegisterService(UserRegisterService userRegisterService) {
		this.userRegisterService = userRegisterService;
	}
	public String getQueryPassWord() {
		return queryPassWord;
	}
	public void setQueryPassWord(String queryPassWord) {
		this.queryPassWord = queryPassWord;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getjCaptchaResponse() {
		return jCaptchaResponse;
	}
	public void setjCaptchaResponse(String jCaptchaResponse) {
		this.jCaptchaResponse = jCaptchaResponse;
	}
	public Integer getOpertNum() {
		return opertNum;
	}
	public void setOpertNum(Integer opertNum) {
		this.opertNum = opertNum;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	private String findUserName;
	
	public String getFindUserName() {
		return findUserName;
	}
	public void setFindUserName(String findUserName) {
		this.findUserName = findUserName;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getFindPasswordInfo() {
		return findPasswordInfo;
	}
	public void setFindPasswordInfo(String findPasswordInfo) {
		this.findPasswordInfo = findPasswordInfo;
	}
}
