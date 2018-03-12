package com.windf.module.development.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.module.development.entity.Field;
import com.windf.module.development.service.ControlerService;
import com.windf.module.development.service.EntityService;
import com.windf.module.development.service.FieldService;
import com.windf.module.development.service.ManageDevelopmentService;
import com.windf.module.development.service.ServiceService;
import com.windf.plugins.manage.service.ManageGirdService;

@Service
public class ManageDevelopmentServiceImpl implements ManageDevelopmentService {
	
	@Resource
	private FieldService fieldService;
	@Resource
	private EntityService entityService;
	@Resource
	private ServiceService serviceService;
	@Resource
	private ControlerService controlerService;
	@Resource
	private ManageGirdService<? extends Object> manageGirdService;

	@Override
	public void saveField(Field field) throws Exception {
		/*
		 * 保存字段
		 */
		fieldService.save(field);
		/*
		 * 构建表格样式
		 */
	}

	@Override
	public Field getFieldById(String fieldId) throws Exception {
		return fieldService.detail(fieldId);
	}

}
