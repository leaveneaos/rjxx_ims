package com.rjxx.taxeasy.security;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Created by Administrator on 2016/7/6.
 */
public class SessionValidateFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestUrl = request.getRequestURI();
        if (requestUrl.endsWith("/login") || requestUrl.endsWith("/logout")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        Object object = request.getSession().getAttribute(SecurityConstants.LOGIN_SESSION_KEY);
        if (object == null) {
            response.sendRedirect("/login/login");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
