package com.windf.module.development.modle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.windf.core.exception.UserException;
import com.windf.module.development.entity.Module;

/**
 * Module Master
 * 
 * @author chenyafeng
 *
 */
public class ModuleMaster {
	
	private static final ModuleMaster instance = new ModuleMaster();
	public static final ModuleMaster getInstance() {
		return instance;
	}

	private Map<String, Module> modules = new HashMap<String, Module>();
	
	/**
	 * 获取模块，如果模块不存在，重新从配置文件加载
	 * @param moduleCode
	 * @return
	 * @throws UserException
	 */
	public Module getModule(String moduleCode) {
		return modules.get(moduleCode);
	}
	
	/**
	 * 新增一个模块
	 * @param module
	 */
	public void addModule(Module module) {
		modules.put(module.getCode(), module);
	}
	
	/**
	 * 获取所有模块
	 * @return
	 */
	public List<Module> getModules() {
		return new ArrayList<Module>(modules.values());
	}


}
