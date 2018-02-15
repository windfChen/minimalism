package com.windf.module.development.fdao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.windf.core.exception.DataAccessException;
import com.windf.module.development.Constant;
import com.windf.module.development.entity.Dao;
import com.windf.module.development.entity.Module;
import com.windf.module.development.fdao.DaoDao;
import com.windf.module.development.fdao.ModuleDao;

@Repository
public class DaoDaoImpl implements DaoDao{
	
	@Resource
	private ModuleDao moduleDao;
	
	@Override
	public Dao read(String moduleCode, String daoName) throws DataAccessException {
		Module module = moduleDao.read(moduleCode);
		if (module == null) {
			throw new DataAccessException("模块不存在！");
		}
		/*
		 * 查找Dao
		 */
		Dao result = null;
		List<Dao> daoList = module.getDaos();
		for (int i = 0; i < daoList.size(); i++) {
			Dao dao = daoList.get(i);
			if (daoName.equals(dao.getName())) {
				result = dao;
				break;
			}
		}
		return result;
	}

	@Override
	public int create(Dao dao) throws DataAccessException {
		dao.setId(Constant.JAVA_MODULE_BASE_PACKAGE_POINT + "." + dao.getModule().getCode() + ".dao." + dao.getName());
		Module module = moduleDao.read(dao.getModule().getCode());
		if (module == null) {
			throw new DataAccessException("模块不存在！");
		}
		module.getDaos().add(dao);
		return 1;
	}

	@Override
	public List<Dao> listByModuleCode(String moduleCode) throws DataAccessException {
		Module module = moduleDao.read(moduleCode);
		if (module == null) {
			throw new DataAccessException("模块不存在！");
		}
		return module.getDaos();
	}
	
	
}
