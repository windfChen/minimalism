package com.windf.module.development.fdao;

import java.util.List;

import com.windf.core.exception.DataAccessException;
import com.windf.module.development.entity.DaoMethod;

public interface DaoMethodDao {
	DaoMethod read(String moduleCode,String entityName, String daoName) throws DataAccessException;
	int create(DaoMethod bean) throws DataAccessException;
	List<DaoMethod> listByDaoName(String moduleCode, String daoName) throws DataAccessException;
}
