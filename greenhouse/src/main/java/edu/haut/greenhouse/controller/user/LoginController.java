package edu.haut.greenhouse.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
/**
 * 
 * @Description 注册和登录
 * @author chen haijian
 * @date 2018年4月20日
 * @version 1.0
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.haut.greenhouse.common.util.JsonStatus;
import edu.haut.greenhouse.common.util.JsonUtils;
import edu.haut.greenhouse.common.util.WebUtils;
import edu.haut.greenhouse.common.util.user.UserUtil;
import edu.haut.greenhouse.pojo.user.User;
import edu.haut.greenhouse.pojo.user.UserInfo;
import edu.haut.greenhouse.service.user.UserInfoService;
import edu.haut.greenhouse.service.user.UserService;
@Controller
public class LoginController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@RequestMapping("/register")
	@ResponseBody
	public String register(HttpServletRequest request) {
		
		User user = UserUtil.fillUser(request);
		Map<Object, Object> map = UserUtil.verifyUser(user);
		if (map.get(JsonStatus.STATUS).equals(JsonStatus.SUCCESS)) {
			try {
				Integer result = userService.save(user);
				if (result == 1) {
					map.put(JsonStatus.MSG, "成功！");
				} else {
					map.put(JsonStatus.STATUS, JsonStatus.ERROR);
					map.put(JsonStatus.MSG, "失败！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.toString());
				map.put(JsonStatus.STATUS, JsonStatus.SERVER_ERROR);
				map.put(JsonStatus.MSG, JsonStatus.SERVER_ERROR_MSG);
			}
		}
		
		return JsonUtils.toJson(map);
	}
	
	/**
	 * 用户登录
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request, Map<Object, Object> map) {
		
		//登录邮箱
		String email = WebUtils.getNullIfEmpty(request, "email");
		if (email == null) {
			map.put(JsonStatus.STATUS, JsonStatus.ERROR);
			map.put(JsonStatus.MSG, "登录邮箱不能为空");
			return "/common/login";
		}
		
		//登录密码
		String passwd = WebUtils.getNullIfEmpty(request, "passwd");
		if (passwd == null) {
			map.put(JsonStatus.STATUS, JsonStatus.ERROR);
			map.put(JsonStatus.MSG, "密码不能为空");
			return "/common/login";
		}
		
		//是否记住我
		boolean rememberMe = false;
		Integer rem = WebUtils.getInt(request, "rememberme", 0);
		if (rem == 1) {
			rememberMe = true;
		}
		
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(email, passwd, rememberMe);
		try {
			
			//登录
			subject.login(token);
			
		} catch (AuthenticationException e) {
			map.put(JsonStatus.STATUS, JsonStatus.ERROR);
			map.put(JsonStatus.MSG, "用户名或密码错误！");
			log.error(e.toString());
			e.printStackTrace();
		}
		
		if (subject.isAuthenticated()) {
			//登录成功
			map.put(JsonStatus.STATUS, JsonStatus.SUCCESS);
			//将当前用户放入session
			Session session = subject.getSession();
			User record = new User();
			record.setEmail(email);
			User user = userService.queryOne(record);
			if (user != null) {
				session.setAttribute("currUser", user);
				
				UserInfo record1 = new UserInfo();
				record1.setUserId(user.getId());
				UserInfo userInfo = userInfoService.queryOne(record1);
				session.setAttribute("currUserInfo", userInfo);
			}
			
			return "/common/main";
		}
		
		return "/common/login";
	}
	
	/**
	 * 跳转到登录界面
	 * @return
	 */
	@RequestMapping("goLogin")
	public String goLogin() {
		return "/common/login";
	}
	
}
