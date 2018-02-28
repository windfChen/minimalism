package com.windf.module.development.controler;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.windf.core.util.ParameterUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.entity.AbstractBaseCodeEntity;
import com.windf.module.development.entity.ControlerMethod;
import com.windf.module.development.entity.Parameter;
import com.windf.module.development.service.ControlerMethodParameterService;
import com.windf.plugins.manage.service.ManageGirdService;
import com.windf.plugins.manage.web.controler.ManagerGridControler;

@Controller
@Scope("prototype")
@RequestMapping(value = ControlerMethodParameterControler.CONTROLER_PATH)
public class ControlerMethodParameterControler extends ManagerGridControler<Parameter>{
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "/parameter";

	@Resource
	private ControlerMethodParameterService controlerMethodParameterService;
	
	@Override
	protected ManageGirdService<Parameter> getManagerGridService() {
		return controlerMethodParameterService;
	}

	@Override
	public Class<Parameter> getEntity() {
		return Parameter.class;
	}
	
	@RequestMapping(value = "/save", method = {RequestMethod.POST})
	public String save() {
		/*
		 * 接收参数
		 */
		Parameter entity = paramenter.getObject("entity", this.getEntity());
		String codeEntityName = paramenter.getString("entity.codeEntity.name");
		String notEmpty = paramenter.getString("entity.notEmpty");
		if (entity == null || ParameterUtil.hasEmpty(codeEntityName, notEmpty)) {
			return responseReturn.parameterError();
		}
		/*
		 * 组织参数
		 */
		if ("不能为空".equals(notEmpty)) {
			entity.setNotEmpty(true);
		}
		AbstractBaseCodeEntity controlerMethod = new ControlerMethod();
		controlerMethod.setName(codeEntityName);
		entity.setCodeEntity(controlerMethod);
		/*
		 * 调用方法
		 */
		try {
			this.getManagerGridService().save(entity);
			return responseReturn.success();
		} catch (Exception e) {
			e.printStackTrace();
			return responseReturn.error(e.getMessage());
		}
	}
}
