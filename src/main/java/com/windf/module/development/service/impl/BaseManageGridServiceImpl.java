package com.windf.module.development.service.impl;

import java.util.Map;

import com.windf.plugins.manage.entity.GridConfig;
import com.windf.plugins.manage.service.ManageGirdService;

public abstract class BaseManageGridServiceImpl<T> implements ManageGirdService<T>{

	@Override
	public GridConfig getGridConfig(String code, String roleId, Map<String, Object> condition) throws Exception {
		GridConfig gridConfig = GridConfig.loadGridConfigByCode(code, condition);
		return gridConfig;
	}
	
}
