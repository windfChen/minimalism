package com.windf.module.development.modle.file.java.code;

import java.util.ArrayList;
import java.util.List;

import com.windf.core.exception.UserException;
import com.windf.module.development.entity.Field;
import com.windf.module.development.util.JavaCodeUtil;

public class FieldCoder extends AbstractCodeable<Field>{
	
	private boolean isGetter;
	
	public FieldCoder() {
		
	}
	
	public FieldCoder(boolean isGetter) {
		this.isGetter = isGetter;
	}
	
	public void setIsGetter(boolean isGetter) {
		this.isGetter = isGetter;
	}

	@Override
	public List<String> toCodes(Field t, int tabCount) throws UserException {
		List<String> result = new ArrayList<String>();
		if (isGetter) {
			StringBuffer getter = new StringBuffer();
			getter.append(JavaCodeUtil.getTabString(tabCount));
			getter.append("return this." + t.getName() + ";");
			result.add(getter.toString());
		} else {
			StringBuffer setter = new StringBuffer();
			setter.append(JavaCodeUtil.getTabString(tabCount));
			setter.append("this." + t.getName() + " = " + t.getName() + ";");
			result.add(setter.toString());
		}
		return result;
	}

	@Override
	public Field toObject(List<String> codes) {
		return null;
	}

}
