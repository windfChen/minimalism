package com.windf.module.development.fdao.impl;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.windf.core.exception.DataAccessException;
import com.windf.core.util.file.FileUtil;
import com.windf.core.util.file.XmlFileUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.entity.Controler;
import com.windf.module.development.entity.Module;
import com.windf.module.development.fdao.ControlerDao;
import com.windf.module.development.fdao.ModuleDao;
import com.windf.module.development.util.file.SourceFileUtil;

@Repository
public class ControlerDaoImpl implements ControlerDao {
	
	@Resource
	private ModuleDao moduleDao;

	@Override
	public Controler read(String moduleCode, String controlerName) throws DataAccessException {
		/*
		 * 读取模块
		 */
		Module module = moduleDao.read(moduleCode);
		/*
		 * 查找Controler
		 */
		Controler result = null;
		List<Controler> controlerList = module.getControlers();
		for (int i = 0; i < controlerList.size(); i++) {
			Controler controler = controlerList.get(i);
			if (controlerName.equals(controler.getName())) {
				result = controler;
				break;
			}
		}
		/*
		 * 如果不存在，或者没有详情，尝试从文件系统中获取
		 */
		if (result == null || result.getModule() == null) {
			result = readObjectFromFile(moduleCode, controlerName);
			result.setModule(module);
		}
		return result;
	}

	@Override
	public int create(Controler bean) throws DataAccessException {
		/*
		 * 设置id
		 */
		bean.setId(Constant.JAVA_MODULE_BASE_PACKAGE_POINT + "." + bean.getModule().getCode() + ".controler." + bean.getName());
		/*
		 * 读取模块
		 */
		Module module = moduleDao.read(bean.getModule().getCode());
		/*
		 * 删除之前的同名Controler
		 */
		List<Controler> controlerList = module.getControlers();
		Iterator<Controler> iterator = controlerList.iterator();
		while (iterator.hasNext()) {
			Controler controler = (Controler) iterator.next();
			if (bean.getName().equals(controler.getName())) {
				iterator.remove();
				break;
			}
		}
		/*
		 * 添加新的Controler
		 */
		module.getControlers().add(bean);
		/*
		 * 存储文件
		 */
		writeObject2File(bean);
		/*
		 * 更新模块配置文件
		 */
		moduleDao.update(module);
		return 1;
	}

	@Override
	public List<Controler> listByModuleCode(String moduleCode) throws DataAccessException {
		/*
		 * 读取模块
		 */
		Module module = moduleDao.read(moduleCode);
		/*
		 * 获取列表
		 */
		return module.getControlers();
	}

	private void writeObject2File(Controler entity) {
		/*
		 * 获取存储文件
		 */
		File file = getConfigFileByCode(entity.getModule().getCode(), entity.getName());
		/*
		 * 如果文件不存在，创建文件的目录
		 */
		FileUtil.createIfNotExists(file, true);
		/*
		 * 写入文件
		 */
		XmlFileUtil.writeObject2Xml(entity, file);
	}
	
	private Controler readObjectFromFile(String moduleCode, String code) throws DataAccessException {
		/*
		 * 获取存储文件
		 */
		File file = getConfigFileByCode(moduleCode, code);
		/*
		 * 读取xml文件
		 */
		return XmlFileUtil.readXml2Object(file, Controler.class);
	}

	private File getConfigFileByCode(String moduleCode, String name) {
		return FileUtil.getFile(SourceFileUtil.getModuleFilePath() + "/" + moduleCode + "/controler/" + name + ".xml");
	}
}
