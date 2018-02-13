package com.windf.core.frame;

public interface Orderable {
	static final int EARLIEST = Integer.MIN_VALUE;
	static final int EARLY = Integer.MIN_VALUE / 2;
	static final int NORMAL = 0;
	static final int LATTER = Integer.MIN_VALUE / 2;
	static final int LATEST = Integer.MAX_VALUE;
	
	/**
	 * 获得排序编号
	 * @return
	 */
	int getOrder();
}
