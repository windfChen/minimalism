package com.windf.module.development.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.windf.core.util.StringUtil;

public class Entity extends AbstractBaseCodeBean {
	
	private static Map<String, Entity> allEntities = new HashMap<String, Entity>();
	public static Entity getByName(String name) {
		return allEntities.get(name);
	}
	
	private String tableName;
	private String comment;
	private List<Field> fields = new ArrayList<Field>();
	
	private Module module;
	
	public Field getFieldByName(String name) {
		Field result = null;
		for (int i = 0; i < this.getAllField().size(); i++) {
			Field f = this.getAllField().get(i);
			if (f.getName().equals(name)) {
				result= f;
				break;
			}
		}
		return result;
	}
	
	public Field getFieldByDatabaseName(String databaseName) {
		Field result = null;
		for (int i = 0; i < this.getAllField().size(); i++) {
			Field f = this.getAllField().get(i);
			if (f.getDatabaseName().equalsIgnoreCase(databaseName)) {
				result= f;
				break;
			}
		}
		return result;
	}
	
	@Override
	public void setName(String name) {
		super.setName(name);
		allEntities.put(name, this);
	}
	
	public List<Field> getAllField() {
		List<Field> result = new ArrayList<Field>();
		
		if (StringUtil.isNotEmpty(this.getParent())) {
			String[] parents = this.getParent().split(",");
			for (String parentName : parents) {
				Entity parentEntity = Entity.getByName(parentName);
				if (parentEntity != null) {
					result.addAll(parentEntity.getAllField());
				}
			}
			result.addAll(this.getFields());
		} else {
			result = this.getFields();
		}
		
		return result;
		
	}
	
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

}
