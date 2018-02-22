package com.windf.core.util.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.windf.core.Constant;
import com.windf.core.exception.CodeException;
import com.windf.core.exception.UserException;
import com.windf.core.util.StringUtil;
import com.windf.core.util.reflect.BeanUtil;
import com.windf.core.util.reflect.ReflectUtil;
import com.windf.core.util.reflect.SerializableBaseTypeParameter;
import com.windf.core.util.reflect.SerializableParameterName;
import com.windf.core.util.reflect.SimpleField;
import com.windf.core.util.reflect.UnSerializable;

public class XmlFileUtil {
	private static final String DEFAULT_LIST_ITEM_ELEMENT_NAME = "item";
	
	/**
	 * 读取xml文档，加载所有xml内容到map里面
	 * @param xmlFile
	 * @throws UserException 
	 */
	public static <T> T readXml2Object(File xmlFile, Class<T> clazz) throws CodeException {
		T result = null;
		SAXReader saxReader = new SAXReader();
		Document document;
		try {
			document = saxReader.read(xmlFile);
			result = readXml2Object(document.getRootElement(), clazz, null);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 读取xml文档，加载所有xml内容到map里面
	 * @param element	解析的元素节点
	 * @param clazz	类型
	 * @param type 如果有泛型，泛型是什么类型（泛型可以嵌套），没有泛型则为null
	 * @throws UserException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T readXml2Object(Element element, Class<T> clazz, Type type) throws CodeException {
		
		Object result = null;
		
		try {
			// 判断clazz类型
			if (ReflectUtil.isBaseType(clazz)) {	// 如果是基本类型，包括字符串和基本类型封装类
				result = ReflectUtil.getValue(clazz, getElementText(element));
			} else if (ReflectUtil.isCollection(clazz)) {	// 如果是集合
				if (type != null) {
					Collection collection = ReflectUtil.createCollection(clazz);
					
					// 遍历element的子元素，递归处理
					Iterator elementIterator = element.elementIterator();
					while (elementIterator.hasNext()) {
						Element subElement = (Element) elementIterator.next();
						
						// 判断是否为泛型
						Type genericType = null;
						Class genericClass = null;
						if (ReflectUtil.isGeneric(type)) {	// 如果是，获取泛型中的类型
							 genericType = ReflectUtil.getGenericOfCollection(type);
				             genericClass = ReflectUtil.getRawType(type);
						} else {
							genericClass = (Class) type;
						}
						Object obj = readXml2Object(subElement, genericClass, genericType);
						collection.add(obj);
					}
					
					result = collection;
				}
			} else if (ReflectUtil.isMap(clazz)) {
				if (type != null) {
					Map map = ReflectUtil.createMap(clazz);
					// 遍历element的子元素，递归处理
					Iterator elementIterator = element.elementIterator();
					while (elementIterator.hasNext()) {
						Element subSubElement = (Element) elementIterator.next();
						
						// 判断是否为泛型
						Type valueType = null;
						Class genericClass = null;
						if (ReflectUtil.isGeneric(type)) {	// 如果是，获取泛型中的类型
							 Type keyType = ReflectUtil.getGenericOfMapKey(type);
							 if (keyType != String.class) {
								 throw new CodeException("map的key必须是String类型");
							 }
							 valueType = ReflectUtil.getGenericOfMapValue(type);
							 genericClass = ReflectUtil.getRawType(type);
						} else {
							genericClass = (Class) type;
						}
						
						Object obj = readXml2Object(subSubElement, genericClass, valueType);
						map.put(subSubElement.getName(), obj);
					}
					result = map;
				}
			} else {	// 如果是其他自定义对象
				// 创建对象
				Object object = clazz.newInstance();
				
				// 遍历element的子元素，递归设置每个字段
				Iterator elementIterator = element.elementIterator();
				while (elementIterator.hasNext()) {
					Element subSubElement = (Element) elementIterator.next();
				
					SimpleField simpleField = SimpleField.getSimpleField(object, subSubElement.getName());
					if (simpleField != null) {
						simpleField.setValue(readXml2Object(subSubElement, simpleField.getFieldClass(), simpleField.getGenericType()));
					}
				}
				
				/*
				 * 遍历属性，设置值
				 */
				Iterator attributeIterator = element.attributeIterator();
				while (attributeIterator.hasNext()) {
					Attribute attribute = (Attribute) attributeIterator.next();
					
					SimpleField simpleField = SimpleField.getSimpleField(object, attribute.getName());
					if (simpleField != null) {
						simpleField.setStringValue(attribute.getStringValue());
					}
				}
				
				// 返回创建的对象
				result = object;
			}
		
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return (T) result;
	}

	/**
	 * 把对象写入xml文件中
	 * @param obj
	 * @param xmlFile
	 * @return
	 */
	public static boolean writeObject2Xml(Object obj, File xmlFile) {
		
		String fileName = xmlFile.getName();
		if (fileName.lastIndexOf(".") > 0) {
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
		}
		
		Document document = DocumentHelper.createDocument(); 
		Element element = document.addElement(fileName);
        
        writeObject2Xml(obj, element, null, null, false);

        // 创建输出格式(OutputFormat对象)
        OutputFormat format = OutputFormat.createPrettyPrint();

        ///设置输出文件的编码
        format.setEncoding(Constant.DEFAULT_ENCODING);

        try {
            // 创建XMLWriter对象
            XMLWriter writer = new XMLWriter(new FileOutputStream(xmlFile), format);

            //设置不自动进行转义
            writer.setEscapeText(false);

            // 生成XML文件
            writer.write(document);

            //关闭XMLWriter对象
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return false;
	}
	
	/**
	 * 把对象写入xml文件中
	 * @param object
	 * @param xmlFile
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean writeObject2Xml(Object object, Element element, Set<Class> stopDeadlock, String[] serializableParameterNames, boolean onlyBaseParameter) {
		
		if (ReflectUtil.isBaseType(object.getClass())) {
			element.setText(object.toString());
		} else if (ReflectUtil.isCollection(object.getClass())) {
			Collection collection = (Collection) object;
			/*
			 * 拆解list，递归取值
			 */
			Iterator iterator = collection.iterator();
			while (iterator.hasNext()) {
				Element subElement = element.addElement(DEFAULT_LIST_ITEM_ELEMENT_NAME);
				Object obj = (Object) iterator.next();
				writeObject2Xml(obj, subElement, null, serializableParameterNames, onlyBaseParameter);
			}
		} else if (ReflectUtil.isMap(object.getClass())) {
			Map map = (Map) object;
			/*
			 * 拆解map，递归取值
			 */
			Iterator iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				Object obj = map.get(key);
				
				Element subElement = element.addElement(key);
				writeObject2Xml(obj, subElement, null, serializableParameterNames, onlyBaseParameter);
			}
		} else {
			/*
			 * 防止死循环
			 */
			if (stopDeadlock == null) {
				stopDeadlock = new HashSet<Class>();
			}
			if (!stopDeadlock.add(object.getClass())) {	
				element.getParent().remove(element);
				return false;
			}
			/*
			 * 遍历对象的所有属性
			 */
			Field[] fields = ReflectUtil.getAllField(object.getClass());
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				/*
				 * 静态属性不序列化
				 */
				int modifiers = field.getModifiers();
				if (Modifier.isStatic(modifiers)) {
					continue;
				}
				/*
				 * 非序列化属性不序列化
				 */
				if (Modifier.isTransient(modifiers)) {
					continue;
				}
				/*
				 * 如果指定了序列化的字段，需要验证是否在该序列化中
				 */
				if (serializableParameterNames != null) {
					boolean hasParameterName = false;
					/*
					 * 遍历指定的序列化字段名称数组
					 */
					for (int j = 0; j < serializableParameterNames.length; j++) {
						if (field.getName().equals(serializableParameterNames[j])) {
							hasParameterName = true;
							break;
						}
					}
					/*
					 * 如果没有找到，不再进行
					 */
					if (!hasParameterName) {
						continue;
					}
				}
				/*
				 * 检查是否可以序列化基本属性
				 */
				if (onlyBaseParameter && !ReflectUtil.isBaseType(field.getType())) {
					continue;
				}
				/*
				 * 不能是非序列化的属性
				 */
				if (field.getAnnotation(UnSerializable.class) != null) {
					continue;
				}
				/*
				 * 读取属性的值
				 */
				Object result = BeanUtil.invockeGetterMethod(object, BeanUtil.getGetterMethodName(field.getName()));
				/*
				 * 如果属性值为空，不再写入
				 */
				if (result == null) {
					continue;
				}
				/*
				 * 检查字段是否拥有参数序列化注解，只是序列化对象的部分属性
				 */
				SerializableParameterName serializableParameterName = field.getAnnotation(SerializableParameterName.class);
				String[] newSerializableParameterNames = null;
				if (serializableParameterName != null) {
					newSerializableParameterNames = serializableParameterName.value();	
				}
				/*
				 * 检查字段是是否拥有值序列化基础实行的注解
				 */
				boolean newOnlyBaseParameter = false;
				if (field.getAnnotation(SerializableBaseTypeParameter.class) != null) {
					newOnlyBaseParameter = true;
				}
				/*
				 * 根据属性名创建元素
				 */
				Element subElement = element.addElement(field.getName());
				/*
				 * 递归，读取Object到subElement
				 */
				writeObject2Xml(result, subElement, stopDeadlock, newSerializableParameterNames, newOnlyBaseParameter);
			}
		}
		
		return true;
	}

	/**
	 * 获取元素的文本，如果元素下面只有一个子元素或者属性，级联获取
	 * @param element
	 * @return
	 */
	private static String getElementText(Element element) {
		String result = element.getTextTrim();
		
		if (StringUtil.isEmpty(result)) {
			if (element.elements().size() == 1) {
				result = getElementText((Element) element.elements().get(0));
			} else 	if (element.attributes().size() == 1) {
				result = element.attribute(0).getStringValue();
			}
		}
		
		return result;
	}
}
