package com.windf.module.index.controler;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.windf.module.index.Constant;
import com.windf.plugins.web.BaseControler;

@Controller
@Scope("prototype")
@RequestMapping(value = IndexControler.CONTROLER_PATH)
public class IndexControler extends BaseControler{
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "";

	/**
	 * 首页
	 * @return
	 */
	@RequestMapping(value = {"/", ""}, method = {RequestMethod.GET})
	public String index() {
		return responseReturn.redirect("/manage/dev");
	}
	
}
