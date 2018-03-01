package com.windf.module.development.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.windf.core.util.StringUtil;
import com.windf.core.util.file.FileUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateUtil {
	private static final String BASE_TEMPLATE_PATH = "/data/template/";
	
	public static List<String> getStringByTemplate(String templatePath, String templateName, Map<String,Object> dataMap) throws IOException {
		List<String> result = null;
		/*
		 * 获取模板配置
		 */
		Configuration configuration = new Configuration();
        configuration.setDirectoryForTemplateLoading(FileUtil.getFile(FileUtil.getWEBINFPath() + BASE_TEMPLATE_PATH + StringUtil.fixNull(templatePath)));
		try {
			/*
			 * 读取模板
			 */
			Template template = configuration.getTemplate(templateName);
			String templateContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);
			/*
			 * 按行切割成String形式的List
			 */
			String[] strs = templateContent.split("\\n\\t");
			result = Arrays.asList(strs);
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
		
		return result;
    }
}
