package com.ytoxl.module.uhome.uhomecontent.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.BaseTest;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Notice;

public class NoticeServiceTest extends BaseTest {

	@Autowired
	private NoticeService noticeService;
	
	//@Test
	public void TestAddNotice() throws UhomeStoreException{
		Notice notice = new Notice();
		Short type = 1;
		notice.setTitle("test4444");
		notice.setType(type);
		notice.setSource("http://www.baidu.com");
		notice.setContent("test4444");
		notice.setAuthorId(1);
		notice.setStatus(Notice.CHECKED_NO);
		notice.setIsTop(Notice.TOP_NO);
		noticeService.addNotice(notice);
	}
	
	//@Test
	public void TestUpdateNotice() throws UhomeStoreException{
		Notice notice = new Notice();
		Short type = 2;
		notice.setNoticeId(1);
		notice.setTitle("1111test111");
		notice.setType(type);
		notice.setSource("http://www.baidu.com");
		notice.setContent("1111test111");
		notice.setStatus(Notice.CHECKED_NO);
		noticeService.updateNotice(notice);
	}
	
	@Test
	public void TestDeleteNoticeByIds() throws UhomeStoreException{
		Integer ids[] = {5,6,7};
		noticeService.deleteNoticeByIds(ids);
	}
	
	@Test
	public void TestUpdateCheck() throws UhomeStoreException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		String ids = "1,2,3,4";
		params.put("checkerId", 3);
		params.put("status", Notice.CHECKED_YES);
		params.put("ids", ids.split(","));
		noticeService.updateCheck(params);
	}
	
	@Test
	public void TestUpdateIsTop() throws UhomeStoreException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("noticeId", 2);
		params.put("isTop", 0);
		noticeService.updateIsTop(params);
	}
	
	//@Test
	public void TestGetNoticeById() throws UhomeStoreException{
		Notice notice = noticeService.getNoticeById(1);
		log.info("@@@@@@@@@title=="+notice.getTitle()
				+",type=="+notice.getType());
	}
	
	//@Test
	public void TestSearchNotice() throws UhomeStoreException{
		BasePagination<Notice> noticePage = new BasePagination<Notice>();
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("title", "test");
		map.put("type", "1");
		map.put("status", "1");
		
		noticePage.setParams(map);
		noticePage.setCurrentPage(1);
		noticePage.setLimit(10);
		
		noticeService.searchNotices(noticePage);
		log.info("@@@@@@@@@@size=="+noticePage.getResult().size());
	}
}
