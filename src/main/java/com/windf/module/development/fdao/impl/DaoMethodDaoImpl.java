package com.windf.module.development.fdao.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.windf.core.exception.DataAccessException;
import com.windf.core.exception.ParameterException;
import com.windf.module.development.entity.Dao;
import com.windf.module.development.entity.DaoMethod;
import com.windf.module.development.fdao.DaoDao;
import com.windf.module.development.fdao.DaoMethodDao;

@Repository
public class DaoMethodDaoImpl implements DaoMethodDao {

	@Resource
	private DaoDao daoDao;

	@Override
	public DaoMethod read(String moduleCode, String entityName, String fieldName) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(DaoMethod bean) throws DataAccessException {
		Dao dao = this.getDao(bean.getDao().getModule().getCode(), bean.getDao().getName());
		bean.setDao(dao);
		/*
		 * 删除之前的同名字段
		 */
		List<DaoMethod> daoMethodList = dao.getDaoMethods();
		Iterator<DaoMethod> iterator = daoMethodList.iterator();
		while (iterator.hasNext()) {
			DaoMethod daoMethod = (DaoMethod) iterator.next();
			if (bean.getName().equals(daoMethod.getName())) {
				iterator.remove();
				break;
			}
		}
		/*
		 * 添加新的字段
		 */
		daoMethodList.add(bean);
		return 1;
	}

	@Override
	public List<DaoMethod> listByDaoName(String moduleCode, String daoName) throws DataAccessException {
		return this.getDao(moduleCode, daoName).getDaoMethods();
	}

	/**
	 * 根据参数获取Dao
	 * 
	 * @param condition
	 * @return
	 * @throws ParameterException
	 * @throws DataAccessException
	 */
	protected Dao getDao(String moduleCode, String daoName) throws DataAccessException {
		Dao dao = daoDao.read(moduleCode, daoName);
		if (dao == null) {
			throw new DataAccessException("Dao不存在！");
		}

		return dao;
	}

}
