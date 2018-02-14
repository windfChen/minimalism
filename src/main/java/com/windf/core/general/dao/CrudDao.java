package com.windf.core.general.dao;

import java.io.Serializable;

import com.windf.core.exception.DataAccessException;

public interface CrudDao<T> extends WritableDao<T> {
	/**
	 * 单个查询
	 * @param id
	 * @throws DataAccessException
	 */
	T read(Serializable id) throws DataAccessException;
}
