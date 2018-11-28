package com.ytoxl.uhomemanage.web.action.coupon;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibm.icu.text.SimpleDateFormat;
import com.opensymphony.xwork2.ActionContext;
import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExportExcel;
import com.ytoxl.module.uhome.uhomebase.dataobject.Coupon;
import com.ytoxl.module.uhome.uhomebase.dataobject.Event;
import com.ytoxl.module.uhome.uhomebase.dataobject.EventRange;
import com.ytoxl.module.uhome.uhomebase.service.CouponService;
import com.ytoxl.uhomemanage.web.action.BaseAction;

@SuppressWarnings("serial")
public class CouponAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(CouponAction.class);
	
	private static final String SEARCH_COUPONS = "searchCoupons";
	private static final String EDIT_COMMON_COUPON = "editCommonCoupon";
	private static final String EDIT_WECHAT_COUPON = "editWechatCoupon";
	private static final String EDIT_COUPON_PACKAGE = "editCouponPackage";
	private static final String GET_EVENT_RANGES = "getEventRanges";
	
	private BasePagination<Coupon> couponPage;
	private Event event;
	private Short type;
	private List<Event> events;
	private List<BigDecimal> allowanceList;
	private Date startTime;
	private Date endTime;
	private List<EventRange> eventRangeList;
	
	private InputStream excelStream;
	private String fileName;
	
	@Autowired
	private CouponService couponService;
	
	/**
	 * 查询优惠券
	 * @return
	 */
	public String searchCoupons(){
		try {
			if (couponPage == null) {
				couponPage = new BasePagination<Coupon>();
			}
			allowanceList = couponService.listAllowances();
			couponService.searchCoupons(couponPage);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return SEARCH_COUPONS;
	}
	
	/**
	 * 配置普通优惠券
	 * @return
	 */
	public String editCommonCoupon(){
		try {
			events = couponService.listEvents(Event.TYPE_COMMON);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return EDIT_COMMON_COUPON;
	}
	
	/**
	 * 配置微信优惠券
	 * @return
	 */
	public String editWechatCoupon(){
		try {
			events = couponService.listEvents(Event.TYPE_WECHAT);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return EDIT_WECHAT_COUPON;
	}
	
	/**
	 * 配置优惠券礼包
	 * @return
	 */
	public String editCouponPackage(){
		try {
			events = couponService.listEvents(Event.TYPE_PROMOTION);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return EDIT_COUPON_PACKAGE;
	}
	
	/**
	 * 保存
	 * @return
	 */
	public String save(){
		try {
			couponService.saveEvent(event);
			setMessage(Boolean.TRUE.toString(),"保存成功");
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
		return JSONMSG;
	}
	
	/**
	 * 生成优惠券
	 * @return
	 */
	/*public String create(){
		try {
			couponService.createCoupon(event.getEventId(), null);
			setMessage(Boolean.TRUE.toString(),"生成优惠券成功");
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return JSONMSG;
	}*/
	
	/**
	 * 导出
	 * @return
	 */
	public String export(){
		try {
			List<Coupon> coupons = couponService.listCoupons(event.getEventId());
			
			ActionContext ac = ActionContext.getContext();
	        ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        this.setFileName(sdf.format(new Date()).replace("-", "").replace(" ", "").replace(":", ""));
	        String x = sc.getRealPath("/")+"couponReport.xls";
			if(coupons==null || coupons.size()==0){
				FileOutputStream fos= new FileOutputStream(new File(x));
				BufferedOutputStream buff=new BufferedOutputStream(fos);
				buff.write("".getBytes());
			}else{
				ExportExcel<Coupon> ee = new ExportExcel<Coupon>(coupons);
				 ee.export(x);
			}
			File file=new File(x);
			this.setExcelStream(new FileInputStream(file));
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}catch (IOException e) {
			logger.error(e.getMessage());
		}
		return "excel";
	}
	
	/**
	 * 非常规删除
	 * @return
	 */
	public String abnormalDel(){
		try {
			couponService.abnormalDeleteEvent(event);
			setMessage(Boolean.TRUE.toString(),"删除成功");
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
		return JSONMSG;
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		try {
			couponService.deleteEvent(event);
			setMessage(Boolean.TRUE.toString(),"删除成功");
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
		return JSONMSG;
	}
	
	/**
	 * 获取适用范围
	 * @return
	 */
	public String getEventRanges(){
		try {
			eventRangeList = couponService.getEventRanges(startTime, endTime);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
		return GET_EVENT_RANGES;
	}
	
	/**
	 * 激活状态
	 * @return
	 */
	public Short[] getActiveStatuses(){
		return Coupon.ACTIVESTATUSES;
	}
	
	/**
	 * 状态
	 * @return
	 */
	public Short[] getStatuses() {
		return Coupon.STATUSES;
	}

	public BasePagination<Coupon> getCouponPage() {
		return couponPage;
	}

	public void setCouponPage(BasePagination<Coupon> couponPage) {
		this.couponPage = couponPage;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<BigDecimal> getAllowanceList() {
		return allowanceList;
	}

	public void setAllowanceList(List<BigDecimal> allowanceList) {
		this.allowanceList = allowanceList;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<EventRange> getEventRangeList() {
		return eventRangeList;
	}

	public void setEventRangeList(List<EventRange> eventRangeList) {
		this.eventRangeList = eventRangeList;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
