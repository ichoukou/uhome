package com.ytoxl.module.uhome.uhomecontent.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomecontent.dataobject.MailTemplate;

/**
 * @author wangguoqing
 *
 */
public interface MailTemplateMapper<T extends MailTemplate> extends BaseSqlMapper<T> {
	
	/**
	 * 查找出所有的有邮件模版
	 * @return
	 * @throws DataAccessException
	 */
	public List<MailTemplate> getAllMailTemplate() throws DataAccessException;
	
	/**
	 * 根据类型查找模版
	 * @param type
	 * @return
	 * @throws DataAccessException
	 */
	public MailTemplate getMailTemplateByType(Short type) throws DataAccessException;

}
