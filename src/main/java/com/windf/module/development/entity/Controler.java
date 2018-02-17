package com.windf.module.development.entity;

import java.util.ArrayList;
import java.util.List;

public class Controler extends AbstractBaseCodeEntity {
	// 基本信息
	private String urlPath;
	// 方法列表
	private List<ControlerMethod> controlerMehthods = new ArrayList<ControlerMethod>();
	
	// 反向引用
	private Module module;

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public List<ControlerMethod> getControlerMehthods() {
		return controlerMehthods;
	}

	public void setControlerMehthods(List<ControlerMethod> controlerMehthods) {
		this.controlerMehthods = controlerMehthods;
	}

}
