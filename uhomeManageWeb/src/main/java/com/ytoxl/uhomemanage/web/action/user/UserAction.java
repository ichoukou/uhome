package com.ytoxl.uhomemanage.web.action.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.ibm.icu.text.SimpleDateFormat;
import com.opensymphony.xwork2.ActionContext;
import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExportExcel;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.Seller;
import com.ytoxl.module.uhome.uhomebase.dataobject.UserInfo;
import com.ytoxl.module.uhome.uhomebase.dataobject.tbl.SellerTbl;
import com.ytoxl.module.uhome.uhomebase.service.BrandService;
import com.ytoxl.module.uhome.uhomebase.service.UserInfoService;
import com.ytoxl.module.user.common.exception.YtoxlUserException;
import com.ytoxl.module.user.dataobject.User;
import com.ytoxl.module.user.service.UserService;
import com.ytoxl.uhomemanage.web.action.BaseAction;

public class UserAction extends BaseAction {
	private static final long serialVersionUID = -1683456422079577697L;
	private static Logger logger = LoggerFactory.getLogger(UserAction.class);
	
	private static final String SEARCH_SELLERS = "searchSellers";
	private static final String SEARCH_BUYERS = "searchBuyers";
	private static final String REDIRECT_SELLERS = "redirectSellers";
	private static final String SELLERS_WILDCARD = "sellerWildcard";
	private static final String SELLER="seller";
	private static final String STATUS_DENIY="1";//账号被禁用被禁用 、用户的账号重复
	private static final String STATUS_ACCESS="2";//账号良好
	private static final String SELLER_SINGLE="sellerDetailSingle";
	private static final String SELLER_SINGLE_PassWORD="sellerDetailSingleWord";
	
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private UserService userService;
	
	private BasePagination<Seller> sellerPage;
	private Seller seller;
	private BasePagination<UserInfo> buyerPage;
	private UserInfo buyer;
	private User user;
	private String nextAction;
	private InputStream excelStream;
	private String fileName;
	private String email;
	private Integer userId;
	private Map<String, List<Brand>> brandMap;
	
