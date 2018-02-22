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
import com.windf.module.development.entity.Dao;
import com.windf.module.development.entity.Module;
import com.windf.module.development.fdao.DaoDao;
import com.windf.module.development.fdao.ModuleDao;
import com.windf.module.development.util.file.SourceFileUtil;

@Repository
public class DaoDaoImpl implements DaoDao{
	
	@Resource
	private ModuleDao moduleDao;
	
	@Override
	public Dao read(String moduleCode, String daoName) throws DataAccessException {
		/*
		 * 读取模块
		 */
		Module module = moduleDao.read(moduleCode);
		/*
		 * 查找Dao
		 */
		Dao result = null;
		List<Dao> daoList = module.getDaos();
		for (int i = 0; i < daoList.size(); i++) {
			Dao dao = daoList.get(i);
			if (daoName.equals(dao.getName())) {
				result = dao;
				break;
			}
		}/*
		 * 如果不存在，或者没有详情，尝试从文件系统中获取
		 */
		if (result == null || result.getModule() == null) {
			result = readObjectFromFile(moduleCode, daoName);
			result.setModule(module);
		}
		return result;
	}

	@Override
	public int create(Dao bean) throws DataAccessException {
		/*
		 * 设置id
		 */
		bean.setId(Constant.JAVA_MODULE_BASE_PACKAGE_POINT + "." + bean.getModule().getCode() + ".dao." + bean.getName());
		/*
		 * 寻找模块
		 */
		Module module = moduleDao.read(bean.getModule().getCode());
		/*
		 * 删除同名dao
		 */
		List<Dao> entityList = module.getDaos();
		Iterator<Dao> iterator = entityList.iterator();
		while (iterator.hasNext()) {
			Dao entity = (Dao) iterator.next();
			if (bean.getName().equals(entity.getName())) {
				iterator.remove();
				break;
			}
		}
		/*
		 * 添加到对象模型中
		 */
		module.getDaos().add(bean);
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
	public List<Dao> listByModuleCode(String moduleCode) throws DataAccessException {
		/*
		 * 获取模块
		 */
		Module module = moduleDao.read(moduleCode);
		/*
		 * 获取列表
		 */
		return module.getDaos();
	}

	private void writeObject2File(Dao entity) {
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
	
	private Dao readObjectFromFile(String moduleCode, String code) throws DataAccessException {
		/*
		 * 获取存储文件
		 */
		File file = getConfigFileByCode(moduleCode, code);
		/*
		 * 读取xml文件
		 */
		return XmlFileUtil.readXml2Object(file, Dao.class);
	}

	private File getConfigFileByCode(String moduleCode, String name) {
		return FileUtil.getFile(SourceFileUtil.getModuleFilePath() + "/" + moduleCode + "/dao/" + name + ".xml");
	}
	
}
