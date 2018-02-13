package com.windf.module.development.modle.file.java;

import java.util.ArrayList;
import java.util.List;

import com.windf.core.exception.UserException;
import com.windf.module.development.modle.Codeable;

public class CodeBlock<T> extends AbstractType {
	public static final int PARAMS_BLOCK_INDEX = Integer.MIN_VALUE;
	public static final int RETURN_BLOCK_INDEX = Integer.MAX_VALUE;
	public static final int NORMAL_BLOCK_INDEX = 0;

	private List<String> codes;
	private Codeable<T> codeable;
	private int tabCount;
	
	public CodeBlock () {
	}
	
	/**
	 * 根据代码创建
	 * @param coderLines
	 */
	public CodeBlock(List<String> codes) {
		this.codes = codes;
	}

	public void setCodeable(Codeable<T> codeable) {
		this.codeable = codeable;
	}
	
	public List<String> getCodes() {
		return codes;
	}
	
	public void setTabCount(int tabCount) {
		this.tabCount = tabCount;
	}

	/**
	 * 输出代码
	 * @return
	 */
	public List<String> write() {
		List<String> result = new ArrayList<String>();
		
		if (comment != null) {
			result.addAll(comment.write());
		}
		result.addAll(codes);
		
		return result;
	}
	
	/**
	 * 根据代码，构建对应类型的对象出来
	 * 与注释无关
	 * @return
	 */
	public T build() {
		return codeable.toObject(codes);
	}
	
	/**
	 * 根据对象，重新修改对象
	 * 与注释无关
	 * @param object
	 * @throws UserException 
	 */
	public void serialize(T t) throws UserException {
		this.codes = codeable.toCodes(t, tabCount);
	}
}
