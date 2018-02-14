package com.windf.module.development.service;

import java.util.List;

import com.windf.module.development.entity.Entity;
import com.windf.plugins.manage.service.ManageGirdService;

public interface EntityService extends ManageGirdService<Entity>{

	/**
	 * 查询的所有列表
	 * @return
	 */
	List<Entity> getMyList();
}
