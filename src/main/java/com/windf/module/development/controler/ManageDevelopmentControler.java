package com.windf.module.development.controler;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.windf.module.development.Constant;
import com.windf.module.development.entity.Field;
import com.windf.module.development.service.ManageDevelopmentService;
import com.windf.plugins.web.BaseControler;

@Controller
@Scope("prototype")
@RequestMapping(value = ManageDevelopmentControler.CONTROLER_PATH)
public class ManageDevelopmentControler extends BaseControler{
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "/manage";
	
	@Resource
	private ManageDevelopmentService manageDevelopmentService;
	
	@RequestMapping(value = "", method = {RequestMethod.GET})
	public String page() {
		Map<String, Object> data = new HashMap<String, Object>();
		
		responseReturn.page(Constant.WEB_BASE_VIEW + "detail");
		return responseReturn.successData(data);
	}
	
	@RequestMapping(value = "/entity", method = {RequestMethod.POST})
	public String createEntityManage() {
		return responseReturn.success();
	}

	@RequestMapping(value = "/grid", method = {RequestMethod.POST})
	public String createManageGrid() {
		return responseReturn.success();
	}
	
	@RequestMapping(value = "/field", method = {RequestMethod.POST})
	public String getField() {
		String fieldId = this.paramenter.getString("id");

		Object data = null;
		try {
			data = manageDevelopmentService.getFieldById(fieldId);
		} catch (Exception e) {
			return responseReturn.error(e.getMessage());
		}
		return responseReturn.successData(data);
	}
	
	@RequestMapping(value = "/field/save", method = {RequestMethod.POST})
	public String saveField() {
		Field field = this.paramenter.getObject("entity", Field.class);
		if (field == null) {
			return responseReturn.parameterError();
		}
		
		try {
			manageDevelopmentService.saveField(field);
		} catch (Exception e) {
			return responseReturn.error(e.getMessage());
		}
		return responseReturn.success();
	}

}
