package com.windf.plugins.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.windf.core.frame.ProjectStart;

/**
 * 跟随项目启动的类的起点
 *
 */
public class StartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public StartServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		ProjectStart.getInstance().start();
	}

}
