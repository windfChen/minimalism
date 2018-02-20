package com.windf.module.development.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.windf.module.development.Constant;

/**
 * package相关的工具类
 * @author chenyafeng
 *
 */
public class PackageUtil {
	/**
	 * 从jarJar和用户导入jar和用户class中分别查找指定packageName下所有class（递归）
	 * @param packageName
	 * @return
	 */
	public static List<String> getClasses(String packageName) {
		List<String> result  = new ArrayList<String>();
		result.addAll(getClassesFromBaseJar(packageName));
		result.addAll(getClassesFromUserResources(packageName));
		return result;
	}
	
	/**
	 * 从jreJar中获得类
	 * @param packageName
	 * @return
	 */
	public static List<String> getClassesFromBaseJar(String packageName) {
        List<String> result = new ArrayList<String>();
        /*
         * 把package变成目录的形式，方便遍历
         */
        String packageDirName = packageName.replace('.', '/');
        /*
         * 遍历%JAVA_HOME%/lib目录
         */
		File javaHomeLibDir = new File(System.getProperty("java.home") + File.separator + "lib");
		if (javaHomeLibDir.exists()) {
			File[] subFiles = javaHomeLibDir.listFiles();
			for (int i = 0; i < subFiles.length; i++) {
				try {
					File file = subFiles[i];
					/*
					 * 遍历所有jar文件 
					 */
					if (file.getName().endsWith(".jar")) {
				        JarFile jar = new JarFile(file);  
				        result.addAll(getClassesFromJar(packageDirName, jar));
					}
				} catch (IOException e) {
					
				}
			}
		}
    
		return result;
	}
    
    /**
     * 从包package中获取所有的Class
     * @param pack
     * @return
     */
    public static List<String> getClassesFromUserResources(String packageName){
        List<String> classes = new ArrayList<String>();
        String packageDirName = packageName.replace('.', '/');
        try {
        	Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()){
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();	//得到协议的名称
                if ("file".equals(protocol)) { //如果是以文件的形式保存在服务器上
                    String filePath = URLDecoder.decode(url.getFile(), Constant.DEFAULT_ENCODING);
                    findAndAddClassesInPackageByFile(packageName, filePath, true, classes);
                } else if ("jar".equals(protocol)){
                    try {
                    	JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        classes.addAll(getClassesFromJar(packageDirName, jar));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       
        return classes;
    }
    
    /**
     * 以文件的形式来获取包下的所有Class
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<String> classes){
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        /*
         * 获取所有目录和class文件
         */
        File[] dirfiles = dir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
			}
        });
        for (File file : dirfiles) {
            if (file.isDirectory()) {
            	// 递归扫描
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
                                      file.getAbsolutePath(),
                                      recursive,
                                      classes);
            } else {
            	// 添加类
                String className = file.getName().substring(0, file.getName().length() - 6);
                classes.add(packageName + '.' + className);
            }
        }
    }
	
	/**
	 * 遍历jar，寻找指定包下的所有类路径
	 * @param packageDirName
	 * @param jar
	 * @return
	 */
	public static List<String> getClassesFromJar(String packageDirName, JarFile jar) {
        List<String> result = new ArrayList<String>();
        
        /*
         * 遍历jar
         */
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            if (name.charAt(0) == '/') {
                name = name.substring(1);
            }
            if (name.startsWith(packageDirName)) {
                int idx = name.lastIndexOf('/');
                if (idx != -1){
                    if (name.endsWith(".class") && !entry.isDirectory()) {
                        result.add(name.substring(0, name.length() - ".class".length()).replace("/", "."));
                      }
                }
            }
        }
	
        return result;
	}
}
