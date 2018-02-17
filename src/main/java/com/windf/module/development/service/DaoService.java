package com.windf.module.development.service;

import java.util.List;

import com.windf.module.development.entity.Dao;
import com.windf.plugins.manage.service.ManageGirdService;

public interface DaoService extends ManageGirdService<Dao> {
	/**
	 * 查询的所有列表
	 * @return
	 */
	List<Dao> getAll();
}
