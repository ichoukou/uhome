package com.ytoxl.module.uhome.uhomebase.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.BaseTest;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Product;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSkuOptionValue;

public class ProductServiceTest extends BaseTest {
	@Autowired
	private ProductService productService;

	@Test
	public void testsaveProduct() {

		Product product = new Product();

		product.setBrandId(100000);
		product.setDescribe("不错的衣服");
		product.setName("阿迪达斯");
		product.setImageUrls("123");
		product.setSecKillPrice(new BigDecimal(100));
		product.setSalePrice(new BigDecimal(300));
		product.setMarketPrice(new BigDecimal(600));
		product.setIsImport(new Short("1"));
		product.setRank(1);
		product.setSellStartTime(new Date());
		product.setSellEndTime(new Date());

		ProductSku productSku = new ProductSku();

		productSku.setSecKillInventory(100);
		productSku.setInventory(200);
		productSku.setSkuCode("sku111111111");
		productSku.setSalesQuantity(10000);

		ProductSkuOptionValue productSkuOptionValue1 = new ProductSkuOptionValue();
		productSkuOptionValue1.setSkuOptionValueId(100002);
		ProductSkuOptionValue productSkuOptionValue2 = new ProductSkuOptionValue();
		productSkuOptionValue2.setSkuOptionValueId(100005);

		List<ProductSkuOptionValue> productSkuOptionValues = new ArrayList<ProductSkuOptionValue>();
		productSkuOptionValues.add(productSkuOptionValue1);
		productSkuOptionValues.add(productSkuOptionValue2);

		productSku.setProductSkuOptionValues(productSkuOptionValues);

		List<ProductSku> productSkus = new ArrayList<ProductSku>();
		productSkus.add(productSku);

		product.setProductSkus(productSkus);
		
		JSON json = JSONSerializer.toJSON(product);
//		JSONArray object = JSONArray.fromObject(product.getProductSkus());
		log.info(json.toString());

//		try {
//			productService.saveProduct(product);
//		} catch (UhomeStoreException e) {
//			log.info("存在异常", e);
//		}

	}

	@Test
	public void testupdateProduct() {

		Product product = new Product();

		product.setProductId(100019);
		product.setBrandId(100000);
		product.setDescribe("不错的衣服");
		product.setName("阿迪达斯");
		product.setImageUrls("123");
		product.setSecKillPrice(new BigDecimal(100));
		product.setSalePrice(new BigDecimal(300));
		product.setMarketPrice(new BigDecimal(600));
		product.setIsImport(new Short("1"));
		product.setRank(1);
		product.setSellStartTime(new Date());
		product.setSellEndTime(new Date());

		ProductSku productSku = new ProductSku();

		productSku.setProductSkuId(100007);
		productSku.setSecKillInventory(100);
		productSku.setInventory(200);
		productSku.setSkuCode("sku2222222222");
		productSku.setSalesQuantity(10000);

		ProductSkuOptionValue productSkuOptionValue1 = new ProductSkuOptionValue();
		productSkuOptionValue1.setSkuOptionValueId(100000);
		productSkuOptionValue1.setOverrideSkuOptionValue("黄色");
		ProductSkuOptionValue productSkuOptionValue2 = new ProductSkuOptionValue();
		productSkuOptionValue2.setSkuOptionValueId(100006);

		List<ProductSkuOptionValue> productSkuOptionValues = new ArrayList<ProductSkuOptionValue>();
		productSkuOptionValues.add(productSkuOptionValue1);
		productSkuOptionValues.add(productSkuOptionValue2);

		productSku.setProductSkuOptionValues(productSkuOptionValues);

		List<ProductSku> productSkus = new ArrayList<ProductSku>();
		productSkus.add(productSku);

		product.setProductSkus(productSkus);

		try {
			productService.saveProduct(product);
		} catch (UhomeStoreException e) {
			log.info("存在异常", e);
		}
	}

	@Test
	public void getProductListByHitsTet() throws UhomeStoreException{
		List<Product> products = productService.getProductListByHits(8);
		log.info("################:"+products);
	}
	
	@Test
	public void getProductListByProductId4Front() throws UhomeStoreException{
		Product product = new Product();
		product.setProductId(100022);
		Product p = productService.getProductByProductId4Front(product);
		//log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%:p"+p);
		//log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@:"+p.getProductSkus());
		//log.info("##########################:proMap"+p.getProductSkuOptionValueMap());
	}
	
	@Test
	public void getProductByProductId() throws UhomeStoreException{
		Product product = productService.getProductByProductId(100000);
	}

}
