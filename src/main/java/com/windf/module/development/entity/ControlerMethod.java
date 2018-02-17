package com.windf.module.development.entity;

public class ControlerMethod extends AbstractBaseCodeEntity{

	private String urlPath;
	private String requestMethod;
	private Controler controler;

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public Controler getControler() {
		return controler;
	}

	public void setControler(Controler controler) {
		this.controler = controler;
	}
	
	
}
