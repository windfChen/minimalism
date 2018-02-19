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
import com.windf.module.development.entity.Module;
import com.windf.module.development.entity.Service;
import com.windf.module.development.fdao.ModuleDao;
import com.windf.module.development.fdao.ServiceDao;
import com.windf.module.development.util.file.SourceFileUtil;

@Repository
public class ServiceDaoImpl implements ServiceDao {
	
	@Resource
	private ModuleDao moduleDao;

	@Override
	public Service read(String moduleCode, String serviceName) throws DataAccessException {
		Module module = moduleDao.read(moduleCode);
		/*
		 * 查找Service
		 */
		Service result = null;
		List<Service> serviceList = module.getServices();
		for (int i = 0; i < serviceList.size(); i++) {
			Service service = serviceList.get(i);
			if (serviceName.equals(service.getName())) {
				result = service;
				break;
			}
		}
		/*
		 * 如果不存在，尝试从文件系统中获取
		 */
		if (result == null) {
			result = readObjectFromFile(moduleCode, serviceName);
		}
		/*
		 * 设置初始化属性
		 */
		if (result.getModule() == null) {
			result.setModule(module);
		}
		return result;
	}

	@Override
	public int create(Service bean) throws DataAccessException {
		/*
		 * 设置id
		 */
		bean.setId(Constant.JAVA_MODULE_BASE_PACKAGE_POINT + "." + bean.getModule().getCode() + ".service." + bean.getName());
		/*
		 * 寻找模块
		 */
		Module module = moduleDao.read(bean.getModule().getCode());
		/*
		 * 删除之前的同名Service
		 */
		List<Service> serviceList = module.getServices();
		Iterator<Service> iterator = serviceList.iterator();
		while (iterator.hasNext()) {
			Service service = (Service) iterator.next();
			if (bean.getName().equals(service.getName())) {
				iterator.remove();
				break;
			}
		}
		/*
		 * 添加新的Service
		 */
		module.getServices().add(bean);
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
	public List<Service> listByModuleCode(String moduleCode) throws DataAccessException {
		/*
		 * 获取模块
		 */
		Module module = moduleDao.read(moduleCode);
		/*
		 * 获取列表
		 */
		return module.getServices();
	}

	private void writeObject2File(Service entity) {
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
	
	private Service readObjectFromFile(String moduleCode, String code) throws DataAccessException {
		/*
		 * 获取存储文件
		 */
		File file = getConfigFileByCode(moduleCode, code);
		/*
		 * 读取xml文件
		 */
		return XmlFileUtil.readXml2Object(file, Service.class);
	}

	private File getConfigFileByCode(String moduleCode, String name) {
		return FileUtil.getFile(SourceFileUtil.getModuleFilePath() + "/" + moduleCode + "/service/" + name + ".xml");
	}
}
