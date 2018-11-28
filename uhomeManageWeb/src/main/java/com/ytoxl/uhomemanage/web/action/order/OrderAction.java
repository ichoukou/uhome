package com.ytoxl.uhomemanage.web.action.order;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibm.icu.text.SimpleDateFormat;
import com.opensymphony.xwork2.ActionContext;
import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.core.common.utils.StringUtils;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.common.utils.DateUtil;
import com.ytoxl.module.uhome.uhomebase.common.utils.ExcelUtils;
import com.ytoxl.module.uhome.uhomebase.common.utils.PropertyUtil;
import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExportExcel;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.Express;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductCategory;
import com.ytoxl.module.uhome.uhomebase.dataobject.Seller;
import com.ytoxl.module.uhome.uhomebase.dataobject.UserInfo;
import com.ytoxl.module.uhome.uhomebase.dataobject.resultmap.ExpressMessage;
import com.ytoxl.module.uhome.uhomebase.service.BrandService;
import com.ytoxl.module.uhome.uhomebase.service.ExpressService;
import com.ytoxl.module.uhome.uhomebase.service.ProductService;
import com.ytoxl.module.uhome.uhomebase.service.UserInfoService;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderExpress;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderHead;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderItem;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderPayment;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturn;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturnExport;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturnItem;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturnPayment;
import com.ytoxl.module.uhome.uhomeorder.service.OrderExpressService;
import com.ytoxl.module.uhome.uhomeorder.service.OrderPaymentService;
import com.ytoxl.module.uhome.uhomeorder.service.OrderService4Manage;
import com.ytoxl.module.uhome.uhomeorder.service.ReturnOrderService;
import com.ytoxl.module.user.common.exception.YtoxlUserException;
import com.ytoxl.module.user.dataobject.Urole;
import com.ytoxl.module.user.dataobject.User;
import com.ytoxl.module.user.dataobject.resultmap.UserModel;
import com.ytoxl.module.user.mapper.UserMapper;
import com.ytoxl.module.user.security.CustomUserDetails;
import com.ytoxl.module.user.service.UserService;
import com.ytoxl.uhomemanage.web.action.BaseAction;

public class OrderAction extends BaseAction {

	private static final long serialVersionUID = -1683456422079577697L;
	private static Logger logger = LoggerFactory.getLogger(OrderAction.class);
	private static final String ORDER_WILDCARD="orderWildcard";
	//后台订单管理
	private static final String SEARCHORDERS4MANAGER = "searchOrders4Manager";
	private static final String SEARCHORDERS4SELLER = "searchOrders4Seller";
	
	//后台管理员查看订单明细
	private static final String GETORDER4MANAGER = "getOrder4Manager";
	
	private static final String GETORDER4SELLER = "getOrder4Seller";

	private BasePagination<OrderHead> orderPage;

	@Autowired
	private UserMapper<User> userMapper;
	@Autowired
	private BrandService brandService;
	@Autowired
	private ProductService productService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private OrderService4Manage orderService4Manage;
	@Autowired
	private ReturnOrderService returnOrderService;
	@Autowired
	private ExpressService expressService;
	
	@Autowired
	private OrderExpressService orderExpressService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderPaymentService orderPaymentService;
	
	private OrderHead orderHead;
	private OrderReturn orderReturn;
	//有多个退货订单
	private List<OrderReturn> orderReturns;
	private UserInfo user;
	private OrderPayment orderPayment;
	
	private InputStream excelStream;
	private String fileName;
	private Integer status;
	
	private Boolean isAdmin;
	private File file;
	
	private List<Express> listExpresses;
	private OrderExpress orderExpress;
	//用于卖家对退货商品的审核
	private OrderReturnItem orderReturnItem;
	//用于 管理员确认退款
	private OrderReturnPayment orderReturnPayment;
	
	public BasePagination<OrderHead> getOrderPage() {
		return orderPage;
	}

