package com.ytoxl.module.uhome.uhomecontent.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Notice;

/**
 * 公告
 * 
 * @author xupf
 *
 * @param <T>
 */
public interface NoticeMapper<T extends Notice> extends BaseSqlMapper<T> {
	
	/**
	 * 根据ID删除一条或多条公告
	 * 
	 * @param ids
	 * @throws DataAccessException
	 */
	public void deleteNoticeByIds(Integer... ids) throws DataAccessException;
	
	/**
	 * 更新审核状态
	 * 
	 * @param params
	 * @throws DataAccessException
	 */
	public void updateCheck(Map<String, Object> params) throws DataAccessException;

	/**
	 * 更新置顶状态
	 * 
	 * @throws DataAccessException
	 */
	public void updateIsTop(Map<String, Object> params) throws DataAccessException;
	
	/**
	 * 分页查询公告信息
	 * 
	 * @param searchParams
	 * @return
	 * @throws DataAccessException
	 */
	public List<Notice> searchNotices(Map<String, Object> searchParams) throws DataAccessException;
	
	/**
	 * 查询公告记录数
	 * 
	 * @param searchParams
	 * @return
	 * @throws DataAccessException
	 */
	public Integer searchNoticesCount(Map<String, Object> searchParams) throws DataAccessException;
	
	/**
	 * 分页查询公告信息时间排序
	 * 
	 * @param searchParams
	 * @return
	 * @throws DataAccessException
	 */
	public List<Notice> searchNoticeList(Map<String, Object> searchParams) throws DataAccessException;
	
	/**
	 * 上一篇公告
	 * 
	 * @param searchParams
	 * @return
	 * @throws DataAccessException
	 */
	public Notice previousNotice(Map<String,Object> map) throws DataAccessException;
	
	/**
	 * 下一篇公告
	 * 
	 * @param searchParams
	 * @return
	 * @throws DataAccessException
	 */
	public Notice nextNotice(Map<String,Object> map) throws DataAccessException;
}
