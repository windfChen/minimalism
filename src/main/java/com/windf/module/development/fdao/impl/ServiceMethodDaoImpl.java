package com.windf.module.development.fdao.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.windf.core.exception.DataAccessException;
import com.windf.core.exception.ParameterException;
import com.windf.module.development.entity.Service;
import com.windf.module.development.entity.ServiceMethod;
import com.windf.module.development.fdao.ServiceDao;
import com.windf.module.development.fdao.ServiceMethodDao;

@Repository
public class ServiceMethodDaoImpl implements ServiceMethodDao {

	@Resource
	private ServiceDao serviceDao;

	@Override
	public ServiceMethod read(String moduleCode, String serviceName, String serviceMethodName) throws DataAccessException {
		Service service = this.getService(moduleCode, serviceName);
		/*
		 * 查找ServiceMethod
		 */
		ServiceMethod result = null;
		List<ServiceMethod> serviceMethodList = service.getServiceMethods();
		for (int i = 0; i < serviceMethodList.size(); i++) {
			ServiceMethod serviceMethod = serviceMethodList.get(i);
			if (serviceMethodName.equals(serviceMethod.getName())) {
				result = serviceMethod;
				break;
			}
		}
		
		return result;
	}

	@Override
	public int create(ServiceMethod bean) throws DataAccessException {
		Service service = this.getService(bean.getService().getModule().getCode(), bean.getService().getName());
		bean.setService(service);
		/*
		 * 删除之前的同名serviceName
		 */
		List<ServiceMethod> serviceMethodList = service.getServiceMethods();
		Iterator<ServiceMethod> iterator = serviceMethodList.iterator();
		while (iterator.hasNext()) {
			ServiceMethod serviceMethod = (ServiceMethod) iterator.next();
			if (bean.getName().equals(serviceMethod.getName())) {
				iterator.remove();
				break;
			}
		}
		/*
		 * 添加新的serviceName
		 */
		serviceMethodList.add(bean);
		return 1;
	}

	@Override
	public List<ServiceMethod> listByServiceName(String moduleCode, String serviceName) throws DataAccessException {
		return this.getService(moduleCode, serviceName).getServiceMethods();
	}

	/**
	 * 根据参数获取service
	 * 
	 * @param condition
	 * @return
	 * @throws ParameterException
	 * @throws DataAccessException
	 */
	protected Service getService(String moduleCode, String serviceName) throws DataAccessException {
		Service service = serviceDao.read(moduleCode, serviceName);
		if (service == null) {
			throw new DataAccessException("Service不存在！");
		}

		return service;
	}

}
