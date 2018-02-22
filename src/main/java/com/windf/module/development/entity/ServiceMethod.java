package com.windf.module.development.entity;

import java.util.List;

public class ServiceMethod extends AbstractBaseCodeEntity{

	private String name;
	private List<Parameter> parameters;
	private Entity ret;
	private transient Service service;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public Entity getRet() {
		return ret;
	}

	public void setRet(Entity ret) {
		this.ret = ret;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

}
