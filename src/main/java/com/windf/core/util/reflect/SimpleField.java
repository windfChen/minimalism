package com.windf.core.util.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.windf.core.exception.UserException;

/**
 * 简单的字段控制方法
 * 用于javabean的字段，具有getter方法和setter方法
 * 用于判断字段的返回类型，为字段设置值
 * @author chenyafeng
 *
 */
public class SimpleField {
	
	private Object object;
	private Method getter;
	private Method setter;
	@SuppressWarnings("rawtypes")
	private Class fieldClazz;
	
	public static SimpleField getSimpleField(Object object, String fieldName) {
		SimpleField simpleField = null;
		try {
			simpleField = new SimpleField(object, fieldName);
		} catch (UserException e) {
			simpleField = null;
		}
		return simpleField;
	}
	
	
	private SimpleField(Object object, String fieldName) throws UserException{
		this.object = object;
		
		String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		try {
			getter = object.getClass().getMethod(getterName);
			fieldClazz = getter.getReturnType();
			setter = object.getClass().getMethod(setterName, fieldClazz);
		} catch (NoSuchMethodException e) {
			throw new UserException(e);
		} catch (SecurityException e) {
			throw new UserException(e);
		}
	}
	
	/**
	 * 通过setter为field设置值
	 * @param value
	 * @return
	 */
	public boolean setStringValue(String value) {
		Class<? extends Object>[] parameterTypes = setter.getParameterTypes();
		return setValue(ReflectUtil.getValue(parameterTypes[0], value));
	}
	
	/**
	 * 通过setter为field设置值
	 * @param value
	 * @return
	 */
	public boolean setValue(Object value) {
		boolean result = false;
		try {
			setter.invoke(object, value);
			result = true;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获得字段的类型
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Class getFieldClass() {
		return fieldClazz;
	}
	
	/**
	 * 获得泛型的类型
	 * @return
	 */
	public Type getGenericType() {
		Type genericClazz = null;
		Type type = getter.getGenericReturnType();
		if (type instanceof  ParameterizedType) {
			 ParameterizedType parameterizedType = (ParameterizedType) type;  
			 
             genericClazz = parameterizedType.getActualTypeArguments()[0];
		}
		return genericClazz;
	}

}
