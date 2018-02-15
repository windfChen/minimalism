package com.windf.module.development.modle.file.java;

import java.util.List;

public class MethodParameter extends AbstractType {
	private String type;
	private String name;
	
	public MethodParameter() {
		
	}
	
	public MethodParameter(String type, String name) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	List<String> write() {
		return null;
	}

}
