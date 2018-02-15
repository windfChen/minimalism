package com.windf.module.development.modle.file.java;

public class MethodReturn {
	public static final String STRING = "String";
	public static final String MAP_STRING_OBJECT = "Map<String, Object>";
	public static final String VOID = "void";
	
	private String type;
	
	public MethodReturn() {
		
	}
	
	public MethodReturn(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
