package com.windf.module.development.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.windf.core.bean.Page;
import com.windf.core.exception.UserException;
import com.windf.module.development.entity.Service;
import com.windf.module.development.fdao.ServiceDao;
import com.windf.module.development.modle.component.ServiceCoder;
import com.windf.module.development.service.ServiceService;

@org.springframework.stereotype.Service
public class ServiceServiceImpl extends BaseManageGridServiceImpl<Service> implements ServiceService{
	
	@Resource
	private ServiceDao serviceDao;
	
	@Override
	public List<Service> getAll(String moduleCode) throws UserException {
		return serviceDao.listByModuleCode(moduleCode);
	}

	@Override
	public Page<Service> list(Map<String, Object> condition, Integer pageNo, Integer pageSize) throws Exception {
		List<Service> serviceList = serviceDao.listByModuleCode((String) condition.get("moduleCode"));
		
		Page<Service> page = new Page<Service>(Long.valueOf(pageNo) , pageSize);
		if (serviceList.size() > 0) {
			page.setTotal(Long.valueOf(serviceList.size()));
			page.setData(serviceList.subList(page.getStartIndex().intValue(), page.getEndIndex().intValue()));
		}
		
		return page;
	}

	@Override
	public int save(Service service) throws Exception {
		/*
		 * 创建对象模型
		 */
		serviceDao.create(service);
		/*
		 * 创建代码
		 */
		ServiceCoder serviceCoder = new ServiceCoder(service);
		serviceCoder.create();
		return 0;
	}

	@Override
	public Service detail(Serializable id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Service bean) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(List<? extends Serializable> id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}
