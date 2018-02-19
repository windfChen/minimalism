package com.windf.module.development.modle.component;

import java.util.ArrayList;
import java.util.List;

import com.windf.core.exception.UserException;
import com.windf.core.util.StringUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.entity.Entity;
import com.windf.module.development.entity.Field;
import com.windf.module.development.modle.file.java.Attribute;
import com.windf.module.development.modle.file.java.CodeBlock;
import com.windf.module.development.modle.file.java.JavaCoder;
import com.windf.module.development.modle.file.java.Method;
import com.windf.module.development.modle.file.java.MethodParameter;
import com.windf.module.development.modle.file.java.MethodReturn;
import com.windf.module.development.modle.file.java.code.FieldCoder;

public class EntityCoder {
	private JavaCoder javaCoder;
	private Entity entity;
	
	public EntityCoder(Entity entity) {
		this.entity = entity;
		javaCoder = JavaCoder.getJavaCoderByName(entity.getName());
	}
	
	/**
	 * 创建实体
	 * 前提：javaCoder为空
	 * @throws UserException
	 */
	public void create() throws UserException {
		if (javaCoder == null) {
			javaCoder = new JavaCoder(Constant.JAVA_MODULE_BASE_PACKAGE + "/" + entity.getModule().getCode() + "/entity", entity.getName());
			javaCoder.setExtend(entity.getParent());
			javaCoder.write();
		}
	}
	
	/**
	 * 添加字段
	 * @param field
	 * @throws UserException 
	 */
	public void addField(Field field) throws UserException {
		/*
		 * 检查javaCoder是否存在，如果存在就读取，否则创建
		 */
		create();
		/*
		 * field属性
		 */
		Attribute attribute = new Attribute(field.getType(), field.getName());
		attribute.setModifier(Constant.MODIFY_PRIVATE);
		attribute.setLineComment(field.getComment());
		javaCoder.createAttribute(attribute);
		/*
		 * getter方法
		 */
		MethodReturn getterRet = new MethodReturn(field.getType());
		Method getterMethod = new Method("get" + StringUtil.firstLetterUppercase(field.getName()), getterRet, null, null, false);
		javaCoder.createMethod(getterMethod);
		CodeBlock<Field> fieldGetterCoderBlock = new CodeBlock<Field>();
		fieldGetterCoderBlock.setCodeable(new FieldCoder(true));
		fieldGetterCoderBlock.setTabCount(2);
		fieldGetterCoderBlock.serialize(field);
		getterMethod.addCodeBlock(0, fieldGetterCoderBlock);
		/*
		 * setter方法
		 */
		MethodReturn setterRet = new MethodReturn(MethodReturn.VOID);
		List<MethodParameter> parameters = new ArrayList<MethodParameter>();
		MethodParameter parameter = new MethodParameter();
		parameter.setType(field.getType());
		parameter.setName(field.getName());
		parameters.add(parameter);
		Method setterMethod = new Method("set" + StringUtil.firstLetterUppercase(field.getName()), setterRet, parameters, null, false);
		CodeBlock<Field> fieldSetterCoderBlock = new CodeBlock<Field>();
		fieldSetterCoderBlock.setCodeable(new FieldCoder(false));
		fieldSetterCoderBlock.setTabCount(2);
		fieldSetterCoderBlock.serialize(field);
		setterMethod.addCodeBlock(0, fieldSetterCoderBlock);
		javaCoder.createMethod(setterMethod);
		/*
		 * 写入
		 */
		javaCoder.write();
	}
}
