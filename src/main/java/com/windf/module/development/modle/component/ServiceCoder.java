package com.windf.module.development.modle.component;

import java.util.ArrayList;
import java.util.List;

import com.windf.core.exception.UserException;
import com.windf.module.development.Constant;
import com.windf.module.development.entity.Entity;
import com.windf.module.development.entity.Parameter;
import com.windf.module.development.entity.Service;
import com.windf.module.development.entity.ServiceMethod;
import com.windf.module.development.modle.file.java.Annotation;
import com.windf.module.development.modle.file.java.ExceptionType;
import com.windf.module.development.modle.file.java.JavaCoder;
import com.windf.module.development.modle.file.java.Method;
import com.windf.module.development.modle.file.java.MethodParameter;
import com.windf.module.development.modle.file.java.MethodReturn;

public class ServiceCoder {
	
	private JavaCoder javaCoder;
	private JavaCoder javaImplCoder;
	private Service service;
	
	public ServiceCoder(Service service) {
		this.service = service;
		javaCoder = JavaCoder.getJavaCoderByName(service.getName());
		javaImplCoder = JavaCoder.getJavaCoderByName(service.getName() + "Impl");
	}
	
	/**
	 * 创建service，和实现类
	 * 如果service对应的JavaCoder不存在，创建；否则不做任何操作
	 * @throws UserException
	 */
	public void create() {
		if (javaCoder == null) {
			/*
			 * 创建java接口和实现类
			 */
			String moduleCode = service.getModule().getCode();
			String serviceName = service.getName();
			javaCoder = new JavaCoder(Constant.JAVA_MODULE_BASE_PACKAGE + "/" + moduleCode + "/service", serviceName);
			javaImplCoder = new JavaCoder(Constant.JAVA_MODULE_BASE_PACKAGE + "/" + moduleCode + "/service/impl", serviceName + "Impl");
			/*
			 * 设置javaCode为接口
			 */
			javaCoder.setInterfaceType();
			/*
			 * 设置注解
			 */
			Annotation serviceAnnotation = javaImplCoder.getAnnotationByName("Service");
			if (serviceAnnotation == null) {
				serviceAnnotation = new Annotation("Service");
				javaImplCoder.setAnnotation(serviceAnnotation);
			}
			/*
			 * 写入java文件
			 */
			javaCoder.write();
			javaImplCoder.write();
		}
	}
	
	/**
	 * 添加方法
	 * @return
	 * @throws UserException 
	 */
	public void addMethod(ServiceMethod serviceMethod) throws UserException {
		/*
		 * 检查javaCoder是否存在，如果存在就读取，否则创建
		 */
		create();
		/*
		 * 返回值
		 */
		Entity ret = serviceMethod.getRet();
		MethodReturn methodReturn = new MethodReturn(ret.getName());
		/*
		 * 方法名
		 */
		String name = serviceMethod.getName();
		/*
		 * 参数
		 */
		List<Parameter> parameters = serviceMethod.getParameters();
		List<MethodParameter> methodParameters = new ArrayList<MethodParameter>();
		for (int i = 0; i < parameters.size(); i++) {
			Parameter parameter = parameters.get(i);
			methodParameters.add(new MethodParameter(parameter.getType(), parameter.getName()));
		}
		/*
		 * 异常设置为数据访问异常
		 */
		ExceptionType exceptionType = new ExceptionType("UserException");
		/*
		 * 创建java类和实现类
		 */
		Method javaCoderMethod = new Method(name, methodReturn, methodParameters, exceptionType, true);
		Method javaImplCoderMethod = new Method(name, methodReturn, methodParameters, exceptionType, false);
		javaCoderMethod = javaCoder.createMethod(javaCoderMethod);
		javaImplCoderMethod = javaImplCoder.createMethod(javaImplCoderMethod);
		/*
		 * 设置Overrid注解
		 */
		javaImplCoderMethod.setAnnotation(new Annotation("Override"));
		/*
		 * 写入java文件
		 */
		javaCoder.write();
		javaImplCoder.write();
	}
}
