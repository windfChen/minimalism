package com.windf.plugins.manage.web.controler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.windf.core.frame.SessionContext;
import com.windf.plugins.manage.Constant;
import com.windf.plugins.web.BaseControler;

@Controller
@Scope("prototype")
@RequestMapping(value = ManagerIndexControler.MANAGE_PATH)
public class ManagerIndexControler extends BaseControler {
	protected final static String MANAGE_PATH = Constant.WEB_BASE_PATH;

	@RequestMapping(value = {"", "/"}, method = {RequestMethod.GET})
	public String index() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("queryString", request.getQueryString());
		data.put(Constant.SESSION_TEMPLATE, SessionContext.get(Constant.SESSION_TEMPLATE));

		responseReturn.page(Constant.WEB_BASE_VIEW + SessionContext.get(Constant.SESSION_TEMPLATE) + "_index");
		return responseReturn.successData(data);
	}
}
