package com.windf.module.development.controler;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.windf.module.development.Constant;
import com.windf.module.development.entity.Service;
import com.windf.module.development.service.ServiceService;
import com.windf.plugins.manage.service.ManageGirdService;
import com.windf.plugins.manage.web.controler.ManagerGridControler;

@Controller
@Scope("prototype")
@RequestMapping(value = ServiceControler.CONTROLER_PATH)
public class ServiceControler extends ManagerGridControler<Service>{
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "/service";

	@Resource
	private ServiceService serviceService;
	
	@Override
	protected ManageGirdService<Service> getManagerGridService() {
		return serviceService;
	}

	@Override
	public Class<Service> getEntity() {
		return Service.class;
	}

}
