package com.ytoxl.module.uhome.uhomecontent.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Notice;
import com.ytoxl.module.uhome.uhomecontent.mapper.NoticeMapper;
import com.ytoxl.module.uhome.uhomecontent.service.NoticeService;

/**
 * 公告service实现
 * 
 * @author xupf
 * 
 */
@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeMapper<Notice> noticeMapper;

	@Override
	public void addNotice(Notice notice) throws UhomeStoreException {
		noticeMapper.add(notice);
	}

	@Override
	public void updateNotice(Notice notice) throws UhomeStoreException {
		noticeMapper.update(notice);
	}
	
	@Override
	public void deleteNoticeByIds(Integer... ids) throws UhomeStoreException {
		noticeMapper.deleteNoticeByIds(ids);
	}

	@Override
	public void updateCheck(Map<String, Object> params)
			throws UhomeStoreException {
		noticeMapper.updateCheck(params);
	}

	@Override
	public void updateIsTop(Map<String, Object> params)
			throws UhomeStoreException {
		noticeMapper.updateIsTop(params);
	}

	@Override
	public Notice getNoticeById(Integer noticeId) throws UhomeStoreException {
		return noticeMapper.get(noticeId);
	}

	@Override
	public void searchNotices(BasePagination<Notice> noticePage)
			throws UhomeStoreException {
		Map<String, Object> paramsMap = noticePage.getSearchParamsMap();
		if (noticePage.isNeedSetTotal()) {
			Integer total = noticeMapper.searchNoticesCount(paramsMap);
			noticePage.setTotal(total);
		}
		noticePage.setResult(noticeMapper.searchNotices(paramsMap));
	}

	@Override
	public List<Notice> searchIndexNotice() throws UhomeStoreException {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("start", 0);
		paramsMap.put("limit", 3);
		paramsMap.put("status",Notice.CHECKED_YES);
		return noticeMapper.searchNotices(paramsMap);
	}
	
	@Override
	public void searchNoticeList(BasePagination<Notice> noticePage)
			throws UhomeStoreException {
		Map<String, Object> paramsMap = noticePage.getSearchParamsMap();
		paramsMap.put("status", Notice.CHECKED_YES);
		if (noticePage.isNeedSetTotal()) {
			Integer total = noticeMapper.searchNoticesCount(paramsMap);
			noticePage.setTotal(total);
		}
		noticePage.setResult(noticeMapper.searchNoticeList(paramsMap));
	}
	
	@Override
	public Notice previousNotice(Integer noticeId,Short type) throws UhomeStoreException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("noticeId", noticeId);
		map.put("type", type);
		map.put("status", Notice.CHECKED_YES);
		return noticeMapper.previousNotice(map);
	}
	
	@Override
	public Notice nextNotice(Integer noticeId,Short type) throws UhomeStoreException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("noticeId", noticeId);
		map.put("type", type);
		map.put("status", Notice.CHECKED_YES);
		return noticeMapper.nextNotice(map);
	}

}
