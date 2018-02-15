package com.windf.module.development.modle.component;

import java.util.ArrayList;
import java.util.List;

import com.windf.core.exception.UserException;
import com.windf.module.development.Constant;
import com.windf.module.development.entity.Dao;
import com.windf.module.development.entity.DaoMethod;
import com.windf.module.development.entity.Parameter;
import com.windf.module.development.modle.file.java.ExceptionType;
import com.windf.module.development.modle.file.java.JavaCoder;
import com.windf.module.development.modle.file.java.Method;
import com.windf.module.development.modle.file.java.MethodParameter;
import com.windf.module.development.modle.file.java.MethodReturn;
import com.windf.module.development.modle.file.sql.DmlMapper;

public class DaoCoder {
	private JavaCoder javaCoder;
	private DmlMapper dmlMapper;
	private Dao dao;
	
	public DaoCoder(Dao dao) {
		this.dao = dao;
		
		javaCoder = JavaCoder.getJavaCoderByName(dao.getName());
		dmlMapper = new DmlMapper(dao);
	}
	
	public void create() throws UserException {
		if (javaCoder == null) {
			javaCoder = new JavaCoder(Constant.JAVA_MODULE_BASE_PACKAGE + "/" + dao.getModule().getCode() + "/dao", dao.getName());
			javaCoder.setClassType(Constant.TYPE_INTERFACE);
			javaCoder.setExtend(dao.getParent());
			javaCoder.write();
		}
		dmlMapper.write();
	}
	
	public void addMethod(DaoMethod daoMethod) throws UserException {
		/*
		 * 返回值设置为dao对应的实体
		 */
		MethodReturn ret = new MethodReturn(daoMethod.getEntity().getName());
		/*
		 * 异常设置为数据访问异常
		 */
		ExceptionType exceptionType = new ExceptionType("DataAccessException");
		/*
		 * 将接收到的参数，转换为方法参数
		 */
		List<MethodParameter> methodParameters = new ArrayList<MethodParameter>();
		for (int i = 0; i < daoMethod.getParameters().size(); i++) {
			Parameter parameter = daoMethod.getParameters().get(i);
			MethodParameter methodParameter = new MethodParameter(parameter.getType(), parameter.getName());
			methodParameters.add(methodParameter);
		}
		/*
		 * 创建方法
		 */
		Method method = new Method(daoMethod.getName(), ret, methodParameters, exceptionType, true);
		javaCoder.createMethod(method);
		javaCoder.write();
		/*
		 * sql语句更新
		 */
		dmlMapper.write();
	}

	public void write() {
		javaCoder.write();
		dmlMapper.write();
	}

}
