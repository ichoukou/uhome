package com.ytoxl.module.uhome.uhomebase.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.BaseTest;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;

/**
 * @author wangguoqing
 *
 */
public class BrandMapperTest extends BaseTest{
	
	@Autowired
	BrandMapper<Brand> brandMapper;
	
	@Test
	public void getBrandByBrandId(){
		Brand brand = new Brand();
		brand.setBrandId(100000);
		log.info("@@@@@@@@@@@@@@:"+brandMapper.get(brand));
	}

}