	public void setOrderPage(BasePagination<OrderHead> orderPage) {
		this.orderPage = orderPage;
	}
	private Boolean validRole() throws YtoxlUserException {
		CustomUserDetails ud=userService.getCurrentUser();
	 	UserModel userModel=userService.getUserModelHave(ud.getUserId());
	 	//得到用户关联的角色
	 	List<Urole> roles=userModel.getSelectUroles();
	 	if(roles.size()>0){
	 		if(roles.get(0).getUroleId().intValue() == UserInfo.USER_ROLE_SELLER.intValue()){
	 			return false;
	 		}else 
//	 			if(roles.get(0).getUroleId().intValue() == UserInfo.USER_ROLE_ADMIN.intValue())
	 			{
	 			return true;
	 		}
	 	}
	 	return false;
	}
	public String searchOrders(){
		try {
			if (orderPage == null) {
				orderPage = new BasePagination<OrderHead>();
			}
			isAdmin=validRole();
			if(! isAdmin){
				Seller seller=userInfoService.getSellerByUserId(userService.getCurrentUser().getUserId());
				if(this.orderPage.getParams() == null ){
					Map<String, String > params=new HashMap<String, String>();
					params.put("sellerId", ""+seller.getSellerId());
					this.orderPage.setParams(params);
				}else{
					this.orderPage.getParams().put("sellerId", ""+seller.getSellerId());
					this.orderPage.getParams().remove("buyerId");
				}
			}
			orderService4Manage.searchOrders(this.orderPage);
			
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		} catch (YtoxlUserException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return ORDER_WILDCARD;
	}
	//订单后台
	public String searchOrders4Manager(){
		//TODO
		if (orderPage == null) {
			orderPage = new BasePagination<OrderHead>();
		}
		try {
			isAdmin=validRole();
			if(isAdmin){
				//管理员
				if(this.orderPage.getParams() == null ){
					Map<String, String > params=new HashMap<String, String>();
					//默认交易日期近一周
					Date curDate = new Date();
					String beginTime = DateUtil.toDay(DateUtil.add(curDate, Calendar.DATE, -6));
					String endTime = DateUtil.toDay(curDate);
					params.put("beginTime", beginTime);
					params.put("endTime", endTime);
					this.orderPage.setParams(params);
				}
				orderService4Manage.searchOrders4Manager(orderPage);
			}
		} catch (UhomeStoreException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}catch (YtoxlUserException e) {
			e.printStackTrace();
		}
		return SEARCHORDERS4MANAGER;
	}
	//卖家订单后台
	public String searchOrders4Seller(){
		//TODO
		if (orderPage == null) {
			orderPage = new BasePagination<OrderHead>();
		}
		try {
			Seller seller=userInfoService.getSellerByUserId(userService.getCurrentUser().getUserId());
			if(this.orderPage.getParams() == null ){
					Map<String, String > params=new HashMap<String, String>();
					if(seller.getSellerId()!=null){
						params.put("sellerId", ""+seller.getSellerId());
					}
					//默认交易日期近一周
					Date curDate = new Date();
					String beginTime = DateUtil.toDay(DateUtil.add(curDate, Calendar.DATE, -6));
					String endTime = DateUtil.toDay(curDate);
					params.put("beginTime", beginTime);
					params.put("endTime", endTime);
					this.orderPage.setParams(params);
			}else{
				if(seller.getSellerId()!=null){
						this.orderPage.getParams().put("sellerId", ""+seller.getSellerId());
				}
		    }
			orderService4Manage.searchOrders4Manager(orderPage);
		} catch (YtoxlUserException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (UhomeStoreException e) {
			e.printStackTrace();
		}
		return SEARCHORDERS4SELLER;
	}

	/**
	 * 获取商品分类
	 * @return
	 */
	public List<ProductCategory> getProductCategories(){
		List<ProductCategory> productCategories = new ArrayList<ProductCategory>();
		try {
			productCategories = productService.listProductCategories();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return productCategories;
	}

	/**
	 * 获取全部品牌
	 * @return
	 */
	public List<Brand> getBrands() {
		List<Brand> brands = new ArrayList<Brand>();
		try {
			brands = brandService.listBrands();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return brands;
	}
	/**
	 * 获取所需要的全部品牌
	 * @return
	 * @throws YtoxlUserException 
	 */
	public List<Brand> getBrandList() {
		List<Brand> brandlist = new ArrayList<Brand>();
		try {
			Integer sellerId = userInfoService.getCurrentSellerId();
			brandlist = userInfoService.getUserInfoBrandbyid(sellerId);
		} catch (UhomeStoreException e) {
			e.printStackTrace();
		}
		return brandlist;
	}
	
	/**
	 * 获取卖家可售品牌
	 * @return
	 */
	public List<Brand> getSellerBrands() {
		List<Brand> sellerBrands = new ArrayList<Brand>();
		try {
			Integer sellerId = userInfoService.getCurrentSellerId();
			sellerBrands = brandService.listBrandsBySellerId(sellerId);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return sellerBrands;
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
	 * 查看订单明细
	 * @return
	 */
	public String getOrder(){
		try {
			orderHead = orderService4Manage.getOrderById(orderHead.getOrderId());
			if(orderHead != null && orderHead.getUserId() != null){
				user = userInfoService.getMemberInfoByUserId(orderHead.getUserId());
			}
			/*if(orderHead.getOrderExpress()!=null && 
					((StringUtils.isNotEmpty(orderHead.getOrderExpress().getExpressName()) && orderHead.getOrderExpress().getExpressName().indexOf("圆通")>=0 )
							|| (orderHead.getOrderExpress().getExpress()!=null && StringUtils.isNotEmpty(orderHead.getOrderExpress().getExpress().getExpressName())&& orderHead.getOrderExpress().getExpress().getExpressName().indexOf("圆通")>=0 )
					)){
				orderHead.setDetailExpress(expressService.getExpressDetailInfo(orderHead.getOrderExpress().getMailNo()).get(0));
			}*/
			
			//查询快递信息
			OrderExpress orderExpress = orderHead.getOrderExpress();
			Express express = orderExpress.getExpress();
			if(express != null){
				//快递单号
				String mailNo = orderExpress.getMailNo();
				//快递公司代码
				String companyCode = express.getCompanyCode();
				ExpressMessage msg = expressService.getExpressDetailInfoFromKuaidi100(companyCode, mailNo);
				orderHead.setExpressMessage(msg);
			}
			
			orderReturns=returnOrderService.getOrderReturnById(orderHead.getOrderId());
			listExpresses=expressService.listExpresses();
			isAdmin=validRole();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
			return JSONMSG;
		} catch (YtoxlUserException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
		return "getOrder";
	}
	
	/**
	 * 管理员查看订单明细
	 */
	public String getOrder4Manager(){
		try {
			orderHead = orderService4Manage.getOrderById(orderHead.getOrderId());
			if(orderHead != null && orderHead.getUserId() != null){
				user = userInfoService.getMemberInfoByUserId(orderHead.getUserId());
			}
			/*if(orderHead.getOrderExpress()!=null && 
					((StringUtils.isNotEmpty(orderHead.getOrderExpress().getExpressName()) && orderHead.getOrderExpress().getExpressName().indexOf("圆通")>=0 )
							|| (orderHead.getOrderExpress().getExpress()!=null && StringUtils.isNotEmpty(orderHead.getOrderExpress().getExpress().getExpressName())&& orderHead.getOrderExpress().getExpress().getExpressName().indexOf("圆通")>=0 )
					)){
				orderHead.setDetailExpress(expressService.getExpressDetailInfo(orderHead.getOrderExpress().getMailNo()).get(0));
			}*/
			orderReturns=returnOrderService.getOrderReturnById(orderPage);
			//查询快递信息
			OrderExpress orderExpress = orderHead.getOrderExpress();
			Express express = orderExpress.getExpress();
			if(express != null){
				//快递单号
				String mailNo = orderExpress.getMailNo();
				//快递公司代码
				String companyCode = express.getCompanyCode();
				ExpressMessage msg = expressService.getExpressDetailInfoFromKuaidi100(companyCode, mailNo);
				orderHead.setExpressMessage(msg);
			}
			
			isAdmin=validRole();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
			return JSONMSG;
		} catch (YtoxlUserException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
		return GETORDER4MANAGER;
	}
	
	/**
	 * 卖家查看订单明细
	 */
	public String getOrder4Seller(){
		try {
			orderHead = orderService4Manage.getOrderById(orderHead.getOrderId());
			if(orderHead != null && orderHead.getUserId() != null){
				user = userInfoService.getMemberInfoByUserId(orderHead.getUserId());
			}
			/*if(orderHead.getOrderExpress()!=null && 
					((StringUtils.isNotEmpty(orderHead.getOrderExpress().getExpressName()) && orderHead.getOrderExpress().getExpressName().indexOf("圆通")>=0 )
							|| (orderHead.getOrderExpress().getExpress()!=null && StringUtils.isNotEmpty(orderHead.getOrderExpress().getExpress().getExpressName())&& orderHead.getOrderExpress().getExpress().getExpressName().indexOf("圆通")>=0 )
					)){
				try{
				orderHead.setDetailExpress(expressService.getExpressDetailInfo(orderHead.getOrderExpress().getMailNo()).get(0));
				}catch (UhomeStoreException e) {
					logger.error("获取物流信息异常！面单号："+orderHead.getOrderExpress().getMailNo());
				}
			}*/
			
			//查询快递信息
			OrderExpress orderExpress = orderHead.getOrderExpress();
			Express express = orderExpress.getExpress();
			if(express != null){
				//快递单号
				String mailNo = orderExpress.getMailNo();
				//快递公司代码
				String companyCode = express.getCompanyCode();
				ExpressMessage msg = expressService.getExpressDetailInfoFromKuaidi100(companyCode, mailNo);
				orderHead.setExpressMessage(msg);
			}
			
			orderReturns=returnOrderService.getOrderReturnById(orderPage);
			listExpresses=expressService.listExpresses();
			isAdmin=validRole();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
			return JSONMSG;
		} catch (YtoxlUserException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
		return GETORDER4SELLER;
	}
	
	/**
	 * 导出订单明细
	 * @return
	 */
	public String exportOrder(){
		try {
			if (this.orderPage == null) {
				this.orderPage = new BasePagination<OrderHead>();
			}
			isAdmin=validRole();
			if(! isAdmin){
				Seller seller=userInfoService.getSellerByUserId(userService.getCurrentUser().getUserId());
				if(this.orderPage.getParams() == null ){
					Map<String, String > params=new HashMap<String, String>();
					params.put("sellerId", ""+seller.getSellerId());
					this.orderPage.setParams(params);
				}else{
					this.orderPage.getParams().put("sellerId", ""+seller.getSellerId());
					this.orderPage.getParams().remove("buyerId");
				}
			}
			List<OrderHead> orders=orderService4Manage.listOrders(this.orderPage);
			ActionContext ac = ActionContext.getContext();
	        ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        this.setFileName(sdf.format(new Date()).replace("-", "").replace(" ", "").replace(":", ""));
	        String x = sc.getRealPath("/")+"/orders.zip";
			if(orders==null || orders.size()==0){
				orders=new ArrayList<OrderHead>();
			}//else{
				//订单头
				String ordersHead = sc.getRealPath("/")+"orders.xls";
				ExportExcel<OrderHead> e = new ExportExcel<OrderHead>(orders);
				e.setMerge(true);
				e.export(ordersHead);
				
				//订单明细
				String ordersDetail = sc.getRealPath("/")+"ordersDetail.xls";
				List<OrderItem> items =  new ArrayList<OrderItem>();
				for(OrderHead head : orders){
					items.addAll(head.getItems());
				}
				ExportExcel<OrderItem> ee = new ExportExcel<OrderItem>(items);
		        ee.setMerge(true);
		        ee.export(ordersDetail);
		        
		        //TODO 将2个xls压缩成zip文件下载
		        ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(x));
		        InputStream insOrdersHead = new FileInputStream(ordersHead);
		        InputStream insOrdersDetail = new FileInputStream(ordersDetail);
		        int count;
		        byte[] data = new byte[1024];
		        //压缩订单头部
		        zip.putNextEntry(new ZipEntry("orders.xls"));
		        while((count=insOrdersHead.read(data, 0, 1024))!=-1){
		        	zip.write(data, 0, count);
		        }
		        //gzip.flush();
		        //压缩订单明细
		        zip.putNextEntry(new ZipEntry("ordersDetail.xls"));
		        while((count=insOrdersDetail.read(data, 0, 1024))!=-1){
		        	zip.write(data, 0, count);
		        }
		        zip.flush();
		        zip.close();
		        insOrdersHead.close();
		        insOrdersDetail.close();
			//}
			
	        File file=new File(x);
	        this.setExcelStream(new FileInputStream(file));
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (YtoxlUserException e) {
			logger.error(e.getMessage());
		}
		return "excel";
	}
	
	/**导出 退货订单
	 * @return
	 */
	public String exportReturnOrders(){
		try{
			if (this.orderPage == null) {//判断查询条件是否为null
				this.orderPage = new BasePagination<OrderHead>();
			}
			isAdmin=validRole();//判断是否为管理员用户
			if(!isAdmin){//如果不是管理员
				Seller seller=userInfoService.getSellerByUserId(userService.getCurrentUser().getUserId());//根据当前当户的sellerId得到商家对象
				if(this.orderPage.getParams() == null ){//参数为空 就创建
					Map<String, String > params=new HashMap<String, String>();
					params.put("sellerId", ""+seller.getSellerId());
					this.orderPage.setParams(params);
				}else{//不为空 就添加查询参数
					this.orderPage.getParams().put("sellerId", ""+seller.getSellerId());
					this.orderPage.getParams().remove("buyerId");
				}
			}
			List<OrderReturnExport> orders = orderService4Manage.listReturnOrders(this.orderPage);//得到退货所属的订单
			ActionContext ac = ActionContext.getContext();
	        ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
			//导出订单的状态
	        String orderStatus = "全部";
	        Object obj = orderPage.getParams().get("status");
	        if(null != obj &&  obj.toString().length() > 0){
	        	Short status = Short.parseShort(obj.toString());
	        	orderStatus = PropertyUtil.getPropertyValue("order.status."+status,null);
	        	if(OrderHead.STATUS_RETURN.equals(status) || OrderHead.STATUS_ALLRETURN.equals(status)){
	        		orderStatus = "退货";
	        	}
	        	if(OrderHead.STATUS_NEW.equals(status)){
	        		orderStatus = "待付款";
	        	}
	        }
	        orderStatus += "订单";
	        //导出订单的名称
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String fName = orderStatus+sdf.format(new Date()).replace("-", "").replace(" ", "").replace(":", "");
//	        this.setFileName(fName);
//	        this.setFileName(new String(fName.getBytes("GBK"),"ISO8859-1"));
	        //判断浏览器
			HttpServletRequest request = ServletActionContext.getRequest();
			String userAgent = request.getHeader("USER-AGENT");
			String finalFileName = null;
			 if(StringUtils.contains(userAgent, "MSIE")){//IE浏览器
	               finalFileName = URLEncoder.encode(fName,"UTF8");
	         }else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
	               finalFileName = new String(fName.getBytes(), "ISO8859-1");
	         }else{
	               finalFileName = URLEncoder.encode(fName,"UTF8");//其他浏览器
	          }
//			this.setFileName(new String(fName.getBytes("utf-8"),"ISO8859-1"));
			this.setFileName(finalFileName);
	        String x = sc.getRealPath("/")+File.separator+fName+".xls";
	        //TODO
	        List<OrderItem> items =  new ArrayList<OrderItem>();
			for(OrderReturnExport head : orders){
				OrderItem item = head.getItem();
				item.setAddress(head.getAddress());
				item.setPayment(head.getPayment());
				if(null == item.getRebatePrice()){
					item.setRebatePrice(item.getClosingCost().multiply(new BigDecimal(item.getNum())));
				}
				item.setOrderStatus(head.getStatusChar());
				items.add(item);
			}
			ExportExcel<OrderItem> ee = new ExportExcel<OrderItem>(items);
	        ee.setMerge(true);
	        ee.export(x);
	        
	        File file=new File(x);
	        this.setExcelStream(new FileInputStream(file));
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (YtoxlUserException e) {
			logger.error(e.getMessage());
		}
	     return "excels";
	}
	
	
	
	/**
	 * 导出订单 2013-06-13
	 * @return
	 */
	public String exportOrders(){
		try{
			if (this.orderPage == null) {
				this.orderPage = new BasePagination<OrderHead>();
			}
			isAdmin=validRole();
			if(! isAdmin){
				Seller seller=userInfoService.getSellerByUserId(userService.getCurrentUser().getUserId());
				if(this.orderPage.getParams() == null ){
					Map<String, String > params=new HashMap<String, String>();
					params.put("sellerId", ""+seller.getSellerId());
					this.orderPage.setParams(params);
				}else{
					this.orderPage.getParams().put("sellerId", ""+seller.getSellerId());
					this.orderPage.getParams().remove("buyerId");
				}
			}
			List<OrderHead> orders=orderService4Manage.listOrders(this.orderPage);
			ActionContext ac = ActionContext.getContext();
	        ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
			//导出订单的状态
	        String orderStatus = "全部";
	        Object obj = orderPage.getParams().get("status");
	        if(null != obj &&  obj.toString().length() > 0){
	        	Short status = Short.parseShort(obj.toString());
	        	orderStatus = PropertyUtil.getPropertyValue("order.status."+status,null);
	        	if(OrderHead.STATUS_RETURN.equals(status) || OrderHead.STATUS_ALLRETURN.equals(status)){
	        		orderStatus = "退货";
	        	}
	        	if(OrderHead.STATUS_NEW.equals(status)){
	        		orderStatus = "待付款";
	        	}
	        }
	        orderStatus += "订单";
	        //导出订单的名称
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String fName = orderStatus+sdf.format(new Date()).replace("-", "").replace(" ", "").replace(":", "");
//	        this.setFileName(fName);
//	        this.setFileName(new String(fName.getBytes("GBK"),"ISO8859-1"));
	        //判断浏览器
			HttpServletRequest request = ServletActionContext.getRequest();
			String userAgent = request.getHeader("USER-AGENT");
			String finalFileName = null;
			 if(StringUtils.contains(userAgent, "MSIE")){//IE浏览器
	               finalFileName = URLEncoder.encode(fName,"UTF8");
	         }else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
	               finalFileName = new String(fName.getBytes(), "ISO8859-1");
	         }else{
	               finalFileName = URLEncoder.encode(fName,"UTF8");//其他浏览器
	          }
//			this.setFileName(new String(fName.getBytes("utf-8"),"ISO8859-1"));
			this.setFileName(finalFileName);
	        String x = sc.getRealPath("/")+File.separator+fName+".xls";
	        //TODO
	        List<OrderItem> items =  new ArrayList<OrderItem>();
			for(OrderHead head : orders){
				List<OrderItem> item = head.getItems();
				for(OrderItem i : item ){
					i.setAddress(head.getAddress());
					i.setPayment(head.getPayment());
					if(null == i.getRebatePrice()){
						i.setRebatePrice(i.getClosingCost().multiply(new BigDecimal(i.getNum())));
					}
					i.setOrderStatus(PropertyUtil.getPropertyValue("order.status."+i.getOrderStatus(), null));
					items.add(i);
				}
			}
			ExportExcel<OrderItem> ee = new ExportExcel<OrderItem>(items);
	        ee.setMerge(true);
	        ee.export(x);
	        
	        File file=new File(x);
	        this.setExcelStream(new FileInputStream(file));
		}catch (UhomeStoreException e) {
			logger.error("导出订单出错:"+e.getMessage());
		} catch (YtoxlUserException e) {
			logger.error("导出订单权限出错:"+e.getMessage());
		} catch (FileNotFoundException e) {
			logger.error("导出订单action 创建文件异常:"+e.getMessage());
		} catch (IOException e) {
			logger.error("导出订单action IO异常:"+e.getMessage());
		}
        return "excels";
	}
	
	/**
	 * 商家导出订单
	 * @return
	 */
	public String exportSellerOrders(){
		return this.exportOrders();
	}
	/**
	 * 确认发货
	 * @return
	 */
	public String confirmSendProduct(){
		try {
			String orderStatus=orderService4Manage.confirmSendProduct(this.orderExpress);
			setMessage(Boolean.TRUE.toString(),"发货成功",new String[]{orderStatus});
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(),e.getMessage());
		} catch (Exception e) {
			setMessage(Boolean.FALSE.toString(),e.getMessage());
		}
		return JSONMSG;
	}
	
	/**
	 * 审核通过
	 * @return
	 */
	public String passAudit(){
		try {
			//this.orderReturn.setStatus(OrderReturn.STATUS_ACCEPT);
			//将item设置为审核通过
			this.orderReturnItem.setStatus(OrderReturnItem.STATUS_ACCEPT);
			String status=orderService4Manage.audit(this.orderReturnItem);
			setMessage(Boolean.TRUE.toString(),"审核通过", new String[]{status});
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(),e.getMessage());
		} 
		return JSONMSG;
	}
	
	/**
	 * 审核不通过
	 * @return
	 */
	public String rejectAudit(){
		try {
			//this.orderReturn.setStatus(OrderReturn.STATUS_REFUSEED);
			//将item设置为不通过
			this.orderReturnItem.setStatus(OrderReturnItem.STATUS_REFUSEED);
			String status=orderService4Manage.audit(this.orderReturnItem);
			setMessage(Boolean.TRUE.toString(),"审核不通过",new String[]{status});
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(),e.getMessage());
		} 
		return JSONMSG;
	}
	
	/**
	 * 同意退款
	 * @return
	 */
	public String agreePayment(){
		try {
			//传过来的是orderReturnItemId  通过orderReturnItemId 查询orderReturnId he orderItemId
			//this.orderReturnItem;
			//OrderReturnPayment orderReturnPayment = this.orderReturn.getOrderReturnPayment();
			String status=orderService4Manage.agreePayment(orderReturnItem);
			setMessage(Boolean.TRUE.toString(),"操作成功",new String[]{status});
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(),e.getMessage());
		} 
		return JSONMSG;
	}
	
	/**
	 * 确认退款
	 * @return
	 */
	public String confirmPayment(){
		try {
			//this.orderPayment.setStatus(OrderPayment.STATUS_REFUND);
			//设置ip
			orderReturnPayment.setIpAddress(ServletActionContext.getRequest().getRemoteAddr());
			//退款成功
			orderReturnPayment.setStatus(OrderReturnPayment.STATUS_REFUND);
			String status=orderService4Manage.confirmPayment(orderReturnPayment);
			setMessage(Boolean.TRUE.toString(),"退款成功",new String[]{status});
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(),e.getMessage());
		} 
		return JSONMSG;
	}
	
	/**
	 * 批量发货
	 * @return
	 */
	public String batchUpload(){
		try {
			 InputStream inp = new FileInputStream(this.file);
			 ExcelUtils excel = new ExcelUtils();
			 excel.setSheetName("Sheet1");
			 List<String[]> orders = excel.read(inp);
			if(orders!=null && orders.size()>0){
				orderService4Manage.batchUpload(orders, user.getUserId());
				setMessage(Boolean.TRUE.toString(),"发货成功");
			}else{
				setMessage(Boolean.TRUE.toString(),"表格中没有数据");
			}
			
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(),e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(),e.getMessage());
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(),e.getMessage());
		}
		return JSONMSG;
	}
	
	public List<OrderReturn> getOrderReturns() {
		return orderReturns;
	}

	public void setOrderReturns(List<OrderReturn> orderReturns) {
		this.orderReturns = orderReturns;
	}

	/**
	 * 批量发货
	 * @return
	 */
	public String downloadTemplate(){
		try {
			ActionContext ac = ActionContext.getContext();
	        ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
	        String x = sc.getRealPath("/")+"/default_template.xlsx";
	        this.setExcelStream(new FileInputStream(new File(x)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(),e.getMessage());
		} 
		return "template";
	}

	
	/**
	 * 获取订单状态
	 * @return
	 */
	public Short[] getStatuses() {
		return OrderHead.STATUSES;
	}
	public Short[] getItemStatuses(){
		return OrderReturnItem.ITEM_STATUSES;
	}
	/**
	 * 获取支付状态待退款
	 * @return
	 */
	public Short getPaymentStatusArrived() {
		return OrderPayment.STATUS_WAITREFUND;
	}
	

	/**
	 * 退货订单 已经退款
	 * @return
	 */
	public Short getAlreadyRefund(){
		return OrderReturnPayment.STATUS_REFUND;
	}
	
	
	/**
	 * 获订单状态：待发货
	 * @return
	 */
	public Short getOrderStatusWaitSend() {
		return OrderHead.STATUS_WAITSEND;
	}
	
	/**
	 * 获订单状态：已发货
	 * @return
	 */
	public Short getOrderStatusSend(){
		return OrderHead.STATUS_SEND;
	}
	
	/**
	 * 订单状态：已完成
	 * @return
	 */
	public Short getOrderStatusFinished(){
		return OrderHead.STATUS_FINISHED;
	}
	
	/**
	 * 退货订单状态 未审核
	 * @return
	 */
	public Short getOrderReturnItemStatusNotAudit(){
		return OrderReturnItem.STATUS_NOTAUDIT;
	}
	
	/**
	 * 退货订单状态 审核通过
	 * @return
	 */
	public Short getOrderReturnItemStatusAccept(){
		return OrderReturnItem.STATUS_ACCEPT;
	}
	
	/**
	 *  退货订单状态 审核未通过
	 * @return
	 */
	public Short getOrderReturnItemStatusRefuseed(){
		return OrderReturnItem.STATUS_REFUSEED;
	}
	
	/**
	 *  退货订单状态 已收货
	 * @return
	 */
	public Short getOrderReturnItemStatusTakeGoods(){
		return orderReturnItem.STATUS_TAKE_GOODS;
	}
	
	/**
	 * 获支付状态：已支付
	 * @return
	 */
	public Short getPaymentStatusPayed() {
		return OrderPayment.STATUS_PAYED;
	}
	
	/**
	 * 退货获支付状态：待退款
	 * @return
	 */
	public Short getReturnPaymentStatusWaitRefund(){
		return OrderReturnPayment.STATUS_WAITREFUND;
	}
	
	/**
	 * 退货获支付状态：已退款
	 * @return
	 */
	public Short getPaymentStatusRefund() {
		return OrderReturnPayment.STATUS_REFUND;
	}
	
	public OrderReturnPayment getOrderReturnPayment() {
		return orderReturnPayment;
	}

	public void setOrderReturnPayment(OrderReturnPayment orderReturnPayment) {
		this.orderReturnPayment = orderReturnPayment;
	}

	public OrderHead getOrderHead() {
		return orderHead;
	}

	public void setOrderHead(OrderHead orderHead) {
		this.orderHead = orderHead;
	}

	public OrderReturn getOrderReturn() {
		return orderReturn;
	}

	public OrderReturnItem getOrderReturnItem() {
		return orderReturnItem;
	}

	public void setOrderReturnItem(OrderReturnItem orderReturnItem) {
		this.orderReturnItem = orderReturnItem;
	}

	public void setOrderReturn(OrderReturn orderReturn) {
		this.orderReturn = orderReturn;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public List<Express> getListExpresses() {
		return listExpresses;
	}

	public void setListExpresses(List<Express> listExpresses) {
		this.listExpresses = listExpresses;
	}

	public OrderExpress getOrderExpress() {
		return orderExpress;
	}

	public void setOrderExpress(OrderExpress orderExpress) {
		this.orderExpress = orderExpress;
	}

	public OrderPayment getOrderPayment() {
		return orderPayment;
	}

	public void setOrderPayment(OrderPayment orderPayment) {
		this.orderPayment = orderPayment;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
}
