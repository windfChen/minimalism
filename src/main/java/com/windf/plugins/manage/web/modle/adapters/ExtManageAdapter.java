package com.windf.plugins.manage.web.modle.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.windf.core.bean.Page;
import com.windf.plugins.manage.Constant;
import com.windf.plugins.manage.web.modle.ManageAdapter;

@Component
public class ExtManageAdapter extends ManageAdapter {

	@Override
	public String getIndexPage() {
		return Constant.WEB_BASE_VIEW + "ext_index";
	}
	
	@Override
	public String getGridPage() {
		return Constant.WEB_BASE_VIEW + "ext_grid";
	}

	@Override
	public Map<String, Object> pageDataAdapter(Page<? extends Object> page) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("models", page.getData());
		result.put("totalCount", page.getTotal());
		return result;
	}

	@Override
	public Object entityDataAdapter(Object entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", entity);
		List<Object> list = new ArrayList<Object>();
		list.add(map);
		return list;
	}

}
