package com.windf.module.development.entity;

import java.util.ArrayList;
import java.util.List;

public class Module extends com.windf.core.bean.Module {

	private List<Entity> entitys = new ArrayList<Entity>();
	private List<Dao> daos = new ArrayList<Dao>();
	public List<Entity> getEntitys() {
		return entitys;
	}
	public void setEntitys(List<Entity> entitys) {
		this.entitys = entitys;
	}
	public List<Dao> getDaos() {
		return daos;
	}
	public void setDaos(List<Dao> daos) {
		this.daos = daos;
	}

	

}
