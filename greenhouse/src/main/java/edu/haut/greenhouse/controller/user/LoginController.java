package edu.haut.greenhouse.controller.user;

import java.util.Date;
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
/**
 * 
 * @Description 注册和登录
 * @author chen haijian
 * @date 2018年5月1日
 * @version 1.0
 */
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
			
			if (subject.isAuthenticated()) {
				
				//登录成功
//				map.put(JsonStatus.STATUS, JsonStatus.SUCCESS);
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
					session.setAttribute("loginTime", new Date());
					/*try {
						//更新用户的登录信息
						UserInfo info = new UserInfo();
						info.setId(userInfo.getId());
						info.setUserId(user.getId());
						info.setLastLogin(new Date());
						String realIp = WebUtils.getRealIp(request);
						info.setLastIp(realIp);
						Map<Object, Object> ipInfo = WebUtils.getIpInfo(realIp);
						info.setLastCountry(ipInfo.get("country").toString());
						info.setLastCity(ipInfo.get("city").toString());
						userInfoService.updateSelective(info);
					} catch (Exception e) {
						e.printStackTrace();
					}*/
				}
				
				return "redirect:/main";
			}
			
		} catch (AuthenticationException e) {
			map.put(JsonStatus.STATUS, JsonStatus.ERROR);
			map.put(JsonStatus.MSG, "用户名或密码错误！");
			log.error(e.toString());
			e.printStackTrace();
		}
		token.clear();
		return "redirect:/goLogin";
	}
	
	/**
	 * 登录成功
	 * @param request
	 * @return
	 */
	@RequestMapping("/main")
	public String loginSuccess(HttpServletRequest request) {
		return "/common/main";
	}
	
	/**
	 * 用户登出
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		
		Subject subject = SecurityUtils.getSubject();
	
		//更新用户信息
		try {
			Session session = subject.getSession();
			UserInfo userInfo = (UserInfo) session.getAttribute("currUserInfo");
			Date loginTime = (Date) session.getAttribute("loginTime");
			
			UserInfo info = new UserInfo();
			info.setId(userInfo.getId());
			info.setUserId(userInfo.getUserId());
			info.setLastLogin(loginTime);
			String realIp = WebUtils.getRealIp(request);
			info.setLastIp(realIp);
			Map<Object, Object> ipInfo = WebUtils.getIpInfo(realIp);
			info.setLastCountry(ipInfo.get("country").toString());
			info.setLastCity(ipInfo.get("city").toString());
			userInfoService.updateSelective(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		subject.logout();
        
        return "redirect:/goLogin";
	}
	
	/**
	 * 跳转到登录界面
	 * @return
	 */
	@RequestMapping("goLogin")
	public String goLogin(HttpServletRequest request, Map<Object, Object> map) {
		
		String status = request.getParameter("status");
		map.put(JsonStatus.STATUS, status);
		String msg = request.getParameter("msg");
		map.put(JsonStatus.MSG, msg);
		
		return "/common/login";
	}
	
	@RequestMapping("/getip")
	@ResponseBody
	public String getIp(HttpServletRequest request) {
		
		return WebUtils.getRealIp(request);
	}
	
}
