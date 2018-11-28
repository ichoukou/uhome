package com.ytoxl.uhomefront.web.action.index;



import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.common.utils.BrowseUtil;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.BrandSort;
import com.ytoxl.module.uhome.uhomebase.dataobject.Plan;
import com.ytoxl.module.uhome.uhomebase.dataobject.Product;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductCategory;
import com.ytoxl.module.uhome.uhomebase.dataobject.UserInfo;
import com.ytoxl.module.uhome.uhomebase.dataobject.resultmap.ProductCategoryModel;
import com.ytoxl.module.uhome.uhomebase.service.BrandService;
import com.ytoxl.module.uhome.uhomebase.service.BrandSortService;
import com.ytoxl.module.uhome.uhomebase.service.PlanService;
import com.ytoxl.module.uhome.uhomebase.service.ProductService;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Advertisement;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Notice;
import com.ytoxl.module.uhome.uhomecontent.service.AdvService;
import com.ytoxl.module.uhome.uhomecontent.service.NoticeService;
import com.ytoxl.module.user.common.exception.YtoxlUserException;
import com.ytoxl.module.user.dataobject.Urole;
import com.ytoxl.module.user.dataobject.resultmap.MenuModel;
import com.ytoxl.module.user.security.CustomUserDetails;
import com.ytoxl.module.user.service.UserService;
import com.ytoxl.uhomefront.web.action.BaseAction;

/**
 * @author Administrator
 *
 */
