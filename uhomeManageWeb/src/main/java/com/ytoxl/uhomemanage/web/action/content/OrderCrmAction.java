package com.ytoxl.uhomemanage.web.action.content;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.OrderCrm;
import com.ytoxl.module.uhome.uhomecontent.service.OrderCrmService;
import com.ytoxl.module.user.common.exception.YtoxlUserException;
import com.ytoxl.module.user.security.CustomUserDetails;
import com.ytoxl.module.user.service.UserService;
import com.ytoxl.uhomemanage.web.action.BaseAction;

public class OrderCrmAction extends BaseAction {
	private static final long serialVersionUID = -31542334459833291L;
	private static Logger logger = LoggerFactory.getLogger(OrderCrmAction.class);

	@Autowired
	private OrderCrmService OrderCrmService;
	@Autowired
	private UserService userService;
	
	/** 客服记录 */
	private OrderCrm orderCrm;
	
	private Integer orderId;
	
	private List<OrderCrm> orderCrmList;
	
	private CustomUserDetails customUser;
	
	public String listOrderCrmByOrderId(){
		try {
			orderCrmList = OrderCrmService.listOrderCrmByOrderId(orderId);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "listOrderCrm";
	}
	
	public String addOrderCrm(){
		try {
			customUser = userService.getCurrentUser();
			orderCrm.setUserId(customUser.getUserId());
			orderCrm.setUserName(customUser.getUsername());
			try {
				OrderCrmService.addOrderCrm(orderCrm);
				setMessage(Boolean.TRUE.toString(),customUser.getUsername());
			} catch (UhomeStoreException e) {
				logger.error(e.getMessage());
			}
		} catch (YtoxlUserException e) {
			logger.error(e.getMessage());
		}
		return JSONMSG;
	}

	public OrderCrm getOrderCrm() {
		return orderCrm;
	}

	public void setOrderCrm(OrderCrm orderCrm) {
		this.orderCrm = orderCrm;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public List<OrderCrm> getOrderCrmList() {
		return orderCrmList;
	}

	public void setOrderCrmList(List<OrderCrm> orderCrmList) {
		this.orderCrmList = orderCrmList;
	}

	public CustomUserDetails getCurrentUser() {
		CustomUserDetails detail = null;
		try {
			detail = userService.getCurrentUser();
		} catch (YtoxlUserException e) {
			logger.error(e.getMessage());
		}
		return detail;
	}

	public CustomUserDetails getCustomUser() {
		return customUser;
	}

	public void setCustomUser(CustomUserDetails customUser) {
		this.customUser = customUser;
	}
	

}
