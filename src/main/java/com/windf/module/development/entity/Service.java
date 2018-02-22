package com.windf.module.development.entity;

import java.util.ArrayList;
import java.util.List;

public class Service extends AbstractBaseCodeEntity{

	private String path;
	private List<ServiceMethod> serviceMethods = new ArrayList<ServiceMethod>();
	private transient Module module;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<ServiceMethod> getServiceMethods() {
		return serviceMethods;
	}

	public void setServiceMethods(List<ServiceMethod> serviceMethods) {
		this.serviceMethods = serviceMethods;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

}
