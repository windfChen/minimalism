package com.windf.module.development.entity;

import java.util.List;

import com.windf.core.util.reflect.UnSerializable;

public class ControlerMethod extends AbstractBaseCodeEntity{

	private List<Parameter> parameters;
	private String urlPath;
	private String requestMethod;
	@UnSerializable
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

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
	
	
}
