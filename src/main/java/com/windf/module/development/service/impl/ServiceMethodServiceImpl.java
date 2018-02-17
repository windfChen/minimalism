package com.windf.module.development.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.bean.Page;
import com.windf.module.development.entity.ServiceMethod;
import com.windf.module.development.fdao.ServiceDao;
import com.windf.module.development.fdao.ServiceMethodDao;
import com.windf.module.development.modle.component.ServiceCoder;
import com.windf.module.development.service.ServiceMethodService;

@Service
public class ServiceMethodServiceImpl extends BaseManageGridServiceImpl<ServiceMethod> implements ServiceMethodService{
	
	@Resource
	private ServiceDao serviceDao;
	@Resource
	private ServiceMethodDao serviceMethodDao;

	@Override
	public Page<ServiceMethod> list(Map<String, Object> condition, Integer pageNo, Integer pageSize) throws Exception {
		List<ServiceMethod> serviceMethodList = serviceMethodDao.listByServiceName((String) condition.get("moduleCode"), (String) condition.get("serviceName"));
		
		Page<ServiceMethod> page = new Page<ServiceMethod>(Long.valueOf(pageNo) , pageSize);
		page.setTotal(Long.valueOf(serviceMethodList.size()));
		page.setData(serviceMethodList.subList(page.getStartIndex().intValue(), page.getEndIndex().intValue()));
		
		return page;
	}

	@Override
	public int save(ServiceMethod serviceMethod) throws Exception {
		/*
		 * 内存模型更新
		 */
		serviceMethodDao.create(serviceMethod);
		/*
		 * 代码文件更新
		 */
		ServiceCoder serviceCoder = new ServiceCoder(serviceMethod.getService());
		serviceCoder.addMethod(serviceMethod);
		return 0;
	}

	@Override
	public ServiceMethod detail(Serializable id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(ServiceMethod bean) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(List<? extends Serializable> id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}
