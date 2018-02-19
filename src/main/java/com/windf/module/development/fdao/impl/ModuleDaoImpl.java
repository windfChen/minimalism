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
import com.windf.module.development.util.file.SourceFileUtil;

@Repository
public class ModuleDaoImpl implements ModuleDao{

	@Override
	public int create(Module entity) throws DataAccessException {
		/*
		 * 添加内存模型
		 */
		ModuleMaster.getInstance().addModule(entity);
		/*
		 * 添加到配置文件
		 */
		writeObject2File(entity);
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
		/*
		 * 获得内存模型中的模块
		 */
		ModuleMaster moduleMaster = ModuleMaster.getInstance();
		Module module = moduleMaster.getModule((String) code);
		/*
		 * 如果内存模型中不存在，尝试从文件系统中读取
		 */
		if (module == null) {
			module = readObjectFromFile((String) code);
			if (module == null) {
				throw new DataAccessException("模块不存在！");
			}
			/*
			 * 添加到内存模型中
			 */
			moduleMaster.addModule(module);
		}
		return module;
	}

	@Override
	public List<Module> getList() {
		return ModuleMaster.getInstance().getModules();
	}

	private void writeObject2File(Module entity) {
		/*
		 * 获取存储文件
		 */
		File file = getConfigFileByCode(entity.getCode());
		/*
		 * 如果文件不存在，创建文件的目录
		 */
		FileUtil.createIfNotExists(file, true);
		/*
		 * 写入文件
		 */
		XmlFileUtil.writeObject2Xml(entity, file);
	}
	
	private Module readObjectFromFile(String code) {
		/*
		 * 获取存储文件
		 */
		File file = getConfigFileByCode(code);
		/*
		 * 读取xml文件
		 */
		return XmlFileUtil.readXml2Object(file, Module.class);
	}

	private File getConfigFileByCode(String code) {
		return FileUtil.getFile(SourceFileUtil.getModuleFilePath() + "/" + code + "/moduleInfo.xml");
	}
}
