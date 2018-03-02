package com.windf.plugins.manage.entity;

public class ColumnConfig extends FieldConfig {

	private boolean required;
	private boolean canList;
	private boolean canSearch;
	private boolean canAdd;
	private boolean canBatchAdd;
	private boolean canUpdate;
	private boolean canReport;
	private boolean canOrder;
	
	private String display;	// 显示用的模板,eg：<a href="{m.id}">{m,value}</a>

	public boolean isCanSearch() {
		return canSearch;
	}

	public void setCanSearch(boolean canSearch) {
		this.canSearch = canSearch;
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

	public boolean isCanUpdate() {
		return canUpdate;
	}

	public void setCanUpdate(boolean canUpdate) {
		this.canUpdate = canUpdate;
	}

	public boolean isCanReport() {
		return canReport;
	}

	public void setCanReport(boolean canReport) {
		this.canReport = canReport;
	}

	public boolean isCanOrder() {
		return canOrder;
	}

	public void setCanOrder(boolean canOrder) {
		this.canOrder = canOrder;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public boolean isCanList() {
		return canList;
	}

	public void setCanList(boolean canList) {
		this.canList = canList;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

}
