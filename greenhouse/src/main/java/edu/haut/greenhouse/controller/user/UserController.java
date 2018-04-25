package edu.haut.greenhouse.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.haut.greenhouse.common.util.JsonStatus;
import edu.haut.greenhouse.common.util.JsonUtils;
import edu.haut.greenhouse.common.util.WebUtils;
import edu.haut.greenhouse.common.util.user.UserUtil;
import edu.haut.greenhouse.pojo.user.Role;
import edu.haut.greenhouse.pojo.user.User;
import edu.haut.greenhouse.service.user.RoleService;
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
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	
	
	@RequestMapping("/pageList")
	public String pageList(HttpServletRequest request, Map<Object, Object> map) {
		
		Integer page = WebUtils.getInt(request, "page", null);
		if (page == null || page < 1) {
			page = 1;
		}
		
		
		return "/user/userManager";
	}
	
	@RequestMapping("/toEdit")
	public String toEdit(HttpServletRequest request, Map<Object, Object> map) {
		
		List<Role> roles = roleService.queryAll();
		map.put("roles", roles);
		
		return "/user/insertUser";
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
		System.out.println(roleList);	
		//用户数据校验
		Map<Object, Object> res = UserUtil.verifyUser(user);
		
		//验证邮箱是否已经被注册
		if (user.getEmail() != null) {
			User user1 = new User();
			user1.setEmail(user.getEmail());
			List<User> users = userService.queryListByWhere(user1);
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
			
			List<Role> roles = roleService.queryAll();
			map.put("roles", roles);
			map.put("user", user);
			map.put("roleList", roleList);
			return "/user/insertUser";
		}
		
		if (user.getId() == null) {   //用户ID为空，为新增用户
			userService.save(user, roleList);
		} else {                    //更新用户信息
			
		}
		//重定向到用户列表
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
		
		User user = new User();
		user.setEmail(email);
		List<User> users = userService.queryListByWhere(user);
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
