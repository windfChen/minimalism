package com.windf.plugins.manage.web.controler;

import com.windf.core.spring.SpringUtil;
import com.windf.core.util.PropertiesUtil;
import com.windf.plugins.manage.Constant;
import com.windf.plugins.manage.web.modle.ManageAdapter;
import com.windf.plugins.web.BaseControler;

public abstract class BaseManagerControler<T> extends BaseControler {

	/**
	 * 根据角色等信息获得适配器，目前只获得默认适配器
	 * @return
	 */
	protected ManageAdapter getAdapter() {
		String defaultAdapter = PropertiesUtil.getPluginsConfig(Constant.MODULE_CODE, null, "manage.apapter.default");
		return (ManageAdapter) SpringUtil.getBean(defaultAdapter + "ManageAdapter");
	}
}
