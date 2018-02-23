package com.windf.module.development.modle.file.java.code;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.windf.core.util.CollectionUtil;
import com.windf.core.util.RegularUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.entity.Parameter;

public class ParameterVerifyCoder extends AbstractCodeable<List<Parameter>> {
	
	@Override
	public List<String> toCodes(List<Parameter> parameters, int tabCount) {
		List<String> result = new ArrayList<String>();
		this.tabCount = tabCount;
		
		if (CollectionUtil.isNotEmpty(parameters)) {
			/*
			 * 验证是否为空 
			 */
			StringBuffer notEmptyVariableNames = new StringBuffer();
			for (int i = 0; i < parameters.size(); i++) {
				Parameter parameter = parameters.get(i);
				
				if (parameter.getNotEmpty()) {
					notEmptyVariableNames.append(parameter.getName());
					notEmptyVariableNames.append(", ");
				}
			}
			if (notEmptyVariableNames.length() > 0) {
				if (", ".equals(notEmptyVariableNames.substring(notEmptyVariableNames.length() - 2))) {
					notEmptyVariableNames.delete(notEmptyVariableNames.length() - 2, notEmptyVariableNames.length());
				}
				result.add(tab() + "if (ParameterUtil.hasEmpty(" + notEmptyVariableNames.toString() + ")) {");
				result.add(tab(1) + "return responseReturn.parameterError();");
				result.add(tab() +  "}");
				this.newEmptyLine(result);
			}
			/*
			 * 验证正则表达式
			 */
			Map<String, String> allPatterns = RegularUtil.getAllPattern();
			for (Parameter parameter : parameters) {
				if (Constant.TYPE_JAVA_STRING.equals(parameter.getType())) {
					String name = parameter.getName();
					
					Map<String, String> patterns = parameter.getPatterns();
					if (patterns != null) {
						Iterator<String> iterator = patterns.keySet().iterator();
						while (iterator.hasNext()) {
							String pattern = iterator.next();
							String note = patterns.get(pattern);
							
							String regularValueName = allPatterns.get(pattern);
							if (regularValueName != null) {
								result.add(tab() + "if (!RegularUtil.match(" + name + ", RegularUtil." + regularValueName + ")) {");
								note = pattern;
							} else {
								result.add(tab() + "if (!RegularUtil.match(" + name + ", \"" + pattern + "\")) {");
							}
							
							result.add(tab(1) + "throw new ParameterException(\"" + note + "\");");
							result.add(tab() + "}");
						}
					}
				}
			}
		}
		
		return result;
	}
	
	@Override
	public List<Parameter> toObject(List<String> codes) {
		 return null;
	}
}
