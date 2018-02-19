package com.windf.core.util.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReflectUtil {

	/**
	 * 判断类是否是基本类型
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isBaseType(Class<? extends Object> clazz) {
		boolean result = true;
		if (!clazz.isPrimitive()) {
			if (!clazz.getName().startsWith("java.lang")) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * 判断类是否是基本类型
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isBaseType(Object obj) {
		return isBaseType(obj.getClass());
	}
	
	public static boolean isInteger(Class<? extends Object> clazz) {
		boolean result = false;
		
		if (clazz != null) {
			if ("java.lang.Integer".equals(clazz.getName())) {
				result = true;
			}
		}
		
		return result;
	}
	
	public static boolean isNumber(Class<? extends Object> clazz) {
		boolean result = false;
		if ((Number.class.isAssignableFrom(clazz))) {
			result = true;
		}
		return result;
	}
	
	public static boolean isLong(Class<? extends Object> clazz) {
		boolean result = false;
		
		if (clazz != null) {
			if ("java.lang.Long".equals(clazz.getName())) {
				result = true;
			}
		}
		
		return result;
	}
	
	public static boolean isBoolean(Class<? extends Object> clazz) {
		boolean result = false;
		
		if (clazz != null) {
			if ("java.lang.Boolean".equals(clazz.getName())) {
				result = true;
			} else if ("boolean".equals(clazz.getName())) {
				result = true;
			}
		}

		return result;
	}

	
	public static boolean isDate(Class<? extends Object> clazz) {
		boolean result = false;
		
		if (clazz != null) {
			if ("java.util.Date".equals(clazz.getName())) {
				result = true;
			}
		}
		
		return result;
	}

	/**
	 * 判断类，是否是集合类型
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isCollection(Class<? extends Object> clazz) {
		boolean result = false;
		if (clazz.isAssignableFrom(List.class) || clazz.isAssignableFrom(Set.class)) {
			result = true;
		}
		if (!result) {
			if (!result) {
				Class<? extends Object>[] interfaces = clazz.getInterfaces();
				for (int i = 0; i < interfaces.length; i++) {
					Class<? extends Object> c = interfaces[i];
					if (List.class.isAssignableFrom(c) || Set.class.isAssignableFrom(c)) {
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}

	/**
	 * 判断对象是map
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isMap(Class<? extends Object> clazz) {
		boolean result = false;
		if (Map.class.isAssignableFrom(clazz)) {
			result = true;
		}

		if (!result) {
			Class<? extends Object>[] interfaces = clazz.getInterfaces();
			for (int i = 0; i < interfaces.length; i++) {
				Class<? extends Object> c = interfaces[i];
				if (Map.class.isAssignableFrom(c)) {
					result = true;
					break;
				}
			}
		}

		return result;
	}

	/**
	 * 判断type是否是泛型
	 * 
	 * @param type
	 * @return
	 */
	public static boolean isGeneric(Type type) {
		return type instanceof ParameterizedType;
	}

	/**
	 * 根据class，创建一个map
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Map createMap(Class clazz) {
		return new HashMap();
	}

	/**
	 * 根据集合类型，创建集合
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Collection<? extends Object> createCollection(Class<? extends Object> clazz) {
		Collection<? extends Object> result = null;
		if (clazz.isAssignableFrom(List.class)) {
			result = new ArrayList();
		} else if (clazz.isAssignableFrom(Set.class)) {
			result = new HashSet();
		}
		return result;
	}

	/**
	 * 根据class，转换字符串类型
	 * 
	 * @param clazz
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValue(Class<? extends Object> clazz, String value) {
		T result = null;
		if (clazz.isAssignableFrom(Integer.class)) {
			result = (T) new Integer(value);
		} else if (clazz.isAssignableFrom(String.class)) {
			result = (T) value;
		}

		return result;
	}
	
	/**
	 * 获得map的key的类型
	 * 
	 * @param type
	 * @return
	 */
	public static Type getGenericOfMapKey(Type type) {
		ParameterizedType parameterizedType = (ParameterizedType) type;
		Type keyType = parameterizedType.getActualTypeArguments()[0];
		return keyType;
	}

	/**
	 * 获得map的value的类型
	 * 
	 * @param type
	 * @return
	 */
	public static Type getGenericOfMapValue(Type type) {
		ParameterizedType parameterizedType = (ParameterizedType) type;
		Type valueType = parameterizedType.getActualTypeArguments()[1];
		return valueType;
	}

	/**
	 * 获得类型
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Class<? extends Object> getRawType(Type type) {
		ParameterizedType parameterizedType = (ParameterizedType) type;
		Class<? extends Object> genericClass = (Class<? extends Object>) parameterizedType.getRawType();
		return genericClass;
	}

	/**
	 * 获得集合的泛型
	 * @param type
	 * @return
	 */
	public static Type getGenericOfCollection(Type type) {
		ParameterizedType parameterizedType = (ParameterizedType) type;
		return  parameterizedType.getActualTypeArguments()[0];
	}
	
	/**
	 * 获得类的所有泛型
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Class<? extends Object>[]  getGenericOfClass(Class<? extends Object> clazz) {
		Type[] types = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments();
		Class<? extends Object>[] result = new Class[types.length];
		for (int i = 0; i < types.length; i++) {
			result[i] = (Class<? extends Object>) types[i];
		}
		return result;
	}
	
	/**
	 * 获得所有常量
	 * @param clazz
	 * @return 变量名-value的键值对
	 */
	public static Map<String, Object> getAllConstantValue(Class<? extends Object> clazz) {
		Map<String, Object> result = new HashMap<String, Object>();
		Field[] fields = clazz.getFields();
		for (Field field : fields) {
			if ((field.getModifiers() & Modifier.STATIC) == Modifier.STATIC) {
				if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
					try {
						result.put(field.getName(), field.get(clazz));
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取注解
	 * TODO 等待优化，直接获取值，而不是返回注解基类
	 * @param obj
	 * @param annotationClass
	 * @return
	 */
	public static Annotation getAnnotation(Object obj, Class<? extends Object> annotationClass){
		if (obj == null) {
			return null;
		}
		
		Annotation result = null;
		for (Annotation annotation : obj.getClass().getAnnotations()) {
			if (annotation.annotationType().equals(annotationClass)) {
				result = annotation;
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * 获取包括父类在内的所有字段
	 * @param clazz
	 * @return
	 */
	public static Field[] getAllField(Class<? extends Object> clazz) {
		List<Field> fieldList = new ArrayList<>() ;
		while (clazz != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
		      fieldList.addAll(Arrays.asList(clazz .getDeclaredFields()));
		      clazz = clazz.getSuperclass(); //得到父类,然后赋给自己
		}
		
		return fieldList.toArray(new Field[fieldList.size()]);
	}
}
