package com.windf.plugins.web.response;

import com.windf.plugins.web.BaseControler;

public class ResponseReturnFactory {
	public static ResponseReturn getResponseReturn(String suffix, BaseControler baseControler) {
		ResponseReturn result = null;
		
		if ("json".equals(suffix)) {
			result = new JsonReturn(baseControler);
		} else {
			result = new PageReturn(baseControler);
		}
		
		return result;	
	}
}