	/**
	 * 分页查询商家信息
	 * @return
	 */
	public String searchSellers(){
		if (sellerPage == null) {
			sellerPage=new BasePagination<Seller>();
		}
		try {
			userInfoService.searchSellers(sellerPage);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return SEARCH_SELLERS;
	}
	/**
	 * 分页查询用户信息
	 * @return
	 */
	public String searchBuyers(){
		if (buyerPage == null) {
			buyerPage=new BasePagination<UserInfo>();
			buyerPage.setSort("u.createTime");
			buyerPage.setDir("desc");
		}
		try {
			userInfoService.searchBuyers(buyerPage);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return SEARCH_BUYERS;
	}
	
	/**
	 * 更改用户状态
	 * @return
	 */
	public String updateUserStatus(){
		List<Integer> userIds = new ArrayList<Integer>();
		userIds.add(this.getUser().getUserId());
		try {
			userInfoService.updateUserStauts(userIds,this.getUser().getStatus());
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		this.nextAction="user-searchSellers.htm";
		return REDIRECT_SELLERS;
	}
	
	/**
	 * 重置用户密码
	 * @return
	 */
	public String resetPassword(){
		try {
			userInfoService.resetPassword(this.getUser().getUserId());
			setMessage(Boolean.TRUE.toString(),"重置密码成功");
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(),e.getLocalizedMessage());
		}
		return JSONMSG;
	}
	
	/**
	 * 商家基本信息界面
	 * @return
	 */
	public String seller(){
		try {
			if(seller != null && seller.getSellerId() != null){
				this.seller=userInfoService.getSellerById(seller.getSellerId(), true);
				List<Brand> brands = brandService.listBrandsBySellerId(seller.getSellerId());
				brandMap = new HashMap<String, List<Brand>>();
				for(Brand brand : brands){
					brand.setSeller(seller);
					String firstLetter = brand.getBrandPinYin();
					if(!brandMap.containsKey(firstLetter)){
						List<Brand> list = brandService.listBrandsByBrandPinYin(brand);
						brandMap.put(firstLetter, list);
					}
				}
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return SELLER;
	}
	
	/**
	 * 增加的时候判断邮箱是否重复
	 * @return
	 */
	public String validateEmailIsRepate(){
		try {
			boolean flag = userInfoService.validateEmailIsRepate(email, userId);
			setMessage(Boolean.toString(flag), null, null);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return JSONMSG;
	}
	/**
	 * 保存商家
	 * @return
	 */
	public String saveSeller(){
		try {
			if(this.getSeller().getSellerId()!=null && !this.getSeller().getSellerId().equals("")){
				userInfoService.updateSeller(this.seller);
			}else{
				userInfoService.addSeller(this.seller);
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		this.setNextAction("user-searchSellers.htm");
		return REDIRECT_SELLERS;
	}
	/**
	 * 查看商家
	 * @return
	 */
	public String sellerDetail(){
		try {
			this.seller=userInfoService.getSellerById(this.seller.getSellerId(),false);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return SELLERS_WILDCARD;
	}
	
	/**
	 * 导出商家信息
	 * @return
	 * @throws IOException 
	 */
	public String listExportSellers() throws IOException{
		try {
			ActionContext ctx = ActionContext.getContext();
			HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);  
			List<SellerTbl> sellers=userInfoService.listExportSellers();
			ExportExcel<SellerTbl> ee = new ExportExcel<SellerTbl>(sellers);
			ActionContext ac = ActionContext.getContext();
	        ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
	        String x = sc.getRealPath("/")+"seller.xls";
	        ee.setMerge(true);
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        this.setFileName(sdf.format(new Date()).replace("-", "").replace(" ", "").replace(":", ""));
	        ee.export(x);
	        File file=new File(x);
	        this.setExcelStream(new FileInputStream(file));
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		} 
		return "excel";
	}
	
	public Short[] getSupplierTypes(){
		return Seller.SUPPLIER_TYPES;
	}
	
	/**
	 * 查看商家
	 * @return
	 */
	public String editPassword(){
		return SELLERS_WILDCARD;
	}
	
	public String  savePassword(){
		try {
			userInfoService.savePassword(this.seller);
			setMessage(Boolean.TRUE.toString(),"修改密码成功");
		} catch (UhomeStoreException e) {
			e.printStackTrace();
			setMessage(Boolean.FALSE.toString(),e.getMessage());
		}
		return JSONMSG;
	}
	/**
	 * 验证添加商家登录名
	 * @return
	 */
	public String  validLoginName(){
		if(!userService.repeatUsername(this.seller.getUser().getUsername())){
			 setMessage(STATUS_DENIY);//用户账号重复
		}else{
			 setMessage(STATUS_ACCESS);
		}
		return JSONMSG;
	}
	
	/**
	 * 验证用户密码一致性
	 * @return
	 */
	public String validPassword(){
		try {
			String userName = userService.getCurrentUser().getUsername();
			String password = seller.getUser().getPassword();
			boolean flag = userService.checkPassword(userName, password);
			if(flag){
				setMessage(Boolean.TRUE.toString());
			}else{
				setMessage(Boolean.FALSE.toString());
			}
		} catch (YtoxlUserException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(),e.getMessage());
		}
		return JSONMSG;
	}
	
	/**
	 * 查看商家
	 * @return
	 */
	public String sellerDetailSeller(){
		try {
			try {
				this.seller=userInfoService.getSellerBySellerUserId(userService.getCurrentUser().getUserId(),false);
			} catch (YtoxlUserException e) {
				e.printStackTrace();
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return SELLER_SINGLE;
	}
	
	/**
	 * 卖家账号修改
	 * @return
	 */
	public String  getPasswordSell(){
		return SELLER_SINGLE_PassWORD;
	}
	
	/**
	 * 卖家账号修改
	 * @return
	 */
	public String  savePasswordSell(){
		try {
			userInfoService.savePassword(this.getSeller());
			setMessage(Boolean.TRUE.toString(),"修改密码成功");
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(),e.getMessage());
		}
		return JSONMSG;
	}
	
	public BasePagination<Seller> getSellerPage() {
		return sellerPage;
	}

	public void setSellerPage(BasePagination<Seller> sellerPage) {
		this.sellerPage = sellerPage;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public BasePagination<UserInfo> getBuyerPage() {
		return buyerPage;
	}

	public void setBuyerPage(BasePagination<UserInfo> buyerPage) {
		this.buyerPage = buyerPage;
	}

	public UserInfo getBuyer() {
		return buyer;
	}

	public void setBuyer(UserInfo buyer) {
		this.buyer = buyer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Map<String, List<Brand>> getBrandMap() {
		return brandMap;
	}

	public void setBrandMap(Map<String, List<Brand>> brandMap) {
		this.brandMap = brandMap;
	}

	public String getNextAction() {
		return nextAction;
	}

	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}
}
