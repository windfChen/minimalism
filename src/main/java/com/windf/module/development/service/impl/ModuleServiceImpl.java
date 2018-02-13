package com.windf.module.development.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.windf.core.bean.Page;
import com.windf.module.development.entity.Module;
import com.windf.module.development.entity.ModuleMaster;
import com.windf.module.development.service.ModuleService;
import com.windf.plugins.manage.entity.GridConfig;

@Service
public class ModuleServiceImpl extends BaseManageGridServiceImpl  implements ModuleService {

	@Override
	public GridConfig getGridConfig(String code, String roleId, Map<String, Object> condition) {
		GridConfig gridConfig = GridConfig.loadGridConfigByCode(code, condition);
		return gridConfig;
	}

	@Override
	public Page<Module> list(Map<String, Object> condition, Integer pageNo, Integer PageSize) {

		ModuleMaster moduleMaster = ModuleMaster.getInstance();
		
		Page<Module> page = new Page<Module>(Long.valueOf(pageNo) , PageSize);
		if (moduleMaster.getModules().size() > 0) {
			page.setTotal(Long.valueOf(moduleMaster.getModules().size()));
			page.setData(moduleMaster.getModules().subList(page.getStartIndex().intValue(), page.getEndIndex().intValue()));
		}
		
		return page;
	}

	@Override
	public int save(Object entity) throws Exception {
		/*
		 * 模块实体
		 */
		Module module = (Module) entity;
		
		/*
		 * 写入配置文件
		 */
		module.write();
		
		/*
		 * 添加模块到模块管理类
		 */
		ModuleMaster.getInstance().getModules().add(module);
		
		return 0;
	}

	@Override
	public Object detail(Serializable id) throws Exception {
		return ModuleMaster.getInstance().findModuleByCode((String) id);
	}

	@Override
	public int update(Object bean) throws Exception {
		Module newModule = (Module) bean;
		Module module = ModuleMaster.getInstance().findModuleByCode(newModule.getCode());
		
		module.setCode(newModule.getCode());
		module.setName(newModule.getName());
		module.setBasePath(newModule.getBasePath());
		module.setInfo(newModule.getInfo());
		
		/*
		 * 写入配置文件
		 */	
		module.write();
		
		return 0;
	}

	@Override
	public int delete(List<? extends Serializable> id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
