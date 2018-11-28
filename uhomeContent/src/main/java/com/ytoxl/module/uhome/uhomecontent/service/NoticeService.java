package com.ytoxl.module.uhome.uhomecontent.service;

import java.util.List;
import java.util.Map;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Notice;

/**
 * 公告
 * 
 * @author xu
 * 
 */
public interface NoticeService {

	/**
	 * 新增公告
	 * 
	 * @param notice
	 * @throws UhomeStoreException
	 */
	public void addNotice(Notice notice) throws UhomeStoreException;

	/**
	 * 修改公告信息
	 * 
	 * @param notice
	 * @throws UhomeStoreException
	 */
	public void updateNotice(Notice notice) throws UhomeStoreException;

	/**
	 * 根据ID删除公告
	 * 
	 * @param noticeId
	 * @throws UhomeStoreException
	 */
	public void deleteNoticeByIds(Integer... ids) throws UhomeStoreException;

	/**
	 * 审核公告
	 * 
	 * @param params
	 * @throws UhomeStoreException
	 */
	public void updateCheck(Map<String, Object> params)
			throws UhomeStoreException;

	/**
	 * 更新置顶状态
	 * 
	 * @param params
	 * @throws UhomeStoreException
	 */
	public void updateIsTop(Map<String, Object> params)
			throws UhomeStoreException;

	/**
	 * 根据公告ID，获取公告信息
	 * 
	 * @param noticeId
	 * @return
	 * @throws UhomeStoreException
	 */
	public Notice getNoticeById(Integer noticeId) throws UhomeStoreException;

	/**
	 * 分页查询公告信息
	 * 
	 * @param noticePage
	 * @throws UhomeStoreException
	 */
	public void searchNotices(BasePagination<Notice> noticePage)
			throws UhomeStoreException;

	/**
	 * 首页公告信息
	 * 
	 * @param noticePage
	 * @throws UhomeStoreException
	 */
	public List<Notice> searchIndexNotice() throws UhomeStoreException;

	/**
	 * 分页查询公告信息时间排序
	 * 
	 * @param noticePage
	 * @throws UhomeStoreException
	 */
	public void searchNoticeList(BasePagination<Notice> noticePage)
			throws UhomeStoreException;

	/**
	 * 上一篇公告
	 * 
	 * @param noticePage
	 * @throws UhomeStoreException
	 */
	public Notice previousNotice(Integer noticeId,Short type)throws UhomeStoreException;

	/**
	 * 下一篇公告
	 * 
	 * @param noticePage
	 * @throws UhomeStoreException
	 */
	public Notice nextNotice(Integer noticeId,Short type)throws UhomeStoreException;
}
