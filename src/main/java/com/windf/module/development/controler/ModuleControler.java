package com.windf.module.development.controler;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.windf.module.development.Constant;
import com.windf.module.development.entity.Module;
import com.windf.module.development.service.ModuleService;
import com.windf.plugins.manage.service.ManageGirdService;
import com.windf.plugins.manage.web.controler.ManagerGridControler;

@Controller
@Scope("prototype")
@RequestMapping(value = ModuleControler.CONTROLER_PATH)
public class ModuleControler extends ManagerGridControler<Module> {
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "/module";
	
	@Resource
	private ModuleService moduleService ;
	
	@Override
	protected ManageGirdService<Module> getManagerGridService() {
		return moduleService;
	}
	
	@Override
	protected Class<Module> getEntity() {
		return Module.class;
	}

}
