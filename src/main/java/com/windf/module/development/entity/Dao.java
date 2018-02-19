package com.windf.module.development.entity;

import java.util.ArrayList;
import java.util.List;

import com.windf.core.util.reflect.UnSerializable;

public class Dao extends AbstractBaseCodeEntity {

	private Entity entity; // 针对的那个实体
	private List<DaoMethod> daoMethods = new ArrayList<DaoMethod>();
	@UnSerializable
	private Module module;

	public List<DaoMethod> getDaoMethods() {
		return daoMethods;
	}

	public void setDaoMethods(List<DaoMethod> daoMethods) {
		this.daoMethods = daoMethods;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

}
