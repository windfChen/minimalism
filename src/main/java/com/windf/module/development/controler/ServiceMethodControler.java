package com.windf.module.development.controler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.windf.core.util.ParameterUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.entity.Parameter;
import com.windf.module.development.entity.ServiceMethod;
import com.windf.module.development.service.ServiceMethodService;
import com.windf.plugins.manage.service.ManageGirdService;
import com.windf.plugins.manage.web.controler.ManagerGridControler;

@Controller
@Scope("prototype")
@RequestMapping(value = ServiceMethodControler.CONTROLER_PATH)
public class ServiceMethodControler extends ManagerGridControler<ServiceMethod> {
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "/service/method";

	@Resource
	private ServiceMethodService serviceMethodService;
	
	@Override
	protected ManageGirdService<ServiceMethod> getManagerGridService() {
		return serviceMethodService;
	}

	@Override
	protected Class<ServiceMethod> getEntity() {
		return ServiceMethod.class;
	}
	
	@RequestMapping(value = "/save", method = {RequestMethod.POST})
	public String save() {
		/*
		 * 接收参数
		 */
		ServiceMethod entity = paramenter.getObject("entity", this.getEntity());
		String paramStr = paramenter.getString("entity.paramStr");
		if (entity == null || ParameterUtil.hasEmpty(paramStr)) {
			return responseReturn.parameterError();
		}
		/*
		 * 组织参数
		 */
		List<Parameter> parameters = new ArrayList<Parameter>();
		String[] params = paramStr.trim().split(",");
		for (int i = 0; i < params.length; i++) {
			String param = params[i];
			String[] strs = param.split(" ");
			parameters.add(new Parameter(strs[0], strs[1]));
		}
		entity.setParameters(parameters);
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
