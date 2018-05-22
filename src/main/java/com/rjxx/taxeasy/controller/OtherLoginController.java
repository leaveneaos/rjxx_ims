package com.rjxx.taxeasy.controller;

import com.rjxx.taxeasy.bizcomm.utils.IpUtils;
import com.rjxx.taxeasy.domains.Cszb;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.domains.Yh;
import com.rjxx.taxeasy.security.SecurityConstants;
import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.utils.PasswordUtils;
import com.rjxx.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/otherLogin")
@CrossOrigin
public class OtherLoginController extends BaseController {
    @Autowired
    private YhService yhService;

    @Autowired
    private CszbService cszbService;

    @Autowired
    protected AuthenticationManager authenticationManager;

    @RequestMapping
    public String index() {
        return "login/otherLogin";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login/otherLogin";
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public String login(String dlyhid, String yhmm, String code,String flag, ModelMap modelMap) throws Exception {
        String sessionCode = (String) session.getAttribute("rand");
        String encryptYhmm = PasswordUtils.encrypt(yhmm);
        if (code != null && sessionCode != null && code.equals(sessionCode)) {
            Map params = new HashMap<>();
            params.put("dlyhid", dlyhid);
            Yh loginUser = yhService.findOneByParams(params);
            if (loginUser != null && StringUtils.isNotBlank(loginUser.getRoleids())) {
                String savedYhmm = loginUser.getYhmm();
                if (!encryptYhmm.equals(savedYhmm)) {
                    modelMap.put("errors", "用户名或密码不正确");
                    if("1".equals(flag)){
                        return "login/otherLogin";
                    }else{
                        return "login/login";
                    }
                }
                // 自动登录
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dlyhid, yhmm);
                token.setDetails(new WebAuthenticationDetails(request));
                Authentication authenticatedUser = authenticationManager.authenticate(token);
                SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
                session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                        SecurityContextHolder.getContext());
                String ip= IpUtils.getIpAddr(request);
                logger.info("------IP地址-------"+ip);
                Cszb cszb = cszbService.getSpbmbbh(getGsdm(), null, null, "ipwhite");
                if(cszb.getCsz()!=null&&!"".equals(cszb.getCsz())){
                    boolean isInRange = IpUtils.ipIsInRange(ip, cszb.getCsz());
                    if(!isInRange){
                        modelMap.put("errors", "该IP被限制登录");
                        if("1".equals(flag)){
                            return "login/otherLogin";
                        }else{
                            return "login/login";
                        }
                    }
                }
                List<Xf> xfList = getXfList();
                if (!xfList.isEmpty()) {
                    if("1".equals(flag)){
                        session.setAttribute("loginFlag","1");
                        session.setAttribute("pingtai","中科联通电子发票服务平台");
                        session.setAttribute("banquan","© Copyright 2018-2023 北京中科联通科技有限公司 京ICP备13015326号-1");
                    }
                    return "redirect:/main";
                }
                return "redirect:/qymp";
            } else {
                modelMap.put("errors", "用户名或密码不正确");
                if("1".equals(flag)){
                    return "login/otherLogin";
                }else{
                    return "login/login";
                }
            }
        } else {
            modelMap.put("errors", "验证码不正确");
            if("1".equals(flag)){
                return "login/otherLogin";
            }else{
                return "login/login";
            }
        }
    }
}
