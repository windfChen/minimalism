package com.windf.module.development.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.bean.Page;
import com.windf.module.development.entity.Dao;
import com.windf.module.development.entity.Module;
import com.windf.module.development.fdao.DaoDao;
import com.windf.module.development.modle.ModuleMaster;
import com.windf.module.development.modle.component.DaoCoder;
import com.windf.module.development.service.DaoService;

@Service
public class DaoServiceImpl extends BaseManageGridServiceImpl<Dao> implements DaoService{
	
	@Resource
	private DaoDao daoDao;

	@Override
	public Page<Dao> list(Map<String, Object> condition, Integer pageNo, Integer pageSize) throws Exception {
		List<Dao> daoList = daoDao.listByModuleCode((String) condition.get("moduleCode"));
		
		Page<Dao> page = new Page<Dao>(Long.valueOf(pageNo) , pageSize);
		if (daoList.size() > 0) {
			page.setTotal(Long.valueOf(daoList.size()));
			page.setData(daoList.subList(page.getStartIndex().intValue(), page.getEndIndex().intValue()));
		}
		
		return page;
	}

	@Override
	public int save(Dao dao) throws Exception {
		/*
		 * 设置参数
		 */
		Module module = ModuleMaster.getInstance().getModule(dao.getModule().getCode());
		dao.setModule(module);
		/*
		 * 数据模型创建更新
		 */
		daoDao.create(dao);
		/*
		 * 代码更新
		 */
		DaoCoder daoCoder = new DaoCoder(dao);
		daoCoder.create();
		return 0;
	}

	@Override
	public Dao detail(Serializable id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Dao bean) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(List<? extends Serializable> id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Dao> getMyList() {
		// TODO Auto-generated method stub
		return null;
	}

}
