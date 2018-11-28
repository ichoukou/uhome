package com.ytoxl.uhomefront.web.action.seckill;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Plan;
import com.ytoxl.module.uhome.uhomebase.dataobject.Product;
import com.ytoxl.module.uhome.uhomebase.service.PlanService;
import com.ytoxl.module.uhome.uhomebase.service.ProductService;
import com.ytoxl.module.uhome.uhomecontent.dataobject.HelpCategory;
import com.ytoxl.module.uhome.uhomecontent.service.HelpService;
import com.ytoxl.uhomefront.web.action.BaseAction;

/**
 * @author wangguoqing
 *
 */
@SuppressWarnings("serial")
public class SecKillAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(SecKillAction.class);
	
	private static String SECKILL = "secKill";
	private static String SECKILLDETAIL = "seckillDetail";
	
	@Autowired
	private PlanService planService;
	@Autowired
	private ProductService productService;
	//	帮助
	@Autowired
	private HelpService helpService;
	
	//今天秒杀
	private List<Plan> todaySecKills;
	//明天秒杀
	private List<Plan> tomorrowSecKills;
	
	//秒杀详细信息
	private Product product;
	//排期
	private Plan plan;
	//帮助
	private Map<String,List<HelpCategory>> helpCategoryMaps;
	//用户的浏览记录
	private List<Product> products;
	
	//获取秒杀数据
	public String secKill(){
		try {
			todaySecKills = planService.listPlanSecKillToday();
			tomorrowSecKills = planService.listPlanSecKillTomorrow();
		} catch (UhomeStoreException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return SECKILL;
	}
	
	//秒杀商品详细信息
	public String secKillDetail(){
		HttpServletRequest request = ServletActionContext.getRequest();
    	HttpServletResponse response = ServletActionContext.getResponse();
		try {
			product = productService.getProductByProductId4FrontSecKillDetail(plan);
			//TODO 如果product 为空  跳转到error页面
			if(product == null){
				return "error";
			}
			helpCategoryMaps = helpService.getHelp4ProductDetail();
			//读取cookies用户的浏览记录  并向浏览器添加用户的浏览记录
			products = productService.getUserProductHistory(request, response, product.getProductId());
		} catch (UhomeStoreException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return SECKILLDETAIL;
	}
	
	public List<Plan> getTodaySecKills() {
		return todaySecKills;
	}
	public void setTodaySecKills(List<Plan> todaySecKills) {
		this.todaySecKills = todaySecKills;
	}
	public List<Plan> getTomorrowSecKills() {
		return tomorrowSecKills;
	}
	public void setTomorrowSecKills(List<Plan> tomorrowSecKills) {
		this.tomorrowSecKills = tomorrowSecKills;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public Map<String, List<HelpCategory>> getHelpCategoryMaps() {
		return helpCategoryMaps;
	}

	public void setHelpCategoryMaps(Map<String, List<HelpCategory>> helpCategoryMaps) {
		this.helpCategoryMaps = helpCategoryMaps;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
