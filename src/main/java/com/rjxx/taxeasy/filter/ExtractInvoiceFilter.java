package com.rjxx.taxeasy.filter;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.rjxx.utils.AESUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 提取发票的filter
 * Created by Administrator on 2016/8/17.
 */
public class ExtractInvoiceFilter implements Filter {

    /**
     * servlet前缀
     */
    private String servletPrefix = "/extractInvoice";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String prefix = filterConfig.getInitParameter("servletPrefix");
        if (StringUtils.isNotBlank(servletPrefix)) {
            servletPrefix = prefix;
        }
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) arg0;
        HttpServletResponse resp = (HttpServletResponse) arg1;
        String requestUri = req.getRequestURI();
        int pos = requestUri.indexOf(servletPrefix);
        if (pos == -1) {
            throw new ServletException("ExtractInvoiceServlet配置的" + servletPrefix + "不正确");
        }
        String suffixUri = requestUri.substring(pos + servletPrefix.length());
        String gsdm = null;
        String gsdmSubString = suffixUri.substring(1);
        pos = gsdmSubString.indexOf("/");
        String tqm = null;
        if (pos == -1) {
            //没有其他/，表示后面的都是公司代码
            gsdm = gsdmSubString;
        } else {
            gsdm = gsdmSubString.substring(0, pos);
            //否则最后跟着的是提取码
            tqm = gsdmSubString.substring(pos + 1);
        }
        String key = gsdm;
        if (StringUtils.isNotBlank(tqm)) {
            key += "," + tqm;
        }
        String secretKey = "";
        try {
            if (StringUtils.isNotBlank(key)) {
                secretKey = Base64.encodeBase64String(AESUtils.encrypt(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        secretKey = URLEncoder.encode(secretKey, "UTF-8");
        String url = req.getContextPath() + "/tqm?key=" + secretKey;
        resp.sendRedirect(url);
    }

    @Override
    public void destroy() {

    }


}
