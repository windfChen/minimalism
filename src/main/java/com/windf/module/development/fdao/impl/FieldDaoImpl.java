package com.windf.module.development.fdao.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.windf.core.exception.DataAccessException;
import com.windf.module.development.Constant;
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
		/*
		 * 读取模块
		 */
		Entity entity = this.getEntity(moduleCode, entityName);
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
		/*
		 * 设置id
		 */
		bean.setId(Constant.JAVA_MODULE_BASE_PACKAGE_POINT + "." + bean.getEntity().getModule().getCode() + ".entity." + bean.getEntity().getName() + "." + bean.getName());
		/*
		 * 读取实体
		 */
		Entity entity = this.getEntity(bean.getEntity().getModule().getCode(), bean.getEntity().getName());
		/*
		 * 删除之前的同名Field
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
		 * 添加新的Field
		 */
		entity.getFields().add(bean);
		/*
		 * 持久化文件
		 */
		entityDao.create(entity);
		return 1;
	}

	@Override
	public List<Field> listByEntityName(String moduleCode, String entityName) throws DataAccessException {
		/*
		 * 读取模块
		 */
		Entity entity = this.getEntity(moduleCode, entityName);
		/*
		 * 读取列表
		 */
		return entity.getFields();
	}
	
	private Entity getEntity(String moduleCode, String entityName) throws DataAccessException {
		Entity entity = entityDao.read(moduleCode, entityName);
		if (entity == null) {
			throw new DataAccessException("找不到实体");
		}
		return entity;
	}
}
