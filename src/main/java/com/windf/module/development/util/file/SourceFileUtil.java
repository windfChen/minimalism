package com.windf.module.development.util.file;

import java.io.File;

import com.windf.module.development.Constant;

public class SourceFileUtil {
	public static String  DEVELOPMENT_BASE_PATH = null;
	public static String JAVA_SOURCE_BASE_PATH = null;
	public static String CONFIG_SOURCE_BASE_PATH = null;
	public static String CREATE_TABLE_SQL_FILE_BASE_PATH = null;
	
	static {
		String classPath = Constant.class.getClassLoader().getResource("").getPath();
		String basePath = classPath.substring(0, classPath.lastIndexOf("src"));
		DEVELOPMENT_BASE_PATH = new File(basePath).getPath();
		
		JAVA_SOURCE_BASE_PATH =  DEVELOPMENT_BASE_PATH + "/src/main/java";
		CONFIG_SOURCE_BASE_PATH =  DEVELOPMENT_BASE_PATH + "/src/main/resources/config";
		
		CREATE_TABLE_SQL_FILE_BASE_PATH = SourceFileUtil.DEVELOPMENT_BASE_PATH + "/document/sql/module";
	}
	
	/**
	 * 获得开发根路径 eg: E
	 * @return
	 */
	public static String getBasePath() {
		return DEVELOPMENT_BASE_PATH;
	}
	
	/**
	 * 获得java路径的位置
	 * @return
	 */
	public static String getJavaPath() {
		return JAVA_SOURCE_BASE_PATH;
	}
	
	/**
	 * 获得源码配置文件的位置
	 * @return
	 */
	public static String getConfigPath() {
		return CONFIG_SOURCE_BASE_PATH;
	}

}
