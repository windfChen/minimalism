package com.windf.module.development.frame;

import com.windf.core.frame.Initializationable;
import com.windf.module.development.modle.init.ModuleInitialization;

public class Initialization implements Initializationable{
	
	@Override
	public void init() {
		/*
		 * 初始化模板文件
		 */
		new ModuleInitialization();
	}

	@Override
	public int getOrder() {
		return NORMAL;
	}

	

}
