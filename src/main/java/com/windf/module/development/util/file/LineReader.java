package com.windf.module.development.util.file;

import java.util.List;

public interface LineReader {
	/**
	 * 读取每一行
	 * @param oldLines
	 * @param lineContent
	 * @param lineNo
	 * @return 修改后的内容
	 */
	public String readLine(List<String> oldLines, String lineContent, int lineNo);
}