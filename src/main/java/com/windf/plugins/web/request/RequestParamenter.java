package com.windf.plugins.web.request;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.windf.core.util.StringUtil;
import com.windf.core.util.reflect.BeanUtil;


public class RequestParamenter {

	protected HttpServletRequest request;

	public RequestParamenter(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 获取字符串类型的参数
	 * 
	 * @param name
	 * @return
	 */
	public String getString(String name) {
		String result = request.getParameter(name);
		if (StringUtil.isNotEmpty(result)) {
			result = result.trim();
		}
		return result;
	}

	/**
	 * 获取int类型的参数
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public Integer getInteger(String name) {
		return this.getInteger(name, null);
	}

	/**
	 * 获取long类型的参数
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public Long getLong(String name) {
		return this.getLong(name, null);
	}

	/**
	 * 获取integer类型的参数
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public Integer getInteger(String name, Integer defaultValue) {
		Integer result = defaultValue;
		String strValue = request.getParameter(name);
		try {
			result = Integer.parseInt(strValue);
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 获取long类型的参数
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public Long getLong(String name, Long defaultValue) {
		Long result = defaultValue;
		String strValue = request.getParameter(name);
		try {
			result = Long.parseLong(strValue);
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 获取double类型的参数
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public Double getDouble(String name, Double defaultValue) {
		Double result = defaultValue;
		String strValue = request.getParameter(name);
		try {
			result = Double.parseDouble(strValue);
		} catch (Exception e) {
		}
		return result;
	}
	
	/**
	 * 获取boolean参数
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public boolean getBoolean(String name) {
		boolean result = false;
		String strValue = request.getParameter(name);
		try {
			result = Boolean.parseBoolean(strValue);
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 获得map形式的参数
	 * 
	 * @param name
	 * @return
	 */
	public Map<String, Object> getMap(String name) {
		Map<String, Object> result = new HashMap<String, Object>();

		String nameKey = name + ".";

		@SuppressWarnings("rawtypes")
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String parameterName = (String) enumeration.nextElement();
			if (parameterName.startsWith(nameKey)) {
				String key = parameterName.substring(nameKey.length());
				
				// 如果key中还有.递归寻找map,否则直接放到value中
				int pointIndex = key.indexOf(".");
				if (pointIndex > -1) {
					key = key.substring(0, pointIndex);
					result.put(key, getMap(nameKey + key));
				} else {
					String value = this.getString(parameterName);
					if (StringUtil.isNotEmpty(value)) {
						result.put(key, value);
					}
				}
				
			}

		}

		if (result.size() > 0) {
			return result;
		} else {
			return null;
		}
	}
	
	/**
	 * 获取对象类型参数
	 * @param <T>
	 * @param name
	 * @param clazz
	 * @return
	 */
	public <T> T getObject(String name, Class<T> clazz) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtil.isEmpty(name)) {
			map = this.getAll();
		} else {
			map = this.getMap(name);
		}
		
		T result = BeanUtil.getObjectByMap(clazz, map);
		
		return result;
	}

	/**
	 * 获得所有参数
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getAll() {
		Map<String, Object> result = new HashMap<String, Object>();

		Enumeration<String> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			String value = this.getString(key);
			if (StringUtil.isNotEmpty(value)) {
				result.put(key, value);
			}
		}

		return result;
	}

	/**
	 * 获取查询字符串中的参数
	 * 
	 * @return
	 */
	public Map<String, String> getQueryStringValues() {
		Map<String, String> result = new HashMap<String, String>();
		String queryString = request.getQueryString();
		if (StringUtil.isNotEmpty(queryString)) {
			String[] parameters = queryString.split("&");
			for (String parameter : parameters) {
				String[] parameterStrs = parameter.split("=");
				if (parameterStrs != null && parameterStrs.length == 2) {
					String key = parameterStrs[0];
					String value = this.getString(key);
					result.put(key, value);
				}
			}
		}

		return result;
	}

	/**
	 * 设置值
	 * 
	 * @param name
	 * @param value
	 */
	public void setValue(String name, Object value) {
		request.setAttribute(name, value);
	}
}
