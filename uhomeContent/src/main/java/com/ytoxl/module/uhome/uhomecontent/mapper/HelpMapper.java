package com.ytoxl.module.uhome.uhomecontent.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Help;
import com.ytoxl.module.uhome.uhomecontent.dataobject.HelpCategory;

public interface HelpMapper <T extends HelpCategory> extends BaseSqlMapper<T> {
	/**
	 * 分页查询帮助类别
	 * @return
	 */
	public List<HelpCategory> listHelpCategorys() throws DataAccessException;
	
	/**
	 * 查询帮助子选项
	 * @return
	 */
	public List<HelpCategory> listHelps(Integer helpCategoryId) throws DataAccessException;
	
	/**
	 * 根据id查询内容
	 * @return
	 */
	public Help getContentByHelpId(Integer helpId) throws DataAccessException;
	
	/**
	 * 更新内容
	 * @return
	 */
	public void updateContent(Help help) throws DataAccessException;
	
}
