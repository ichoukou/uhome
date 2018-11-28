package com.ytoxl.uhomemanage.web.action.report;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.core.common.utils.DateUtil;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Plan;
import com.ytoxl.module.uhome.uhomebase.dataobject.Seller;
import com.ytoxl.module.uhome.uhomebase.service.PlanService;
import com.ytoxl.module.uhome.uhomebase.service.UserInfoService;
import com.ytoxl.module.uhome.uhomereport.dataobject.PlanCheck;
import com.ytoxl.module.uhome.uhomereport.service.PlanCheckService;
import com.ytoxl.module.user.common.exception.YtoxlUserException;
import com.ytoxl.module.user.service.UserService;
import com.ytoxl.uhomemanage.web.action.BaseAction;

public class PlanCheckAction extends BaseAction {

	private static final long serialVersionUID = -1683456422079577697L;
	private static Logger logger = LoggerFactory.getLogger(PlanCheckAction.class);
	@Autowired
	private UserService userService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private PlanService planService;
	@Autowired
	private PlanCheckService planCheckService;
	
	private BasePagination<PlanCheck> planCheckPagination;
	private BasePagination<PlanCheck> planCheckPage;
	private PlanCheck planCheck;
 	private String reportMenuId;
 	private String nextAction;
	
	public String listPlanCheck(){
		String returnResult ="";
		if (planCheckPage == null) {
			planCheckPage=new BasePagination<PlanCheck>();
		}
		try {
			planCheckService.searchPlanCheckByPlanId(planCheckPage);
		 
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		Integer feedbackCount = Integer.parseInt(planCheckPage.getSearchParamsMap().get("feedbackCount")+"");
		if(feedbackCount.shortValue()  == PlanCheck.FEEDBACKCCOUNT_ONE ){
			returnResult = "listPlanCheckOne" ;
		}else if(feedbackCount.shortValue() == PlanCheck.FEEDBACKCCOUNT_TWO ){
			returnResult = "listPlanCheckTwo";
		}else if(feedbackCount.shortValue() == PlanCheck.FEEDBACKCCOUNT_FINISH){
			returnResult = "listPlanCheckFinish";
		}
		return returnResult;
	}
	/**
	 * 第一次对账
	 * @return
	 */
	public String finishOneCheck(){
		try {
			PlanCheck tempPlanCheck = planCheckService.getPlanCheckByPlanCheckId(planCheck.getPlanCheckId());
			if(tempPlanCheck!=null){
				planCheckService.updateStatusByPlanCheckId(planCheck.getPlanCheckId(),userService.getCurrentUser().getUserId());
				planCheckService.updateFeedBackStatus(tempPlanCheck.getPlanId(),tempPlanCheck.getSellerId());
			/*	PlanCheck pc = new PlanCheck();
				pc.setPlanId(tempPlanCheck.getPlanId());
				pc.setSellerId(tempPlanCheck.getSellerId());
				pc.setFeedbackCount(Integer.valueOf(PlanCheck.FEEDBACKCCOUNT_TWO)); 
 				pc.setFeedbackStatus(String.valueOf(PlanCheck.STATUS_FEEDBACKCSTATUS_NO));
				pc.setCreateTime(tempPlanCheck.getCreateTime());
				planCheckService.savePlanCheck(pc); */
			}	
 
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		} catch (YtoxlUserException e) {
			e.printStackTrace();
		}
		this.nextAction = "planCheck-listPlanCheck.htm?planCheckPage.params.feedbackCount=1&reportMenuId=jsgl_one";
		return "finishOneCheck";
	}
	
	/**
	 * 第二次对账
	 * @return
	 */
	public String finishTwoCheck(){
	 	 
		try {
			
			PlanCheck tempPlanCheck = planCheckService.getPlanCheckByPlanCheckId(planCheck.getPlanCheckId());
			if(tempPlanCheck!=null){
				planCheckService.updateStatusByPlanCheckId(planCheck.getPlanCheckId(),userService.getCurrentUser().getUserId());
				
			}	
		} catch (UhomeStoreException e) {
		logger.error(e.getMessage());
		}  catch (YtoxlUserException e) {
 			e.printStackTrace();
		}
		this.nextAction = "planCheck-listPlanCheck.htm?planCheckPage.params.feedbackCount=2&reportMenuId=jsgl_two";

		return "finishTwoCheck";
	}
	/**
	 * 获取全部商家
	 * @return
	 */
	public List<Seller> getSellers() {
		List<Seller> sellers = new ArrayList<Seller>();
		try {
			sellers = userInfoService.listSellers();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return sellers;
	}
	
	/**
	 * 获取全部特卖
	 * @return
	 */
	public List<Plan> getPlans() {
		List<Plan> plans = new ArrayList<Plan>();
			try {
				plans = planService.listAllPlans();
			} catch (UhomeStoreException e) {
				logger.error(e.getMessage());
			}
		return plans;
	}
 

	public BasePagination<PlanCheck> getPlanCheckPage() {
		return planCheckPage;
	}
	public void setPlanCheckPage(BasePagination<PlanCheck> planCheckPage) {
		this.planCheckPage = planCheckPage;
	}
	
	public PlanCheck getPlanCheck() {
		return planCheck;
	}



	public void setPlanCheck(PlanCheck planCheck) {
		this.planCheck = planCheck;
	}



	public String getNowDateTime() {
		Calendar calendar = Calendar.getInstance();
		if(calendar.get(Calendar.DAY_OF_MONTH)>15){
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
		}else{
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-2);
		}
		return DateUtil.format(calendar.getTime(), "yyyy-MM");
	}

	/**
	 * 根据商家id获取排期信息进行结算
	 * @return
	 */
	public String searchSellerPlanCheck(){
		try {
			if(planCheckPagination == null){
				planCheckPagination = new BasePagination<PlanCheck>();
			}
			Integer sellerId = userInfoService.getCurrentSellerId();
			planCheckService.listSellerPlanCheckBySellerId(sellerId,planCheckPagination);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "searchSellerPlanCheck";
	}
	public String editSellerPlanCheck(){
		HttpServletRequest request =  ServletActionContext.getRequest();
		try {
			String planCheckId = request.getParameter("planCheckId");
			planCheckService.updateIsConfirmByPlanCheckId(Integer.parseInt(planCheckId));
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "searchSellerPlanCheck";
	}
	/**
	 * 保存商家针对排期的申请结算信息
	 * @return
	 */
	public String saveSellerPlanCheck(){
		HttpServletRequest request =  ServletActionContext.getRequest();
		try {
			String planId = request.getParameter("planId");
			Integer sellerId = userInfoService.getCurrentSellerId();
			Integer userId = userService.getCurrentUser().getUserId();
			PlanCheck planCheck = new PlanCheck();
			planCheck.setUserId(userId.toString());
			planCheck.setCreateTime(new Date());
			planCheck.setFeedbackCount(1);
			planCheck.setFeedbackStatus("0");
			planCheck.setFeedbackTime(new Date());
			planCheck.setPlanId(Integer.parseInt(planId));
			planCheck.setSellerId(sellerId);
			planCheckService.savePlanCheck(planCheck);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		} catch (YtoxlUserException e){
			logger.error(e.getMessage());
		}
		return "searchSellerPlanCheck";
	}


	public BasePagination<PlanCheck> getPlanCheckPagination() {
		return planCheckPagination;
	}

	public void setPlanCheckPagination(BasePagination<PlanCheck> planCheckPagination) {
		this.planCheckPagination = planCheckPagination;
	}
	public String getReportMenuId() {
		return reportMenuId;
	}
	public void setReportMenuId(String reportMenuId) {
		this.reportMenuId = reportMenuId;
	}
	public String getNextAction() {
		return nextAction;
	}
	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}
	
	
}
