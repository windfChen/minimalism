package com.windf.plugins.manage.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class FieldConfig {
	private String dataIndex;
	private String name;
	private String type;

	private String regex;
	private String regexText;

	private String comboUrl;
	private List<Map<String, Serializable>> comboDataArray;

	private Integer orderNum; // 用于列之间排序

	public String getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
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

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getRegexText() {
		return regexText;
	}

	public void setRegexText(String regexText) {
		this.regexText = regexText;
	}

	public String getComboUrl() {
		return comboUrl;
	}

	public void setComboUrl(String comboUrl) {
		this.comboUrl = comboUrl;
	}

	public List<Map<String, Serializable>> getComboDataArray() {
		return comboDataArray;
	}

	public void setComboDataArray(List<Map<String, Serializable>> comboDataArray) {
		this.comboDataArray = comboDataArray;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

}
