package com.windf.module.development.util.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.windf.core.Constant;

public class TextFileUtil {
	/**
	 * 按行读取每个内容
	 * @param file
	 * @param lineReader
	 */
	public static void readLine(File file, LineReader lineReader) {
		readLine(file, lineReader, false);
	}
	/**
	 * 按行读取每个内容
	 * @param file
	 * @param lineReader
	 */
	public static void readLine(File file, LineReader lineReader, boolean readonly) {
		BufferedReader reader = null;
		String lineContent = null;
		List<String> oldLines = null;
		try {
			/*
			 * 读取文件
			 */
			oldLines = new ArrayList<String>();
			FileInputStream fileInputStream = new FileInputStream(file);  
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, Constant.DEFAULT_ENCODING);
			reader = new BufferedReader(inputStreamReader);
			
			int line = 1;
			while ((lineContent = reader.readLine()) != null) {
				String newlineContent = lineReader.readLine(oldLines, lineContent, line);
				oldLines.add(newlineContent);
				line++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}

		/*
		 * 重新写入文件
		 */
		if (!readonly && oldLines != null) {
			writeFile(file, oldLines);
		}
		
	}
	
	public static List<String> readFile(File file) {
		List<String> result = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(file);  
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, Constant.DEFAULT_ENCODING);
			reader = new BufferedReader(inputStreamReader);
			
			String lineContent = null;
			while ((lineContent = reader.readLine()) != null) {
				result.add(lineContent);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return result;
	}
	
	/**
	 * 将内容写到指定的文件中
	 * @param file
	 * @param lines
	 */
	public static void writeFile(File file, List<String> lines) { 
		BufferedWriter writer = null;
		try {
			String lineContent = null;
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			FileOutputStream fileOutputStream = new FileOutputStream(file);   
		    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, Constant.DEFAULT_ENCODING); 
			writer = new BufferedWriter(outputStreamWriter);
			for (int i = 0; i < lines.size(); i++) {
				lineContent = lines.get(i);
				writer.write(lineContent);
				writer.newLine();
			}
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e1) {
				}
			}
		}
	}
}
