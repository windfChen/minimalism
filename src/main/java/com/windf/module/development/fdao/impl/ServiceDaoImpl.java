package com.windf.module.development.fdao.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.windf.core.exception.DataAccessException;
import com.windf.module.development.entity.Module;
import com.windf.module.development.entity.Service;
import com.windf.module.development.fdao.ModuleDao;
import com.windf.module.development.fdao.ServiceDao;

@Repository
public class ServiceDaoImpl implements ServiceDao {
	
	@Resource
	private ModuleDao moduleDao;

	@Override
	public Service read(String moduleCode, String serviceName) throws DataAccessException {
		Module module = moduleDao.read(moduleCode);
		if (module == null) {
			throw new DataAccessException("找不到模块");
		}
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
		
		return result;
	}

	@Override
	public int create(Service bean) throws DataAccessException {
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
		return 1;
	}

	@Override
	public List<Service> listByModuleCode(String moduleCode) throws DataAccessException {
		Module module = moduleDao.read(moduleCode);
		if (module == null) {
			throw new DataAccessException("模块不存在");
		}
		
		return module.getServices();
	}

}
