package com.ytoxl.uhomefront.web.action.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Point;
import com.ytoxl.module.uhome.uhomebase.service.PointService;
import com.ytoxl.module.user.common.exception.YtoxlUserException;
import com.ytoxl.module.user.service.UserService;
import com.ytoxl.uhomefront.web.action.BaseAction;

@SuppressWarnings("serial")
public class PointAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(PointAction.class);
	
	private static final String GET_RULE = "getRule";
	
	private Point point;

	@Autowired
	public UserService userService;
	@Autowired
	public PointService pointService;

	/**
	 * 获取用户积分
	 * 
	 * @return
	 */
	public String listPoints() {
		try {
			// 获取当前用户id
			Integer userId = userService.getCurrentUser().getUserId();
			// 获取用户积分信息
			point = pointService.getPointByUserId(userId);
		} catch (YtoxlUserException e) {
			logger.error(e.getMessage());
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}

		return SUCCESS;
	}
	
	/**
	 * 获取积分规则
	 * @return
	 */
	public String getPointRule(){
		return GET_RULE;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

}
