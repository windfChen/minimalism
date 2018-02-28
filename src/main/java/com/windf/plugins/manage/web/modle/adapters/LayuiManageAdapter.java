package com.windf.plugins.manage.web.modle.adapters;

import org.springframework.stereotype.Component;

import com.windf.plugins.manage.Constant;
import com.windf.plugins.manage.web.modle.ManageAdapter;

@Component
public class LayuiManageAdapter extends ManageAdapter {

	@Override
	public String getIndexPage() {
		return Constant.WEB_BASE_VIEW + "layui_index";
	}
	
	@Override
	public String getGridPage() {
		return Constant.WEB_BASE_VIEW + "layui_grid";
	}

}
