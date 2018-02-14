package com.windf.module.development.fdao.impl;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.windf.core.exception.DataAccessException;
import com.windf.core.util.file.FileUtil;
import com.windf.core.util.file.XmlFileUtil;
import com.windf.module.development.entity.Module;
import com.windf.module.development.fdao.ModuleDao;
import com.windf.module.development.modle.ModuleMaster;

@Repository
public class ModuleDaoImpl implements ModuleDao{

	@Override
	public int create(Module bean) throws DataAccessException {
		Module module = (Module) bean;
		
		/*
		 * 获取配置文件位置
		 */
		File file = getMoudleConfigFileByCode(module.getCode());
		
		/*
		 * 如果不存在，创建文件的目录
		 */
		FileUtil.createIfNotExists(file, true);
		
		/*
		 * 写入配置文件
		 */
		XmlFileUtil.writeObject2Xml(module, file);
		return 1;
	}

	@Override
	public int delete(List<? extends Serializable> id) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Module bean) throws DataAccessException {
		return this.create(bean);
	}

	@Override
	public Module read(Serializable code) throws DataAccessException {
		ModuleMaster moduleMaster = ModuleMaster.getInstance();
		Module module = moduleMaster.getModule((String) code);
		if (module == null) {
			throw new DataAccessException("模块不存在！");
		}
		return module;
	}

	@Override
	public List<Module> getList() {
		return ModuleMaster.getInstance().getModules();
	}

	private File getMoudleConfigFileByCode(String code) {
		return Module.getMoudleConfigFileByCode(code);
	}
}
