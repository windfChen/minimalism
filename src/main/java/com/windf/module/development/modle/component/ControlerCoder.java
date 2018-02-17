package com.windf.module.development.modle.component;

import com.windf.core.exception.UserException;
import com.windf.module.development.Constant;
import com.windf.module.development.entity.Controler;
import com.windf.module.development.entity.ControlerMethod;
import com.windf.module.development.modle.file.java.Annotation;
import com.windf.module.development.modle.file.java.JavaCoder;
import com.windf.module.development.modle.file.java.Method;
import com.windf.module.development.modle.file.java.MethodReturn;

public class ControlerCoder {
	private JavaCoder javaCoder;
	private Controler controler;
	
	public ControlerCoder(Controler controler) {
		this.controler = controler;
		javaCoder = JavaCoder.getJavaCoderByName(controler.getName());
	}
	
	/**
	 * 创建实体
	 * 前提：javaCoder为空
	 * @throws UserException
	 */
	public void create() throws UserException {
		if (javaCoder == null) {
			/*
			 * 创建java文件
			 */
			javaCoder = new JavaCoder(Constant.JAVA_MODULE_BASE_PACKAGE + "/" + controler.getModule().getCode() + "/controler", controler.getName());
			/*
			 * 设置继承
			 */
			javaCoder.setExtend(controler.getParent());
			/*
			 * 设置control注解
			 */
			Annotation controllerAnnotation = new Annotation("Controller");
			javaCoder.setAnnotation(controllerAnnotation);
			/*
			 * 设置scope多例注解
			 */
			Annotation scopeAnnotation = new Annotation("Scope", "prototype");
			javaCoder.addAnnotation(scopeAnnotation);
			/*
			 * 设置webPath
			 */
			Annotation requestMappingAnnotation = new Annotation("RequestMapping");
			requestMappingAnnotation.addValue("value", controler.getUrlPath());
			javaCoder.addAnnotation(requestMappingAnnotation);
			/*
			 * 写入文件
			 */
			javaCoder.write();
		}
	}
	
	/**
	 * 添加方法
	 * @param field
	 * @throws UserException 
	 */
	public void addMethod(ControlerMethod controlerMehtod) throws UserException {
		/*
		 * 设置返回值为String
		 */
		MethodReturn ret =  new MethodReturn(MethodReturn.STRING);
		/*
		 * 创建方法
		 */
		Method method = new Method(controlerMehtod.getName(), ret, null, null, false);
		javaCoder.createMethod(method);
		/*
		 * 设置注解
		 */
		Annotation requestMappingAnnotation = new Annotation("RequestMapping");
		requestMappingAnnotation.addValue("value", controlerMehtod.getUrlPath());
		if ("get".equalsIgnoreCase(controlerMehtod.getRequestMethod())) {
			requestMappingAnnotation.addValue("method", "{RequestMethod.GET}");
		} else if ("post".equalsIgnoreCase(controlerMehtod.getRequestMethod())) {
			requestMappingAnnotation.addValue("method", "{RequestMethod.POST}");
		}
		method.addAnnotation(requestMappingAnnotation);
		/*
		 * 写入
		 */
		javaCoder.write();
	}
}
