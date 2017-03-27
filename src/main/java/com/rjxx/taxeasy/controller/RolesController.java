package com.rjxx.taxeasy.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.relation.Role;

import org.bouncycastle.crypto.tls.HashAlgorithm;
import org.bouncycastle.jce.provider.BrokenJCEBlockCipher.BrokePBEWithMD5AndDES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.PrivilegeTypes;
import com.rjxx.taxeasy.domains.Privileges;
import com.rjxx.taxeasy.domains.Roleprivs;
import com.rjxx.taxeasy.domains.Roles;
import com.rjxx.taxeasy.domains.Yh;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.PrivilegeTypesService;
import com.rjxx.taxeasy.service.PrivilegesService;
import com.rjxx.taxeasy.service.RoleprivsService;
import com.rjxx.taxeasy.service.RolesService;
import com.rjxx.taxeasy.service.YhService;
import com.rjxx.taxeasy.vo.PrivilegeTypesVo;
import com.rjxx.taxeasy.vo.RolesVo;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("role")
public class RolesController extends BaseController {
	@Autowired
	private RolesService rolesService;
	@Autowired
	private YhService yhService;
	@Autowired
	private PrivilegeTypesService privilegeTypesService;
	@Autowired
	private RoleprivsService RoleprivsService;
	@Autowired
	private PrivilegesService privilegesService;

	@RequestMapping
	public String index() {
		request.setAttribute("types", loadPri(getYhid()));
		return "role/index";
	}

