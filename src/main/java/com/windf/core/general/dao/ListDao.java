package com.windf.core.general.dao;

import java.util.List;

import com.windf.core.exception.DataAccessException;

public interface ListDao {
	/**
	 * 查询符合条件的总数
	 * @param condition
	 * @return
	 * @throws DataAccessException
	 */
	Long count(Object condition) throws DataAccessException;
	/**
	 * 查询符合条件的列表
	 * @param condition
	 * @param pageNo
	 * @param PageSize
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("rawtypes")
	List list(Object condition, Long start, Integer PageSize) throws DataAccessException;
}
