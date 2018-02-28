package com.windf.plugins.manage.web.modle;

import java.util.HashMap;
import java.util.Map;

import com.windf.core.bean.Page;
import com.windf.plugins.manage.entity.GridConfig;
import com.windf.plugins.manage.web.controler.ManagerGridControler;

public abstract class ManageAdapter {
	/**
	 * 获取配置首页
	 * @return
	 */
	public abstract String getIndexPage();
	/**
	 * 获取配置表格页面
	 * @return
	 */
	public abstract String getGridPage();
	/**
	 * 重新设置gridConfig
	 * @param gridConfig
	 * @return
	 */
	public GridConfig resetGridConfig(GridConfig gridConfig) {
		return gridConfig;
	}
	/**
	 * 重新格式化分页对象，组装成map，然后转换成json输出
	 * @param page
	 * @return
	 */
	public Map<String, Object> pageDataAdapter(Page<? extends Object> page) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", page.getData());
		result.put("totalCount", page.getTotal());
		return result;
	}
	/**
	 * 重新格式化实体对象
	 * @param page
	 * @return
	 */
	public Object entityDataAdapter(Object entity) {
		return entity;
	}
	/**
	 * 获得实体独享
	 * @param controler
	 * @return
	 */
	public Object getEntity(ManagerGridControler<? extends Object> controler) {
		return controler.paramenter.getObject("entity", controler.getEntity());
	}
}
