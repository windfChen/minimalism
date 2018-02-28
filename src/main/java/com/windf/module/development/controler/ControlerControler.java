package com.windf.module.development.controler;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.windf.module.development.Constant;
import com.windf.module.development.entity.Controler;
import com.windf.module.development.service.ControlerService;
import com.windf.plugins.manage.service.ManageGirdService;
import com.windf.plugins.manage.web.controler.ManagerGridControler;

@Controller
@Scope("prototype")
@RequestMapping(value = ControlerControler.CONTROLER_PATH)
public class ControlerControler extends ManagerGridControler<Controler>{
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "/controler";

	@Resource
	private ControlerService controlerService;
	
	@Override
	protected ManageGirdService<Controler> getManagerGridService() {
		return controlerService;
	}

	@Override
	public Class<Controler> getEntity() {
		return Controler.class;
	}

}
