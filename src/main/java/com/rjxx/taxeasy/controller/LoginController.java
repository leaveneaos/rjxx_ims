package com.rjxx.taxeasy.controller;

import com.rjxx.taxeasy.bizcomm.utils.IpUtils;
import com.rjxx.taxeasy.bizcomm.utils.XfUtils;
import com.rjxx.taxeasy.domains.Cszb;
import com.rjxx.taxeasy.domains.Rabbitmq;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/login")
@CrossOrigin
public class LoginController extends BaseController {

	@Autowired
	private YhService yhService;

	@Autowired
	private XfService xfService;

	@Autowired
	private PrivilegesService privilegesService;

	@Autowired
	private PrivilegeTypesService privilegeTypesService;

	@Autowired
	private RabbitmqService rabbitmqService;

	@Autowired
	private CszbService cszbService;

	@Autowired
	protected AuthenticationManager authenticationManager;

	@RequestMapping
	public String index() {
		return "login/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		Cookie[] cs = request.getCookies();
		if(cs!=null && cs.length>0){
			for(Cookie cookie:cs){
				if("login_cookie".equals(cookie.getName())){
					if("zhongke".equals(cookie.getValue())){
						return "login/otherLogin";
					}
				}
			}
		}
		return "login/login";
	}

	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	public String login(String dlyhid, String yhmm, String code, ModelMap modelMap) throws Exception {
		Cookie[] cookies = request.getCookies();
		if(cookies==null){
			Cookie cookie = new Cookie("login_cookie", "rjxx");
			cookie.setPath("/");
			response.addCookie(cookie);
		}else{
			boolean flag = true;
			for(Cookie cookie:cookies){
				if("login_cookie".equals(cookie.getName())){
					cookie.setValue("rjxx");
					cookie.setPath("/");
					response.addCookie(cookie);
					flag = false;
					break;
				}
			}
			if(flag){
				Cookie cookie = new Cookie("login_cookie", "rjxx");
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}



		String sessionCode = (String) session.getAttribute("rand");
		String encryptYhmm = PasswordUtils.encrypt(yhmm);
		if (code != null && sessionCode != null && code.equals(sessionCode)) {
			Map params = new HashMap<>();
			params.put("dlyhid", dlyhid);
			Yh loginUser = yhService.findOneByParams(params);
			//if (loginUser != null && StringUtils.isNotBlank(loginUser.getRoleids())) {
			if (loginUser != null) {
				String savedYhmm = loginUser.getYhmm();
				if (!encryptYhmm.equals(savedYhmm)) {
					modelMap.put("errors", "用户名或密码不正确");
					return "login/login";
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
						return "login/login";
					}
				}
				//先跳过该页面，直接登录进入系统管理页面。
				//List<Xf> xfList = getXfList();
				//if (!xfList.isEmpty()) {
					session.setAttribute("loginFlag","0");
					session.setAttribute("pingtai","泰易（TaxEasy）开票通V2.0");
					session.setAttribute("banquan","© Copyright 2014-2017 上海容津信息技术有限公司 沪ICP备15020560号");
					return "redirect:/main";
				//}
				//return "redirect:/qymp";
			} else {
				modelMap.put("errors", "用户名或密码不正确");
				return "login/login";
			}
		} else {
			modelMap.put("errors", "验证码不正确");
			return "login/login";
		}
	}

	/**
	 * 客户端登录
	 *
	 * @param username
	 * @param password
	 * @param sh
	 */
	@RequestMapping(value = "/clientLogin", method = RequestMethod.POST)
	@ResponseBody
	public String clientLogin(String username, String password, String sh) throws Exception {
		Map map = new HashMap();
		try {
			Map params = new HashMap<>();
			params.put("dlyhid", username);
			Yh loginUser = yhService.findOneByParams(params);
			if (loginUser != null && StringUtils.isNotBlank(loginUser.getRoleids())) {
				String encryptYhmm = PasswordUtils.encrypt(password);
				String savedYhmm = loginUser.getYhmm();
				if (!encryptYhmm.equals(savedYhmm)) {
					map.put("success", false);
					map.put("message", "用户名或密码不正确");
					return mapToXmlString(map);
				}
				List<Xf> xfList = xfService.getXfListByYhId(loginUser.getId());
				if (xfList == null || xfList.isEmpty()) {
					map.put("success", false);
					map.put("message", "税号不正确");
					return mapToXmlString(map);
				}
				Xf xf = XfUtils.containsSh(xfList, sh);
				if (xf == null) {
					map.put("success", false);
					map.put("message", "税号不正确");
					return mapToXmlString(map);
				}
				// 获取rabbitmq信息
				int id = xf.getId();
				Rabbitmq rabbitmq = rabbitmqService.findByXfid(id);
				if (rabbitmq == null) {
					map.put("success", false);
					map.put("message", "没有关联的mq信息");
					return mapToXmlString(map);
				}
				// 返回
				map.put("success", true);
				map.put("mqHost", rabbitmq.getHost());
				map.put("mqPort", rabbitmq.getPort());
				map.put("mqAccount", rabbitmq.getAccount());
				map.put("mqPassword", rabbitmq.getPassword());
				map.put("mqVhost", rabbitmq.getVhost());
				return mapToXmlString(map);
			} else {
				map.put("success", false);
				map.put("message", "用户名或密码不正确");
				return mapToXmlString(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("message", e.getMessage());
			return mapToXmlString(map);
		}
	}

	/**
	 * map转换成xml形式的字符串
	 *
	 * @param data
	 * @return
	 */
	private String mapToXmlString(Map data) {
		StringBuilder stringBuilder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		stringBuilder.append("<root>");
		Iterator iterator = data.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			String value = data.get(key).toString();
			stringBuilder.append("<" + key + ">").append(value).append("</" + key + ">");
		}
		stringBuilder.append("</root>");
		return stringBuilder.toString();
	}

	@RequestMapping(value = "/logout")
	public String logout() throws Exception {
		session.removeAttribute(SecurityConstants.LOGIN_SESSION_KEY);
		String loginFlag = (String )session.getAttribute("loginFlag");
		//session.removeAttribute("loginFlag");
		//session.removeAttribute("pingtai");
		session.invalidate();
		if("1".equals(loginFlag)){
			return "redirect:/zkltLogin/login";
		}else{
			return "redirect:/login/login";
		}

		//return "redirect:/login/login";
	}

}
