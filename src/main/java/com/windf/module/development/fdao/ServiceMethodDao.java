package com.windf.module.development.fdao;

import java.util.List;

import com.windf.core.exception.DataAccessException;
import com.windf.module.development.entity.ServiceMethod;

public interface ServiceMethodDao {
	ServiceMethod read(String moduleCode,String serviceName, String serviceMethodName) throws DataAccessException;
	int create(ServiceMethod bean) throws DataAccessException;
	List<ServiceMethod> listByServiceName(String moduleCode, String serviceName) throws DataAccessException;
}
