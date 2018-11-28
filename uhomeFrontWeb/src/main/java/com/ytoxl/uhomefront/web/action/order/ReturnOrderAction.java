package com.ytoxl.uhomefront.web.action.order;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.core.common.utils.DateUtil;
import com.ytoxl.module.uhome.uhomebase.common.CommonConstants;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Region;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderExpress;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderHead;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderItem;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturn;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturnItem;
import com.ytoxl.module.uhome.uhomeorder.service.OrderService;
import com.ytoxl.module.uhome.uhomeorder.service.ReturnOrderService;
import com.ytoxl.module.user.common.exception.YtoxlUserException;
import com.ytoxl.module.user.service.UserService;
import com.ytoxl.uhomefront.web.action.BaseAction;

/**
 * @author zongmingliang
 *
 */
public class ReturnOrderAction extends BaseAction {

	private static final long serialVersionUID = 1563032240216719019L;
	private static Logger logger = LoggerFactory.getLogger(ReturnOrderAction.class);
	private static final String My_FH_RESULT ="myFhResult";
	private static final String My_CHOOSE_TH_RESULT ="ordReturnReasons";
	private static final String My_ALREAY_TH_RESULT ="returnOrders";
	private static final String My_ALREAY_TH_SINGLE ="returnSingle";
	private BasePagination<OrderHead> orderPage;//当前用户下面所有的可以退货的订单
	private BasePagination<OrderReturn> orderReturnPage;//当前用户下面所有的可以退货的订单
	private OrderReturn orderReturn;//单个退货详情
	private OrderReturnItem orderReturnItem;//退货明细
	private OrderHead orderHead;
	private OrderItem orderItem;//orderItem
	private Region region;
	private OrderExpress express;
	private Integer tuihuoOrderIds;//退货物品的订单id
	private List<Integer> itemIds;
	private String tuihuoProductIds;//商品退货的id号码
	private String ps;//付款状态 0：未付款 1：已付款

	@Autowired
	private OrderService orderService;
	@Autowired
	private ReturnOrderService returnOrderService;
	@Autowired
	private UserService userService;
	
	/**
     * 分页
     * 查找当前已发货的订单	
     * @return
     */
    public String myOrders(){
    	if (orderPage == null) {
    		orderPage=new BasePagination<OrderHead>();
			try {
				Integer userId  = userService.getCurrentUser().getUserId();
				Map<String,String> params = new HashMap<String,String>();
				Date now = new Date();
				params.put("userId",userId.toString());
				if(tuihuoOrderIds != null){
					params.put("orderId",tuihuoOrderIds.toString());
				}else{
					params.put(CommonConstants.BEGINTIME, DateUtil.toDay(DateUtil.previousSevenDate(now)));
					params.put(CommonConstants.ENDTIME, DateUtil.toDay(now));
				}
				orderPage.setParams(params);
			} catch (YtoxlUserException e) {
				logger.error("===myOrders()===获取当前用户信息异常：{}",e.getMessage());
			}
    	}
    	try {
    		orderService.getdeliveredOrders(orderPage);
    	} catch (UhomeStoreException e) {
    		logger.error("===myOrders()===查询用户可退货订单异常：{}",e.getMessage());
    	}
    	return My_FH_RESULT;
    }
    
    /**
     * 
     * 查找当前用户的已选择退货的订单	
     * @return
     * @throws UhomeStoreException 
     */
    public String myChooseOrders() throws UhomeStoreException{
    	try{
		Integer userId  = userService.getCurrentUser().getUserId();
		orderHead = returnOrderService.getReturnOrder(userId,tuihuoOrderIds, itemIds);
    	}catch(Exception e){
    		logger.error("===myChooseOrders()===查看用户选中退货订单信息异常：{}",e.getMessage());
    		throw new UhomeStoreException(e.getMessage());
    	}
    	return My_CHOOSE_TH_RESULT;
    }
    
    /**
     * 添加退货订单商品的信息到当前用户底下
     * @return
     */
    public String addReturnMssage(){
    	try {
    		returnOrderService.saveOrderReturnModel(orderReturn);
			setMessage(Boolean.TRUE.toString(),"提交成功,客服人员将会尽快与您取得联系！");
		} catch (UhomeStoreException e) {
			setMessage(Boolean.FALSE.toString(),"退货遇到异常！");
		}
		return JSONMSG;
    }
    
