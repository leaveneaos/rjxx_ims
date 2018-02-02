package com.rjxx.taxeasy.security;

import com.rjxx.taxeasy.bizcomm.utils.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xlm on 2018/2/2.
 */
public class IpInterceptor implements HandlerInterceptor{

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

                    String ip= IpUtils.getIpAddr(request);
                    String ip1= request.getRemoteAddr();
                    String ip2 = request.getHeader("x-forwarded-for");
                    String ip3 = request.getHeader("Proxy-Client-IP");
                    String ip4 = request.getHeader("WL-Proxy-Client-IP");
                    logger.info("-------IP地址------"+ip);
                    logger.info("-------IP地址------"+ip1);
                    logger.info("-------IP地址------"+ip2);
                    logger.info("-------IP地址------"+ip3);
                    logger.info("-------IP地址------"+ip4);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
