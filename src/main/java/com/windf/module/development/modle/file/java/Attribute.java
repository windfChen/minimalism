package com.windf.module.development.modle.file.java;

import java.util.ArrayList;
import java.util.List;

import com.windf.core.util.StringUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.util.JavaCodeUtil;

public class Attribute extends AbstractType {
	/**
	 * 判断是否是属性，所有属性都是一行
	 * 
	 * @param lineContent
	 * @return
	 */
	static boolean isAttributeLine(String lineContent) {
		boolean result = false;
		lineContent = lineContent.split("//")[0];
		if (JavaCodeUtil.lineStartTabCount(lineContent) == 1 && lineContent.trim().length() > 0
				&& lineContent.trim().endsWith(";")) {
			result = true;
		}

		return result;
	}

	/*
	 * codes
	 */
	String codes;
	/*
	 * info
	 */
	String modifier;
	boolean isStatic;
	boolean isFinal;
	String type;
	String name;
	String valueStr;
	String lineComment;

	public Attribute(String lineContent) {
		this.codes = lineContent;

		initInfoByCodes();
	}

	public Attribute(String type, String name) {
		this.type = type;
		this.name = name;
	}

	private void initInfoByCodes() {
		String[] ss1 = codes.split(";");
		if (ss1.length > 1) {
			String comment2 = ss1[1].trim();
			if (comment2.length() > 2) {
				this.lineComment = comment2.substring(2).trim();
			}
		}
		String[] ss2 = ss1[0].split("=");
		if (ss2.length > 1) {
			this.valueStr = ss2[1].trim();
		}
		
		String[] ss3 = JavaCodeUtil.getInnerString(ss2[0],"^\\s*(public|private|protected)?( static)?( final)?\\s+(\\w*)\\s+(\\w*)\\s*$");
		if (ss3.length > 0) {
			modifier = ss3[0];
			if (modifier == null) {
				modifier = "package";
			}
			isStatic = ss3[1] != null;
			isFinal = ss3[2] != null;
			type = ss3[3];
			name = ss3[4];
			name = name.trim();
			if (name.endsWith(";")) {
				name = name.substring(0, name.length() - 1);
				name = name.trim();
			}
		}
	}

	/**
	 * write codes
	 * 
	 * @return
	 */
	List<String> write() {
		List<String> result = new ArrayList<String>();

		result.addAll(this.getComment());
		result.addAll(this.getAnnotationsString(1));
		result.add(this.getLineCode());

		return result;
	}
	
	private String getLineCode() {
		StringBuffer line = new StringBuffer();
		line.append(JavaCodeUtil.getTabString(1));
		if (!"package".equals(this.getModifier()) && StringUtil.isNotEmpty(this.getModifier())) {
			line.append(this.getModifier() + Constant.WORD_SPLIT); 
		}
		if (isStatic) {
			line.append("static" + Constant.WORD_SPLIT);
		}
		if (isFinal) {
			line.append("final" + Constant.WORD_SPLIT);
		}
		line.append(this.getType() + Constant.WORD_SPLIT + this.getName());
		if (StringUtil.isNotEmpty(this.getValueStr())) {
			line.append(Constant.WORD_SPLIT + "=" + Constant.WORD_SPLIT);
			line.append(valueStr);
		}
		line.append(";");
		if (StringUtil.isNotEmpty(this.getLineComment())) {
			line.append(Constant.WORD_SPLIT + "//" + Constant.WORD_SPLIT + this.getLineComment());
		}
		return line.toString();
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public String getLineComment() {
		return lineComment;
	}

	public void setLineComment(String lineComment) {
		this.lineComment = lineComment;
	}
	public String getValueStr() {
		return valueStr;
	}

	public void setValueStr(String valueStr) {
		this.valueStr = valueStr;
	}


}
