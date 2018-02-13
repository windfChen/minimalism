package com.windf.core.frame;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.windf.core.bean.Module;
import com.windf.core.exception.CodeException;
import com.windf.core.util.CollectionUtil;
import com.windf.core.util.file.FileUtil;
import com.windf.core.util.file.ModuleFile;
import com.windf.core.util.reflect.Scanner;
import com.windf.core.util.reflect.ScannerHandler;

public class ProjectStart implements ScannerHandler{
	private static ProjectStart projectStart = new ProjectStart();
	
	public static ProjectStart getInstance() {
		return projectStart;
	}
	
	private Map<String, Module> modules = new HashMap<String, Module>(); // 模块
	private Map<String, Module> plugins = new HashMap<String, Module>(); // 插件
	
	private ProjectStart() {
		
	}
	
	/**
	 * 项目开始的时候执行，用于初始化模块，和注册信息
	 */
	public void start() {
		/*
		 * 读取classes，初始化模块
		 */
		initModules();
		/*
		 * 注册各个模块
		 */
		initRegister();
		/*
		 * 执行各个模块的初始化操作
		 */
		InitializationControler.getInstance().doInit();
	}
	
	/**
	 * 读取各个模块，寻找模块的描述信息
	 * 寻找初始化类
	 * 寻找过滤器
	 * session注册
	 * 读取各个插件，寻找插件的描述信息
	 */
	private void initModules() {
		/*
		 * 获取遍历目录
		 */
		Scanner scanner = new Scanner(FileUtil.getClassPath(), this);
		scanner.run();
		
		/*
		 * TODO 模块间排序
		 */
	}
	
	private void initRegister() {
		/*
		 * 遍历模块和插件，注册服务 
		 */
		Iterator<Module> iterator = modules.values().iterator();
		while (iterator.hasNext()) {
			Module module = (Module) iterator.next();
			this.registerModule(module);
		}
		iterator = plugins.values().iterator();
		while (iterator.hasNext()) {
			Module module = (Module) iterator.next();
			this.registerModule(module);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void handle(File file) {
		ModuleFile moduleFile = new ModuleFile(FileUtil.getClassPath() ,file);
		if (moduleFile.verifyPath()) {
			if ("class".equals(moduleFile.getPrefix())) { // 如果是java的解析
				
				/*
				 *  初始化模块
				 */
				Module currentModule = null;
				if ("module".equals(moduleFile.getModuleType())) {
					currentModule = modules.get(moduleFile.getModuleCode());
					if (currentModule == null) {
						currentModule = new Module(moduleFile.getModuleCode());
						modules.put(moduleFile.getModuleCode(), currentModule);
					}
				} else if ("plugins".equals(moduleFile.getModuleType())){
					currentModule = plugins.get(moduleFile.getModuleCode());
					if (currentModule == null) {
						currentModule = new Module(moduleFile.getModuleCode());
						plugins.put(moduleFile.getModuleCode(), currentModule);
					}
				} else {
					return;
				}

				try {
					Class clazz = Class.forName(moduleFile.getClassName());

					if ("frame".equals(moduleFile.getPackageName())) {
						if (Initializationable.class.isAssignableFrom(clazz)) { // 添加初始化
							if (CollectionUtil.isEmpty(currentModule.getInitializationables())) {
								currentModule.setInitializationables(new ArrayList<Initializationable>());
							}
							currentModule.getInitializationables().add((Initializationable) clazz.newInstance());
						} else if (Filter.class.isAssignableFrom(clazz)) { // 添加拦截器
							if (CollectionUtil.isEmpty(currentModule.getFilters())) {
								currentModule.setFilters(new ArrayList<Filter>());
							}
							currentModule.getFilters().add((Filter) clazz.newInstance());
						} else if (Session.class.isAssignableFrom(clazz)) { // 添加会话
							if (CollectionUtil.isEmpty(currentModule.getSessions())) {
								currentModule.setSessions(new ArrayList<Session>());
							}
							currentModule.getSessions().add((Session) clazz.newInstance());
						}
					}

				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
					throw new CodeException("Initializationable 初始化类启动错误", e);
				}
			}
		}
	}
	

	/**
	 * 模块的各个服务注册
	 * @param module
	 */
	private void registerModule(Module module) {
		/*
		 * 过滤器注册
		 */
		if (CollectionUtil.isNotEmpty(module.getFilters())) {
			for (Filter filter : module.getFilters()) {
				FilterControler.getInstance().addFilter(filter);
			}
		}
		
		/*
		 * 初始化注册
		 */
		if (CollectionUtil.isNotEmpty(module.getInitializationables())) {
			for (Initializationable initializationable : module.getInitializationables()) {
				InitializationControler.getInstance().addInitializationable(initializationable);
			}
		}
		
	}

}
