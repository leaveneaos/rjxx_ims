package com.rjxx.taxeasy.controller;

import com.rjxx.taxeasy.bizcomm.utils.XfUtils;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/login")
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
	protected AuthenticationManager authenticationManager;

	@RequestMapping
	public String index() {
		return "login/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		return "login/login";
	}

	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	public String login(String dlyhid, String yhmm, String code, ModelMap modelMap) throws Exception {
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
					return "login/login";
				}
				// 自动登录
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dlyhid, yhmm);
				token.setDetails(new WebAuthenticationDetails(request));
				Authentication authenticatedUser = authenticationManager.authenticate(token);
				SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
				session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
						SecurityContextHolder.getContext());
				List<Xf> xfList = getXfList();
				if (!xfList.isEmpty()) {
					return "redirect:/main";
				}
				
				return "redirect:/qymp";
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
		return "redirect:/login/login";
	}

}
