package com.windf.core.util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.windf.core.exception.CodeException;

public class FileUtil {
	private static String classPath = null;
	
	/**
	 * 获取类文件路径 e: 项目路径/WEB-INF/classes
	 * 
	 * @return
	 */
	public static String getClassPath() {
		if (classPath == null) {
			classPath = new File(FileUtil.class.getClassLoader().getResource("").getPath()).getPath();
		}
		return classPath;
	}

	/**
	 * 获取配置文件路径 e: 项目路径/WEB-INF/classes/config
	 * 
	 * @return 配置文件路径
	 */
	public static String getConfigPath() {
		String classPath = getClassPath();
		String configPath = classPath + File.separator + "config";
		return configPath;
	}

	/**
	 * 获取项目的部署路径
	 */
	public static String getWebappPath() {
		String classPath = getClassPath();
		return classPath.substring(0, classPath.lastIndexOf("WEB-INF"));
	}
	
	/**
	 * 获得WEB-INF文件位置
	 * @return
	 */
	public static String getWEBINFPath() {
		return getWebappPath() + File.separator + "WEB-INF";
	}
	
	/**
	 * 获得文件的真实路径
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileRealPath(String filePath) {
		String result = filePath;
		
		String realPath = getWebappPath();
		if (!filePath.startsWith(realPath)) {
			result = realPath + filePath;
		}
		
		return result;
	}
	
	/**
	 * 获得文件,根据是否已webweb路径开头，判断是否是相对路径
	 * @param filePath
	 * @return
	 */
	public static File getFile(String filePath) {
		return getFile(filePath, false);
	}
	
	/**
	 * 获得文件
	 * @param filePath
	 * @return
	 */
	public static File getFile(String filePath, boolean isRealPath) {
		String realPath = filePath;
		if (!isRealPath) {
			realPath = FileUtil.getFileRealPath(filePath);
		}
		File file = new File(realPath);
		return file;
	}

	/**
	 * 复制web目录下的文件夹
	 * 
	 * @param source
	 * @param target
	 */
	public static List<File> copyWebFolder(String source, String target) {
		return copyFolder(getWebappPath() + source, getWebappPath() + target);
	}

	/**
	 * 复制文件夹
	 * 
	 * 如果目标目录不存在创建 复制文件、递归复制文件夹
	 * 
	 * @param source
	 *            源目录
	 * @param target
	 *            目标目录
	 */
	public static List<File> copyFolder(String source, String target) {

		List<File> list = new LinkedList<File>();

		/*
		 * 获取目标目录，如果目标目录不存在创建
		 */
		File targetFolder = new File(target);
		if (!targetFolder.exists()) {
			targetFolder.mkdirs();
		}

		/*
		 * 遍历源目录，复制文件、递归复制文件夹
		 */
		File sourceFolder = new File(source);
		File[] sourceFiles = sourceFolder.listFiles();
		if (sourceFiles != null) {
			for (File file : sourceFiles) {
				// 不遍历隐藏文件
				if (file.getName().startsWith(".")) {
					continue;
				}

				if (file.isFile()) {
					String targetFilePath = target + File.separator + file.getName();
					boolean success = copyFile(file.getPath(), targetFilePath);

					/*
					 * 添加复制的文件到文件列表
					 */
					if (success) {
						File targetFile = new File(targetFilePath);
						list.add(targetFile);
					}
				} else if (file.isDirectory()) {
					String targetDirectoryPath = target + File.separator + file.getName();
					List<File> tempList = copyFolder(file.getPath(), targetDirectoryPath);

					/*
					 * 添加复制的文件夹到文件列表
					 */
					if (tempList != null && tempList.size() > 0) {
						File targetDirectory = new File(targetDirectoryPath);
						list.add(targetDirectory);
					}

					/*
					 * 添加复制的所有文件到文件列表
					 */
					list.addAll(tempList);

				}
			}
		}

		return list;

	}

