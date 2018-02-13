package com.windf.module.development.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.windf.core.bean.Page;
import com.windf.core.exception.ParameterException;
import com.windf.core.util.SQLUtil;
import com.windf.module.development.entity.Entity;
import com.windf.module.development.entity.Module;
import com.windf.module.development.entity.ModuleMaster;
import com.windf.module.development.modle.component.EntityCoder;
import com.windf.module.development.service.EntityService;

@Service
public class EntityServiceImpl extends BaseManageGridServiceImpl implements EntityService{

	@Override
	public Page<? extends Object> list(Map<String, Object> condition, Integer pageNo, Integer pageSize) throws Exception {
		ModuleMaster moduleMaster = ModuleMaster.getInstance();
		Module module = moduleMaster.findModuleByCode((String) condition.get("moduleCode"));
		if (module == null) {
			throw new ParameterException("模块不存在！");
		}
		
		Page<Entity> page = new Page<Entity>(Long.valueOf(pageNo) , pageSize);
		if (module.listEntitys().size() > 0) {
			page.setTotal(Long.valueOf(module.listEntitys().size()));
			page.setData(module.listEntitys().subList(page.getStartIndex().intValue(), page.getEndIndex().intValue()));
		}
		
		return page;
	}

	@Override
	public int save(Object bean) throws Exception {
		/*
		 * 转换参数
		 */
		Entity entity = (Entity) bean;	
		/*
		 * 设置默认参数
		 */
		entity.setTableName(SQLUtil.entityName2TableName(entity.getName()));
		/*
		 * 数据模型创建更新
		 */
		Module module = ModuleMaster.getInstance().findModuleByCode(entity.getModule().getCode());
		entity.setModule(module);
		module.listEntitys().add(entity);
		/*
		 * 代码更新
		 */
		EntityCoder entityCoder = new EntityCoder(entity);
		entityCoder.createEntity();
		return 0;
	}

	@Override
	public Object detail(Serializable id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Object bean) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(List<? extends Serializable> id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Entity> getMyList() {
		// TODO Auto-generated method stub
		List<Entity> result = new ArrayList<Entity>();
		result.addAll(ModuleMaster.getInstance().getCore().listEntitys());
		List<Module> modules = ModuleMaster.getInstance().getModules();
		for (int i = 0; i < modules.size(); i++) {
			Module module = modules.get(i);
			result.addAll(module.listEntitys());
		}
		return result;
	}

}
