package com.windf.module.development.modle.file.java;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.windf.core.exception.UserException;
import com.windf.core.util.CollectionUtil;
import com.windf.core.util.RegularUtil;
import com.windf.core.util.StringUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.util.JavaCodeUtil;
import com.windf.module.development.util.file.LineReader;
import com.windf.module.development.util.file.SourceFileUtil;
import com.windf.module.development.util.file.TextFileUtil;

public class JavaCoder extends AbstractType{
	
	private static Map<String, JavaCoder> allJavaCoders = new HashMap<String, JavaCoder>();
	public static JavaCoder getJavaCoderByName(String name) {
		return allJavaCoders.get(name);
	}
	
	/*
	 * 类信息
	 */
	private Imports imports = new Imports();
	private String modifier;
	private String classType;
	private String className;
	private boolean isAbstract;
	private String classGenre;
	private String extend;
	private List<Attribute> attributes = new ArrayList<Attribute>();
	private List<Method> methods = new ArrayList<Method>();
	/*
	 * 类文件行字符串信息
	 */
	private String packageInfo;
	private String implementsStr;
	private String classEnd;
	/*
	 * 类文件信息
	 */
	private String classPath;
	
	public JavaCoder(String packagePath, String className) {
		this.classPath = SourceFileUtil.getJavaPath() + "/" + packagePath + "/" + className + ".java";
		
		File javaFile = new File(this.classPath);
		
		/*
		 * 如果不存在，创建java 
		 */
		if (javaFile.exists()) {
			readCodes(javaFile);
		} else {
			String newPackagePath = packagePath.replace("/", ".");
			if (newPackagePath.startsWith(".")) {
				newPackagePath = newPackagePath.substring(1);
			}
			this.packageInfo = "package" + Constant.WORD_SPLIT + newPackagePath + ";";
			this.className = className;
			this.modifier = Constant.MODIFY_PUBLIC;
			this.classType = Constant.TYPE_CLASS;
			this.classEnd = "}";
			this.write();
		}
		
		/*
		 * 统一存储
		 */
		allJavaCoders.put(className, this);
	}

	private void readCodes(File javaFile) {
		
		TextFileUtil.readLine(javaFile, new LineReader() {
			
			List<String> annotations = new ArrayList<String>();
			boolean isInMethod = false;
			Method method = null;
			boolean inComments = false;
			Comment comment = null;
			
			@Override
			public String readLine(List<String> oldLines, String lineContent, int lineNo) {
				
				// 统一制表符
				lineContent = lineContent.replace("\t", Constant.TAB);
			
				if (lineContent.startsWith("package ")) {
					packageInfo = lineContent;
					return lineContent;
				} else if (lineContent.startsWith("import ")) {
					imports.addLine(lineContent);
					return lineContent;
				} 
				
				imports.recognitionNewClass(lineContent);
				
				String classLinePatternStr = "^\\s*(public|private|protected)?\\s*(abstract)?\\s*(class|interface|@interface){1}\\s*(\\w*)(<(\\w*)>)?\\s*(extends ((?!implements)[^\\{])*)?\\s*(implements\\s*[^\\{]*)?\\s*\\{\\s*$";
				if (RegularUtil.match(lineContent, classLinePatternStr)) {
					String[] ss = JavaCodeUtil.getInnerString(lineContent, classLinePatternStr);
					modifier = ss[0];
					if (ss[1] != null) {
						isAbstract = true;
					}
					classType = ss[2];
					className = ss[3];
					classGenre = ss[5];
					extend = ss[6] == null? null: ss[6].substring("extends ".length());
					implementsStr = ss[7];
					
					setComment(comment);
					setAnnotations(annotations);
					reset();
				} else if (Method.isMethodEnd(lineContent)) {
					isInMethod = false;
					
					method.setComment(comment);
					method.setAnnotations(annotations);
					method.initCodeBlocks();
					reset();
				} else if (isInMethod) {
					method.addLine(lineContent);
				} else if (Comment.isCommentEnd(lineContent)) {
					inComments = false;
					comment.end(lineContent);
				} else if (inComments) {
					comment.addLine(lineContent);
				} else if (Comment.isCommentStart(lineContent)) {
					inComments = true;
					comment = new Comment(lineContent);
				} else if (Annotation.isAnnotationLine(lineContent)) {
					annotations.add(lineContent);
				} else if (Method.isMethodStart(lineContent)) {
					isInMethod = true;
					method = new Method(lineContent);
					methods.add(method);
				} else if (Method.isInterfaceMethod(lineContent)) {
					method = new Method(lineContent);
					methods.add(method);
					method.setComment(comment);
					method.setAnnotations(annotations);
					method.initCodeBlocks();
					reset();
				} else if (Attribute.isAttributeLine(lineContent)) {
					Attribute attribute = new Attribute(lineContent);
					attributes.add(attribute);
					attribute.setAnnotations(annotations);
					attribute.setComment(comment);
					reset();
				} else if (isClassEnd(lineContent)) {
					classEnd = lineContent;
				}
				
				return null;
			}
			
			private void reset() {
				annotations = new ArrayList<String>();
				comment = null;
			}
		}, true);
	}