    /**
     *分页查询当前用户下面所有的退货记录
     * @return
     */
   public String myReturnOrders(){
	   Map<String, String> searchParams = new HashMap<String, String>();
	   if (orderReturnPage == null) {
		   orderReturnPage= new BasePagination<OrderReturn>();
	   		Date now = new Date();
	   		searchParams.put(CommonConstants.BEGINTIME, DateUtil.toDay(DateUtil.previousSevenDate(now)));
	   		searchParams.put(CommonConstants.ENDTIME, DateUtil.toDay(now));
	   		orderReturnPage.setParams(searchParams);
	   }
	   try {
		   orderReturnPage.getParams().put("userId",Integer.valueOf(userService.getCurrentUser().getUserId()).toString());
		   returnOrderService.getMyReturnOrders(orderReturnPage);
	   }catch (Exception e) {
			logger.error("===myReturnOrders()===查询当前用户退货记录信息异常：{}",e.getMessage());
	   }
	   return My_ALREAY_TH_RESULT;
   }
   
   /***
    * 根据orderReturnId 查询当前的退货订单详情
    * @return
    */
    public String getReturnOrder(){
    	try {
			orderReturn = returnOrderService.getOrderReturnByOrderReturnId(orderReturn.getOrderReturnId());
		} catch (UhomeStoreException e) {
			logger.error("===getReturnOrder()===查询当前的退货订单详情异常：{}",e.getMessage());
		}
		return My_ALREAY_TH_SINGLE;
    }
    
    /**
     * 添加发运信息
     * @return
     */
    public String addMail(){
    	try {
    		returnOrderService.addmail(express);
    		setMessage("添加成功！");
		} catch (UhomeStoreException e) {
			logger.error("===addMail()===添加发运信息异常：{}",e.getMessage());
		}
		 return JSONMSG;
    }
    
    /***
     * 获取当前退货记录未通过的原因
     * 
     * @return
     */
    public String getNOpassReason(){
    	OrderReturnItem reItem =null;
    	try {
    		reItem = returnOrderService.getOrderReturnItemById(orderReturnItem.getOrderReturnItemId());
    		if(reItem!=null){
    			setMessage(reItem.getNoPassReason());
    		}
		} catch (UhomeStoreException e) {
			logger.error("===getNOpassReason()===获取当前退货记录未通过原因异常：{}",e.getMessage());
		}
    	return JSONMSG;
    }

	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}
	public OrderReturn getOrderReturn() {
		return orderReturn;
	}
	public void setOrderReturn(OrderReturn orderReturn) {
		this.orderReturn = orderReturn;
	}
	public OrderItem getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}
	public OrderHead getOrderHead() {
		return orderHead;
	}
	public void setOrderHead(OrderHead orderHead) {
		this.orderHead = orderHead;
	}
	public Integer getTuihuoOrderIds() {
		return tuihuoOrderIds;
	}
	public void setTuihuoOrderIds(Integer tuihuoOrderIds) {
		this.tuihuoOrderIds = tuihuoOrderIds;
	}
	public String getTuihuoProductIds() {
		return tuihuoProductIds;
	}
	public void setTuihuoProductIds(String tuihuoProductIds) {
		this.tuihuoProductIds = tuihuoProductIds;
	}
	public List<Integer> getItemIds() {
		return itemIds;
	}
	public void setItemIds(List<Integer> itemIds) {
		this.itemIds = itemIds;
	}
	public BasePagination<OrderHead> getOrderPage() {
		return orderPage;
	}
	public void setOrderPage(BasePagination<OrderHead> orderPage) {
		this.orderPage = orderPage;
	}
	public BasePagination<OrderReturn> getOrderReturnPage() {
		return orderReturnPage;
	}
	public void setOrderReturnPage(BasePagination<OrderReturn> orderReturnPage) {
		this.orderReturnPage = orderReturnPage;
	}
	public OrderReturnItem getOrderReturnItem() {
		return orderReturnItem;
	}
	public void setOrderReturnItem(OrderReturnItem orderReturnItem) {
		this.orderReturnItem = orderReturnItem;
	}

	public OrderExpress getExpress() {
		return express;
	}

	public void setExpress(OrderExpress express) {
		this.express = express;
	}

	public String getPs() {
		return ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}
	
}
