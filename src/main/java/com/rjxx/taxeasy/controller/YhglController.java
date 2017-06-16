package com.rjxx.taxeasy.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjxx.taxeasy.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.Group;
import com.rjxx.taxeasy.domains.Gsxx;
import com.rjxx.taxeasy.domains.Roles;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.domains.Yh;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.security.SecurityContextUtils;
import com.rjxx.taxeasy.security.WebPrincipal;
import com.rjxx.taxeasy.vo.YhVO;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.time.TimeUtil;
import com.rjxx.utils.PasswordUtils;

@Controller
@RequestMapping("/nyhgl")
public class YhglController extends BaseController {
	@Autowired
	private RolesService rolesService;
	@Autowired
	private SkpService skpService;
	@Autowired
	private YhService yhService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private GsxxService gs;

	@Autowired
	private XfService xfService;

	@RequestMapping
	public String index() throws Exception {
		request.setAttribute("xfs", getXfList(getYhid()));
		request.setAttribute("jss", loadJs(getGsdm()));
		request.setAttribute("sksbs", getKpdByXf(getXfList(getYhid())));
		request.setAttribute("gsdm", getGsdm());
		return "nyhgl/index";
	}

	public List<Xf> getXfList(int yhid){
		List<Xf> xfList = xfService.getXfListByYhId(yhid);
		return  xfList;
	}

