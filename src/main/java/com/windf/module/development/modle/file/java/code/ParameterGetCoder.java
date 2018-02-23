package com.windf.module.development.modle.file.java.code;

import java.util.ArrayList;
import java.util.List;

import com.windf.core.util.CollectionUtil;
import com.windf.core.util.StringUtil;
import com.windf.module.development.entity.Parameter;

public class ParameterGetCoder extends AbstractCodeable<List<Parameter>> {
	
	@Override
	public List<String> toCodes(List<Parameter> parameters, int tabCount) {
		List<String> result = new ArrayList<String>();
		this.tabCount = tabCount;
		
		if (CollectionUtil.isNotEmpty(parameters)) {
			/*
			 * 获取参数
			 */
			for (Parameter parameter : parameters) {
				String type = StringUtil.firstLetterUppercase(parameter.getType());
				String name = parameter.getName();
				
				result.add(tab() + type + " " + name + " = paramenter.get" + type + "(\"" + name + "\");");
			}
		}
		
		return result;
	}
	
	@Override
	public List<Parameter> toObject(List<String> codes) {
		 return null;
	}
}
