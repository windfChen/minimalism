package com.windf.module.development.controler;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.windf.module.development.Constant;
import com.windf.module.development.entity.Field;
import com.windf.module.development.service.FieldService;
import com.windf.plugins.manage.service.ManageGirdService;
import com.windf.plugins.manage.web.controler.ManagerGridControler;

@Controller
@Scope("prototype")
@RequestMapping(value = FieldControler.CONTROLER_PATH)
public class FieldControler extends ManagerGridControler{
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "/field";
	
	@Resource
	private FieldService fieldService;

	@Override
	protected ManageGirdService getManagerGridService() {
		return fieldService;
	}

	@Override
	protected Class<? extends Object> getEntity() {
		return Field.class;
	}

}
