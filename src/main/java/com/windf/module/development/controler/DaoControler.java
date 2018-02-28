package com.windf.module.development.controler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.windf.module.development.Constant;
import com.windf.module.development.entity.Dao;
import com.windf.module.development.service.DaoService;
import com.windf.plugins.manage.service.ManageGirdService;
import com.windf.plugins.manage.web.controler.ManagerGridControler;

@Controller
@Scope("prototype")
@RequestMapping(value = DaoControler.CONTROLER_PATH)
public class DaoControler extends ManagerGridControler<Dao>{
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "/dao";

	@Resource
	private DaoService daoService;
	
	@Override
	protected ManageGirdService<Dao> getManagerGridService() {
		return daoService;
	}

	@Override
	public Class<Dao> getEntity() {
		return Dao.class;
	}

	@RequestMapping(value = "/myList", method = {RequestMethod.GET})
	public String myList() {
		List<Dao> data = daoService.getAll();
		List<String[]> result = new ArrayList<String[]>();
		for (int i = 0; i < data.size(); i++) {
			Dao dao = data.get(i);
			result.add(new String[]{dao.getName(), dao.getName()});
		}
		return responseReturn.returnData(result);
	}
}
