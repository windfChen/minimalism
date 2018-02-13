package com.windf.core.frame;

import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Filter extends Orderable{
	
	/**
	 * 获得拦截范围
	 * @return
	 */
	List<String> getUrlPattern();

	/**
	 * preHandle方法是进行处理器拦截用的
	 * @param request
	 * @param response
	 * @param chain
	 */
	boolean preHandle(HttpServletRequest request, HttpServletResponse response, FilterChain chain);
	
	/**
	 * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行
	 * @param request
	 * @param response
	 * @param ex
	 * @throws Exception
	 */
	void afterCompletion(HttpServletRequest request,  HttpServletResponse response, Exception ex) throws Exception ;
	
}
