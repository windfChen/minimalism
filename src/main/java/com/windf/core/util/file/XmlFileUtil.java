package com.windf.core.util.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import com.windf.core.util.reflect.SimpleField;

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
        
        writeObject2Xml(obj, element, null);

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
	public static boolean writeObject2Xml(Object object, Element element, Set<Class> stopDeadlock) {
		
		if (ReflectUtil.isBaseType(object.getClass())) {
			element.setText(object.toString());
		} else if (ReflectUtil.isCollection(object.getClass())) {
			Collection collection = (Collection) object;
			Iterator iterator = collection.iterator();
			while (iterator.hasNext()) {
				Element subElement = element.addElement(DEFAULT_LIST_ITEM_ELEMENT_NAME);
				Object obj = (Object) iterator.next();
				writeObject2Xml(obj, subElement, null);
			}
		} else if (ReflectUtil.isMap(object.getClass())) {
			Map map = (Map) object;
			Iterator iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				Object obj = map.get(key);
				
				Element subElement = element.addElement(key);
				writeObject2Xml(obj, subElement, null);
				
			}
		} else {
			/*
			 *  防止死循环
			 */
			if (stopDeadlock == null) {
				stopDeadlock = new HashSet<Class>();
			} 
			if (!stopDeadlock.add(object.getClass())) {	
				element.getParent().remove(element);
				return false;
			}

			/*
			 *  获得对象的所有非空属性
			 */
			Map<String, Object> getterMethodValueMap = BeanUtil.getAllGetterMethods(object);
			
			/*
			 * 遍历对象的getter属性对应的值，把属性名和值放到xml文件里
			 */
			Iterator<String> getterMethodValueMapKeysIterator = getterMethodValueMap.keySet().iterator();
			while (getterMethodValueMapKeysIterator.hasNext()) {
				String methodName = getterMethodValueMapKeysIterator.next();
				Object result = getterMethodValueMap.get(methodName);
				
				Element subElement = element.addElement(methodName);
				writeObject2Xml(result, subElement, stopDeadlock);
				
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
