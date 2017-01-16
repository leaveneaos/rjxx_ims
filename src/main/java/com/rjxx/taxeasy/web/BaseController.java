package com.rjxx.taxeasy.web;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.security.SecurityContextUtils;
import com.rjxx.taxeasy.security.WebPrincipal;

/**
 * Created by Administrator on 2016/8/18.
 */
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected HttpSession session;

    protected WebPrincipal getPrinciple() {
        return SecurityContextUtils.getCurrentLoginInfo().getWebPrincipal();
    }

    protected int getYhid() {
        return getPrinciple().getYhid();
    }

    protected List<Xf> getXfList() {
        return getPrinciple().getXfList();
    }

    protected String getXfmc() {
        return getPrinciple().getXfmc();
    }

    protected int getXfid() {
        return getPrinciple().getXfid();
    }

    protected String getXfsh() {
        return getPrinciple().getXfsh();
    }

    ;

    protected String getGsdm() {
        return getPrinciple().getGsdm();
    }
    protected List<Skp> getSkpList() {
        return getPrinciple().getSkpList();
    }

}
