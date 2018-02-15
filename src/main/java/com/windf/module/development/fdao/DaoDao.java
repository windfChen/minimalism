package com.windf.module.development.fdao;

import java.util.List;

import com.windf.core.exception.DataAccessException;
import com.windf.module.development.entity.Dao;

public interface DaoDao {
	Dao read(String moduleCode, String daoName) throws DataAccessException;
	int create(Dao bean) throws DataAccessException;
	List<Dao> listByModuleCode(String moduleCode) throws DataAccessException;
}
