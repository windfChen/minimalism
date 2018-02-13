package com.windf.plugins.log.impl;

import org.apache.log4j.Logger;

public class Log4jLogService implements com.windf.plugins.log.Logger{
	private Logger logger = null;
	
	@SuppressWarnings("rawtypes") 
	public Log4jLogService(Class clazz) {
		logger = Logger.getLogger(clazz);
	}
	
	@Override
	public void error(Object message) {
		logger.error(message);
	}

	@Override
	public void warn(Object message) {
		logger.warn(message);
	}

	@Override
	public void debug(Object message) {
		logger.debug(message);
	}

	@Override
	public void info(Object message) {
		logger.info(message);
	}

	@Override
	public void error(Object message, Throwable e) {
		logger.error(message,  e);
	}

	@Override
	public void warn(Object message, Throwable e) {
		logger.warn(message,  e);
	}

	@Override
	public void debug(Object message, Throwable e) {
		logger.debug(message,  e);
	}

	@Override
	public void info(Object message, Throwable e ){
		logger.info(message,  e);
	}

	@Override
	public void error(Throwable e) {
		logger.info(e);
	}

	@Override
	public void warn(Throwable e) {
		logger.warn(e);
	}

	@Override
	public void debug(Throwable e) {
		logger.debug(e);
	}

	@Override
	public void info(Throwable e) {
		logger.info(e);
	}

}
