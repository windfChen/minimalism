package com.windf.plugins.web.request;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;

import com.windf.core.util.reflect.ReflectUtil;
import com.windf.plugins.web.BaseControler;

public class RequestPath {

	protected HttpServletRequest request;
	protected BaseControler controler;
	
	
	public RequestPath(HttpServletRequest request, BaseControler controler) {
		this.request = request;
		this.controler = controler;
	}

	/**
	 * 返回项目的根路径
	 * @return
	 */
	protected String getBasePath() {
		return request.getContextPath();
	}
	
	/**
	 * 获得请求路径
	 * @return
	 */
	public String getFullPath() {
		return (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
	}
	
	/**
	 * 获得控制器的路径
	 * @return
	 */
	public String getControlerPath() {
		RequestMapping a = (RequestMapping) ReflectUtil.getAnnotation(controler, RequestMapping.class);
		return a.value()[0];
	}
	
	/**
	 * 获得请求后缀
	 * @return
	 */
	public String getSuffix() {
		String path = request.getRequestURI();
		String result = "";
		if (path.lastIndexOf(".") > 0) {
			result = path.substring(path.lastIndexOf(".") + 1, path.length());
		}
		return result;
	}
	
}
