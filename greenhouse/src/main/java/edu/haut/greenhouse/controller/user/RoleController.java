package edu.haut.greenhouse.controller.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;

import edu.haut.greenhouse.bean.PageResult;
import edu.haut.greenhouse.bean.user.PermissionStatus;
import edu.haut.greenhouse.common.util.JsonStatus;
import edu.haut.greenhouse.common.util.JsonUtils;
import edu.haut.greenhouse.common.util.WebUtils;
import edu.haut.greenhouse.pojo.user.Permission;
import edu.haut.greenhouse.pojo.user.Role;
import edu.haut.greenhouse.pojo.user.RolePermission;
import edu.haut.greenhouse.service.user.PermissionService;
import edu.haut.greenhouse.service.user.RolePermissionService;
import edu.haut.greenhouse.service.user.RoleService;

/**
 * 
 * @Description 角色
 * @author chen haijian
 * @date 2018年4月25日
 * @version 1.0
 */
@Controller
@RequestMapping("role")
public class RoleController {
	
	private static final int DISPLAY_PAGE = 5;
	
	private static final int PAGE_SIZE = 10;

	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private RolePermissionService rolePermissionService;
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 分页查询角色详情
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/pageList")
	public String pageList(HttpServletRequest request, Map<Object, Object> map) {
		Integer page = WebUtils.getInt(request, "page", null);
		if (page == null || page < 1) {
			page = 1;
		}
		
		PageResult result = roleService.pageList(page, PAGE_SIZE);
		map.put("result", result);
		int fromPage = Math.max(1, (page - 1) / DISPLAY_PAGE * DISPLAY_PAGE + 1);
		int toPage = Math.min(fromPage + DISPLAY_PAGE - 1, result.getPages());
		map.put("fromPage", fromPage);
		map.put("toPage", toPage);
		
		return "/role/roleManager";
	}
	
