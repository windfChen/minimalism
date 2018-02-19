package com.windf.module.development.fdao.impl;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.windf.core.exception.DataAccessException;
import com.windf.core.util.file.FileUtil;
import com.windf.core.util.file.XmlFileUtil;
import com.windf.module.development.entity.Entity;
import com.windf.module.development.entity.Module;
import com.windf.module.development.fdao.EntityDao;
import com.windf.module.development.fdao.ModuleDao;
import com.windf.module.development.util.file.SourceFileUtil;

@Repository
public class EntityDaoImpl implements EntityDao {

	@Resource
	private ModuleDao moduleDao;

	@Override
	public Entity read(String moduleCode, String entityName) throws DataAccessException {
		Module module = moduleDao.read(moduleCode);
		/*
		 * 查找Entity
		 */
		Entity result = null;
		List<Entity> entityList = module.getEntitys();
		for (int i = 0; i < entityList.size(); i++) {
			Entity entity = entityList.get(i);
			if (entityName.equals(entity.getName())) {
				result = entity;
				break;
			}
		}
		/*
		 * 如果不存在，尝试从文件系统中获取
		 */
		if (result == null) {
			result = readObjectFromFile(moduleCode, entityName);
		}
		/*
		 * 设置初始化属性
		 */
		if (result.getModule() == null) {
			result.setModule(moduleDao.read(moduleCode));
		}
		return result;
	}

	@Override
	public int create(Entity bean) throws DataAccessException {
		Module module = moduleDao.read(bean.getModule().getCode());
		/*
		 * 删除之前的同名Entity
		 */
		List<Entity> entityList = module.getEntitys();
		Iterator<Entity> iterator = entityList.iterator();
		while (iterator.hasNext()) {
			Entity entity = (Entity) iterator.next();
			if (bean.getName().equals(entity.getName())) {
				iterator.remove();
				break;
			}
		}
		/*
		 * 添加新的Entity
		 */
		module.getEntitys().add(bean);
		/*
		 * 存储文件
		 */
		writeObject2File(bean);
		/*
		 * 更新模块配置文件
		 */
		moduleDao.update(bean.getModule());
		return 1;
	}

	@Override
	public List<Entity> listByModuleCode(String moduleCode) throws DataAccessException {
		/*
		 * 获取模块
		 */
		Module module = moduleDao.read(moduleCode);
		/*
		 * 获取模块列表
		 */
		return module.getEntitys();
	}

	private void writeObject2File(Entity entity) {
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
	
	private Entity readObjectFromFile(String moduleCode, String code) throws DataAccessException {
		/*
		 * 获取存储文件
		 */
		File file = getConfigFileByCode(moduleCode, code);
		/*
		 * 读取xml文件
		 */
		Entity result = XmlFileUtil.readXml2Object(file, Entity.class);
		return result;
	}

	private File getConfigFileByCode(String moduleCode, String name) {
		return FileUtil.getFile(SourceFileUtil.getModuleFilePath() + "/" + moduleCode + "/entity/" + name + ".xml");
	}
}
