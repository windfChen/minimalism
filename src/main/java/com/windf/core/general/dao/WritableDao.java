package com.windf.core.general.dao;

import java.io.Serializable;
import java.util.List;

import com.windf.core.exception.DataAccessException;

public interface WritableDao<T> {
	/**
	 * 添加
	 * @param condition
	 * @throws DataAccessException
	 */
	int create(T bean) throws DataAccessException;
	/**
	 * 删除
	 * @param condition
	 * @throws DataAccessException
	 */
	int delete(List<? extends Serializable> id) throws DataAccessException;
	/**
	 * 修改
	 * @param condition
	 * @throws DataAccessException
	 */
	int update(T bean) throws DataAccessException;
}
