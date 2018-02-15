package com.windf.module.development.entity;

import java.util.HashMap;
import java.util.Map;

public class Parameter {
	private String name;
	private String descript;
	private String type;
	private boolean notEmpty;
	private Map<String, String> patterns;
	
	public Parameter() {
		
	}
	
	public Parameter(String type, String name) {
		this.name = name;
		this.type = type;
	}
	
	public void addPattern(String pattern, String errorMsg) {
		if (patterns == null) {
			patterns = new HashMap<String, String>();
		}
		patterns.put(pattern, errorMsg);
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

	public boolean isNotEmpty() {
		return notEmpty;
	}

	public void setNotEmpty(boolean notEmpty) {
		this.notEmpty = notEmpty;
	}

}
