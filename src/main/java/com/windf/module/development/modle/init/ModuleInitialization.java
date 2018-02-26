package com.windf.module.development.modle.init;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.windf.core.exception.DataAccessException;
import com.windf.core.spring.SpringUtil;
import com.windf.core.util.file.FileUtil;
import com.windf.module.development.fdao.ModuleDao;
import com.windf.module.development.util.file.SourceFileUtil;

public class ModuleInitialization {

	public ModuleInitialization() {
		/*
		 * 获得配置文件基础路径
		 */
		File basePath = FileUtil.getFile(SourceFileUtil.getModuleFilePath());
		/*
		 * 遍历所有路径，找到对应的模块code，读取
		 */
		List<String> moduleCodeList = new ArrayList<String>();
		File[] subFiles = basePath.listFiles();
		if (subFiles != null) {
			for (int i = 0; i < subFiles.length; i++) {
				File file = subFiles[i];
				/*
				 * 排除隐藏文件
				 */
				if (file.getName().startsWith(".")) {
					continue;
				}
				moduleCodeList.add(file.getName());
			}
		}
		
		/*
		 * 依次读取模块
		 */
		ModuleDao moduleDao = (ModuleDao) SpringUtil.getBean("moduleDao");
		for (int i = 0; i < moduleCodeList.size(); i++) {
			try {
				moduleDao.read(moduleCodeList.get(i));
			} catch (DataAccessException e) {
				e.printStackTrace();
			}
		}
	}

}
