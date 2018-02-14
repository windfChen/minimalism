package com.windf.module.development.entity;

import java.util.ArrayList;
import java.util.List;

public class Entity extends AbstractBaseCodeEntity {

	private String tableName;
	private List<Field> fields = new ArrayList<Field>();
	private Module module;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

}
