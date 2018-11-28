package com.ytoxl.module.uhome.uhomecontent.mapper;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomecontent.BaseTest;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Suggest;

public class SuggestMapperTest extends BaseTest {
	@Autowired
	private SuggestMapper<Suggest> suggestMapper;
	
	@Test
	public void testAdd(){
		Suggest suggest = new Suggest();
		suggest.setTitle("title"+new Date());
		suggest.setContent("content"+new Date());
		suggest.setType((short) 1);
		suggest.setStatus((short) 1);
		suggestMapper.add(suggest);
	}
}
