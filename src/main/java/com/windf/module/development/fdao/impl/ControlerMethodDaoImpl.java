package com.windf.module.development.fdao.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.windf.core.exception.DataAccessException;
import com.windf.core.exception.ParameterException;
import com.windf.module.development.entity.Controler;
import com.windf.module.development.entity.ControlerMethod;
import com.windf.module.development.fdao.ControlerDao;
import com.windf.module.development.fdao.ControlerMethodDao;

@Repository
public class ControlerMethodDaoImpl implements ControlerMethodDao {

	@Resource
	private ControlerDao controlerDao;

	@Override
	public ControlerMethod read(String moduleCode, String daoName, String daoMethodName) throws DataAccessException {
		Controler controler = this.getControler(moduleCode, daoName);
		/*
		 * 查找ControlerMethod
		 */
		ControlerMethod result = null;
		List<ControlerMethod> controlerMethodList = controler.getControlerMehthods();
		for (int i = 0; i < controlerMethodList.size(); i++) {
			ControlerMethod controlerMethod = controlerMethodList.get(i);
			if (daoMethodName.equals(controlerMethod.getName())) {
				result = controlerMethod;
				break;
			}
		}
		
		return result;
	}

	@Override
	public int create(ControlerMethod bean) throws DataAccessException {
		Controler controler = this.getControler(bean.getControler().getModule().getCode(), bean.getControler().getName());
		bean.setControler(controler);
		/*
		 * 删除之前的同名ControlerMethod
		 */
		List<ControlerMethod> controlerMethodList = controler.getControlerMehthods();
		Iterator<ControlerMethod> iterator = controlerMethodList.iterator();
		while (iterator.hasNext()) {
			ControlerMethod daoMethod = (ControlerMethod) iterator.next();
			if (bean.getName().equals(daoMethod.getName())) {
				iterator.remove();
				break;
			}
		}
		/*
		 * 添加新的ControlerMethod
		 */
		controlerMethodList.add(bean);
		return 1;
	}

	@Override
	public List<ControlerMethod> listByControlerName(String moduleCode, String controlerName) throws DataAccessException {
		return this.getControler(moduleCode, controlerName).getControlerMehthods();
	}

	/**
	 * 根据参数获取Controler
	 * 
	 * @param moduleCode
	 * @param controlerName
	 * @return
	 * @throws ParameterException
	 * @throws DataAccessException
	 */
	protected Controler getControler(String moduleCode, String controlerName) throws DataAccessException {
		Controler controler = controlerDao.read(moduleCode, controlerName);
		if (controler == null) {
			throw new DataAccessException("Controler不存在！");
		}

		return controler;
	}

}
