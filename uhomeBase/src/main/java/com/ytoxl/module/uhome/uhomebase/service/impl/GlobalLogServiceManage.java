package com.ytoxl.module.uhome.uhomebase.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.GlobalLog;
import com.ytoxl.module.uhome.uhomebase.mapper.GlobalLogMapper;
import com.ytoxl.module.uhome.uhomebase.service.GlobalLogService;
@Service
@Transactional(readOnly=true)
public class GlobalLogServiceManage implements GlobalLogService {
	
	protected static Logger log = LoggerFactory.getLogger(GlobalLogService.class);
	
	@Autowired
	private GlobalLogMapper<GlobalLog> globalLogMapper;
	
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class)
	public void recordLog(String logContent,Short logType,Short logLevel)  throws UhomeStoreException {
		GlobalLog log = new GlobalLog();
		log.setLogContent(logContent);
		log.setLogType(logType);
		log.setLogLevel(logLevel);
		globalLogMapper.add(log);
	}
}
