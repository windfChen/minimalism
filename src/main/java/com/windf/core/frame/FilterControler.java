package com.windf.core.frame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.windf.core.frame.Filter;

public class FilterControler {
	
	public static FilterControler filterControler = new FilterControler();
	
	public static FilterControler getInstance() {
		return filterControler;
	}
	
	private List<Filter> filters = new ArrayList<Filter>();
	
	private FilterControler() {
		
	}
	
	public void addFilter(Filter filter) {
		OrderHandler.add(filters, filter);
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		for (Filter filter : filters) {
			if (this.verifyUrlPattern(filter.getUrlPattern())) {
				boolean success = filter.preHandle((HttpServletRequest) request, (HttpServletResponse) response, chain);
				if (!success) {
					return;
				}
			}
		}
		
		chain.doFilter(request, response);
		
		for (int i = filters.size() - 1; i >= 0; i --) {
			Filter filter = filters.get(i);
			if (this.verifyUrlPattern(filter.getUrlPattern())) {
				try {
					filter.afterCompletion((HttpServletRequest) request, (HttpServletResponse) response, null);
				} catch (Exception e) {
					// TODO 处理异常
					e.printStackTrace();
				}
			}
		}
		
	}

	/**
	 * 
	 * @param urlPattern
	 * @return
	 */
	private boolean verifyUrlPattern(List<String> urlPattern) {
		return true;
	}
	
}
