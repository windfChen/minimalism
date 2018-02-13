package com.windf.core.general.dao;

import java.util.List;

import com.windf.core.general.entity.NameBean;

/**
 * 一般用于查询所有列表，返回id，name键值对
 * 
 * @author windf
 *
 */
public interface MyListDao {
	/**
	 * 查询所有id、name
	 * 
	 * @return
	 */
	List<NameBean> getMyList();
}
