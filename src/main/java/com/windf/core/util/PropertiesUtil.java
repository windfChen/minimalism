package com.windf.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.windf.core.bean.Module;
import com.windf.core.util.file.FileUtil;

public class PropertiesUtil {
	/**
	 * 根据路径获取配置文件，获取配置文件中的值
	 * @param classfilePath
	 * @param key
	 * @return
	 */
	public static String get(String classfilePath, String key) {
		String result = null;
		
		Properties properties = new Properties();
		FileInputStream in = null;
		try {
		    in = new FileInputStream(FileUtil.getFile(classfilePath));
		    properties.load(in);
		    result= properties.getProperty(key);
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		    	if (in != null) {
		    		in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	/**
	 * 读取默认配置文件
	 * @param key
	 * @return
	 */
	public static String getConfig(Class<?> clazz, String key) {
		String classfilePath = Module.getCurrentMoudle(clazz).getConfigFilePath() + File.separator + "config.properties";
		return get(classfilePath, key);
	}
	
	/**
	 * 读取插件的配置文件
	 * @param code
	 * @param fileName
	 * @param key
	 * @return
	 */
	public static String getPluginsConfig(String code, String fileName, String key) {
		if (StringUtil.isEmpty(fileName)) {
			fileName = "config.properties";
		}
		String classfilePath = FileUtil.getConfigPath() + "/plugins/" + code + "/" + fileName;
		return get(classfilePath, key);
	}
}
