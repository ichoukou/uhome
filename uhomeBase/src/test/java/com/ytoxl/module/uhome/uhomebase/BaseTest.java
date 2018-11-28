package com.ytoxl.module.uhome.uhomebase;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring-core.xml",
		"classpath*:/spring-mail.xml", "classpath*:/spring-jcaptcha.xml",
		"classpath*:/spring-security.xml", "classpath*:spring-uHomeBase.xml",
		"classpath*:spring-uHomeBase-test.xml" })
public class BaseTest {
	protected static Logger log = LoggerFactory.getLogger(BaseTest.class);
}
