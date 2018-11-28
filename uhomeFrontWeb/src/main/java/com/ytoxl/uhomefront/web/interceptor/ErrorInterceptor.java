package com.ytoxl.uhomefront.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author wangguoqing
 *
 */
public class ErrorInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = -2923515575914959999L;
	private static Logger logger = LoggerFactory.getLogger(ErrorInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		try{
			String result = "";
			result = invocation.invoke();
			return result;
		}catch (Exception e) {
			logger.error("拦截器异常:"+e.getMessage());
			return "505";
		}
	}

}
