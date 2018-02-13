package com.windf.plugins.log;

import com.windf.plugins.log.impl.Log4jLogService;

public class LogFactory {
	@SuppressWarnings("rawtypes") 
	public static Logger getLogger(Class clazz) {
		return new Log4jLogService(clazz);
	}
}
