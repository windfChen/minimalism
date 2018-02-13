package com.windf.core.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.util.CollectionUtils;

public class CollectionUtil {
	public static boolean isNotEmpty(Collection<? extends Object> collection) {
		return !CollectionUtils.isEmpty(collection);
	}

	public static boolean isNotEmpty(Map<? extends Object, ? extends Object> result) {
		return result != null && result.size() > 0;
	}

	public static boolean isEmpty(Collection<? extends Object> collection) {
		return CollectionUtils.isEmpty(collection);
	}
	
	public static boolean isEmpty(Map<? extends Object, ? extends Object> result) {
		return isNotEmpty(result);
	}
	
	/**
	 * 将map进行反转，key作为value，value作为key
	 * 如果value不具有唯一性，结果不可控
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map reversalMap(Map map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		
		Iterator iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = (Object) iterator.next();
			Object value = map.get(key);
			
			result.put(value, key);
		}
		
		return result;
	}
}
