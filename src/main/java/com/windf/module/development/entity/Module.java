package com.windf.module.development.entity;

import java.util.ArrayList;
import java.util.List;

import com.windf.core.util.reflect.SerializableBaseTypeParameter;

public class Module extends com.windf.core.bean.Module {

	@SerializableBaseTypeParameter
	private List<Entity> entitys = new ArrayList<Entity>();
	@SerializableBaseTypeParameter
	private List<Dao> daos = new ArrayList<Dao>();
	@SerializableBaseTypeParameter
	private List<Service> services = new ArrayList<Service>();
	@SerializableBaseTypeParameter
	private List<Controler> controlers = new ArrayList<Controler>();

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

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public List<Controler> getControlers() {
		return controlers;
	}

	public void setControlers(List<Controler> controlers) {
		this.controlers = controlers;
	}

}
