package com.windf.plugins.manage.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.windf.core.bean.Page;
import com.windf.core.exception.DataAccessException;
import com.windf.core.exception.UserException;
import com.windf.core.general.dao.ManageGridDao;
import com.windf.plugins.manage.entity.GridConfig;
import com.windf.plugins.manage.service.ManageGirdService;

public abstract class ManagerGirdiServiceImpl<T> implements ManageGirdService<T>{
	
	/**
	 * 获取gridDao
	 * @return
	 */
	public abstract ManageGridDao<T> getGridDao() ;

	@Override
	public GridConfig getGridConfig(String code, String roleId, Map<String, Object> condition) throws UserException {
		// TODO Auto-generated method stub
		
		/*
		 * 加载表格结构
		 */
		GridConfig gridConfig = GridConfig.loadGridConfigByCode(code, condition);
		
		/*
		 * 根据权限，过滤表格功能
		 */
		
		return gridConfig;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<T> list(Map<String, Object> condition, Integer pageNo, Integer pageSize)
			throws UserException, DataAccessException {
		
		Page page = new Page(Long.valueOf(pageNo), pageSize);
		
		page.setTotal(this.getGridDao().count(condition));
		page.setData(this.getGridDao().list(condition, page.getStartIndex(), page.getPageSize()));
		
		return page;
	}

	@Override
	public int save(T bean) throws Exception {
		return this.getGridDao().create(bean);
	}

	@Override
	public T detail(Serializable id) throws Exception {
		return this.getGridDao().read(id);
	}

	@Override
	public int update(T bean) throws Exception {
		return this.getGridDao().update(bean);
		
	}

	@Override
	public int delete(List<? extends Serializable> id) throws Exception {
		return this.getGridDao().delete(id);
	}

}
