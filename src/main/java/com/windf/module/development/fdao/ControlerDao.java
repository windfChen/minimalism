package com.windf.module.development.fdao;

import java.util.List;

import com.windf.core.exception.DataAccessException;
import com.windf.module.development.entity.Controler;

public interface ControlerDao {
	Controler read(String moduleCode, String controlerName) throws DataAccessException;
	int create(Controler bean) throws DataAccessException;
	List<Controler> listByModuleCode(String controlerCode) throws DataAccessException;
}
