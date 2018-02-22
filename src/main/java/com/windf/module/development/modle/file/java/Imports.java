package com.windf.module.development.modle.file.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.windf.core.util.CollectionUtil;
import com.windf.core.util.PropertiesUtil;
import com.windf.core.util.RegularUtil;
import com.windf.core.util.StringUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.util.PackageUtil;

public class Imports {
	
	private static Map<String, String> nameIdMap = new HashMap<String, String>();
	static {
		/*
		 * 获取排除目录
		 */
		String excludePackageStrs = PropertiesUtil.getConfig(Imports.class, "import.packages.exclude");
		String[] excludePackage = excludePackageStrs.split(",");
		/*
		 * 从配置文件中读取导入配置，定义包导入的优先级
		 */
		String importPackages = PropertiesUtil.getConfig(Imports.class, "import.packages");
		String[] defaultImportClasses = importPackages.split(",");
		defaultImportClasses = CollectionUtil.reversal(defaultImportClasses);
		for (int i = 0; i < defaultImportClasses.length; i++) {
			/*
			 * 读取包下面的所有内容
			 */
			List<String> classLines = PackageUtil.getClasses(defaultImportClasses[i]);
			/*
			 * 组织类名和类全路径的映射关系
			 */
			for (int j = 0; j < classLines.size(); j++) {
				String classLine = classLines.get(j);
				/*
				 * 是否是排除目录
				 */
				boolean isExclude = false;
				for (int k = 0; k < excludePackage.length; k++) {
					if (classLine.startsWith(excludePackage[k])) {
						isExclude = true;
						break;
					}
				}
				/*
				 * 如果是排除目录，跳过
				 */
				if (isExclude) {
					continue;
				}
				/*
				 * 添加到nameIdMap中
				 */
				int lastPoint = classLine.lastIndexOf(".") + 1;
				String className = classLine.substring(lastPoint);
				nameIdMap.put(className, classLine);
			}
		}
	}
	
	public static void addImprot(JavaCoder javaCoder) {
		nameIdMap.put(javaCoder.getClassName(), javaCoder.getFullClassName());
	}
	
	private String pacakgeInfo = "";
	private List<String> classLineList = new ArrayList<String>();
	private Map<String, String> classLineMap = new HashMap<String, String>();
	
	public Imports() {
		
	}
	
	public Imports(String pacakgeInfo) {
		this.pacakgeInfo = pacakgeInfo;
	}
	
	/**
	 * 从类的行中获取未导入的类
	 * @param line
	 */
	public void recognitionNewClass(String line) {
		if (StringUtil.isNotEmpty(line)) {
			line = line.replaceAll("\"[^\"]*\"", "#string#");
			int coumentIndex = line.indexOf("//");
			if (coumentIndex > -1) {
				line = line.substring(0, coumentIndex);
			}
			String[] ss = line.split("\\W+");
			for (int i = 0; i < ss.length; i++) {
				String s = ss[i];
				if (StringUtil.isEmpty(s)) {
					continue;
				}
				
				if (RegularUtil.match(s, "@?[A-Z]\\w+")) {
					String fullClassName = nameIdMap.get(s);
					if (StringUtil.isNotEmpty(fullClassName)) {
						String packageName = fullClassName.substring(0, fullClassName.lastIndexOf("."));
						if (!"java.lang".equals(packageName) && !pacakgeInfo.equals(packageName)) {
							this.addImport(s, fullClassName);
						}
					} else {
						// TODO 记录未导入的类
					}
				}
			}
		}
	}
	
	/**
	 * 添加一个新的导入代码
	 * 不验证是否重复
	 * @param lineContent
	 */
	public void addLine(String lineContent) {
		lineContent = lineContent.trim();
		String fullClassName = lineContent.substring(("improt" + Constant.WORD_SPLIT).length(), lineContent.length() - 1);
		String className = lineContent.substring(lineContent.lastIndexOf(".") + 1, lineContent.length() - 1);
		addImport(className, fullClassName);
	}
	
	/**
	 * 添加import对象
	 * 如果没有创建，如果有不做任何操作
	 * @param className
	 * @param fullClassName
	 */
	public void addImport(String className, String fullClassName) {
		if (classLineMap.get(className) == null) {
			classLineList.add(fullClassName);
			classLineMap.put(className, fullClassName);
		}
	}
	
	/**
	 * 将import转换为代码输出
	 * 每行代码为list中的一项
	 * @return
	 */
	public List<String> write() {
		/*
		 * 排序
		 */
		List<String> newLineList = sort();
		/*
		 * 格式化输出
		 */
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < newLineList.size(); i++) {
			String line = newLineList.get(i);
			/*
			 * 加入improt关键字
			 */
			result.add(writeImportLine(line));
		}
		return result;
	}
	
	/**
	 * 按照特定的规则，对improt的类的顺序进行排序
	 * 不进行去重复
	 * 不同包之间用，空行分开
	 */
	List<String> sort() {
		/*
		 * 根据包名排序
		 */
		Collections.sort(classLineList, new Comparator<String>() {
	          @Override
	          public int compare(String s1, String s2) {
				String s1Org = getImportOrg(s1);
				String s2Org = getImportOrg(s2);
				
				if (s1Org.equals(s2Org)) {
					return s1.compareTo(s2);
				} else {
					int s1Weight = getImportOrgWeight(s1Org);
					int s2Weight = getImportOrgWeight(s2Org);
					return s2Weight - s1Weight;
				}
	          }

	     });
		/*
		 * 添加空行
		 */
		List<String> newLines = new ArrayList<String>();
		String frontLine = null;
		for (String line : classLineList) {
			/*
			 * 切换包类别的时候，添加空行
			 */
			if (frontLine != null && !getImportOrg(frontLine).equals(getImportOrg(line))) {
				newLines.add("");
			}
			/*
			 * 添加包
			 */
			newLines.add(line);
			/*
			 * 记录上行内容，用于判断下次是否切换了包类别
			 */
			frontLine = line;
		}
		
		return newLines;
	}
	
	protected String writeImportLine(String classFullName) {
		if (StringUtil.isEmpty(classFullName)) {
			return "";
		} else {
			return "import" + Constant.WORD_SPLIT + classFullName + ";";
		}
	}
	
  	/**
  	 * 获得import中包名的机构名
  	 * @param str
  	 * @return
  	 */
  	private String getImportOrg(String str) {
  		return str.substring(0, str.indexOf("."));
  	}
  	
  	/**
  	 * 获得import的权重
  	 * @param org
  	 * @return
  	 */
  	private int getImportOrgWeight(String org) {
  		int result = 0;
  		
  		if ("java".equals(org)) {
  			result = 10;
  		} else if ("javax".equals(org)) {
  			result = 9;
  		} else if ("org".equals(org)) {
  			result = 5;
  		} else if ("com".equals(org)) {
  			result = 3;
  		}
  		
  		return result;
  	}
}
