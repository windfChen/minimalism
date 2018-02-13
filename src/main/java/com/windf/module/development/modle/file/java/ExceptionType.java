package com.windf.module.development.modle.file.java;

import java.util.ArrayList;
import java.util.List;

import com.windf.core.util.StringUtil;

public class ExceptionType {
	private List<String> types;
	
	public ExceptionType() {
		
	}
	
	public ExceptionType(String types) {
		this.types = new ArrayList<String>();
		
		String[] ss = types.split(",");
		for (String t : ss) {
			this.types.add(t.trim());
		}
	}
	
	/**
	 * 添加一个异常，如果异常已存在，返回false
	 * @param type
	 * @return
	 */
	public boolean addException(String type) {
		if (type == null) {
			return false;
		}
		
		if (this.types == null) {
			this.types = new ArrayList<String>();
		}
		
		boolean exists = false;
		for (String str : this.types) {
			if (str.equals(type)) {
				exists = true;
				break;
			}
		}
		
		if (!exists) {
			this.types.add(type);
			return true;
		}
		return false;
	}

	/**
	 * 转换为代码形式
	 * @return
	 */
	public String write() {
		if (this.types == null) {
			return "";
		}
		return "throws " + StringUtil.join(this.types, ", ");
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}
	
}
