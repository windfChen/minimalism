package com.windf.module.development.entity;

import java.util.ArrayList;
import java.util.List;

import com.windf.core.util.reflect.UnSerializable;

public class Entity extends AbstractBaseCodeEntity {

	private List<Field> fields = new ArrayList<Field>();
	@UnSerializable
	private Module module;

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
