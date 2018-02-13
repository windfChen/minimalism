package com.windf.module.development.modle;

import java.util.List;

import com.windf.core.exception.UserException;

public interface Codeable<T> {

	/**
	 * 转换成代码的形式
	 * @return
	 * @throws UserException 
	 */
	public List<String> toCodes(T t, int tabCount) throws UserException ;
	
	/**
	 * 转换代码成对象形式
	 * @return
	 */
	public T toObject(List<String> codes);

}
