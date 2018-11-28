package com.ytoxl.module.uhome.uhomebase.dataobject.tbl;

import java.util.Date;

public class GlobalLogTbl {
	protected Integer logId;
	protected String logContent;
	protected Short logType;
	protected Short logLevel;
	protected Date logCreateTime;//日志创建时间
	protected Date logTime;//日志线程执行时间
	public Integer getLogId() {
		return logId;
	}
	public void setLogId(Integer logId) {
		this.logId = logId;
	}
	public String getLogContent() {
		return logContent;
	}
	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}
	public Short getLogType() {
		return logType;
	}
	public void setLogType(Short logType) {
		this.logType = logType;
	}
	public Short getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(Short logLevel) {
		this.logLevel = logLevel;
	}
	public Date getLogCreateTime() {
		return logCreateTime;
	}
	public void setLogCreateTime(Date logCreateTime) {
		this.logCreateTime = logCreateTime;
	}
	public Date getLogTime() {
		return logTime;
	}
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	
	
	
	
}
