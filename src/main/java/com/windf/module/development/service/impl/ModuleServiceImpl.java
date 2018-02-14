package com.windf.module.development.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.bean.Page;
import com.windf.module.development.entity.Module;
import com.windf.module.development.fdao.ModuleDao;
import com.windf.module.development.service.ModuleService;
import com.windf.plugins.manage.entity.GridConfig;

@Service
public class ModuleServiceImpl extends BaseManageGridServiceImpl<Module> implements ModuleService {

	@Resource
	private ModuleDao devModuleDao;
	
	@Override
	public GridConfig getGridConfig(String code, String roleId, Map<String, Object> condition) {
		GridConfig gridConfig = GridConfig.loadGridConfigByCode(code, condition);
		return gridConfig;
	}

	@Override
	public Page<Module> list(Map<String, Object> condition, Integer pageNo, Integer PageSize) {
		List<Module> moduleMasterList = devModuleDao.getList();
		
		Page<Module> page = new Page<Module>(Long.valueOf(pageNo) , PageSize);
		if (moduleMasterList.size() > 0) {
			page.setTotal(Long.valueOf(moduleMasterList.size()));
			page.setData(moduleMasterList.subList(page.getStartIndex().intValue(), page.getEndIndex().intValue()));
		}
		
		return page;
	}

	@Override
	public int save(Module module) throws Exception {
		/*
		 * 写入配置文件
		 */
		devModuleDao.create(module);
		
		return 1;
	}

	@Override
	public Module detail(Serializable id) throws Exception {
		return devModuleDao.read(id);
	}

	@Override
	public int update(Module newModule) throws Exception {
		Module module = devModuleDao.read(newModule.getCode());
		
		module.setCode(newModule.getCode());
		module.setName(newModule.getName());
		module.setBasePath(newModule.getBasePath());
		module.setInfo(newModule.getInfo());
		
		/*
		 * 写入配置文件
		 */	
		devModuleDao.update(module);
		
		return 1;
	}

	@Override
	public int delete(List<? extends Serializable> id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
