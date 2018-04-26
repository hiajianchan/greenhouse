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
import edu.haut.greenhouse.bean.user.RoleStatus;
import edu.haut.greenhouse.common.util.JsonStatus;
import edu.haut.greenhouse.common.util.JsonUtils;
import edu.haut.greenhouse.common.util.WebUtils;
import edu.haut.greenhouse.common.util.user.UserUtil;
import edu.haut.greenhouse.pojo.user.Role;
import edu.haut.greenhouse.pojo.user.User;
import edu.haut.greenhouse.pojo.user.UserRole;
import edu.haut.greenhouse.service.user.RoleService;
import edu.haut.greenhouse.service.user.UserRoleService;
import edu.haut.greenhouse.service.user.UserService;
/**
 * 
 * @Description 用户
 * @author chen haijian
 * @date 2018年4月25日
 * @version 1.0
 */
@Controller
@RequestMapping("user")
public class UserController {
	
	private static final int DISPLAY_PAGE = 5;
	
	private static final int PAGE_SIZE = 10;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	
	/**
	 * 分页查询用户信息
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
		
		PageResult result = userService.pageList(page, PAGE_SIZE);
		map.put("result", result);
		int fromPage = Math.max(1, (page - 1) / DISPLAY_PAGE * DISPLAY_PAGE + 1);
		int toPage = Math.min(fromPage + DISPLAY_PAGE - 1, result.getPages());
		map.put("fromPage", fromPage);
		map.put("toPage", toPage);
		
		return "/user/userManager";
	}
	
	/**
	 * 跳转到编辑页面
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/toEdit")
	public String toEdit(HttpServletRequest request, Map<Object, Object> map) {
		
		Integer uid = WebUtils.getInt(request, "uid", null);
		if (uid != null) {
			User user = userService.queryById(uid);
			map.put("user", user);
			
			UserRole userRole = new UserRole();
			userRole.setUserId(uid);
			List<UserRole> userRolelist = userRoleService.queryListByWhere(userRole);
			List<Integer> roleList = new ArrayList<>();
			for (UserRole ur : userRolelist) {
				roleList.add(ur.getRoleId());
			}
			map.put("roleList", roleList);
		}
		
		Role role = new Role();
		role.setStatus(RoleStatus.USEABLE.getStatus());
		List<Role> roles = roleService.queryListByWhere(role);
		map.put("roles", roles);
		
		return "/user/edit";
	}
	
	/**
	 * 保存用户
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request, Map<Object, Object> map) {
		
		//页面传过来的用户信息
		User user = UserUtil.fillUser(request);
		
		//角色信息
		String[] roleList = request.getParameterValues("roleList");
//		System.out.println(roleList);	
		//用户数据校验
		Map<Object, Object> res = UserUtil.verifyUser(user);
		
		//验证邮箱是否已经被注册
		if (user.getEmail() != null) {
			Example example = new Example(User.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("email", user.getEmail());
			if (user.getId() != null) {
				criteria.andNotEqualTo("id", user.getId());
			}
			List<User> users = userService.queryByExample(example);
			if (users !=null && !users.isEmpty()) {    //该邮箱已经被注册
				res.put(JsonStatus.STATUS, JsonStatus.ERROR);
				res.put(JsonStatus.MSG, "该用户已被注册");
			}
		}
		
		
		if (roleList == null) {
			res.put(JsonStatus.STATUS, JsonStatus.ERROR);
			res.put(JsonStatus.MSG, "请选择角色");
		}
		
		if ((int)res.get(JsonStatus.STATUS) == JsonStatus.ERROR) {    //数据有问题
			map.put(JsonStatus.MSG, res.get(JsonStatus.MSG));
			
			Role role = new Role();
			role.setStatus(RoleStatus.USEABLE.getStatus());
			List<Role> roles = roleService.queryListByWhere(role);
			map.put("roles", roles);
			map.put("user", user);
			map.put("roleList", roleList);
			return "/user/edit";
		}
		
		if (user.getId() == null) {   //用户ID为空，为新增用户
			userService.saveUser(user, roleList);
		} else {                    //更新用户信息
			userService.updateUser(user, roleList);
		}
		//重定向到用户列表
		return "redirect:/user/pageList";
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
		
		List<Object> uidList = new ArrayList<>();
		for (String str : idsArr) {
			uidList.add(Integer.valueOf(str));
		}
		
		User user = new User();
		user.setStatus(status);
		user.setUpdateTime(new Date());
		
		Example example = new Example(User.class);
		Criteria criteria = example.createCriteria();
		criteria.andIn("id", uidList);
		try {
			userService.updateSelectiveByWhere(user, example);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "redirect:/user/pageList";
	}

	/**
	 * 检查用户是否唯一
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkUserOnly")
	@ResponseBody
	public String checkUserOnly(HttpServletRequest request) {
		Map<Object, Object> map = new HashMap<>();
		
		String email = request.getParameter("email");
		Integer uid = WebUtils.getInt(request, "uid", null);
		
		Example example = new Example(User.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("email", email);
		if (uid != null) {
			criteria.andNotEqualTo("id", uid);
		}
		List<User> users = userService.queryByExample(example);
		
		if (users !=null && !users.isEmpty()) {
			map.put(JsonStatus.STATUS, JsonStatus.SUCCESS);
			map.put(JsonStatus.MSG, "该邮箱已被注册！");
		} else {
			map.put(JsonStatus.STATUS, JsonStatus.ERROR);
			map.put(JsonStatus.MSG, "该邮箱未被注册！");
		}
		return JsonUtils.toJson(map);
	}
}
