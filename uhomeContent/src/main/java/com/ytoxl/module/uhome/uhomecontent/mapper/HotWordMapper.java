package com.ytoxl.module.uhome.uhomecontent.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomecontent.dataobject.HotWord;
/**
 * 热关键字
 * @author guoxinzhi
 *
 * @param <T>
 */
public interface HotWordMapper <T extends HotWord> extends BaseSqlMapper<T> {
	/**
	 * 分页查询建议
	 * @param map
	 * @return
	 */
	public List<HotWord> listHotWords() throws DataAccessException;
	public Integer listHotWordCounts() throws DataAccessException;
	
	/**
	 * 查询该关键字后面的所有关键字
	 * @param map
	 * @return
	 */
	public List<HotWord> listHotWordByRank(Integer rank) throws DataAccessException;
	
	/**
	 * 根据参数查询所有个数的 热搜词
	 * @param num
	 * @return
	 * @throws DataAccessException
	 */
	public List<HotWord> listHotWordsNum(Integer num) throws DataAccessException;
	
}
