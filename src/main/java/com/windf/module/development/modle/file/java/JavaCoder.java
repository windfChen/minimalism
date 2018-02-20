package com.windf.module.development.modle.file.java;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
		/*
		 * 读取java文件
		 */
		File javaFile = new File(this.classPath);
		/*
		 * 判断文件是否存在
		 */
		if (javaFile.exists()) {	// 如果存在，读取文件内容
			readCodes(javaFile);
		} else {	// 如果不存在，默认类的属性内容
			/*
			 * 设置包的信息
			 */
			String newPackagePath = packagePath.replace("/", ".");
			if (newPackagePath.startsWith(".")) {
				newPackagePath = newPackagePath.substring(1);
			}
			this.packageInfo = "package" + Constant.WORD_SPLIT + newPackagePath + ";";
			/*
			 * 设置className
			 */
			this.className = className;
			/*
			 * 设置访问修饰符
			 */
			this.modifier = Constant.MODIFY_PUBLIC;
			/*
			 * 默认类型为Class
			 */
			this.classType = Constant.TYPE_CLASS;
			/*
			 * 默认方法结尾标志
			 */
			this.classEnd = "}";
			/*
			 * 写入文件中
			 */
			this.write();
		}
		
		/*
		 * 统一存储
		 */
		allJavaCoders.put(className, this);
	}

	public void readCodes(File javaFile) {
		
		TextFileUtil.readLine(javaFile, new LineReader() {
			
			List<String> annotations = new ArrayList<String>();
			boolean isInMethod = false;
			Method method = null;
			boolean inComments = false;
			Comment comment = null;
			
			@Override
			public String readLine(List<String> oldLines, String lineContent, int lineNo) {
				/*
				 * 跳过空白行
				 */
				if (lineContent.trim().length() == 0) {
					return lineContent;
				}
				/*
				 *  统一制表符
				 */
				lineContent = lineContent.replace("\t", Constant.TAB);
				/*
				 * 处理package和import信息
				 */
				if (lineContent.startsWith("package ")) {
					packageInfo = lineContent;
					return lineContent;
				} else if (lineContent.startsWith("import ")) {
					imports.addLine(lineContent);
					return lineContent;
				} 
				/*
				 * 发现未导入的类
				 */
				imports.recognitionNewClass(lineContent);
				/*
				 * 解析类的正文
				 */
				String classLinePatternStr = "^\\s*(public|private|protected)?\\s*(abstract)?\\s*(class|interface|@interface){1}\\s*(\\w*)(<(\\w*)>)?\\s*(extends ((?!implements)[^\\{])*)?\\s*(implements\\s*[^\\{]*)?\\s*\\{\\s*$";
				if (RegularUtil.match(lineContent, classLinePatternStr)) {
					/*
					 * 处理类的头部
					 */
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
					/*
					 * 设置并且重置注解和注释
					 */
					setComment(comment);
					setAnnotations(annotations);
					reset();
				} else if (Method.isMethodEnd(lineContent)) {
					/*
					 * 方法结束处理
					 */
					isInMethod = false;
					/*
					 * 处理方法内部信息
					 */
					method.initCodeBlocks();
					/*
					 * 设置并且重置注解和注释
					 */
					method.setComment(comment);
					method.setAnnotations(annotations);
					reset();
				} else if (isInMethod) {
					/*
					 * 如果是方法中，全部放到方法代码里面
					 */
					method.addLine(lineContent);
				} else if (Comment.isCommentEnd(lineContent)) {
					/*
					 * 注释结尾处理
					 */
					inComments = false;
					comment.end(lineContent);
				} else if (inComments) {
					/*
					 * 注释内容处理
					 */
					comment.addLine(lineContent);
				} else if (Comment.isCommentStart(lineContent)) {
					/*
					 * 注释开始处理
					 */
					inComments = true;
					comment = new Comment(lineContent);
				} else if (Annotation.isAnnotationLine(lineContent)) {
					/*
					 * 注解处理
					 */
					annotations.add(lineContent);
				} else if (Method.isMethodStart(lineContent)) {
					/*
					 * 方法开始处理
					 */
					isInMethod = true;
					/*
					 * 创建新的方法
					 */
					method = new Method(lineContent);
					/*
					 * 添加到方法列表中
					 */
					methods.add(method);
				} else if (Method.isInterfaceMethod(lineContent)) {
					/*
					 * abstract方法识别
					 * 创建新的方法
					 */
					method = new Method(lineContent);
					/*
					 * 添加到方法列表
					 */
					methods.add(method);
					/*
					 * 初始化方法内部信息
					 */
					method.initCodeBlocks();
					/*
					 * 设置并重置注解和注释
					 */
					method.setComment(comment);
					method.setAnnotations(annotations);
					reset();
				} else if (Attribute.isAttributeLine(lineContent)) {
					/*
					 * 属性处理
					 * 创建新的属性
					 */
					Attribute attribute = new Attribute(lineContent);
					/*
					 * 添加到属性列表
					 */
					attributes.add(attribute);
					/*
					 * 设置并重置注解和注释
					 */
					attribute.setAnnotations(annotations);
					attribute.setComment(comment);
					reset();
				} else if (isClassEndLine(lineContent)) {
					/*
					 * 方法结尾处理
					 */
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
	 * 把类写到文件中
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
		/*
		 * 输出文件
		 */
		TextFileUtil.writeFile(new File(this.classPath), result);
		/*
		 * 返回类的代码
		 */
		return result;
	}
	
	public void setInterfaceType() {
		this.classType = Constant.TYPE_INTERFACE;
	}
	
	/**
	 * 创建一个方法
	 * @param methodStart
	 * @return
	 */
	public Method createMethod(Method method) throws UserException{
		/*
		 * 移除之前已存在的方法
		 */
		if (CollectionUtil.isNotEmpty(methods)) {
			Iterator<Method> iterator = methods.iterator();
			while (iterator.hasNext()) {
				Method m = (Method) iterator.next();
				if (m.methodName.equals(method.getMethodName())) {
					iterator.remove();
					break;
				}
			}
		}
		/*
		 * 添加方法
		 */
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
	 * 获得所有方法，包括父类
	 * @return
	 */
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
	 * 获得所有属性，包括父类
	 * @return
	 */
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
	
	private static boolean isClassEndLine(String lineContent) {
		boolean result = false;
		
		if (JavaCodeUtil.lineStartTabCount(lineContent) == 0 && lineContent.equals("}")) {
			result = true; 
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
	
	protected String getParentName() {
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


