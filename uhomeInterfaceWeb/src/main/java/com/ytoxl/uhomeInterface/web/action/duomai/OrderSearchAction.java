package com.ytoxl.uhomeInterface.web.action.duomai;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.core.common.utils.StringUtils;
import com.ytoxl.module.uhome.uhomeInterface.model.duomai.OrderBean;
import com.ytoxl.module.uhome.uhomeInterface.model.duomai.OrderContent;
import com.ytoxl.module.uhome.uhomeInterface.service.OrderSearchService;
import com.ytoxl.module.uhome.uhomebase.common.CommonConstants;
import com.ytoxl.module.uhome.uhomebase.common.utils.DateUtil;
import com.ytoxl.uhomeInterface.web.action.BaseAction;


/** 订单查询接口
 * @author zhiming.zeng
 *
 */
public class OrderSearchAction extends BaseAction{

	private static Logger logger = LoggerFactory.getLogger(OrderSearchAction.class);

	private static final long serialVersionUID = -6821578339377561369L;
	
	private OrderBean bean;
	
	@Autowired
	private OrderSearchService orderSearcherService;
	
	private BasePagination<OrderBean> orderPage;
	
	/** 本系统对联盟所作的标识	 */
	private String union_id;
	/**订单开始时间*/
	private Date stime;
	/**订单结束时间*/
	private Date etime;
	/**当前页码数*/
	private Integer current_num;
	/**token 防止恶意访问 */
	@Value("${duomai.token}")
	private String tokenMy;
	/**token 防止恶意访问 */
	private String token;
	
	/** 根据联盟标识 查询每个时间段的 订单详情
	 * @return json type String
	 */
	public String orderSearch(){
		try{
			if(StringUtils.isEmpty(union_id)){//判断空值
				setMessage(0, "uniond_id为空!", current_num, false);
				logger.error("uniond_id为空!");
				return JSONMSG;
			}else{
				if(token.equals(tokenMy)){
					if(stime==null||etime==null){
						setMessage(0, "时间类型非法或未填写!", current_num, false);
						logger.error("时间类型非法或未填写!");
						return JSONMSG;
					}else{
						if(DateUtil.hourInterval(stime,etime)<=0.0){//比较时间
							if(DateUtil.isHourTimeDifference(stime,etime,24)){
								if (orderPage == null) {
					    			orderPage=new BasePagination<OrderBean>();
								}
								Map<String, String> searchParams = orderPage.getParams();
								if(searchParams == null){
				        			searchParams =new HashMap<String, String>();
				        			orderPage.setParams(searchParams);
				        		}
				        		searchParams.put("beginTime", DateUtil.getDateStr(stime, "yyyy-MM-dd"));
				        		searchParams.put("endTime", DateUtil.getDateStr(etime, "yyyy-MM-dd"));
				        		searchParams.put("unionId", union_id);
				        		if(current_num==null||current_num<0){
				        			setMessage(0, "页码需正确填写!", current_num, false);
									logger.error("页码需正确填写!");
									return JSONMSG;
				        		}
				        		searchParams.put("current_num", current_num.toString());
				        		orderPage.setLimit(CommonConstants.PAGE_SIZE);
				        		orderPage.setCurrentPage(current_num);
								bean = orderSearcherService.searchOrderContent(orderPage);
							}else {
								setMessage(0, "查询时间范围过大!", current_num, false);
								logger.error("查询时间范围过大!");
								return JSONMSG;
							}
						}else{
							setMessage(0, "开始时间大于结束时间!", current_num, false);
							logger.error("开始时间大于结束时间!");
							return JSONMSG;
						}
					}
				}else{
						setMessage(0, "token不正确!", current_num,false);
						logger.error("token不正确!");
						return JSONMSG;
				}
			}
		}catch(Exception e){
			setMessage(0, "查询失败!", current_num,false);
			logger.error(e.getMessage());
			return JSONMSG;
		}
		return JSONMSG;
	}
	public void addActionError(String anErrorMessage){
			logger.error(anErrorMessage);
	}
	public void addActionMessage(String aMessage){
			logger.error(aMessage);
	}
	public void addFieldError(String fieldName, String errorMessage){
		   logger.error(errorMessage+":"+fieldName);
	}
	public BasePagination<OrderBean> getOrderPage() {
		return orderPage;
	}

	public void setOrderPage(BasePagination<OrderBean> orderPage) {
		this.orderPage = orderPage;
	}


	public String getTokenMy() {
		return tokenMy;
	}
	public void setTokenMy(String tokenMy) {
		this.tokenMy = tokenMy;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public Integer getCurrent_num() {
		return current_num;
	}
	public void setCurrent_num(Integer current_num) {
		this.current_num = current_num;
	}
	public OrderBean getBean() {
		return bean;
	}
	public void setBean(OrderBean bean) {
		this.bean = bean;
	}
	public OrderSearchService getOrderSearcherService() {
		return orderSearcherService;
	}

	public void setOrderSearcherService(OrderSearchService orderSearcherService) {
		this.orderSearcherService = orderSearcherService;
	}

	public String getUnion_id() {
		return union_id;
	}

	public void setUnion_id(String union_id) {
		this.union_id = union_id;
	}

	public Date getStime() {
		return stime;
	}

	public void setStime(Date stime) {
		this.stime = stime;
	}

	public Date getEtime() {
		return etime;
	}

	public void setEtime(Date etime) {
		this.etime = etime;
	}
	public  void setMessage(Integer status,String info,List<OrderContent> order,Integer current_num,Boolean is_end){
		bean = new OrderBean(status,info,order,current_num,is_end);
	}
	public void setMessage(Integer status,String info,Integer current_num,Boolean is_end){
		setMessage(status,info,null,current_num,is_end);
	} 
	
}
