package com.windf.plugins.log;

public interface Logger {
	void error(Object message);
	void warn(Object message);
	void debug(Object message);
	void info(Object message);
	void error(Object message, Throwable e);
	void warn(Object message, Throwable e);
	void debug(Object message, Throwable e);
	void info(Object message, Throwable e);
	void error(Throwable e);
	void warn(Throwable e);
	void debug(Throwable e);
	void info(Throwable e);
}
