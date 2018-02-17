package com.windf.module.development.fdao.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.windf.core.exception.DataAccessException;
import com.windf.module.development.entity.Entity;
import com.windf.module.development.entity.Module;
import com.windf.module.development.fdao.EntityDao;
import com.windf.module.development.fdao.ModuleDao;

@Repository
public class EntityDaoImpl implements EntityDao{
	
	@Resource
	private ModuleDao moduleDao;

	@Override
	public Entity read(String moduleCode, String entityName) throws DataAccessException {
		Module module = moduleDao.read(moduleCode);
		if (module == null) {
			throw new DataAccessException("找不到模块");
		}
		/*
		 * 查找Entity
		 */
		Entity result = null;
		List<Entity> entityList = module.getEntitys();
		for (int i = 0; i < entityList.size(); i++) {
			Entity entity = entityList.get(i);
			if (entityName.equals(entity.getName())) {
				result = entity;
				break;
			}
		}
		
		return result;
	}

	@Override
	public int create(Entity bean) throws DataAccessException {
		Module module = moduleDao.read(bean.getModule().getCode());
		/*
		 * 删除之前的同名Entity
		 */
		List<Entity> entityList = module.getEntitys();
		Iterator<Entity> iterator = entityList.iterator();
		while (iterator.hasNext()) {
			Entity entity = (Entity) iterator.next();
			if (bean.getName().equals(entity.getName())) {
				iterator.remove();
				break;
			}
		}
		/*
		 * 添加新的Entity
		 */
		module.getEntitys().add(bean);
		return 1;
	}

	@Override
	public List<Entity> listByModuleCode(String moduleCode) throws DataAccessException {
		Module module = moduleDao.read(moduleCode);
		if (module == null) {
			throw new DataAccessException("模块不存在");
		}
		
		return module.getEntitys();
	}


}
