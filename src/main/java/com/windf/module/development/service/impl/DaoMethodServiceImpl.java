package com.windf.module.development.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.bean.Page;
import com.windf.module.development.entity.DaoMethod;
import com.windf.module.development.fdao.DaoDao;
import com.windf.module.development.fdao.DaoMethodDao;
import com.windf.module.development.modle.component.DaoCoder;
import com.windf.module.development.service.DaoMethodService;

@Service
public class DaoMethodServiceImpl extends BaseManageGridServiceImpl<DaoMethod> implements DaoMethodService{
	
	@Resource
	private DaoDao daoDao;
	@Resource
	private DaoMethodDao daoMethodDao;

	@Override
	public Page<DaoMethod> list(Map<String, Object> condition, Integer pageNo, Integer pageSize) throws Exception {
		List<DaoMethod> daoMethodList = daoMethodDao.listByDaoName((String) condition.get("moduleCode"), (String) condition.get("daoName"));
		
		Page<DaoMethod> page = new Page<DaoMethod>(Long.valueOf(pageNo) , pageSize);
		page.setTotal(Long.valueOf(daoMethodList.size()));
		page.setData(daoMethodList.subList(page.getStartIndex().intValue(), page.getEndIndex().intValue()));
		
		return page;
	}

	@Override
	public int save(DaoMethod daoMethod) throws Exception {
		/*
		 * 内存模型更新
		 */
		daoMethodDao.create(daoMethod);
		/*
		 * 代码文件更新
		 */
		DaoCoder daoCoder = new DaoCoder(daoMethod.getDao());
		daoCoder.addMethod(daoMethod);
		return 0;
	}

	@Override
	public DaoMethod detail(Serializable id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(DaoMethod bean) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(List<? extends Serializable> id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}
