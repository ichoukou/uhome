package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.List;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.BaseTest;
import com.ytoxl.module.uhome.uhomebase.dataobject.Event;

public class EventMapperTest extends BaseTest {
	@Autowired
	private EventMapper<Event> eventMapper;
	
	@Test
	public  void listEventsByTypeAndTime() {
		log.info("~~~~~~~~~~~~~~:"+eventMapper);
		List<Event> events = eventMapper.listEventsByTypeAndTime(Event.TYPE_WECHAT);
		log.info("~~~~~~~~~~~~~~~~~~~:"+events);

	}
	
	
}
