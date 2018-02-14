package com.windf.module.development.modle;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.windf.core.exception.CodeException;
import com.windf.core.exception.UserException;
import com.windf.core.util.file.XmlFileUtil;
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
		Module result = modules.get(moduleCode);
		if (result == null) {
			File exampleDescriptFile = Module.getMoudleConfigFileByCode(moduleCode);
			if (!exampleDescriptFile.exists()) {
				 throw new CodeException("模板模块：[" + moduleCode + "]的配置文件不存在");
			}

			result = XmlFileUtil.readXml2Object(exampleDescriptFile, Module.class);
			modules.put(moduleCode, result);
		}

		return result;
	}
	
	/**
	 * 新增一个模块
	 * @param module
	 */
	public void addModule(Module module) {
		modules.put(module.getName(), module);
	}
	
	/**
	 * 获取所有模块
	 * @return
	 */
	public List<Module> getModules() {
		return new ArrayList<Module>(modules.values());
	}


}
