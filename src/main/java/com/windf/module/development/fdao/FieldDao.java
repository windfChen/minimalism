package com.windf.module.development.fdao;

import java.util.List;

import com.windf.core.exception.DataAccessException;
import com.windf.module.development.entity.Field;

public interface FieldDao{
	Field read(String moduleCode,String entityName, String fieldName) throws DataAccessException;
	int create(Field bean) throws DataAccessException;
	List<Field> listByEntityName(String moduleCode, String entityName) throws DataAccessException;
}
