package com.windf.module.development.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.bean.Page;
import com.windf.core.util.SQLUtil;
import com.windf.module.development.entity.Field;
import com.windf.module.development.fdao.FieldDao;
import com.windf.module.development.modle.component.EntityCoder;
import com.windf.module.development.service.FieldService;

@Service
public class FieldServiceImpl extends BaseManageGridServiceImpl<Field> implements FieldService{

	@Resource
	private FieldDao fieldDao;
	
	@Override
	public Page<Field> list(Map<String, Object> condition, Integer pageNo, Integer pageSize) throws Exception {
		List<Field> fieldList = fieldDao.listByEntityName((String) condition.get("moduleCode"), (String) condition.get("entityName"));
		
		Page<Field> page = new Page<Field>(Long.valueOf(pageNo) , pageSize);
		page.setTotal(Long.valueOf(fieldList.size()));
		page.setData(fieldList.subList(page.getStartIndex().intValue(), page.getEndIndex().intValue()));
		
		return page;
	}

	@Override
	public int save(Field bean) throws Exception {
		/*
		 * 设置默认字段
		 */
		Field field = (Field) bean;
		field.setType(SQLUtil.dbType2JavaType(field.getDatabaseType()));
		field.setName(SQLUtil.dbName2JavaName(field.getDatabaseName()));
		/*
		 * 保存字段
		 */
		fieldDao.create(field);
		
		/*
		 * 代码更新
		 */
		EntityCoder entityCoder = new EntityCoder(field.getEntity());
		entityCoder.addField(field);
		
		return 0;
	}

	@Override
	public Field detail(Serializable id) throws Exception {
		Field result = null;
		
		String[] strs = ((String) id).split(".");
		if (strs.length == 3) {
			result = fieldDao.read(strs[0], strs[1], strs[2]);
		}
		
		return result;
	}

	@Override
	public int update(Field bean) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(List<? extends Serializable> id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
