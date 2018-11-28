package com.ytoxl.module.uhome.uhomecontent.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.dataobject.Product;
import com.ytoxl.module.uhome.uhomebase.mapper.ProductMapper;
import com.ytoxl.module.uhome.uhomecontent.BaseTest;

/**
 * @author wangguoqing
 *
 */
public class SeoConfigServiceTest extends BaseTest  {
	
	@Autowired
	private SeoConfigService seoConfigService;
	@Autowired
	private ProductMapper<Product> productMapper;
	
	
	@Test
	public void getSeoConfigByUrlKey(){
		try{
			String[] res = seoConfigService.getSeoConfigByUrlKey("/item-9.htm",productMapper.get(74));
			for(int i=0;i<res.length;i++)
			log.info(res[i]);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
