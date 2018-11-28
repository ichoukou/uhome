package com.ytoxl.module.uhome.uhomebase.service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;



public interface GlobalLogService {
	
	/**
	 * 记录日志到数据库
	 * @param logContent 日志内容
	 * @param logType 日志类型(所属模块)
	 * @param logLevel 日志层级
	 * @throws UhomeStoreException
	 */
	public void recordLog(String logContent,Short logType,Short logLevel)  throws UhomeStoreException ;
	
}
