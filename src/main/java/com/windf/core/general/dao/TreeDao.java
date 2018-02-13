package com.windf.core.general.dao;

import java.util.List;

/**
 * 树的操作接口
 * @author chenyafeng
 *
 * @param <T>
 */
public interface TreeDao<T> extends CrudDao {

	/**
	 * 找到所有子节点和孙子节点
	 * @param entity
	 * @return
	 */
	public List<T> findChildrenAndGrandchildren(Integer id);
	

	/**
	 * 找到直接子节点
	 * @param entity
	 * @return
	 */
	public List<T> findChildren(Integer id);

}
