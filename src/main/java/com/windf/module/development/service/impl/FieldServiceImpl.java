package com.windf.module.development.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.windf.core.bean.Page;
import com.windf.core.exception.ParameterException;
import com.windf.core.util.SQLUtil;
import com.windf.module.development.entity.Entity;
import com.windf.module.development.entity.Field;
import com.windf.module.development.entity.Module;
import com.windf.module.development.entity.ModuleMaster;
import com.windf.module.development.modle.component.EntityCoder;
import com.windf.module.development.service.FieldService;

@Service
public class FieldServiceImpl extends BaseManageGridServiceImpl implements FieldService{

	@Override
	public Page<? extends Object> list(Map<String, Object> condition, Integer pageNo, Integer pageSize) throws Exception {
		Entity entity = this.getEntity((String) condition.get("moduleCode"), (String) condition.get("entityName"));
		
		Page<Field> page = new Page<Field>(Long.valueOf(pageNo) , pageSize);
		page.setTotal(Long.valueOf(entity.getFields().size()));
		page.setData(entity.getFields().subList(page.getStartIndex().intValue(), page.getEndIndex().intValue()));
		
		return page;
	}

	@Override
	public int save(Object bean) throws Exception {
		Field field = (Field) bean;
		field.setType(SQLUtil.dbType2JavaType(field.getDatabaseType()));
		field.setName(SQLUtil.dbName2JavaName(field.getDatabaseName()));
		
		Entity entity = this.getEntity(field.getEntity().getModule().getCode(), field.getEntity().getName());
		
		EntityCoder entityCoder = new EntityCoder(entity);
		entityCoder.addField(field);
		entityCoder.write();
		
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
	
	/**
	 * 根据参数获取Entity
	 * @param condition
	 * @return
	 * @throws ParameterException
	 */
	protected Entity getEntity(String moduleCode, String entityName) throws ParameterException {

		ModuleMaster moduleMaster = ModuleMaster.getInstance();
		Module module = moduleMaster.findModuleByCode(moduleCode);
		if (module == null) {
			throw new ParameterException("模块不存在！");
		}
		Entity entity = module.getEntityByName(entityName);
		if (entity == null) {
			throw new ParameterException("实体不存在！");
		}
		
		return entity;
	}

}
