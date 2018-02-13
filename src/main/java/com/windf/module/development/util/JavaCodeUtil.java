package com.windf.module.development.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.windf.core.exception.CodeException;
import com.windf.core.util.SQLUtil;
import com.windf.core.util.StringUtil;

public class JavaCodeUtil extends StringUtil {
	/**
	 * 获取开始时空格的数量
	 * @param lineContent
	 * @return
	 */
	public static int lineStartTabCount(String lineContent) {
		int count = 0;
		lineContent = lineContent.replace("\t", "    ");
		while (lineContent.startsWith("    ")) {	// 4个空格开始
			count ++;
			lineContent = lineContent.substring(4);
		}
		return count;
	}
	
	/**
	 * 设置开始时空格的数量
	 * @param lineContent
	 * @return
	 */
	public static String getTabString(int count) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < count; i++) {
			result.append("    ");
		}
		return result.toString();
	}
	
	/**
	 * 获得一句话中指定的部分
	 * @eg：line = if (!RegularUtil.match(ageStr, RegularUtil.NUMBER)) {
	 * 	    template = if (!RegularUtil.match(*, *)) {	
	 *         reutrn ：["ageStr", "RegularUtil.NUMBER"]
	 * @param line
	 * @return
	 */
	public static String[] getInnerString(String line, String patternStr) {
		List<String> result = new ArrayList<String>();
		
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(line);
		while(matcher.find()) {
			for (int i = 0; i < matcher.groupCount(); i++) {
				result.add(matcher.group(i + 1));
			}
		}
		
		return result.toArray(new String[result.size()]);
	}
	
	/**
	 * 获得字符串内容中的字符串
	 * eg: "hello world" --> hello world
	 * @param str
	 * @return
	 */
	public static String getStringContent(String str) {
		String result = null;
		if (str.startsWith("\"") && str.endsWith("\"")) {
			result = str.substring(1, str.length() - 1);
		}
		return result;
	}
	
	/**
	 * 合并泛型内容
	 * @param ss
	 * @return
	 */
	public static String[] merginGeneric(String[] ss) {
		return mergin(ss, "<", ">");
	}
	
	/**
	 * 合并括号和引号，默认不自动填充
	 * @param ss
	 * @return
	 */
	public static String[] mergin(String[] ss) {
		return mergin(ss, false);
	}
	
	/**
	 * 合并括号和引号
	 * @param ss
	 * @param filling
	 * @return
	 */
	public static String[] mergin(String[] ss, boolean filling) {
		String[] result = ss;
		result = mergin(ss, "<", ">", filling);
		result = mergin(result, "[", "]", filling);
		result = mergin(result, "(", ")", filling);
		result = mergin(result, "'", "'", filling);
		result = mergin(result, "\"", "\"", filling);
		return result;
	}
	
	/**
	 * 合并内容，默认不自动填充
	 * @param ss
	 * @param leftFlag
	 * @param rightFlag
	 * @return
	 */
	public static String[] mergin(String[] ss, String leftFlag, String rightFlag) {
		return mergin(ss, leftFlag, rightFlag, false);
	}
	
	/**
	 * 合并内容
	 * @param ss
	 * @param leftFlag
	 * @param rightFlag
	 * @param filling
	 * @return
	 */
	public static String[] mergin(String[] ss, String leftFlag, String rightFlag, boolean filling) {
		List<String> list = new ArrayList<String>();

		int leftCount = 0;
		int merginCount = 0;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ss.length; i++) {
			String s = ss[i];
			
			if (s != null && (s.contains(rightFlag) || s.contains(leftFlag))) {

				int left = containsCount(s, leftFlag);
				int right = containsCount(s, rightFlag);
				leftCount = left + leftCount - right;

				if (leftCount > 0) {
					sb.append(s);
					/*
					 * 记录合并元素的数量
					 */
					if (filling) {
						merginCount++;
					}
				} else if (leftCount == 0) {
					sb.append(s);
					list.add(sb.toString());
					sb.setLength(0);
					/*
					 * 用null补全被合并元素
					 */
					if (filling) {
						for (int j = 0; j < merginCount; j++) {
							list.add(null);
						}
						merginCount = 0;
					}
				} else {
					throw new CodeException("标签无法闭合：" + SQLUtil.sqls2OneLine(ss));
				}
			} else if (sb.length() > 0) {
				sb.append(s);
				/*
				 * 记录合并元素的数量
				 */
				if (filling) {
					merginCount++;
				}
			} else {
				list.add(s);
			}
		}

		String[] result = new String[list.size()];
		list.toArray(result);
		return result;
	}
	
	/**
	 * 合并标签
	 * 切割sql后，因为sql中经常有一些标签，是在后面数组的，这时候需要把这些标签移动到下一个数组
	 * eg：
	 * 输入：【 a.id,a.announcetitle announcetitle】【announcement a left join form on form.`id`=a.`fk_form_id`   】【null】【null】【a.status,a.create_time desc    <if test="param2 != null and param3 != null">   】【#{param2}, #{param3}    </if> 】
	 * 输出：【 a.id,a.announcetitle announcetitle】【announcement a left join form on form.`id`=a.`fk_form_id`   】【null】【null】【a.status,a.create_time desc    】【<if test="param2 != null and param3 != null">   #{param2}, #{param3}    </if> 】
	 * @param ss
	 * @return
	 */
	public static String[] merginTag(String[] ss) {
		for (int i = 0; i < ss.length; i++) {
			String s = ss[i];
			
			if (s != null && s.contains("<")) {
				/*
				 * 遍历所有标签，如果是开始标签入栈该标签对应索引列表，如果是结束标签出栈该标签对应索引列表
				 */
				Map<String, Stack<Integer>> tagCountMap = new HashMap<String, Stack<Integer>>();
				Pattern pattern = Pattern.compile("<(/)?(\\w+)[^>]*>");
			    Matcher matcher = pattern.matcher(s);
			    while(matcher.find()) {
			    	boolean endTag = false;
					if (matcher.group(1) != null) {
						endTag = true;
					}
					String tag = matcher.group(2);
					
					Stack<Integer> indexStack = tagCountMap.get(tag);
					if (indexStack == null) {
						indexStack = new Stack<Integer>();
						tagCountMap.put(tag, indexStack);
					}
					
					if (endTag) {
						if (!indexStack.isEmpty()) {
							indexStack.pop();
						}
					} else {
						indexStack.push(matcher.start());
					}
				}
			    /*
			     * 遍历所有标签，获得所有栈中最小的index，从此开始属于下一个数组
			     */
			    int minIndex = s.length();
			    Iterator<String> keyIterator = tagCountMap.keySet().iterator();
			    while (keyIterator.hasNext()) {
					String key = (String) keyIterator.next();
					Stack<Integer> value = tagCountMap.get(key);
					if (!value.isEmpty()) {
						Integer index = value.get(0);
						if (index < minIndex) {
							minIndex = index;
						}
					}
				}
			    /*
			     * 把minIndex后面的字符串，防到下一个非空数组的开头
			     */
			    if (minIndex != s.length() && i < ss.length) {
			    	/*
			    	 * 寻找下一个非空数组
			    	 */
			    	int nextIndex = -1;
			    	for (int j = i + 1; j < ss.length; j++) {
						if (ss[j] != null) {
							nextIndex = j;
							break;
						}
					}
			    	/*
			    	 * 移动字符串
			    	 */
			    	if (nextIndex > -1) {
			    		ss[i] = s.substring(0, minIndex);
			    		ss[nextIndex] = s.substring(minIndex) + ss[nextIndex];
					}
				}
			}
				
		}
		return ss;
	}
	
	/**
	 * 将str封装到List中
	 * @param str
	 * @return
	 */
	public static List<String> string2List(String str) {
		List<String> result = new ArrayList<String>();
		result.add(str);
		return result;
	}
	
	/**
	 * 计算字符串中，包含指定字符串的个数
	 * @param str
	 * @param c
	 * @return
	 */
	public static int containsCount(String str, String c) {
		int index = str.indexOf(c);
		if (index == -1) {
			return 0;
		} else {
			return 1 + containsCount(str.substring(index + 1), c);
		}
	}
	

	public static String[] removeEmpty(String[] ss) {
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < ss.length; i++) {
			if (StringUtil.isNotEmpty(ss[i])) {
				result.add(ss[i]);
			}
		}
		
		String[] resultSs = new String[result.size()];
		result.toArray(resultSs);
		return resultSs;
	}
	
	public static String[] merginKeyword(String[] ss, String keyword) {
		List<String> result = new ArrayList<String>();
		
		String frontKeyword = null;
		for (int i = 0; i < ss.length; i++) {
			String s = ss[i];
			
			if (frontKeyword != null) {
				result.add(frontKeyword + s);
				frontKeyword = null;
			} else {
				if (keyword.equals(s.trim())) {
					frontKeyword = s;
				} else {
					result.add(s);
				}
			}
			
		}
		
		String[] resultSs = new String[result.size()];
		result.toArray(resultSs);
		return resultSs;
	}
}
