package com.windf.module.development.entity;

import java.util.ArrayList;
import java.util.List;

import com.windf.core.util.reflect.UnSerializable;

public class Controler extends AbstractBaseCodeEntity {
	private String urlPath;
	private List<ControlerMethod> controlerMehthods = new ArrayList<ControlerMethod>();	// 方法列表
	@UnSerializable
	private Module module;	// 反向引用

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
