package com.windf.module.development.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.bean.Page;
import com.windf.module.development.entity.Entity;
import com.windf.module.development.entity.Module;
import com.windf.module.development.fdao.EntityDao;
import com.windf.module.development.fdao.ModuleDao;
import com.windf.module.development.modle.component.EntityCoder;
import com.windf.module.development.service.EntityService;

@Service
public class EntityServiceImpl extends BaseManageGridServiceImpl<Entity> implements EntityService{
	
	@Resource
	private EntityDao entityDao;
	@Resource
	private ModuleDao moduleDao;

	@Override
	public Page<Entity> list(Map<String, Object> condition, Integer pageNo, Integer pageSize) throws Exception {
		List<Entity> entityList = entityDao.listByModuleCode((String) condition.get("moduleCode"));
		
		Page<Entity> page = new Page<Entity>(Long.valueOf(pageNo) , pageSize);
		if (entityList.size() > 0) {
			page.setTotal(Long.valueOf(entityList.size()));
			page.setData(entityList.subList(page.getStartIndex().intValue(), page.getEndIndex().intValue()));
		}
		
		return page;
	}

	@Override
	public int save(Entity entity) throws Exception {
		/*
		 * 设置默认参数
		 */
		Module module = moduleDao.read(entity.getModule().getCode());
		entity.setModule(module);
		/*
		 * 数据模型创建更新
		 */
		entityDao.create(entity);
		/*
		 * 代码更新
		 */
		EntityCoder entityCoder = new EntityCoder(entity);
		entityCoder.create();
		return 0;
	}

	@Override
	public Entity detail(Serializable id) throws Exception {
		Entity result = null;
		
		String[] strs = ((String) id).split(".");
		if (strs.length == 2) {
			result = entityDao.read(strs[0], strs[1]);
		}
		
		return result;
	}

	@Override
	public int update(Entity bean) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(List<? extends Serializable> id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Entity> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
