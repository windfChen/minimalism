package com.windf.module.development.fdao.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.windf.core.exception.DataAccessException;
import com.windf.module.development.entity.Entity;
import com.windf.module.development.entity.Field;
import com.windf.module.development.fdao.EntityDao;
import com.windf.module.development.fdao.FieldDao;

@Repository
public class FieldDaoImpl implements FieldDao{
	
	@Resource
	private EntityDao entityDao;

	@Override
	public Field read(String moduleCode, String entityName, String fieldName) throws DataAccessException {
		Entity entity = this.getEntityByCode(moduleCode, entityName);
		/*
		 * 查找Field
		 */
		Field result = null;
		List<Field> fieldList = entity.getFields();
		for (int i = 0; i < fieldList.size(); i++) {
			Field field = fieldList.get(i);
			if (entityName.equals(field.getName())) {
				result = field;
				break;
			}
		}
		
		return result;
	}

	@Override
	public int create(Field bean) throws DataAccessException {
		Entity entity = this.getEntityByCode(bean.getEntity().getModule().getCode(), bean.getEntity().getName());
		/*
		 * 删除之前的同名字段
		 */
		List<Field> fieldList = entity.getFields();
		Iterator<Field> iterator = fieldList.iterator();
		while (iterator.hasNext()) {
			Field field = (Field) iterator.next();
			if (bean.getName().equals(field.getName())) {
				iterator.remove();
				break;
			}
		}
		/*
		 * 添加新的字段
		 */
		entity.getFields().add(bean);
		return 1;
	}

	@Override
	public List<Field> listByEntityName(String moduleCode, String entityName) throws DataAccessException {
		Entity entity = this.getEntityByCode(moduleCode, entityName);
		return entity.getFields();
	}
	
	private Entity getEntityByCode(String moduleCode, String entityName) throws DataAccessException {
		Entity entity = entityDao.read(moduleCode, entityName);
		if (entity == null) {
			throw new DataAccessException("找不到实体");
		}
		return entity;
	}

}
