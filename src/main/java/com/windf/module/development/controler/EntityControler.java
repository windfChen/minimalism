package com.windf.module.development.controler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.windf.module.development.Constant;
import com.windf.module.development.entity.Entity;
import com.windf.module.development.service.EntityService;
import com.windf.plugins.manage.service.ManageGirdService;
import com.windf.plugins.manage.web.controler.ManagerGridControler;

@Controller
@Scope("prototype")
@RequestMapping(value = EntityControler.CONTROLER_PATH)
public class EntityControler extends ManagerGridControler<Entity>{
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "/entity";

	@Resource
	private EntityService entityService;
	
	@Override
	protected ManageGirdService<Entity> getManagerGridService() {
		return entityService;
	}

	@Override
	protected Class<Entity> getEntity() {
		return Entity.class;
	}

	@RequestMapping(value = "/myList", method = {RequestMethod.GET})
	public String myList() {
		List<Entity> data = entityService.getAll();
		List<String[]> result = new ArrayList<String[]>();
		for (int i = 0; i < data.size(); i++) {
			Entity entity = data.get(i);
			result.add(new String[]{entity.getName(), entity.getName()});
		}
		return responseReturn.returnData(result);
	}
}
