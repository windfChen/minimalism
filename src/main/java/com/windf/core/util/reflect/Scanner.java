package com.windf.core.util.reflect;

import java.io.File;

import com.windf.core.util.file.FileUtil;

public class Scanner {

	private String startPath;
	private ScannerHandler handler;
	
	public Scanner(String startPath, ScannerHandler handler) {
		this.startPath = startPath;
		this.handler = handler;
	}

	public void run() {
		/*
		 * 创建文件
		 */
		File file = FileUtil.getFile(startPath, true);
		
		/*
		 *  递归目录
		 */
		if (file.exists()) {
			this.filePathIterator(file);
		}
	}
	
	/**
	 * 遍历目录
	 * 
	 * @param file
	 */
	private void filePathIterator(File file) {

		if (file.isDirectory()) { // 如果是目录继续遍历
			File[] subFiles = file.listFiles();
			for (int i = 0; i < subFiles.length; i++) {
				filePathIterator(subFiles[i]);
			}
		} else {
			handler.handle(file);
		}

	}
	
}
