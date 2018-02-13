package com.windf.core.frame;

public interface Session extends Orderable{
	/**
	 * 根据key获取存储的对象 如果找不到，返回null
	 * 
	 * @param key
	 * @return
	 */
	Object get(String key);

	/**
	 * 设置键值
	 * @param key
	 * @param obj
	 * @return 是否替换
	 */
	boolean set(String key, Object obj);

	/**
	 * 注销
	 */
	void invalidate();
}
