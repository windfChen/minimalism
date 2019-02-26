package com.windf.module.development.modle.component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.windf.core.exception.UserException;
import com.windf.module.development.Constant;
import com.windf.module.development.entity.Controler;
import com.windf.module.development.entity.ControlerMethod;
import com.windf.module.development.entity.Module;
import com.windf.module.development.entity.Parameter;
import com.windf.module.development.modle.file.java.Annotation;
import com.windf.module.development.modle.file.java.CodeBlock;
import com.windf.module.development.modle.file.java.Comment;
import com.windf.module.development.modle.file.java.JavaCoder;
import com.windf.module.development.modle.file.java.Method;
import com.windf.module.development.modle.file.java.MethodReturn;
import com.windf.module.development.modle.file.java.code.ParameterGetCoder;
import com.windf.module.development.modle.file.java.code.ParameterVerifyCoder;
import com.windf.module.development.util.TemplateUtil;

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
			javaCoder.setAnnotation(new Annotation("Controller"));
			/*
			 * 设置scope多例注解
			 */
			javaCoder.addAnnotation(new Annotation("Scope", "prototype"));
			/*
			 * 设置webPath
			 */
			Annotation requestMappingAnnotation = new Annotation("RequestMapping");
			requestMappingAnnotation.addStringValue("value", controler.getUrlPath());
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
		 * 检查javaCoder是否存在，如果存在就读取，否则创建
		 */
		create();
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
		requestMappingAnnotation.addStringValue("value", controlerMehtod.getUrlPath());
		if ("get".equalsIgnoreCase(controlerMehtod.getRequestMethod())) {
			requestMappingAnnotation.addValue("method", "{RequestMethod.GET}");
		} else if ("post".equalsIgnoreCase(controlerMehtod.getRequestMethod())) {
			requestMappingAnnotation.addValue("method", "{RequestMethod.POST}");
		}
		method.addAnnotation(requestMappingAnnotation);
		/*
		 * 参数获取
		 */
		CodeBlock<List<Parameter>> parameterGetBlock = new CodeBlock<List<Parameter>>();
		parameterGetBlock.setCodeable(new ParameterGetCoder());
		parameterGetBlock.setTabCount(2);
		parameterGetBlock.serialize(controlerMehtod.getParameters());
		Comment parameterGetComment =  new Comment(2, false);
		parameterGetComment.addLine("验证获取");
		parameterGetBlock.setComment(parameterGetComment);
		method.addCodeBlock(0, parameterGetBlock);
		/*
		 * 参数验证
		 */
		CodeBlock<List<Parameter>> parameterVerifyBlock = new CodeBlock<List<Parameter>>();
		parameterVerifyBlock.setCodeable(new ParameterVerifyCoder());
		parameterVerifyBlock.setTabCount(2);
		parameterVerifyBlock.serialize(controlerMehtod.getParameters());
		Comment parameterVerifyComment =  new Comment(2, false);
		parameterVerifyComment.addLine("验证参数");
		parameterVerifyBlock.setComment(parameterVerifyComment);
		method.addCodeBlock(1, parameterVerifyBlock);
		/*
		 * 写入
		 */
		javaCoder.write();
	}
	
	/**
	 * 读取模板，获得执行后的模板
	 * @param templateName
	 * @param dataMap
	 * @return
	 */
	private List<String> getCodesByTemplate(String templateName, Map<String,Object> dataMap) {
		List<String> result = null;
		
		String templatePath = Module.getConfigPath(Module.getCurrentMoudle(ControlerCoder.class).getCode());
		try {
			result = TemplateUtil.getStringByTemplate(templatePath, templateName, dataMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
