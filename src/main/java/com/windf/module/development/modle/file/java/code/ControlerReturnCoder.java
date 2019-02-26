package com.windf.module.development.modle.file.java.code;

import java.util.ArrayList;
import java.util.List;

import com.windf.module.development.entity.ControlerMethod;

public class ControlerReturnCoder extends AbstractCodeable<ControlerMethod> {
	
	@Override
	public List<String> toCodes(ControlerMethod parameters, int tabCount) {
		List<String> result = new ArrayList<String>();
		this.tabCount = tabCount;
		
		
		
		return result;
	}
	
	@Override
	public ControlerMethod toObject(List<String> codes) {
		 return null;
	}
}