	/**
	 * 类是否存在
	 * @return
	 */
	public boolean isExist() {
		return methods != null && methods.size() > 0;
	}
	
	/**
	 * 创建一个方法
	 * @param methodStart
	 * @return
	 */
	public Method createMethod(Method method) throws UserException{
		if (this.getMethod(method.methodName) != null) {
			throw new UserException("方法已存在");
		}
		
		this.methods.add(method);
		
		return method;
	}

	/**
	 * 根据方法名，查询一个方法
	 * @param name
	 * @return
	 */
	public Method getMethod(String name) {
		Method result = null;
		
		if (CollectionUtil.isNotEmpty(methods)) {
			for (Method method : methods) {
				if (method.methodName.equals(name)) {
					result = method;
					break;
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 创建一个属性
	 * @param name
	 * @return
	 */
	public Attribute createAttribute(String type, String name) throws UserException {
		Attribute attribute = new Attribute(type, name);
		this.createAttribute(attribute);
		return attribute;
	}
	
	public Attribute createAttribute(Attribute attribute) throws UserException {
		if (this.getAttribute(attribute.getName()) != null) {
			throw new UserException("属性已存在");
		}
		this.attributes.add(attribute);
		return attribute;
	}
	
	/**
	 * 获得一个属性
	 * @param name
	 * @return
	 */
	public Attribute getAttribute(String name) {
		Attribute result = null;
		
		if (CollectionUtil.isNotEmpty(attributes)) {
			for (Attribute attribute : attributes) {
				if (attribute.name.equals(name)) {
					result = attribute;
					break;
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 写类
	 */
	public List<String> write() {
		List<String> result = new ArrayList<String>();
		/*
		 * 头部
		 */
		result.add(packageInfo);
		result.add("");
		/*
		 * 类注释、注解、方法头部
		 */
		result.addAll(getComment());
		result.addAll(getAnnotationsString(0));
		result.add(this.getClassNameLine());
		result.add("");
		/*
		 * 所有属性
		 */
		for (int i = 0; i < attributes.size(); i++) {
			Attribute attribute = attributes.get(i);
			result.addAll(attribute.write());
		}
		if (attributes.size() > 0) {
			result.add("");
		}
		/*
		 * 所有方法
		 */
		for (int i = 0; i < methods.size(); i++) {
			Method method = methods.get(i);
			result.addAll(method.write());
			result.add("");
		}
		/*
		 * 类结尾
		 */
		result.add(classEnd);
		
		/*
		 * 更新导入，插入导入行
		 */
		for (String line : result) {
			imports.recognitionNewClass(line);
		}
		result.add(2, "");
		result.addAll(2, imports.write());
		
		TextFileUtil.writeFile(new File(this.classPath), result);
		
		return result;
	}
	
	public List<Method> getAllMethods() {
		List<JavaCoder> parents = this.getParent();
		
		List<Method> result = null;
		if (CollectionUtil.isNotEmpty(parents)) {
			result = new ArrayList<Method>();
			for (JavaCoder parent : parents) {
				result.addAll(parent.getAllMethods());
			}
			result.addAll(this.methods);
		} else {
			result = this.methods;
		}
		
		return result;
	}
	
	public List<Attribute> getAllAttributes() {
		List<JavaCoder> parents = this.getParent();
		
		List<Attribute> result = null;
		if (CollectionUtil.isNotEmpty(parents)) {
			result = new ArrayList<Attribute>();
			for (JavaCoder parent : parents) {
				result.addAll(parent.getAllAttributes());
			}
			result.addAll(this.attributes);
		} else {
			result = this.attributes;
		}
		
		return result;
	}
	
	protected String getClassNameLine() {
		StringBuffer classNameLine = new StringBuffer();
		if (!"package".equals(modifier)) {
			classNameLine.append(modifier + Constant.WORD_SPLIT);
		}
		classNameLine.append(classType + Constant.WORD_SPLIT);
		classNameLine.append(className + Constant.WORD_SPLIT);
		if (StringUtil.isNotEmpty(extend)) {
			classNameLine.append("extends " + extend + Constant.WORD_SPLIT);
		}
		if (StringUtil.isNotEmpty(implementsStr)) {
			classNameLine.append(implementsStr + Constant.WORD_SPLIT);
		}
		classNameLine.append("{");
		return classNameLine.toString();
	}

	/**
	 * 是否是类的结尾
	 * @param lineContent
	 * @return
	 */
	protected boolean isClassEnd(String lineContent) {
		boolean result = false;
		
		if (JavaCodeUtil.lineStartTabCount(lineContent) == 0 && lineContent.equals("}")) {
			result = true; 
		}
		
		return result;
	}
	
	protected List<JavaCoder> getParent() {
		List<JavaCoder> result = new ArrayList<JavaCoder>();
		
		if (StringUtil.isNotEmpty(this.getParentName())) {
			String[] parentNames = this.getParentName().split(",");
			for (String parentName : parentNames) {
				if (StringUtil.isNotEmpty(parentName)) {
					result.add(allJavaCoders.get(parentName));
				}
			}
		}
		
		return result;
	}
	
	public String getParentName() {
		StringBuffer result = new StringBuffer();
		if (StringUtil.isNotEmpty(extend)) {
			String parentName = extend.trim();
			String[] ss = JavaCodeUtil.getInnerString(parentName, "(\\w+)(?:<(\\w*)>)?");
			if (ss.length > 0) {
				for (int i = 0; i < ss.length; i++) {
					String s = ss[i];
					if (i % 2 == 0) {
						if (result.length() != 0) {
							result.append(",");
						}
						result.append(s);
					}
				}
			}
		}

		return result.toString();
	}

	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	public String getPackageInfo() {
		return packageInfo;
	}

	public void setPackageInfo(String packageInfo) {
		this.packageInfo = packageInfo;
	}

	public Imports getImports() {
		return imports;
	}

	public void setImports(Imports imports) {
		this.imports = imports;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public List<Method> getMethods() {
		return methods;
	}

	public void setMethods(List<Method> methods) {
		this.methods = methods;
	}

	public String getClassEnd() {
		return classEnd;
	}

	public void setClassEnd(String classEnd) {
		this.classEnd = classEnd;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public String getImplementsStr() {
		return implementsStr;
	}

	public void setImplementsStr(String implementsStr) {
		this.implementsStr = implementsStr;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public String getClassGenre() {
		return classGenre;
	}

	public void setClassGenre(String classGenre) {
		this.classGenre = classGenre;
	}
	
}


