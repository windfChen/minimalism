package com.windf.core.frame;

import java.util.List;

public class OrderHandler {
	/**
	 * 排序插入orderList
	 * @param orderList
	 * @param orderable
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void add(List orderList, Orderable orderable) {
		int i = 0;
		for (; i < orderList.size(); i++) {
			Orderable o = (Orderable) orderList.get(i);
			if (orderable.getOrder() <= o.getOrder()) {
				break;
			}
		}
		orderList.add(i, orderable);
	}
}
