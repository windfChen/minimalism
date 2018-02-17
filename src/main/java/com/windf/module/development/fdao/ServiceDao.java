package com.windf.module.development.fdao;

import java.util.List;

import com.windf.core.exception.DataAccessException;
import com.windf.module.development.entity.Service;

public interface ServiceDao {
	Service read(String moduleCode, String entityName) throws DataAccessException;
	int create(Service bean) throws DataAccessException;
	List<Service> listByModuleCode(String moduleCode) throws DataAccessException;
}
