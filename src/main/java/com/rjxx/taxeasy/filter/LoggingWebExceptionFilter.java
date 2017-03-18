package com.rjxx.taxeasy.filter;

import com.rjxx.taxeasy.security.SecurityContextUtils;
import com.rjxx.taxeasy.security.SecurityUser;
import com.rjxx.utils.HtmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017-03-08.
 */
@WebFilter(urlPatterns = "/*", filterName = "LoggingWebExceptionFilter")
public class LoggingWebExceptionFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            String url = req.getRequestURI();
            url = url.substring(req.getContextPath().length());
            //获取当前登录用户及公司代码
            SecurityUser user = SecurityContextUtils.getCurrentLoginInfo();
            if (user != null) {
                logger.error("current login user:" + user.getUsername() + ",corpCode:" + user.getWebPrincipal().getGsdm());
            }
            logger.error("request url:" + url);
            try {
                logger.error("request params:" + HtmlUtils.getRequestParamsString(req));
            } catch (Exception ex) {
                logger.error("", ex);
            }
            logger.error("", e);
        }
    }

    @Override
    public void destroy() {

    }
}
