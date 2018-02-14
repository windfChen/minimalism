package com.windf.module.development.modle.init;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.windf.core.util.file.ModuleFile;
import com.windf.core.util.reflect.Scanner;
import com.windf.core.util.reflect.ScannerHandler;
import com.windf.module.development.entity.Module;
import com.windf.module.development.modle.ModuleMaster;
import com.windf.module.development.util.file.SourceFileUtil;

public class ModuleInitialization implements ScannerHandler {

	private Map<String, Module> modules = new HashMap<String, Module>(); // 模块
	
	public ModuleInitialization() {
		Scanner scanner = new Scanner(SourceFileUtil.getJavaPath(), this);
		scanner.run();
		
		// TODO 模块排序
	}
	
	@Override
	public void handle(File file) {
		ModuleFile moduleFile = new ModuleFile(SourceFileUtil.getJavaPath(), file);
		if (moduleFile.verifyPath()) {
			if ("java".equals(moduleFile.getPrefix())) { // 如果是java的解析
				/*
				 *  初始化模块 // TODO 先不处理插件
				 */
				Module currentModule = null;
				if ("module".equals(moduleFile.getModuleType())) {
					currentModule = modules.get(moduleFile.getModuleCode());
					if (currentModule == null) {
						currentModule = ModuleMaster.getInstance().getModule(moduleFile.getModuleCode());
						modules.put(moduleFile.getModuleCode(), currentModule);
					}
				} else if ("plugins".equals(moduleFile.getModuleType())) {
					return;
				} else {
					return;
				}
			}
		}
		
	}
}
