package com.windf.module.development.fdao;

import java.util.List;

import com.windf.core.exception.DataAccessException;
import com.windf.module.development.entity.ControlerMethod;

public interface ControlerMethodDao {
	ControlerMethod read(String moduleCode,String controlerName, String controlerMethodName) throws DataAccessException;
	int create(ControlerMethod bean) throws DataAccessException;
	List<ControlerMethod> listByControlerName(String moduleCode, String controlerName) throws DataAccessException;
}
