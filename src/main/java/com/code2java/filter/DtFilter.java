package com.code2java.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;

@WebFilter("/DtFilter")
public class DtFilter implements Filter {

	private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(DtFilter.class);

	@Override
	public void destroy() {
		// ...
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		logger.info("DtFilter starts");

		if (!isStaticResourceRequest(req)) {
			logger.info("check session object");
			HttpSession session = req.getSession(false);
			String firstName = (null != session && session.getAttribute("FirstName") != null)
					? session.getAttribute("FirstName").toString()
					: ""; 
			logger.info("firstName from Session :" + firstName);
			String lastName = (null != session && session.getAttribute("LastName") != null)
					? session.getAttribute("LastName").toString()
					: "";
			// logger.info("lastName from Session :"+lastName);
			if (StringUtils.isEmpty(firstName) && StringUtils.isEmpty(lastName)) {
				logger.info("no user session, so redirect to login page");
				if (checkRedirectRequired(req)) {
					logger.info("redirection required");
					res.sendRedirect(req.getContextPath() + "/dologin");
				} else {
					logger.info("redirection not required");
					chain.doFilter(request, response);
				}
			} else {
				logger.info("user session found, no need to redirect to login page");
				chain.doFilter(request, response);
			}
		}else {
			chain.doFilter(request, response);
		}

		logger.info("DtFilter end");

	}

	private boolean isStaticResourceRequest(HttpServletRequest req) {
		return (req.getRequestURI().endsWith(".css") || req.getRequestURI().endsWith(".js")
				|| req.getRequestURI().endsWith(".png") || req.getRequestURI().endsWith(".jpg"));
	}

	private boolean checkRedirectRequired(HttpServletRequest req) {
		String urlpath = req.getServletPath();
		logger.info("urlpath: " + urlpath);
		if (urlpath.contains("dologin")) {
			return false;
			// redirectRequired=false;
		}
		if (urlpath.length() <= 1) {
			// home page
			String samlResponse = req.getParameter("SAMLResponse");
			if (StringUtils.isNotBlank(samlResponse)) {
				logger.info("samplresponse attr present");
				return false;
			} else {
				logger.info("samplresponse attr not present");
			}
		}
		return true;
	}

}