	/**
	 * 复制文件
	 * 
	 * @param source
	 * @param target
	 */
	public static boolean copyFile(String source, String target) {
		boolean result = false;

		/*
		 * 复制文件
		 */
		BufferedReader bufferedReader = null;
		PrintStream printStream = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(source)));
			printStream = new PrintStream(new FileOutputStream(target));
			String s = null;
			while ((s = bufferedReader.readLine()) != null) {
				printStream.println(s);
				printStream.flush();
			}
			result = true;
		} catch (IOException e) {
			throw new CodeException(e);
		} finally {
			try {
				if (bufferedReader != null)
					bufferedReader.close();
				if (printStream != null)
					printStream.close();
			} catch (IOException e) {
				throw new CodeException(e);
			}

		}

		return result;

	}

	/**
	 * 给文件重命名
	 * 
	 * @param file
	 * @param newName
	 * @return
	 */
	public static void rename(File file, String newName) {
		String directory = file.getParentFile().getAbsolutePath();
		file.renameTo(new File(directory + File.separator + newName));
	}
	
	/**
	 * 获取路径中的后缀名
	 * @param fileName
	 * @return
	 */
	public static String getSimpleName(String fileName) {
		// 是否是文件全名
		int lastSeparator = fileName.indexOf(File.separator);
		if (lastSeparator > -1) {
			fileName = fileName.substring(lastSeparator);
		}
		
		// 去掉后缀名
		int lastPoint = fileName.lastIndexOf(".");
		if (lastPoint > -1) {
			fileName = fileName.substring(0, lastPoint);
		}
		return fileName;
	}
	
	/**
	 * 获取路径中的后缀名
	 * @param fileName
	 * @return
	 */
	public static String getPrefix(String fileName) {
		int lastPointIndex = fileName.lastIndexOf(".");
		String prefix = null;
		if (lastPointIndex > 0) {
			prefix = fileName.substring(fileName.lastIndexOf(".")+1);
		}
		return prefix;
	}
	
	/**
	 * 解压缩
	 * @param zipFile
	 * @param unzipDir
	 * @return
	 * @throws Exception
	 */
	public static String unzip(File zipFile, String unzipDir) throws Exception {
		ZipEntry entry = null;
		ZipFile zipfile = new ZipFile(zipFile);
		if(StringUtils.isBlank(unzipDir)){
			String tmp = zipFile.getPath();
			unzipDir = tmp.substring(0, tmp.lastIndexOf(".") );
		}
		File newFile = new File(unzipDir);
		if (!newFile.exists()) {
			newFile.mkdirs();
		}
		@SuppressWarnings("unchecked")
		Enumeration<ZipEntry> ea = zipfile.getEntries();
		byte b[] = new byte[1024];
		int length;
		while (ea.hasMoreElements()) {
			entry = (ZipEntry) ea.nextElement();
			String entryName = entry.getName();
			if (!entry.isDirectory()) {
				File newefile = new File( unzipDir + "/" + entryName);
				if (!newefile.getParentFile().exists()){
					newefile.getParentFile().mkdirs();
				}
				OutputStream outputStream = new FileOutputStream(newefile);
				InputStream inputStream = zipfile.getInputStream(entry);
				while ((length = inputStream.read(b)) > 0) {
					outputStream.write(b, 0, length);
				}
				outputStream.close();
				inputStream.close();
			}else{
				File newefile = new File( unzipDir + "/" + entryName);
				if (!newefile.exists()) {
					newefile.mkdirs();
				}
			}
		}
		zipfile.close();
		return unzipDir;
	}

	/**
	 * 如果文件不存在，创建
	 * @param file
	 * @param onlyPath	是否只创建目录
	 */
	public static File createIfNotExists(File file, boolean onlyPath) {
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			if (!onlyPath) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					throw new CodeException(e);
				}
			}
		}
		
		return file;
	}

}