	// 加载当前用户的的所有角色
	public List<Roles> loadJs(String gsdm) throws Exception {
		try {
			Map params = new HashMap<>();
			List<Roles> xfs = rolesService.findBySql(params);
			return xfs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public List<PrivilegeTypesVo> loadPri(Integer yhid) {
		List<PrivilegeTypesVo> types = null;
		List<Privileges> list = null;
		List<Privileges> Tlist = new ArrayList<>();
		try {
			Yh yh = yhService.findOne(yhid);
			Map params = new HashMap<>();
			params.put("idList", new ArrayList<>(Arrays.asList(yh.getRoleids().split(","))));
			types = privilegeTypesService.findBySql(params);
			/*
			 * String con =
			 * " ID IN(SELECT PRIVILEGETYPEID FROM PRIVILEGES WHERE ID IN (SELECT PRIVID FROM ROLEPRIVS WHERE ROLEID IN ( ? )) and yxbz ='1' and privilege_types.name <> ?  and privilege_types.name <> ?)"
			 * ; types = PrivilegeTypes.findAll(PrivilegeTypes.class, con, new
			 * Object[]{yh.getRoleIds(),"基础数据","系统管理"}, "sort");
			 */
			List<String> list2 = new ArrayList<>();
			/*
			 * String sql3 =
			 * "SELECT privid from roleprivs WHERE roleid in ( ? ) and yxbz = '1'"
			 * ; List<Roleprivs> list3 =
			 * Roleprivs.findBySql(Roleprivs.class,sql3,new
			 * Object[]{yh.getRoleIds()});
			 */
			List<Roleprivs> list3 = RoleprivsService.findBySql(params);
			for (Roleprivs rolePrivs : list3) {
				list2.add(String.valueOf(rolePrivs.getPrivid()));
			}
			if (!types.isEmpty()) {
				for (PrivilegeTypesVo type : types) {
					// list = Privilege.findAll(Privilege.class, "yxbz='1' and
					// privilegeTypeId=? and id in ( ? ) ",
					// new Object[] { type.getId(),
					// StringUtils.join(list2,",")});
					params.put("privilegeTypeId", type.getId());
					list = privilegesService.findOneByParams(params);
					if (Tlist.size() > 0) {
						Tlist.clear();
					}

					Tlist.addAll(list);
					for (int i = 0; i < Tlist.size(); i++) {
						if (!list2.contains(String.valueOf(Tlist.get(i).getId()))) {
							list.remove(Tlist.get(i));
						}
					}
					type.setPrivileges(list);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return types;
	}

	/**
	 * 查询公司角色
	 *
	 * @throws Exception
	 */
	@RequestMapping("getRole")
	@ResponseBody
	@Transactional
	public Map getRole(int length, int start, int draw, String roleName) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Pagination pagination = new Pagination();
			pagination.setPageNo(start / length + 1);
			pagination.setPageSize(length);
			pagination.addParam("roleName", roleName);
			pagination.addParam("gsdm", this.getGsdm());
			List<RolesVo> list = rolesService.findByPage(pagination);
			long total = pagination.getTotalRecord();
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
			result.put("draw", draw);
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("error", true);
			result.put("msg", e.toString());
			result.put("total", 0);
			result.put("rows", Collections.emptyList());

		}
		return result;
	}

	@RequestMapping("getRolePrivs")
	@ResponseBody
	@Transactional
	public Map<String, Object> getRolePrivs(Integer id) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		List<Roleprivs> rolePriv = RoleprivsService.findBySql1(params);
		if (!rolePriv.isEmpty()) {
			result.put("success", true);
			result.put("list", rolePriv);
		} else {
			result.put("success", false);
		}
		return result;
	}

	/**
	 * 新增角色
	 *
	 * @param role
	 *            角色
	 * @param pids
	 *            赋予角色的所有菜单权限
	 * @param cms
	 *            赋予角色菜单的增删改查权限
	 * @throws Exception
	 */
	@RequestMapping("save")
	@ResponseBody
	@Transactional
	@SystemControllerLog(description = "修改角色",key = "name")  
	public Map<String, Object> save(Roles role, String[] firstId, String[] cms) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (firstId == null || firstId.length < 1) {
			result.put("success", false);
			result.put("msg", "新增失败,请选择角色权限");
			return result;
		}
		List<String> pids = new ArrayList<>();
		for (String string : firstId) {
			String[] strs = request.getParameterValues(string);
			if (strs != null && strs.length > 0) {
				for (String str : strs) {
					pids.add(str);
				}
			}
		}
		try {
			if (role.getName() != null && !role.getName().trim().equals("")) {
				Map params = new HashMap<>();
				params.put("gsdm", getGsdm());
				params.put("name", role.getName());
				List<Roles> list = rolesService.findAllByParams(params);
				if (list != null && list.size() > 0) {
					result.put("repeat", true);
					result.put("msg", "新增失败,角色名重复!");
					return result;
				} else {
					// Integer sort = (Integer) Roles.maximum(Role.class,
					// "sort", null, null);
					// role.setSort(sort + 1);
					// Integer id = (Integer) Role.maximum(Role.class, "id",
					// null, null);
					// role.setId(id + 1);
					// Integer userId = getYhid();
					// String sql = "INSERT INTO ROLES (ID, NAME,
					// DESCRIPTION,privilegeIds, SORT, yxbz, LRSJ, XGSJ, LRRY,
					// XGRY) VALUES
					// (?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,?,?)";
					role.setPrivilegeids(join(pids.toArray()));
					role.setYxbz("1");
					role.setLrry(String.valueOf(getYhid()));
					role.setXgry(String.valueOf(getYhid()));
					role.setLrsj(new Date());
					role.setXgsj(new Date());
					rolesService.save(role);
					// Role.execute(sql, new Object[] { role.getId(),
					// role.getName(), role.getDescription(), join(pids),
					// role.getSort(), 1, userId, userId });
					// 角色的权限资源分配
					// saveRolePriv(role, pids, cms, userId);
					saveRolePriv(role, pids.toArray(), getYhid());
					result.put("msg", "新增角色成功!");
					result.put("success", true);
				}
			}
		} catch (Exception e) {
			result.put("error", true);
			result.put("msg", e.toString());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 格式化页面传来的菜单增删改查审权限
	 *
	 * @param cms
	 *            页面传来的权限
	 * @return 格式化后的权限
	 */
	private Map<String, String> getPrivBtn(String[] cms) {
		Map<String, String> privBtn = new HashMap<>();
		String btn;
		String[] split;
		for (String s : cms) {
			split = s.split("-");
			if (privBtn.containsKey(split[0])) {
				privBtn.put(split[0], privBtn.get(split[0]) + split[1]);
			} else {
				btn = "s";
				btn += split[1];
				privBtn.put(split[0], btn);
			}
		}
		return privBtn;
	}

	/**
	 * 更新角色变更的权限
	 *
	 * @param id
	 *            角色ID
	 * @param pids
	 *            赋予角色的所有菜单权限
	 * @param cms
	 *            赋予角色菜单的增删改查权限
	 */
	@RequestMapping("update")
	@ResponseBody
	@Transactional
	@SystemControllerLog(description = "修改角色",key = "id")  
	public Map<String, Object> update(String rolename, String[] firstId, String[] cms, Integer id, String dis, String name,
			String description) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Roles role = rolesService.findOne(id);
		Map<String, Object> params = new HashMap<>();
		params.put("gsdm", getGsdm());
		params.put("name", name);
		params.put("id", id);
		Roles role2 = rolesService.findOneByParams(params);
		if (name == null || "".equals(name.trim())) {
			throw new RuntimeException("角色名不能是空值");
		}
		if (null != role2) {
			result.put("nopeat", true);
			result.put("msg", "角色名称已存在");
			return result;
		}
		if (firstId == null || firstId.length < 1) {
			result.put("nopeat", true);
			result.put("msg", "没有授权");
			return result;
		}
		List<String> pids = new ArrayList<>();
		for (String string : firstId) {
			String[] strs = request.getParameterValues(string);
			if (strs != null && strs.length > 0) {
				for (String str : strs) {
					pids.add(str);
				}
			}
		}
		// View view = null;

		try {
			role.setName(name);
			role.setDescription(description);
			role.setPrivilegeids(join(pids.toArray()));
			role.setXgry(String.valueOf(getYhid()));
			role.setXgsj(new Date());
			// 如果未更改角色名则不更新角色名
			// if (!roleName.equals(role.getName())) {
			// sql += sc;
			// sql += " NAME = ?";
			// sc = ",";
			// roleParam.add(roleName);
			// isUpdateRole = true;
			//
			// column.add("NAME");
			// values.add(roleName);
			// }
			// // 如果未更新角色描述则不更新角色描述
			// if (roleDesc != null && !roleDesc.equals(role.getDescription()))
			// {
			// sql += sc;
			// sql += " DESCRIPTION = ? ";
			// sc = ",";
			// roleParam.add(roleDesc);
			// isUpdateRole = true;
			//
			// column.add("DESCRIPTION");
			// values.add(roleDesc);
			// }
			// if (join(pids) != null &&
			// !join(pids).equals(role.getPrivilegeIds())) {
			// sql += sc;
			// sql += " privilegeIds = ?";
			// sc = ",";
			// roleParam.add(join(pids));
			// isUpdateRole = true;
			//
			// column.add("privilegeIds");
			// values.add(join(pids));
			// }
			// // 如果两者都未改变则不更新角色
			// if (isUpdateRole) {
			// sql = "UPDATE ROLES SET " + sql;
			// sql += " ,xgsj = current_timestamp ,xgry = ?";
			// roleParam.add(userId);
			// sql += " WHERE ID = ?";
			// roleParam.add(role.getId());
			// Role.execute(sql, roleParam.toArray());
			// }
			// 负责更新角色的菜单权限以及菜单的增删改查权限
			// updateRolePriv(id, pids, cms, yh.getYhid());
			rolesService.save(role);
			updateRolePriv(role.getId(), pids.toArray(), getYhid());
			result.put("success", true);
			result.put("msg", "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("error", true);
			result.put("msg", e);
			return result;
		}
		return result;
	}

	/**
	 * 删除
	 * 
	 * @throws ActiveRecordException
	 */
	@RequestMapping("destroy")
	@ResponseBody
	@Transactional
	@SystemControllerLog(description = "删除角色",key = "ids")  
	public Map<String, Object> destroy(String ids) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Roles> list = new ArrayList<>();
		List<Roleprivs> rlist = new ArrayList<>();
		Map<String, Object> params = new HashMap<>();
		String[] strs = ids.split(",");
		for (String str : strs) {
			Roles role = rolesService.findOne(Integer.valueOf(str));
			role.setYxbz("0");
			list.add(role);
			params.put("roleid", role.getId());
			List<Roleprivs> deleteList = RoleprivsService.findAllByParams(params);
			for (Roleprivs r : deleteList) {
				r.setYxbz("2");
			}
			rlist.addAll(deleteList);
		}
			rolesService.save(list);
			RoleprivsService.save(rlist);
			result.put("success", true);
			result.put("msg", "删除角色成功");
		return result;
	}

	/**
	 * 新增角色时保存赋予角色的权限
	 *
	 * @param role
	 *            角色
	 * @param pids
	 *            赋予角色的所有菜单权限
	 * @param cms
	 *            赋予角色菜单的增删改查权限
	 * @param userId
	 *            操作人ID
	 * @throws ActiveRecordException
	 */
	private void saveRolePriv(Roles role, Object[] pids/* , String[] cms */, Integer userId) throws Exception {
		if (pids == null || pids.length < 1) {
			return;
		}
		// Map<String, String> privBtn = getPrivBtn(cms);
		// String sql = "INSERT INTO ROLEPRIVS (ROLEID, PRIVID, BUTTONPRIVS,
		// YXBZ,LRSJ, LRRY, XGSJ, XGRY) VALUES
		// (?,?,?,'1',CURRENT_TIMESTAMP,?,CURRENT_TIMESTAMP,?)";
		List<Roleprivs> list = new ArrayList<>();
		Roleprivs rp = null;
		for (Object pid : pids) {
			rp = new Roleprivs();
			rp.setRoleid(role.getId());
			rp.setPrivid(Integer.valueOf(String.valueOf(pid)));
			rp.setButtonprivs("smc");
			rp.setYxbz("1");
			rp.setLrry(userId);
			rp.setLrsj(new Date());
			rp.setXgry(userId);
			rp.setXgsj(new Date());
			list.add(rp);
		}
		RoleprivsService.save(list);
	}

	private String join(Object[] items) {
		String s = "";
		for (Object item : items) {
			s += item + ",";
		}
		return s.substring(0, s.length() - 1);
	}

	private void updateRolePriv(int roleId, Object[] pids, /* String[] cms, */ Integer userId) throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("roleid", roleId);
		List<Roleprivs> deleteList = RoleprivsService.findAllByParams(params);
		for (Roleprivs roleprivs : deleteList) {
			roleprivs.setYxbz("2");
		}
		RoleprivsService.save(deleteList);
		// Map<String, String> privBtn = getPrivBtn(cms);
		// String newsql = "INSERT INTO ROLEPRIVS (ROLEID, PRIVID, BUTTONPRIVS,
		// YXBZ,LRSJ, LRRY, XGSJ, XGRY) VALUES
		// (?,?,?,1,CURRENT_TIMESTAMP,?,CURRENT_TIMESTAMP,?)";
		// String btnPriv;
		List<Roleprivs> list = new ArrayList<>();
		Roleprivs rp = null;
		for (Object pid : pids) {
			rp = new Roleprivs();
			rp.setRoleid(roleId);
			rp.setPrivid(Integer.valueOf(String.valueOf(pid)));
			rp.setButtonprivs("smc");
			rp.setYxbz("1");
			rp.setLrry(userId);
			rp.setLrsj(new Date());
			rp.setXgry(userId);
			rp.setXgsj(new Date());
			list.add(rp);
		}
		RoleprivsService.save(list);
	}
}
