package com.windf.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLUtil {
	
	private static Map<String, String> dataTypeMap = new HashMap<String, String>();
	private static Map<String, String> defaultDatabaseTypeMap = new HashMap<String, String>();
	private static Map<String, Integer> defaultDatabaseLengthMap = new HashMap<String, Integer>();
	static {
		dataTypeMap.put("varchar", "String");
		dataTypeMap.put("text", "String");
		dataTypeMap.put("longtext", "String");
		dataTypeMap.put("char", "String");
		dataTypeMap.put("int", "Integer");
		dataTypeMap.put("tinyint", "Integer");
		dataTypeMap.put("bigint", "Integer");
		dataTypeMap.put("double", "Double");
		dataTypeMap.put("decimal", "Double");
		dataTypeMap.put("float", "Float");
		dataTypeMap.put("date", "Date");
		dataTypeMap.put("datetime", "Date");
		dataTypeMap.put("time", "Date");

		defaultDatabaseTypeMap.put("String", "varchar");
		defaultDatabaseTypeMap.put("Integer", "int");
		defaultDatabaseTypeMap.put("Double", "double");
		defaultDatabaseTypeMap.put("Float", "float");
		defaultDatabaseTypeMap.put("Date", "datetime");
		
		defaultDatabaseLengthMap.put("String", 50);
		defaultDatabaseLengthMap.put("Integer", 11);
		defaultDatabaseLengthMap.put("Double", 11);
		defaultDatabaseLengthMap.put("Float", 11);
		defaultDatabaseLengthMap.put("Date", null);
	}

	/**
	 * 查询sql中select和from中的字段
	 * @param querySql
	 * @return
	 */
	public static List<String> getSqlSelectNames(String querySql) {
		return getSqlNames(querySql, "select ", "from ", ",");
	}
	
	/**
	 * 获得字符串连个标记中间的字段，用某个字符切割（但是括号、引号中的不算）
	 * 括号和引号中间的东西会被去掉（包括括号和引号本身）
	 * 括号包括：大括号、中括号、小括号
	 * 引号包括：单引号、双引号
	 * TODO 目前只实现了小括号
	 * @param sql
	 * @param startFlag
	 * @param endFlag
	 * @return
	 */
	public static List<String> getSqlNames(String sql, String startFlag, String endFlag, String splitStr) {
		
		sql = sql.toLowerCase();
		String selectStr = sql.substring(sql.indexOf(startFlag) + startFlag.length(), sql.indexOf(endFlag));
		
		// 删除select中的子查询
		while (selectStr.contains("(")) {
			selectStr = selectStr.replaceAll("\\([^\\)\\(]*\\)", "");
		}
		String[] selects = selectStr.split(splitStr);
		
		List<String> names = new ArrayList<String>();
		for (int i = 0; i < selects.length; i++) {
			String key = null;

			String select = selects[i].trim();
			String[] ss = select.split("\\s");
			if (ss.length == 1) {
				ss = ss[0].split(".");
				if (ss.length == 1) {
					key = ss[0];
				} else {
					key = ss[ss.length - 1];
				}
			} else {
				key = ss[ss.length - 1];
			}
			
			if (key != null) {
				names.add(key.trim());
			}
			
		}
		
		return names;
	}
	
	/**
	 * 表名称转换为实体名称
	 * @param tableName
	 * @return
	 */
	public static String tableName2EntityName(String tableName) {
		if (tableName.contains("_r_")) {
			tableName = tableName.replace("_r_", "_");
		}

		String result = StringUtil.toCamelCase(tableName, "_");
		result = StringUtil.firstLetterUppercase(result);
		
		return result;
	}

	/**
	 * 实体名称转换为表名称
	 * @param entityName
	 * @return
	 */
	public static String entityName2TableName(String entityName) {
		String result = StringUtil.splitCamelCase(entityName, "_");
		return result;
	}

	public static String dbName2JavaName(String dbName) {
		return StringUtil.toCamelCase(dbName, "_");
	}
	
	public static String javaName2DbName(String javaName) {
		String result = StringUtil.splitCamelCase(javaName, "_");
		return result;
	}

	
	/**
	 * 数据库类型转换为java类型
	 * TODO 应该从配置文件中读取
	 * @return
	 */
	public static String dbType2JavaType(String dbType) {
		return dataTypeMap.get(dbType);
	}
	
	public static String javaType2dbType(String javaType) {
		return defaultDatabaseTypeMap.get(javaType);
	}
	
	public static Integer javaType2dbLength(String javaType) {
		return defaultDatabaseLengthMap.get(javaType);
	}
	
	/**
	 * 将多行sql转换到一行，方便正则解析
	 * @param lines
	 * @return
	 */
	public static String sqls2OneLine(String[] lines) {
		String result = null;
		if (lines != null) {
			result = StringUtil.join(lines, "  ").toLowerCase();
		}
		return result;
	}
}
