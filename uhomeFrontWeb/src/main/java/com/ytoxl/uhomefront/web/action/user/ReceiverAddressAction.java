package com.ytoxl.uhomefront.web.action.user;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.code.jcaptcha4struts2.core.validation.JCaptchaValidator;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.ReceiverAddress;
import com.ytoxl.module.uhome.uhomebase.dataobject.Region;
import com.ytoxl.module.uhome.uhomebase.service.ReceiverAddressService;
import com.ytoxl.module.uhome.uhomebase.service.RegionService;
import com.ytoxl.module.user.common.exception.YtoxlUserException;
import com.ytoxl.module.user.security.CustomUserDetails;
import com.ytoxl.module.user.service.UserService;
import com.ytoxl.uhomefront.web.action.BaseAction;

public class ReceiverAddressAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(ReceiverAddressAction.class);
	@Autowired
	private ReceiverAddressService receiverAddressService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private UserService userService;
	//private String jCaptchaResponse;
	private int addTotal;//我的收货地址的总条数
	private ReceiverAddress receiverAdd;
	private Region reg;
	private List<ReceiverAddress> receiverAddList;
	private List<Map<String ,Object>> receiverAddRegionList;
	

	//取得所有的默认的地址和非默认地址
	public String getReceiverAddress(){
		try {
			CustomUserDetails user = null;
				try {
					user = userService.getCurrentUser();
				} catch (YtoxlUserException e) {
				}
			// 如果user为空  用户没有登录直接访问url
			if(null == user){
				return SUCCESS;
			}
			List<ReceiverAddress> receiverAddList =receiverAddressService.getRAddressDetailList(user.getAdminUserId());
			userId =user.getAdminUserId();
			receiverAddRegionList =new ArrayList<Map<String ,Object>>();
			Map<String ,Object> receiverAddRegion;
			Region regMrg = null;
			for(ReceiverAddress receiverAddress : receiverAddList){
				receiverAddRegion = new HashMap<String ,Object>();
				if(receiverAddress!=null){
					regMrg = regionService.getDetailInfoByRegionId(receiverAddress.getRegionId());
					receiverAddRegion.put("receiverName",receiverAddress.getReceiverName());
					receiverAddRegion.put("mobile",receiverAddress.getMobile());
					receiverAddRegion.put("telephone",receiverAddress.getTelephone());
					receiverAddRegion.put("receiveAddress",receiverAddress.getReceiveAddress());
					receiverAddRegion.put("receiverAddressId",receiverAddress.getReceiverAddressId());
					receiverAddRegion.put("isDefault",receiverAddress.getIsDefault());
					receiverAddRegion.put("postCode",receiverAddress.getPostCode());
				}
				if(regMrg!=null){
					receiverAddRegion.put("city",regMrg.getCounty());
					receiverAddRegion.put("province",regMrg.getProvince());
					receiverAddRegion.put("county",regMrg.getCity());
				}
				receiverAddRegionList.add(receiverAddRegion);
				addTotal = receiverAddressService.myAddress(userId);
			}
			
		} catch (UhomeStoreException e) {
		}
		return SUCCESS;
	}
	//获取单个addReceiverAddress
	public String getSingleReceiverAddress(){
		try {
			receiverAdd = receiverAddressService.getAddress(receiverAdd.getReceiverAddressId());
		} catch (UhomeStoreException e) {
			logger.error("查询地址出错："+e.getMessage());
		}
		return getReceiverAddress();
	}
	//删除
	public String delReceiverAddress(){
		try {
			Boolean result = receiverAddressService.checkAddressIsMine(receiverAdd.getReceiverAddressId());
			if(result){
				receiverAddressService.delAddress(receiverAdd.getReceiverAddressId());	
	            setMessage("删除成功！");
			}else{
				setMessage("删除失败！");
			}
		} catch (UhomeStoreException e) {
			setMessage("删除失败！");
	}
		return JSONMSG;
	}
	//添加/编辑地址
	public String addReceiverAddress(){
		//大于10个 有验证码
		if(addTotal >= 10){
			boolean flag = JCaptchaValidator.validate();
			if(!flag){
				 setMessage(Boolean.FALSE.toString(),"验证码错误");//输入的验证码不正确
				 return JSONMSG;
			 }
		}
		try {
			if(StringUtils.isNotEmpty(opert)){
				/*if(StringUtils.split(receiverAdd.getRegionCodes(), ',').length != 3){
					throw new UhomeStoreException("收货地区信息错误,请重新录入正确信息！");
				}*/
				if(opert.equals("edit")){
					Boolean result = receiverAddressService.checkAddressIsMine(receiverAdd.getReceiverAddressId());
					if(result){
						receiverAddressService.updateAddress(receiverAdd);//编辑收货地址
						setMessage("修改成功！");
					}else{
						setMessage("修改失败！");
					}
				}else{
					//对于新增，始终是当前操作者的id，避免给别人添加地址
					receiverAdd.setUserId(userService.getCurrentUser().getUserId());
					receiverAddressService.addAddress(receiverAdd);//增加收货地址
					setMessage("添加成功！");
				}
			}
		} catch (UhomeStoreException e) {
			if(StringUtils.isNotEmpty(opert)){
				if(opert.equals("edit")){
					setMessage("添加失败！");
				}else{
					setMessage("修改失败！");
				}
			}
		} catch (YtoxlUserException e) {
			setMessage("登陆状态异常！");
		}
		return JSONMSG;
	}
	
	//设置为默认的地址
	public String setDefualtReceiverAddress(){
		try {
			try {
				receiverAdd.setUserId(userService.getCurrentUser().getUserId());
			} catch (YtoxlUserException e) {
			}
			Boolean result = receiverAddressService.checkAddressIsMine(receiverAdd.getReceiverAddressId());
			if(result){
				receiverAddressService.updateDefualtAddress(receiverAdd);
				setMessage("设置成功！");
			}else{
				setMessage("设置失败！");
			}
		} catch (UhomeStoreException e) {
			setMessage("设置失败！");
		}
		return JSONMSG;
	}
	//验证验证码
	public String checkImg(){
//		ServletActionContext.getRequest().setAttribute("jCaptchaResponse", jCaptchaResponse);
		boolean flag = JCaptchaValidator.validate();
		if(flag){
			 setMessage(Boolean.TRUE.toString(),"验证码正确");//验证码输入正确
		 }else{
			 setMessage(Boolean.FALSE.toString(),"验证码错误");//输入的验证码不正确
		 }
		return JSONMSG;
	}
	
	
	
	public String getAllAddress(){
		CustomUserDetails user = null;
		try {
			user = userService.getCurrentUser();
		} catch (YtoxlUserException e) {
		}
		userId =user.getAdminUserId();
		try {
			myAddressCount=receiverAddressService.myAddress(userId);
		} catch (UhomeStoreException e) {
		}
		setMessage(Integer.valueOf(myAddressCount).toString());
		
		return JSONMSG;
	}
	
	public int getAddTotal() {
		return addTotal;
	}

	public void setAddTotal(int addTotal) {
		this.addTotal = addTotal;
	}

	private  int myAddressCount;
	public int getMyAddressCount() {
		return myAddressCount;
	}

	public void setMyAddressCount(int myAddressCount) {
		this.myAddressCount = myAddressCount;
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public String getOpert() {
		return opert;
	}

	public void setOpert(String opert) {
		this.opert = opert;
	}
	private Integer userId;
	private String opert;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public ReceiverAddressService getReceiverAddressService() {
		return receiverAddressService;
	}

	public void setReceiverAddressService(
			ReceiverAddressService receiverAddressService) {
		this.receiverAddressService = receiverAddressService;
	}

	public RegionService getRegionService() {
		return regionService;
	}

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public ReceiverAddress getReceiverAdd() {
		return receiverAdd;
	}

	public void setReceiverAdd(ReceiverAddress receiverAdd) {
		this.receiverAdd = receiverAdd;
	}

	public Region getReg() {
		return reg;
	}

	public void setReg(Region reg) {
		this.reg = reg;
	}

	public List<ReceiverAddress> getReceiverAddList() {
		return receiverAddList;
	}

	public void setReceiverAddList(List<ReceiverAddress> receiverAddList) {
		this.receiverAddList = receiverAddList;
	}

	public List getReceiverAddRegionList() {
		return receiverAddRegionList;
	}
	public void setReceiverAddRegionList(
			List<Map<String, Object>> receiverAddRegionList) {
		this.receiverAddRegionList = receiverAddRegionList;
	}

}
