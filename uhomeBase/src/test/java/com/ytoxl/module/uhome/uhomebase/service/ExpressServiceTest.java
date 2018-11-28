package com.ytoxl.module.uhome.uhomebase.service;

import net.sf.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.BaseTest;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.resultmap.ExpressMessage;

public class ExpressServiceTest extends BaseTest {
	
	@Autowired
	ExpressService expressService;
	
	@Test
	public void getExpressDetailInfoFromKuaidi100() throws UhomeStoreException{
//		886010451143 668480012304
		ExpressMessage result = expressService.getExpressDetailInfoFromKuaidi100("ems", "668480012304");
		log.info(JSONObject.fromObject(result).toString());
	}
	
}
