package com.windf.module.development.modle.file.java;

import java.util.ArrayList;
import java.util.List;

import com.windf.core.util.CollectionUtil;
import com.windf.module.development.util.JavaCodeUtil;

public class Comment {
	
	/**
	 * 判断是否是注释开始标志
	 * @param lineContent
	 * @return
	 */
	static boolean isCommentStart(String lineContent) {
		return lineContent.trim().startsWith("/*");
	}
	
	/**
	 * 判断是否是注释结束标志
	 */
	static boolean isCommentEnd(String lineContent) {
		return lineContent.trim().equals("*/");
	}
	
	String commentStrat;
	List<String> comment = new ArrayList<String>();
	String commentEnd;
	
	private int tabCount = 0;
	
	public Comment(int tabCount, boolean document) {
		this.tabCount = tabCount;
		this.commentStrat = JavaCodeUtil.getTabString(tabCount) + (document? "/**": "/*");
		this.commentEnd = JavaCodeUtil.getTabString(tabCount) + " */";
	}
	
	Comment (String commentStrat) {
		this.commentStrat = commentStrat;
	}
	
	void end(String commentEnd) {
		this.commentEnd = commentEnd;
	}
	
	
	/**
	 * 添加一行注释
	 * @param lineContent
	 */
	public void addLine(String lineContent) {
		comment.add( (tabCount > 0?JavaCodeUtil.getTabString(tabCount) + " * ": "") + lineContent);
	}

	/**
	 * 获得注释的代码形式
	 * 如果注释为空，返回长度为0的list
	 * @return
	 */
	List<String> write() {
		List<String> result = new ArrayList<String>();
		
		if (CollectionUtil.isNotEmpty(comment)) {
			result.add(commentStrat);
			result.addAll(comment);
			result.add(commentEnd);
		}
		
		return result;
	}
	
}
