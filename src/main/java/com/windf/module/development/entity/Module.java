package com.windf.module.development.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.windf.core.exception.UserException;
import com.windf.core.util.file.FileUtil;
import com.windf.core.util.file.XmlFileUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.util.file.SourceFileUtil;

public class Module extends com.windf.core.bean.Module{
	
	public static File getMoudleConfigSourceFileByCode(String code) {
		String configFilePath = SourceFileUtil.getConfigPath() + Constant.DEFAULT_MODULE_DESCRIPT_PATH + File.separator + code + File.separator + MODULE_XML_FILE_NAME;
		return FileUtil.getFile(configFilePath, true);
	}
	
	private List<Entity> entitys = new ArrayList<Entity>();	
	
	public Module() {
		super(null);
	}
	
	public Module(String code) {
		super(code);
	}
	
	public void write() throws UserException {
		/*
		 * 获取配置文件位置
		 */
		File file = getMoudleConfigSourceFileByCode(this.getCode());
		
		/*
		 * 如果不存在，创建文件的目录
		 */
		FileUtil.createIfNotExists(file, true);
		
		/*
		 * 写入配置文件
		 */
		XmlFileUtil.writeObject2Xml(this, file);
	}
	
	public Entity getEntityByName(String name) {
		Entity result = null;
		for (int i = 0; i < entitys.size(); i++) {
			if (entitys.get(i).getName().equals(name)) {
				result = entitys.get(i);
				break;
			}
			
		}
		return result;
	}

	public String getId() {
		return this.getCode();
	}
	
	public void setId(String code) {
		this.setCode(code);
	}
	
	public List<Entity> listEntitys() {
		return entitys;
	}

	public void setEntitys(List<Entity> entitys) {
		this.entitys = entitys;
	}
}
