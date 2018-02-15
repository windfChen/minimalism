package com.windf.module.development.entity;

import java.util.List;

public class DaoMethod extends AbstractBaseCodeEntity {

	private String type; // insert,select,update,delete
	private Entity entity;
	private List<Parameter> parameters;
	
	private String sqlStr; // sql字符串，先在前台构建，直接传过来

	private Dao dao;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public String getSqlStr() {
		return sqlStr;
	}

	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}

	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
}
