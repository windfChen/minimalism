package com.windf.module.development.fdao;

import java.util.List;

import com.windf.core.exception.DataAccessException;
import com.windf.module.development.entity.Entity;

public interface EntityDao {
	Entity read(String moduleCode, String entityName) throws DataAccessException;
	int create(Entity bean) throws DataAccessException;
	List<Entity> listByModuleCode(String moduleCode) throws DataAccessException;
}
