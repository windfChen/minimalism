package com.windf.module.development.modle.file.sql;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.windf.core.util.CollectionUtil;
import com.windf.module.development.entity.Dao;
import com.windf.module.development.entity.DaoMethod;
import com.windf.module.development.entity.Parameter;
import com.windf.module.development.util.JavaCodeUtil;
import com.windf.module.development.util.file.SourceFileUtil;
import com.windf.module.development.util.file.TextFileUtil;

public class DmlMapper {
	private Dao dao;
	
	private File sqlFile;

	/**
	 * 对象模型新建sql文件
	 * @param dao
	 */
	public DmlMapper(Dao dao) {
		this.dao = dao;
		String sqlFilePath = SourceFileUtil.getConfigPath() + "/module/" + dao.getModule().getCode() + "/" + "database/sqlmap/" + dao.getName().replace("Dao", "Mapper") +".xml";
		this.sqlFile = new File(sqlFilePath);
	}

	public void write() {
		if (dao != null) {
			List<String> result = new ArrayList<String>();
			result.add("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
			result.add("<!DOCTYPE mapper");
			result.add("  PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"");
			result.add("  \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
			result.add("<mapper namespace=\"" + dao.getId() +"\">");
			result.add("");
			for (int i = 0; i < dao.getDaoMethods().size(); i++) {
				DaoMethod daoMethod = dao.getDaoMethods().get(i);
				result.add(JavaCodeUtil.getTabString(1) + "<" + daoMethod.getType() + " id=\"" + daoMethod.getName() + "\" " + this.getType(daoMethod) + " returnType=\"" + this.getReturnStr(daoMethod) + "\" >");
				result.add(daoMethod.getSqlStr());
				result.add(JavaCodeUtil.getTabString(1) + "</" + daoMethod.getType() + ">");
				result.add("");
			}
			result.add("</mapper>"); 
			TextFileUtil.writeFile(sqlFile, result);
		}
	}
	
	public String getReturnStr(DaoMethod daoMethod) {
		StringBuffer parameterType = new StringBuffer();
		List<Parameter> parameterList = daoMethod.getParameters();
		if (CollectionUtil.isNotEmpty(parameterList)) {
			for (int i = 0; i < parameterList.size(); i++) {
				Parameter parameter = parameterList.get(i);
				parameterType.append(parameter.getType());
				if (i != parameterList.size() - 1) {
					parameterType.append(",");
				}
			}
		}
		return parameterType.toString();
	}

	public String getType(DaoMethod daoMethod) {
		String ret = "";
		if (daoMethod.getEntity() != null) {
			ret = "parameterType=\"" + daoMethod.getEntity().getName() + "\"";
		}
		return ret;
	}
}