public class IndexAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(IndexAction.class);
	private static final long serialVersionUID = -1683456422079577697L;
	private String ua;
	private MenuModel  menuModel;
	private static String DEFAULTINDEX = "default";
	@Autowired
	private UserService userService;
	@Autowired
	private PlanService planService;
	@Autowired
	private ProductService productService;
	@Autowired
	private AdvService advService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private BrandSortService brandSortService;
	//最新上线的品牌
	private List<Plan> lastOnLineBrands;
	//按品牌的折扣最低排序
	private List<Plan> lowestDiscountBrands;
	//最受欢迎的品牌 排序
	private List<Plan> mostPopularBrands;
	//即将上线的品牌
	private List<Plan> soonBrands;
	//人气商品
	private List<Product> mostHits;
	private long currentTime;
	//类目
	private ProductCategory productCategory;
	//往期特卖列表  用广告做
	private List<Advertisement> advs;
	//右部五个广告位
	private List<Advertisement> fiveAdvs;
	//热销品牌
	private List<Brand> hotBrands; 
	//首页顶部广告
	private Advertisement homeTopAdv;
	//首页品牌集中营
	private List<BrandSort> brands;

	@Value("${jspvar._domain}")
	private String domain;
	
	public String index(){
		String userAgent = ServletActionContext.getRequest().getHeader("USER-AGENT");// 得到用户浏览器的ua
		setUa(BrowseUtil.checkBrowse(userAgent));
		HttpServletRequest  request = ServletActionContext.getRequest();
		HttpServletResponse  response = ServletActionContext.getResponse();
		String refererUrl = request.getHeader("Referer"); 
		//String contentpath =(String)request.getContextPath();
		String joinMeUrl =(String) ServletActionContext.getRequest().getSession().getAttribute("url");
		String resultName=null;
		try {
			CustomUserDetails userDetails =  userService.getCurrentUser();
			List<Urole> roleList = userDetails.getUroles();
			for(Urole userRole : roleList){
				if(userRole.getUroleId().intValue()==UserInfo.USER_ROLE_BUYER.intValue()){
					request.getSession().setAttribute("user", userDetails.getUsername());
				}
			}
			if(null==refererUrl){
				resultName= SUCCESS;
			}else{
				if(joinMeUrl!=null){
					try {
						ServletActionContext.getRequest().getSession().removeAttribute("url");
						//response.sendRedirect(contentpath+joinMeUrl);
						  response.sendRedirect(joinMeUrl);
					} catch (IOException e) {
					}
					resultName= null;
				}else{
					try {
						response.sendRedirect(refererUrl);
						resultName= null;
					} catch (IOException e) {
					}
				}
			}
		} catch (YtoxlUserException e1) {
		}
		return resultName;
	}
	/**
	 * 默认首页
	 * @return
	 */
	public String defaultIndex(){
		//TODO 查找所有的类目 按照最新上线  最低折扣 最受欢迎 3个类型分别排序
		try{
			//最新上线的品牌
			lastOnLineBrands = planService.listPlanLastOnlineBrands();
			//按品牌的折扣最低排序
			lowestDiscountBrands = planService.listPlanLowestDiscountBrands();
			//最受欢迎的品牌 排序
			mostPopularBrands = planService.listPlanMostPopularBrands();
			//即将上线的品牌
			soonBrands = planService.listPlanSoonBrands();
			currentTime = System.currentTimeMillis()/1000;
			mostHits = productService.getProductListByHits(8);
			//往期特卖列表
			advs = advService.listHomeHistorySpecialAdv();
			//右部五个广告位
			fiveAdvs = advService.listHomeRightAdvertisement();
			//热销品牌
			hotBrands = brandService.listHotBrands();
			//首页顶部广告
			homeTopAdv = advService.getHomeTopAdv();
			//首页品牌集中营
			brands = brandSortService.getBrandListGroupByType();
		}catch (UhomeStoreException e) {
			logger.error("首页默认加载出错:"+e.getMessage());
		}
		return SUCCESS;
	}
	
	public void defaultIndex301(){
		// 301到新的url
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		StringBuffer newUrl = new StringBuffer(domain);
		response.setHeader("Location",newUrl.toString());
	}
	
	public String productCategoryIndex(){
		//TODO 按照类别id  分别按照最新上线  最低折扣 最受欢迎 3个类型分别排序
		Integer productCategoryId = productCategory.getProductCategoryId();
		//判断productCategoryId是否位数字
		
		if(null != productCategoryId){
			try{
				//最新上线的品牌
				lastOnLineBrands = planService.listPlanLastOnlineByCategoryId(productCategoryId);
				//按品牌的折扣最低排序
				lowestDiscountBrands = planService.listPlanLowestDiscountByCategoryId(productCategoryId);
				//最受欢迎的品牌 排序
				mostPopularBrands = planService.listPlanMostPopularByCategoryId(productCategoryId);
				//即将上线的品牌
				soonBrands = planService.listPlanSoonBrands();
				currentTime = System.currentTimeMillis()/1000;
				mostHits = productService.getProductListByHits(8);
				//往期特卖列表
				advs = advService.listHomeHistorySpecialAdv();
				//右部五个广告位
				fiveAdvs = advService.listHomeRightAdvertisement();
				//热销品牌
				hotBrands = brandService.listHotBrands();
				//首页顶部广告
				homeTopAdv = advService.getHomeTopAdv();
				//首页品牌集中营
				brands = brandSortService.getBrandListGroupByType();
				super.setSeoConfigByUrlKey(ServletActionContext.getRequest().getServletPath(), null);
			}catch (UhomeStoreException e) {
				logger.error("首页按"+productCategoryId+"类别id加载出错:"+e.getMessage());
			}
		}
		return DEFAULTINDEX;
	}
	
	//将搜索引擎的以前抓去的连接301
	public void productCategoryIndex301(){
		Integer productCategoryId = productCategory.getProductCategoryId();
		//判断productCategoryId是否位数字
		// 301到新的url
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		StringBuffer newUrl = new StringBuffer(domain);
//		StringBuffer newUrl = new StringBuffer();
		if(null != productCategoryId){
			for(Entry<String,Integer> entry:ProductCategoryModel.PCSMAP.entrySet()){
				if(entry.getValue().equals(productCategoryId)){
					//如果cateId与map中那个值相等 就是那个目录
					newUrl.append(entry.getKey()).append("/");
				}
			}
		}	
		response.setHeader("Location",newUrl.toString());
	}
	
	public List<Advertisement> getFiveAdvs() {
		return fiveAdvs;
	}

	public void setFiveAdvs(List<Advertisement> fiveAdvs) {
		this.fiveAdvs = fiveAdvs;
	}

	public MenuModel getMenuModel() {
		return menuModel;
	}
	public void setMenuModel(MenuModel menuModel) {
		this.menuModel = menuModel;
	}
	public String getUa() {
		return ua;
	}
	public void setUa(String ua) {
		this.ua = ua;
	}

	public List<Plan> getLastOnLineBrands() {
		return lastOnLineBrands;
	}

	public void setLastOnLineBrands(List<Plan> lastOnLineBrands) {
		this.lastOnLineBrands = lastOnLineBrands;
	}

	public List<Plan> getLowestDiscountBrands() {
		return lowestDiscountBrands;
	}

	public void setLowestDiscountBrands(List<Plan> lowestDiscountBrands) {
		this.lowestDiscountBrands = lowestDiscountBrands;
	}

	public List<Plan> getMostPopularBrands() {
		return mostPopularBrands;
	}

	public void setMostPopularBrands(List<Plan> mostPopularBrands) {
		this.mostPopularBrands = mostPopularBrands;
	}

	public List<Plan> getSoonBrands() {
		return soonBrands;
	}

	public void setSoonBrands(List<Plan> soonBrands) {
		this.soonBrands = soonBrands;
	}

	public List<Product> getMostHits() {
		return mostHits;
	}

	public void setMostHits(List<Product> mostHits) {
		this.mostHits = mostHits;
	}

	public long getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(long currentTime) {
		this.currentTime = currentTime;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public List<Advertisement> getAdvs() {
		return advs;
	}

	public void setAdvs(List<Advertisement> advs) {
		this.advs = advs;
	}

	public List<Brand> getHotBrands() {
		return hotBrands;
	}

	public void setHotBrands(List<Brand> hotBrands) {
		this.hotBrands = hotBrands;
	}

	public Advertisement getHomeTopAdv() {
		return homeTopAdv;
	}

	public void setHomeTopAdv(Advertisement homeTopAdv) {
		this.homeTopAdv = homeTopAdv;
	}

	public List<BrandSort> getBrands() {
		return brands;
	}
	public void setBrands(List<BrandSort> brands) {
		this.brands = brands;
	}
}
