package com.windf.plugins.manage.entity;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.windf.core.bean.Module;
import com.windf.core.exception.CodeException;
import com.windf.core.util.JSONUtil;
import com.windf.core.util.file.FileReadUtil;
import com.windf.plugins.manage.Constant;

public class GridConfig {

	/**
	 * 加载GridConfig
	 * 
	 * @param code
	 * @param condition
	 * @return
	 */
	public static GridConfig loadGridConfigByCode(String code, Map<String, Object> condition) {
		GridConfig result = null;
		try {
			Module module = Module.getCurrentMoudle();
			String gridConfigFilePath = module.getConfigFilePath() + Constant.MANAGE_JSON_CONFIG_PATH + code + ".json";
			String gridConfigJsonStr = FileReadUtil.readFileAsString(gridConfigFilePath);

			// 替换参数
			if (condition != null) {
				Iterator<String> iterator = condition.keySet().iterator();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					Object value = condition.get(key);
					if (value != null) {
						gridConfigJsonStr = gridConfigJsonStr.replaceAll("\\$\\{param\\." + key + "\\}",
								value.toString());
					}
				}
			}

			result = JSONUtil.pasrseJSONStr(gridConfigJsonStr, GridConfig.class);
		} catch (Throwable e) {
			throw new CodeException("json 获取错误");
		}

		return result;
	}

	private String title; // 表格标题
	private String dataSource; // 数据源，对应dao，需要实现ListDao
	private List<ColumnConfig> columns;
	private List<MenuConfig> menus;

	private boolean isPrepared;
	private boolean canAdd;
	private boolean canBatchAdd;
	private boolean canDelete;
	private boolean canUpdate;
	private boolean canSearch;

	private String roleCode; // 列的全选过滤，只有相应角色code的人才能查看

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ColumnConfig> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnConfig> columns) {
		this.columns = columns;
	}

	public List<MenuConfig> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuConfig> menus) {
		this.menus = menus;
	}

	public boolean isPrepared() {
		return isPrepared;
	}

	public void setPrepared(boolean isPrepared) {
		this.isPrepared = isPrepared;
	}

	public boolean isCanAdd() {
		return canAdd;
	}

	public void setCanAdd(boolean canAdd) {
		this.canAdd = canAdd;
	}

	public boolean isCanBatchAdd() {
		return canBatchAdd;
	}

	public void setCanBatchAdd(boolean canBatchAdd) {
		this.canBatchAdd = canBatchAdd;
	}

	public boolean isCanDelete() {
		return canDelete;
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

	public boolean isCanUpdate() {
		return canUpdate;
	}

	public void setCanUpdate(boolean canUpdate) {
		this.canUpdate = canUpdate;
	}

	public boolean isCanSearch() {
		return canSearch;
	}

	public void setCanSearch(boolean canSearch) {
		this.canSearch = canSearch;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

}
