package com.windf.module.development.fdao.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.windf.core.exception.DataAccessException;
import com.windf.module.development.entity.Controler;
import com.windf.module.development.entity.Module;
import com.windf.module.development.fdao.ControlerDao;
import com.windf.module.development.fdao.ModuleDao;

@Repository
public class ControlerDaoImpl implements ControlerDao {
	
	@Resource
	private ModuleDao moduleDao;

	@Override
	public Controler read(String moduleCode, String controlerName) throws DataAccessException {
		Module module = moduleDao.read(moduleCode);
		if (module == null) {
			throw new DataAccessException("找不到模块");
		}
		/*
		 * 查找Controler
		 */
		Controler result = null;
		List<Controler> controlerList = module.getControlers();
		for (int i = 0; i < controlerList.size(); i++) {
			Controler controler = controlerList.get(i);
			if (controlerName.equals(controler.getName())) {
				result = controler;
				break;
			}
		}
		
		return result;
	}

	@Override
	public int create(Controler bean) throws DataAccessException {
		Module module = moduleDao.read(bean.getModule().getCode());
		/*
		 * 删除之前的同名Controler
		 */
		List<Controler> controlerList = module.getControlers();
		Iterator<Controler> iterator = controlerList.iterator();
		while (iterator.hasNext()) {
			Controler controler = (Controler) iterator.next();
			if (bean.getName().equals(controler.getName())) {
				iterator.remove();
				break;
			}
		}
		/*
		 * 添加新的Controler
		 */
		module.getControlers().add(bean);
		return 1;
	}

	@Override
	public List<Controler> listByModuleCode(String moduleCode) throws DataAccessException {
		Module module = moduleDao.read(moduleCode);
		if (module == null) {
			throw new DataAccessException("模块不存在");
		}
		
		return module.getControlers();
	}

}
