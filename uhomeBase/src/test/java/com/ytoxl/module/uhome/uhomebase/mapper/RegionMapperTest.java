package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.BaseTest;
import com.ytoxl.module.uhome.uhomebase.dataobject.Region;
import com.ytoxl.module.uhome.uhomebase.dataobject.UserInfo;

public class RegionMapperTest extends BaseTest {
	@Autowired
	private RegionMapper<Region> regionMapper;
	@Autowired
	private UserInfoMapper<UserInfo> userInfoMapper;

	@Test
	public void testgetRegionByCode() {
		 Region region = regionMapper.getRegionByCode("110000");
		 log.info(region.getRegionName());
	}
	@Test
	public void testSaveUserInfo() {
		UserInfo user = new UserInfo();
		user.setAddress("1111");
		user.setBirthday( new Date());
		user.setBrandNames("moto");
		user.setEmail("zuron@312.com");
		user.setMobile("123213");
		user.setName("sdfsdfs");
		user.setUserId(1);
		
		userInfoMapper.addBuyer(user);
		log.info("-----=====------" + user.getUserInfoId());
	}
}
