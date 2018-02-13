package com.windf.module.development.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.windf.core.exception.CodeException;
import com.windf.core.exception.UserException;
import com.windf.core.util.file.XmlFileUtil;

/**
 * Module Master
 * 
 * @author chenyafeng
 *
 */
/**
 * @author chenyafeng
 *
 */
public class ModuleMaster implements Cloneable {

	private static ModuleMaster moduleMaster = new ModuleMaster();

	public static ModuleMaster getInstance() {
		return moduleMaster;
	}
	
	

	private List<Module> modules = new ArrayList<Module>();
	private Module core = new Module();
	
	/**
	 * 加载模块
	 * @param moduleCode
	 * @return
	 * @throws UserException
	 */
	public Module loadModule(String moduleCode) {
		File exampleDescriptFile = com.windf.core.bean.Module.getMoudleConfigFileByCode(moduleCode);
		if (!exampleDescriptFile.exists()) {
			 throw new CodeException("模板模块：[" + moduleCode + "]的配置文件不存在");
		}

		Module module = XmlFileUtil.readXml2Object(exampleDescriptFile, Module.class);

		return module;
	}

	public Module findModuleByCode(String moduleCode) {
		
		Module result = null;
		
		for (int i = 0; i < modules.size(); i++) {
			if (modules.get(i).getCode().equals(moduleCode)) {
				result = modules.get(i);
			}
		}
		
		return result;
	}
	
	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

	public Module getCore() {
		return core;
	}

	public void setCore(Module core) {
		this.core = core;
	}

}
