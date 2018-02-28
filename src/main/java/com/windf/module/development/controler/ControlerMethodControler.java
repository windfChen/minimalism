package com.windf.module.development.controler;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.windf.module.development.Constant;
import com.windf.module.development.entity.ControlerMethod;
import com.windf.module.development.service.ControlerMethodService;
import com.windf.plugins.manage.service.ManageGirdService;
import com.windf.plugins.manage.web.controler.ManagerGridControler;

@Controller
@Scope("prototype")
@RequestMapping(value = ControlerMethodControler.CONTROLER_PATH)
public class ControlerMethodControler extends ManagerGridControler<ControlerMethod>{
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "/controler/method";

	@Resource
	private ControlerMethodService controlerMethodService;
	
	@Override
	protected ManageGirdService<ControlerMethod> getManagerGridService() {
		return controlerMethodService;
	}

	@Override
	public Class<ControlerMethod> getEntity() {
		return ControlerMethod.class;
	}

}
