package com.windf.module.development.modle.file.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.windf.core.util.RegularUtil;
import com.windf.module.development.Constant;

public class Annotation {
	private static final String DEFAULT_KEY = "default_value";
	
	static boolean isAnnotationLine(String lineContent) {
		return lineContent.trim().startsWith("@");
	}
	
	private String name;
	private Map<String, String> values;
	
	/**
	 * 如果包含括号就是代码行，如果不包含，就是name
	 * @param codes
	 */
	public Annotation(String codes) {
		if (codes.contains("@")) {
			String[] ss = codes.trim().split("\\(|\\)");
				
			name = ss[0].substring(1);
			
			if (ss.length > 1) {
				String[] valueStrs = ss[1].split("\\s?,\\s?");
				valueStrs = mergin(valueStrs);
				if (valueStrs.length == 1) {
					values = new HashMap<String, String>();
					values.put(DEFAULT_KEY, valueStrs[0]);
				} else if (valueStrs.length > 1) {
					values = new HashMap<String, String>();
					for (String valueStr : valueStrs) {
						String[] keyValue = valueStr.trim().split("=");
						String key = keyValue[0].trim();
						String value = keyValue[1].trim();
						values.put(key, value);
					}
				}
			}
		} else {
			this.name = codes;
		}
		
	}
	

	
	private String[] mergin(String[] ss) {
		List<String> list = new ArrayList<String>();

		int leftCount = 0;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ss.length; i++) {
			String s = ss[i];
			
			boolean left = s.indexOf("{") > -1;
			boolean right = s.indexOf("}") > -1;
			
			if (left && !right) {
				sb.append(s);
				leftCount++;
			} else if (sb.length() > 0 && !right) {
				sb.append(s);
			} else if (right) {
				sb.append(s);
				if (leftCount > 0) {
					leftCount--;
				}
				
				if (leftCount == 0) {
					list.add(sb.toString());
					sb.setLength(0);
				}
			} else {
				list.add(s);
			}
		}	
		
		String[] result = new String[list.size()];
		list.toArray(result);
		return result;
	}
	
	public Annotation(String name, Object value) {
		this.name = name.trim();
		values = new HashMap<String, String>();
		if (value instanceof String) {
			value = "\"" + value.toString() + "\"";
		}
		values.put(DEFAULT_KEY, value.toString());
	}
	
	public void addValue(String key, Object value) {
		if (values == null) {
			values = new HashMap<String, String>();
		}
		if (value instanceof String) {
			value = "\"" + value.toString() + "\"";
		}
		values.put(key, value.toString());
	}
	
	String write(int tabCount) {
		StringBuffer result = new StringBuffer();
		
		for (int i = 0; i < tabCount; i++) {
			result.append(Constant.TAB);
		}
		
		result.append("@");
		result.append(name);
		if (values != null) {
			result.append("(");
			
			if (values.size() == 1 && values.containsKey(DEFAULT_KEY)) {
				result.append(values.get(DEFAULT_KEY));
			} else {
				Iterator<String> keyIterator = values.keySet().iterator();
				int i = 0;
				while (keyIterator.hasNext()) {
					String key = (String) keyIterator.next();
					String value = values.get(key);
					
					if (i != 0) {
						result.append("," + Constant.WORD_SPLIT);
					}

					result.append(key + Constant.WORD_SPLIT +"=" + Constant.WORD_SPLIT + value);
					
					i++;
					
				}
			}
			
			result.append(")");
		}
		
		return result.toString();
	}

	public String getName() {
		return name;
	}
	
	public String getValue(String key) {
		String result = null;
		
		if (values != null) {
			result = values.get(key);
			if (RegularUtil.match(result, "^\".*\"$")) {
				result = result.substring(1, result.length() - 1);
			}
		}
		
		return result;
	}
	
}