	public List<Roles> loadJs(String gsdm) throws Exception {
		try {
			Map params = new HashMap<>();
			params.put("gsdm", gsdm);
			List<Roles> xfs = rolesService.findBySql(params);
			return xfs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public List<Skp> getKpdByXf(List<Xf> list) throws Exception {
		List<Skp> list3 = new ArrayList<>();
		for (Xf xf : list) {
			Skp skp = new Skp();
			skp.setXfid(xf.getId());
			List<Skp> list2 = skpService.findBySql(skp);
			list3.addAll(list2);
		}
		return list3;
	}

	@RequestMapping("/getYhList")
	@ResponseBody
	public Map<String, Object> getYhList(int length, int start, int draw, String yhzh, String yhmc) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		pagination.addParam("yhzh", yhzh);
		pagination.addParam("yhmc", yhmc);
		pagination.addParam("gsdm", this.getGsdm());
		List<Yh> YhLists1 = yhService.findByPage(pagination);
		List<YhVO> YhLists = new ArrayList<>();
		for (Yh yh : YhLists1) {
			YhVO yhVO = new YhVO(yh);
			YhLists.add(yhVO);
		}
		int total = pagination.getTotalRecord();
		for (YhVO yh : YhLists) {
			// Xf xf = Xf.find(Xf.class, yh.getYhjg());
			Yh yh1 = yhService.findOne(yh.getLrry());
			/*
			 * if (null != xf) { yh.setJgmc(xf.getXfmc()); }
			 */
			if (yh1 != null) {
				yh.setLrrymc(yh1.getYhmc());
			}
			String rolemc = "";
			if (null != yh.getRoleids() && yh.getRoleids().length() > 0) {
				/*
				 * String sql =
				 * "select * from roles where yxbz = '1' and lrry in (select id from t_yh where gsdm = ?)"
				 * ; List<Role> list = Role.findBySql(Role.class, sql,new
				 * Object[]{gsdm});
				 */
				rolemc = "";
				Map map = new HashMap<>();
				map.put("gsdm", getGsdm());
				List<String> roleList = new ArrayList<>(Arrays.asList(yh.getRoleids().split(",")));
				map.put("roleList", roleList);
				List<Roles> list = rolesService.findBySql(map);
				// String sql = "select * from roles where yxbz = '1' and lrry
				// in (select id from t_yh where gsdm = ?) and id in
				// ("+yh.getRoleids() +") ";
				// List<Roles> list = Roles.findBySql(Roles.class, sql,new
				// Object[]{gsdm});
				for (Roles role : list) {
					rolemc += role.getName() + ",";
				}
				/*
				 * for (Role str : list) { if
				 * (yh.getRoleIds().contains(String.valueOf(str.getId()))) {
				 * 
				 * rolemc += str.getName() + ","; } }
				 */
			}
			if (null != yh.getSup() && yh.getSup().equals("1")) {
				yh.setSup("是");
				rolemc += "超级管理员,";
			} else {
				yh.setSup("否");
			}
			if (!"".equals(rolemc) && null != rolemc) {
				yh.setJsmc(rolemc.substring(0, rolemc.length() - 1));
			}
			if ("0".equals(yh.getXb())) {
				yh.setXb("男");
			} else if ("1".equals(yh.getXb())) {
				yh.setXb("女");
			}
		}
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", YhLists);
		return result;
	}

	/**
	 * 新增用户保存
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/yhxz")
	@ResponseBody
	@Transactional
	@SystemControllerLog(description = "新增用户",key = "")  
	public Map<String, Object> yhxz() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String yhmm = request.getParameter("yhmm");
		String yhzh = request.getParameter("yhzh");
		String xb = request.getParameter("xb");
		String yhmc = request.getParameter("yhmc");
		String sjhm = request.getParameter("sjhm");
		String yx = request.getParameter("yx");
		// yh.setGsdm(getGsdm());
		// yh.setYhmm(PasswordUtils.encrypt(yhmm));
		String[] jsids = request.getParameterValues("jsid");
		Map<String, String[]> map = new HashMap<>();
		 String RoleIds = StringUtils.join(jsids, ",");
		Map<String, Object> prms = new HashMap<>();
		prms.put("gsdm", getGsdm());
		int sum = yhService.findAllByParams(prms).size();
		Gsxx gsxx = gs.findOneByParams(prms);
		if (gsxx.getYhnum() != null && sum >= gsxx.getYhnum()) {
			result.put("success", true);
			result.put("msg", "用户数量已达到用户数量设置最大值，不能添加，如需增加请联系平台开发商");
			return result;
		}
		String[] dids = request.getParameterValues("xfid");
		if (dids == null || dids.length < 1) {
			result.put("success", false);
			result.put("msg", "请选择用户销方");
			// view.setContentType("text/html;charset=utf-8");
			return result;
		} else if (jsids.length < 1) {
			result.put("success", false);
			result.put("msg", "请选择用户角色");
			// view.setContentType("text/html;charset=utf-8");
			return result;
		}
		for (String string1 : dids) {
			String[] skpids = request.getParameterValues(string1);
			map.put(string1, skpids);
		}
		Yh yh = new Yh();
		yh.setDlyhid(getGsdm() + "_" + yhzh);
		yh.setSjhm(sjhm);
		yh.setYx(yx);
		yh.setXb(xb);
		yh.setYhmc(yhmc);
		// yh.setYhjg(getXfid());
		yh.setGsdm(getGsdm());
		yh.setYhmm(PasswordUtils.encrypt(yhmm));
		yh.setRoleids(RoleIds);
		yh.setSjhm(sjhm);
		yh.setYx(yx);
		yh.setYxbz("1");
		yh.setLrry(getYhid());
		yh.setXgry(getYhid());
		yh.setLrsj(TimeUtil.getSysDate());
		yh.setXgsj(TimeUtil.getSysDate());
		if (yh.getDlyhid() != null && !yh.getDlyhid().trim().equals("")) {
			try {
				Map params = new HashMap<>();
				params.put("gsdm", getGsdm());
				params.put("dlyhid", yh.getDlyhid());
				Yh users = yhService.findOneByParams(params);
				if (users != null) {
					result.put("success", false);
					result.put("msg", "用户账号已存在");
				} else {
					yhService.save(yh);
					// Yh add = yhService.findOneByParams(params);
					saveGroup(yh.getId(), dids, getYhid(), map);
					result.put("success", true);
					result.put("msg", "新增用户成功!");
				}
			} catch (Exception e) {
				throw e;
			}

		}
		return result;
	}

	/**
	 * 用户新增时保存机构
	 * 
	 * @param yhid
	 * @param dids
	 * @throws ActiveRecordException
	 */
	public void saveGroup(int yhid, String[] dids, int id1, Map<String, String[]> map) throws Exception {
		if (dids == null || dids.length < 1) {
			return;
		}
		String sql = "INSERT INTO T_GROUP (yhid,xfid,yxbz,lrry,lrsj,xgry,xgsj,skpid)VALUES(?,?,'1',?,CURRENT_TIMESTAMP,?,CURRENT_TIMESTAMP,?)";
		for (String id : dids) {
			String[] skpids = map.get(id);
			int xfid = Integer.valueOf(id);
			if (null != skpids && skpids.length > 0) {
				for (String string1 : skpids) {
					try {
						Group group = new Group();
						group.setYhid(yhid);
						group.setXfid(xfid);
						group.setYxbz("1");
						group.setLrry(id1);
						group.setXgry(id1);
						group.setLrsj(TimeUtil.getSysDate());
						group.setXgsj(TimeUtil.getSysDate());
						group.setSkpid(Integer.valueOf(string1));
						groupService.save(group);
					} catch (Exception e) {
						throw e;
					}
				}
			} else {
				Group group = new Group();
				group.setYhid(yhid);
				group.setXfid(xfid);
				group.setYxbz("1");
				group.setLrry(id1);
				group.setXgry(id1);
				group.setLrsj(TimeUtil.getSysDate());
				group.setXgsj(TimeUtil.getSysDate());
				group.setSkpid(null);
				groupService.save(group);
			}

		}
	}

	/**
	 * 删除用户
	 */
	@RequestMapping("/yhsc")
	@ResponseBody
	@Transactional
	@SystemControllerLog(description = "删除用户",key = "ids")  
	public Map yhsc(String ids) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Yh> list = new ArrayList<>();
			String[] strs = ids.split(",");
			List<Group> groupList = new ArrayList<>();
			for (String str : strs) {
				Yh yh = yhService.findOne(Integer.valueOf(str));
				yh.setYxbz("0");
				list.add(yh);
				Group group = new Group();
				group.setYhid(yh.getId());
				List<Group> gList = groupService.findAllByParams(group);
				for (Group group2 : gList) {
					group2.setYxbz("0");
				}
				groupList.addAll(gList);
			}
			
			yhService.save(list);
			groupService.save(groupList);
			result.put("success", true);
			result.put("msg", "删除用户成功");
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "删除用户失败");
			throw e;
		}
		return result;
	}

	/**
	 * 修改用户查询角色
	 */
	@RequestMapping("/yhxgcx")
	@ResponseBody
	@Transactional
	public Map<String, Object> yhxgcx(String dlyhid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		if (null == dlyhid) {
			result.put("msg", false);
			return result;
		}
		Map params = new HashMap<>();
		params.put("gsdm", getGsdm());
		params.put("dlyhid", dlyhid);
		Yh yh = yhService.findOneByParams(params);
		List<Group> list = null;
		try {
			Group group = new Group();
			group.setYhid(yh.getId());
			list = groupService.findAllByParams(group);
			result.put("list", list);
			result.put("list1", yh.getRoleids().split(","));
			result.put("msg", true);
		} catch (Exception e) {
			result.put("msg", false);
			throw e;
		}
		return result;
	}

	/**
	 * 用户修改保存
	 */
	@RequestMapping("/yhxg")
	@ResponseBody
	@Transactional
	@SystemControllerLog(description = "修改用户",key = "yhzh")  
	public Map<String, Object> yhxg(String yhzh) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String xb = request.getParameter("xb");
		String yhmc = request.getParameter("yhmc");
		String sjhm = request.getParameter("sjhm");
		String yx = request.getParameter("yx");
		String[] jsIds = request.getParameterValues("jsid");
		// String roleIds = StringUtils.join(jsIds, ",");
		String[] dids = request.getParameterValues("xfid");
		Map<String, String[]> map = new HashMap<>();
		for (String string1 : dids) {
			String[] skpids = request.getParameterValues(string1);
			map.put(string1, skpids);
		}
		Map nparams = new HashMap<>();
		nparams.put("gsdm", getGsdm());
		nparams.put("dlyhid", getGsdm() + "_" + yhzh);
		Yh yh = yhService.findOneByParams(nparams);
		Yh update = yh;
		update.setId(yh.getId());
		update.setDlyhid(getGsdm() + "_" + yhzh);
		update.setXb(xb);
		update.setYhmc(yhmc);
		update.setSjhm(sjhm);
		update.setYx(yx);
		update.setLrsj(TimeUtil.getSysDate());
		update.setXgsj(TimeUtil.getSysDate());
		update.setXgry(getYhid());
		String[] jsids = jsIds;
		String RoleIds = StringUtils.join(jsids, ",");
		update.setRoleids(RoleIds);
		if (dids == null || dids.length < 1) {
			result.put("success", false);
			result.put("msg", "请选择用户销方");
		} else if (jsids.length < 1) {
			result.put("success", false);
			result.put("msg", "请选择用户角色");
		} else {
			yhService.save(update);
			updateGroup(yh.getId(), dids, map);
		}
		result.put("success", true);
		result.put("msg", "修改成功");
		return result;
	}

	/**
	 * 修改用户时保存机构
	 * 
	 * @param yhid
	 * @param dids
	 * @return
	 * @throws ActiveRecordException
	 */
	@RequestMapping("/updateGroup")
	@ResponseBody
	@Transactional
	public void updateGroup(int yhid, String[] dids, Map<String, String[]> map) throws Exception {
		try {
			List<Group> gList = new ArrayList<>();
			Group group = new Group();
			group.setYhid(yhid);
			gList = groupService.findAllByParams(group);
			groupService.delete(gList);
			for (String pid : dids) {
				String[] skpids = map.get(pid);
				if (null == skpids || skpids.length == 0) {
					Group group2 = new Group();
					group2.setYhid(yhid);
					group2.setXfid(Integer.valueOf(pid));
					group2.setYxbz("1");
					group2.setLrry(yhid);
					group2.setXgry(yhid);
					group2.setXgsj(TimeUtil.getSysDate());
					group2.setLrsj(TimeUtil.getSysDate());
					group2.setSkpid(null);
					groupService.save(group2);
				} else {
					for (String string : skpids) {
						Group group3 = new Group();
						group3.setYhid(yhid);
						group3.setXfid(Integer.valueOf(pid));
						group3.setYxbz("1");
						group3.setLrry(yhid);
						group3.setXgry(yhid);
						group3.setXgsj(TimeUtil.getSysDate());
						group3.setLrsj(TimeUtil.getSysDate());
						group3.setSkpid(Integer.valueOf(string));
						groupService.save(group3);
					}
				}
			}
			/*
			 * List<Group> groups = Group.findAll(Group.class,
			 * "yhid=? and yxbz ='1' ", new Object[] { yhid });
			 * 
			 * List<Group> updateGroup = updateGroup(yhid, dids, groups,map); if
			 * (!updateGroup.isEmpty()) { String updateSql =
			 * "UPDATE T_GROUP SET yxbz='1' where yhid=? and xfid=? and skpid = ?"
			 * ; for (Group group : updateGroup) { String[] skpids =
			 * map.get(group.getXfid()); for (String string1 : skpids) {
			 * Group.execute(updateSql, new Object[] { group.getYhid(),
			 * group.getXfid() ,string1}); } } }
			 */
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 首页修改用户
	 */
	@RequestMapping("/update")
	@ResponseBody
	@Transactional
	@SystemControllerLog(description = "修改用户资料",key = "yhmc")  
	public Map<String, Object> update(String yhmc, String xb, String sjhm, String yx) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Yh user = yhService.findOne(getYhid());
			user.setYhmc(yhmc);
			user.setXb(xb);
			user.setSjhm(sjhm);
			user.setYx(yx);
			yhService.save(user);
			result.put("success", true);
			result.put("msg", "保存成功");
			WebPrincipal webPrincipal = SecurityContextUtils.getCurrentLoginInfo().getWebPrincipal();
			webPrincipal.setYhmc(yhmc);
			webPrincipal.setYx(yx);
			webPrincipal.setXb(xb);
			webPrincipal.setSjhm(sjhm);
			SecurityContextUtils.getCurrentLoginInfo().setWebPrincipal(webPrincipal);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", true);
			result.put("msg", "保存出现错误: " + e);
		}
		return result;
	}

	@RequestMapping("/getGsxx")
	@ResponseBody
	public Map<String, Object> getGsxx(){
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<>();
		params.put("gsdm", getGsdm());
		Gsxx gsxx = gs.findOneByParams(params);
		params.put("id", getYhid());
//		Yh yh = yhService.findOneByParam(params);
//		result.put("yh", yh);
		result.put("gsxx", gsxx);
		result.put("success", true);
		return result;
	}

	/**
	 * 重置秘密
	 */
	@RequestMapping("/resetPassword")
	@ResponseBody
	@Transactional
	@SystemControllerLog(description = "重置密码",key = "dlyhid")  
	public Map<String, Object> resetPassword(String dlyhid, String czmm) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map params = new HashMap<>();
			params.put("gsdm", getGsdm());
			params.put("dlyhid", dlyhid);
			Yh yh = yhService.findOneByParams(params);
			yh.setYhmm(PasswordUtils.encrypt(czmm));
			yhService.save(yh);
			result.put("success", true);
			result.put("msg", "重置成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", true);
			result.put("msg", "重置出现错误: " + e);
		}
		return result;
	}

	@RequestMapping(value="/updatePwd",method= RequestMethod.POST)
	@ResponseBody
	@Transactional
	@SystemControllerLog(description = "修改密码",key = "")  
	public Map<String, Object> updatePwd(String oldPass, String newPass) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Yh user = yhService.findOne(getYhid());
			if (!user.getYhmm().equals(PasswordUtils.encrypt(oldPass))) {
				result.put("nosame", true);
				result.put("msg", "原密码有误，请重新输入");
				return result;
			}
			user.setYhmm(PasswordUtils.encrypt(newPass));
			yhService.save(user);
			result.put("success", true);
			result.put("msg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", true);
			result.put("msg", "保存出现错误: " + e);
		}
		return result;
	}
}
