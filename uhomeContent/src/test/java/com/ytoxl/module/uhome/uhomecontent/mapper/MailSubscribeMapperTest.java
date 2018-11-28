package com.ytoxl.module.uhome.uhomecontent.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomecontent.BaseTest;
import com.ytoxl.module.uhome.uhomecontent.dataobject.MailSubscribe;

/**
 * @author wangguoqing
 *
 */

public class MailSubscribeMapperTest extends BaseTest {
	@Autowired
	private MailSubscribeMapper<MailSubscribe> mailSubscribeMapper;
	
	@Test
	public void saveMailSubscribe(){
		MailSubscribe m = new MailSubscribe();
		m.setBrandId(100002);
		mailSubscribeMapper.add(m);
	}
}
