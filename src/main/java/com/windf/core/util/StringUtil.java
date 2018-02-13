package com.windf.core.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 字符串工具
 * @author windf
 *
 */
public class StringUtil {
    public static final String UTF8 = "UTF-8";
    private static final String ALL_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";   
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return org.apache.commons.lang.StringUtils.isEmpty(str);
	}
	
	/**
	 * 判断字符串是否不为空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return org.apache.commons.lang.StringUtils.isNotEmpty(str);
	}
	
	/**
	 * 如果字符串为null返回空字符串，否则返回该字符串
	 * @param str
	 * @return
	 */
	public static String fixNull(String str) {
		return str == null? "": str;
	}
	
   /**
     * 将以长串压缩
     *
     * @param data
     * @return
     */
    public static int getHashCode(String data) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", data);
        return map.hashCode();
    }
    
    /**
     * 首字母大写
     * @param str
     * @return
     */
    public static String firstLetterUppercase(String str) {
    	if (str == null || str.length() <= 1) {
			return str;
		} else {
			return str.substring(0, 1).toUpperCase() + str.substring(1); 
		}
    }
    /**
     * 将驼峰命名法的字符串，单词之间用指定字符隔开，切单词首字母小写
     * @param str
     * @param separator
     * @return
     */
    public static String splitCamelCase(String str, String separator) {
    	StringBuffer result = new StringBuffer();
    	
    	if (StringUtil.isNotEmpty(str)) {
    		String[] ss = str.split("(?=[A-Z])");
    		for (int i = 0; i < ss.length; i++) {
    			if (result.length() > 0) {
    				result.append(separator);
				}
    			result.append(ss[i].toLowerCase());
			}
		}
    	
    	return result.toString();
    }
    
    /**
     * 将字符中指定字符作为分隔符，分隔符后首字母大写
     * @param str
     * @param separator
     * @return
     */
    public static String toCamelCase(String str, String separator) {
    	if (ParameterUtil.hasEmpty(str, separator)) {
			return null;
		}
    	
    	StringBuffer result = new StringBuffer();

    	String[] ss = str.split(separator);
    	for (int i = 0; i < ss.length; i++) {
    		if (result.length() > 0) {
    			result.append(firstLetterUppercase(ss[i]));
			} else {
				result.append(ss[i]);
			}
		}
    	
    	return result.toString();
    }
    
    /**
     * 将集合转换为字符串，中间用指定字符隔开
     * @param collection
     * @param separator
     * @return
     */
    public static String join(Collection<? extends Object> collection, String separator) {
    	return StringUtils.join(collection, separator);
    }
    
    /**
     * 将集合转换为字符串，中间用指定字符隔开
     * @param collection
     * @param separator
     * @return
     */
    public static String join(String[] ss, String separator) {
    	List<String> collection = Arrays.asList(ss);
    	return StringUtils.join(collection, separator);
    }
    
    /**
     * 获得句子的第一个单词，
     * 如果句子中没有空格，数字第一返回null，数组第二个是参数本身
     * @param s "第一个单词","剩下的句子"
     * @return
     */
    public static String[] getFirstWord(String s) {
    	int firstBlank = s.indexOf(" "); 
		String firstWord = null;
		if (firstBlank > -1) {
			firstWord = s.substring(0, firstBlank);
			s = s.substring(firstBlank + 1);
		}
		return new String[]{firstWord, s};
    }
   
    /**
     * 生成随机字符串
     * @param length
     * @return
     */
    public static String getRandomString(int length){
		StringBuffer sb = new StringBuffer();
		int len = ALL_CHARS.length();
		for (int i = 0; i < length; i++) {
			sb.append(ALL_CHARS.charAt(NumberUtil.getRandom(len - 1)));
		}
		return sb.toString();
    }
    
    
}
