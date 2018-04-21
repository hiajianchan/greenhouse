package edu.haut.greenhouse.controller.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import edu.haut.greenhouse.common.util.user.UserUtil;
import edu.haut.greenhouse.pojo.user.User;
import edu.haut.greenhouse.service.user.UserService;
@Controller
public class LoginController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userService;
	
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
	
	
}