	/**
	 * 跳转到编辑页面
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/toEdit")
	public String toEdit(HttpServletRequest request, Map<Object, Object> map) {
		
		Integer roleId = WebUtils.getInt(request, "rid", null);
		if (roleId != null) {
			Role role = roleService.queryById(roleId);
			map.put("role", role);
			
			RolePermission rp = new RolePermission();
			rp.setRoleId(roleId);
			List<RolePermission> rpList = rolePermissionService.queryListByWhere(rp);
			if (rpList != null && !rpList.isEmpty()) {
				List<Integer> perList = new ArrayList<>();
				for (RolePermission r : rpList) {
					perList.add(r.getPerId());
				}
				map.put("perList", perList);
			}
		}
		
		Permission permission = new Permission();
		permission.setStatus(PermissionStatus.USEABLE.getStatus());
		List<Permission> permissions = permissionService.queryListByWhere(permission);
		map.put("permissions", permissions);
		
		return "/role/edit";
	}
	
	/**
	 * 保存用户
	 * 新增或者删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request, Map<Object, Object> map) {
		
		Role role = fillRole(request);
		//传来的权限ID
		String[] perList = request.getParameterValues("perList");
		Map<Object, Object> res = verifyRole(role);
		
		//校验角色信是否已经存在
		if (role.getRolename() != null) {
			Example example = new Example(Role.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("rolename", role.getRolename());
			if (role.getId() != null) {
				criteria.andNotEqualTo("id", role.getId());
			}
			List<Role> roles = roleService.queryByExample(example);
			if (roles !=null && !roles.isEmpty()) {    //该角色已经存在
				res.put(JsonStatus.STATUS, JsonStatus.ERROR);
				res.put(JsonStatus.MSG, "该角色已经存在");
			}
		}
		
		if (perList == null) {
			res.put(JsonStatus.STATUS, JsonStatus.ERROR);
			res.put(JsonStatus.MSG, "请选择权限");
		}
		
		if ((int)res.get(JsonStatus.STATUS) == JsonStatus.ERROR) {    //数据有问题
			map.put(JsonStatus.MSG, res.get(JsonStatus.MSG));
			
			Permission permission = new Permission();
			permission.setStatus(PermissionStatus.USEABLE.getStatus());
			List<Permission> permissions = permissionService.queryListByWhere(permission);
			map.put("permissions", permissions);
			map.put("role", role);
			map.put("perList", perList);
			return "/role/edit";
		}
		
		if (role.getId() == null) {   //用户ID为空，为新增角色
			roleService.saveRole(role, perList);
		} else {                    //更新用户信息
			roleService.updateRole(role, perList);
		}
		//重定向到用户列表
		return "redirect:/role/pageList";
		
	}
	
	/**
	 * 更改用户的状态值
	 * @param request
	 * @return
	 */
	@RequestMapping("/changeStat")
	public String changeStat(HttpServletRequest request, @RequestParam("id") String ids) {
		
		
		Integer status = WebUtils.getInt(request, "status", null);
		if (ids == null || status == null) {
			return "redirect:/user/pageList";
		}
		
		String[] idsArr = ids.split(",");
		
		List<Object> ridList = new ArrayList<>();
		for (String str : idsArr) {
			ridList.add(Integer.valueOf(str));
		}
		
		Role role = new Role();
		role.setStatus(status);
		role.setUpdateTime(new Date());
		
		Example example = new Example(Role.class);
		Criteria criteria = example.createCriteria();
		criteria.andIn("id", ridList);
		try {
			roleService.updateSelectiveByWhere(role, example);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "redirect:/role/pageList";
	}
	
	/**
	 * 检测输入角色是否唯一
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkRoleOnly")
	@ResponseBody
	public String checkRoleOnly(HttpServletRequest request) {
		Map<Object, Object> map = new HashMap<>();
		
		Integer rid = WebUtils.getInt(request, "rid", null);
		String rolename = request.getParameter("rolename");
		
		Example example = new Example(Role.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("rolename", rolename);
		if (rid != null) {
			criteria.andEqualTo("id", rid);
		}
		List<Role> roles = roleService.queryByExample(example);
		
		if (roles != null && !roles.isEmpty()) {
			//该角色已经存在
			map.put(JsonStatus.STATUS, JsonStatus.SUCCESS);
			map.put(JsonStatus.MSG, "该角色已经存在！");
		} else {
			map.put(JsonStatus.STATUS, JsonStatus.ERROR);
			map.put(JsonStatus.MSG, "该角色不存在");
		}
		return JsonUtils.toJson(map);
	}
	
	/**
	 * 从前端传过来的角色信息进行填充
	 * @param request
	 * @return
	 */
	public Role fillRole(HttpServletRequest request) {
		
		Role role = new Role();
		Integer id = WebUtils.getInt(request, "rid", null);
		role.setId(id);
		
		String rolename = WebUtils.getNullIfEmpty(request, "rolename");
		role.setRolename(rolename);
		
		String rolenameCh = WebUtils.getNullIfEmpty(request, "rolenameCh");
		role.setRolenameCh(rolenameCh);
		
		return role;
	}
	
	/**
	 * 校验角色
	 * @param role
	 * @return
	 */
	public Map<Object, Object> verifyRole(Role role) {
		Map<Object, Object> map = new HashMap<>();
		
		if (role == null) {
			map.put(JsonStatus.STATUS, JsonStatus.ERROR);
			map.put(JsonStatus.MSG, "角色对象为空");
			return map;
		}
		if (role.getRolename() == null) {
			map.put(JsonStatus.STATUS, JsonStatus.ERROR);
			map.put(JsonStatus.MSG, "角色名称为空");
			return map;
		}
		if (role.getRolenameCh() == null) {
			map.put(JsonStatus.STATUS, JsonStatus.ERROR);
			map.put(JsonStatus.MSG, "角色描述为空");
			return map;
		}
		map.put(JsonStatus.STATUS, JsonStatus.SUCCESS);
		return map;
	}
}
