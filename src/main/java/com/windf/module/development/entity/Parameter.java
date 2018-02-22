package com.windf.module.development.entity;

import java.util.HashMap;
import java.util.Map;

public class Parameter {
	private String name;
	private String descript;
	private String type;
	private boolean notEmpty;
	private Map<String, String> patterns = new HashMap<String, String>();
	
	private transient AbstractBaseCodeEntity codeEntity;// 反向关联
	private transient String methodName;
	private transient Module module;
	
	public Parameter() {
		
	}
	
	public Parameter(String type, String name) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, String> getPatterns() {
		return patterns;
	}

	public void setPatterns(Map<String, String> patterns) {
		this.patterns = patterns;
	}

	public boolean getNotEmpty() {
		return notEmpty;
	}

	public void setNotEmpty(boolean notEmpty) {
		this.notEmpty = notEmpty;
	}

	public AbstractBaseCodeEntity getCodeEntity() {
		return codeEntity;
	}

	public void setCodeEntity(AbstractBaseCodeEntity codeEntity) {
		this.codeEntity = codeEntity;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

}
