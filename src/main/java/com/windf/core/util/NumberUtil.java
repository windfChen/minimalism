package com.windf.core.util;

import com.windf.core.util.reflect.ReflectUtil;

public class NumberUtil {
	public static boolean isNumber(Object obj) {
		boolean result = false;
		if (obj != null) {
			if (ReflectUtil.isNumber(obj.getClass())) {
				result = true;
			} else {
				String str = obj.toString();
				if (RegularUtil.match(str, RegularUtil.NUMBER_VALUE)) {
					result = true;
				}
			}
		}
		
		return result;
	}
	
    public static int getRandom(int count) {
    	return (int) Math.round(Math.random() * (count));
    }
}
