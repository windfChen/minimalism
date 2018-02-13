package com.windf.plugins.web.response;

public abstract class AbstractResponseRetrun implements ResponseReturn {
	
	protected String page;
	
	@Override
	public String page(String page) {
		this.page = page;
		return returnData(true, null, null);
	}

}
