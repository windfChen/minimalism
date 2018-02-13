package com.windf.plugins.web.frame;

import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.windf.core.frame.Filter;
import com.windf.core.frame.SessionContext;
import com.windf.plugins.web.session.WebSession;

public class SessionFilter implements Filter{

	private ThreadLocal<WebSession> webSession = new ThreadLocal<WebSession>();
	
	@Override
	public List<String> getUrlPattern() {
		return null;
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) {
		HttpServletRequest request = (HttpServletRequest) req;
		webSession.set(new WebSession(request.getSession()));
		SessionContext.start(webSession.get());
		
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Exception ex)
			throws Exception {
		SessionContext.end(webSession.get());
		webSession.set(null);
	}

	@Override
	public int getOrder() {
		return EARLIEST;
	}

}
