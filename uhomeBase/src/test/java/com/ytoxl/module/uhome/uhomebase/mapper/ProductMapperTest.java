package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.BaseTest;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Product;
import com.ytoxl.module.uhome.uhomebase.service.ProductService;

/**
 * @author wangguoqing
 *
 */
public class ProductMapperTest extends BaseTest {
	
	@Autowired
	ProductMapper<Product> productMapper;
	
	@Autowired
	ProductService prodcutService;
	
	@Test
	public void searchProducts4Front() throws UhomeStoreException{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyWord", "上衣");
		map.put("start", 0);
		map.put("limit", 5);
		//List<Product> products = productMapper.searchProducts4Front(map);
		BasePagination<Product> productPage = new BasePagination<Product>();
		this.prodcutService.searchProducts4Front(productPage);
		log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@:"+productPage.getResult());
		//log.info("###########################:"+products);
	}
	
	@Test
	public void searchProducts4FrontCount(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyWord", "上衣");
		int num = productMapper.searchProducts4FrontCount(map);
		log.info("###########################:"+num);
	}
	
	@Test
	public void searchProducts(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyWord", "上衣");
		map.put("start", 0);
		map.put("limit", 5);
		List<Product> products = productMapper.searchSellerProducts(map);
		log.info("###########################:"+products);
	}
	
	@Test
	public void listPlansByProductIdAndCurrentTime(){
		productMapper.listPlansByProductIdAndCurrentTime(100022);
	}
	
	@Test
	public void searchImportProducts4Front(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("start", 0);
		map.put("limit", 5);
		List<Product> products = productMapper.searchImportProducts4Front(map);
		log.info("###########################:"+products);
	}
	
	@Test 
	public void searchImportProdcuts4FrontBySalesQuantity(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("start", 0);
		map.put("limit", 5);
		List<Product> products = productMapper.searchImportProducts4Front(map);
		log.info("###########################:"+products);
	}
	
	@Test
	public void searchImportProducts4FrontCount(){
		Map<String,Object> map = new HashMap<String,Object>();
		log.info("#############################:"+productMapper.searchImportProducts4FrontCount(map));
	}

}
