package com.windf.core.util.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BeanUtil {


	/**
	 * 判断，是否是用户自定义的getter方法
	 * 今天方法除外
	 * @param method
	 * @return
	 */
	public static boolean isGetterMethod(Method method) {
		boolean result = false;
		int  modifiers  = method.getModifiers();
		if (!Modifier.isStatic(modifiers)) {
			String methodName = method.getName();
			result = methodName.startsWith("get") && method.getParameterTypes().length == 0 && !"getClass".equals(methodName);
		}
		
		return result;
	}
	
	/**
	 * 获得对象的所有非空属性，静态方法除外，非序列化方法除外
	 * 
	 * @param clazz
	 * @return 属性名-属性值
	 */
	public static Map<String, Object> getAllGetterMethods(Object object) {
		Class<? extends Object> clazz = object.getClass();

		Map<String, Object> getterMethodValueMap = new HashMap<String, Object>();
		Method[] methods = clazz.getMethods();
		if (methods != null) {
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				String methodName = method.getName();

				// 是getter方法
				if (isGetterMethod(method)) {
					
					Object result = null;
					try {
						result = method.invoke(object);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					if (result != null) {
						methodName = getNameByGetterOrSetter(methodName);
						getterMethodValueMap.put(methodName, result);
					}
				}
			}
		}

		return getterMethodValueMap;
	}

	/**
	 * 只复制基本数据类型
	 * @param object
	 * @return
	 */
	public static Object cloneOnlyBaseType(Object object) {
			Class<? extends Object> clazz = object.getClass();
			Object newObject = null;
			try {
				newObject = clazz.newInstance();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
			
			if (newObject != null) {
				Map<String, Object> getterMethodMap = getAllGetterMethods(object);
				Iterator<String> iterator = getterMethodMap.keySet().iterator();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					Object value = getterMethodMap.get(key);
					
					if (ReflectUtil.isBaseType(value.getClass())) {
						String setterMethodName = getSetterMethodName(key);
						try {
							Method setterMethod = clazz.getMethod(setterMethodName, value.getClass());
							setterMethod.invoke(newObject, value);
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			return newObject;
			
	}
	
	/**
	 * 根据map设置object的值
	 * 遍历map的key，根据key获得setter方法，设置值为key对应的value
	 * @param clazz
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getObjectByMap(Class<T> clazz, Map<String, ? extends Object> values) {
		T result = createBean(clazz);
		if (result != null) {
			result = (T) setValueByMap(result, values);
		}
		
		
		return result;
	}

	/**
	 * 根据map设置object的值
	 * 遍历map的key，根据key获得setter方法，设置值为key对应的value
	 * @param object
	 * @param values
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object setValueByMap(Object object, Map<String, ? extends Object> values) {
		if (object != null) {
			Class<? extends Object> clazz = object.getClass();
			Iterator<String> iterator = values.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				Object value = values.get(key);
				
				try {
					Method getterMethod = clazz.getMethod(getGetterMethodName(key));
					Method setterMethod = clazz.getMethod(getSetterMethodName(key), getterMethod.getReturnType());
					if (ReflectUtil.isBaseType((getterMethod.getReturnType()))) {
						setBaseTypeValue(object, setterMethod, getterMethod.getReturnType(), value);
					} else if (ReflectUtil.isMap(value.getClass())) {
						setterMethod.invoke(object, getObjectByMap(getterMethod.getReturnType(), (Map) value));
					}
				} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				} 
			}
		}
		
		return object;
	}
	
	/**
	 * 设置基本类型的值
	 * @param object
	 * @param setterMethod
	 * @param type
	 * @param value
	 * @throws NumberFormatException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void setBaseTypeValue(Object object, Method setterMethod, Class type, Object value) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (ReflectUtil.isInteger(type)) {
			setterMethod.invoke(object, Integer.valueOf(value.toString()));
		} else if (ReflectUtil.isLong(type)) {
			setterMethod.invoke(object, Long.valueOf(value.toString()));
		} else if (String.class.isAssignableFrom(type)) {
			setterMethod.invoke(object, value);
		}  else if (ReflectUtil.isBoolean(type)) {
			boolean result = false;
			if (value != null) {
				if ("Y".equals(value.toString()) || "true".equals(value.toString())) {
					result = true;
				}
			}
			setterMethod.invoke(object, result);
		} 
	}
	
	/**
	 * 根据属性名称，获得setter方法名称
	 * 只是拼接字符串，不做任何验证
	 * @param name
	 * @return
	 */
	public static String getSetterMethodName(String name) {
		return "set" + name.substring(0, 1).toUpperCase() + name.substring(1); 
	}
	
	/**
	 * 根据属性名称，获得getter方法名称
	 * 只是拼接字符串，不做任何验证
	 * @param name
	 * @return
	 */
	public static String getGetterMethodName(String name) {
		return "get" + name.substring(0, 1).toUpperCase() + name.substring(1); 
	}
	
	/**
	 * 根据getter或者setter方法，获得属性名
	 * @param methodName
	 * @return
	 */
	public static String getNameByGetterOrSetter(String methodName) {
		return methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
	}
	
	/**
	 * 创建bean
	 * @param clazz
	 * @return
	 */
	public static <T> T createBean(Class<T> clazz) {
		T result = null;
		try {
			result = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
		}
		
		return result;
	}
	
	/*
	 * 执行对象的方法，获得返回值
	 */
	public static Object invockeGetterMethod(Object object, String methodName) {
		Object result = null;
		try {
			Method method = object.getClass().getMethod(methodName);
			result = method.invoke(object);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}
}
