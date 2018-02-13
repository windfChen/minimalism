package com.windf.core.general.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.windf.core.bean.Page;
import com.windf.core.exception.DataAccessException;
import com.windf.core.exception.UserException;

public interface CrudService extends BaseService{

	/**
	 * 分页搜索
	 * @param condition
	 * @param pageNo
	 * @param PageSize
	 * @return
	 * @throws UserException
	 * @throws DataAccessException 
	 */
	Page<? extends Object> list(Map<String, Object> condition, Integer pageNo, Integer pageSize) throws Exception;

	/**
	 * 添加
	 * @param code
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	int save(Object bean)  throws Exception;

	/**
	 * 详情
	 * @param code
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Object detail(Serializable id)  throws Exception;
	
	/**
	 * 修改
	 * @param code
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	int update(Object bean)  throws Exception;

	/**
	 * 删除
	 * @param code
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	int delete(List<? extends Serializable> id)  throws Exception;
}
