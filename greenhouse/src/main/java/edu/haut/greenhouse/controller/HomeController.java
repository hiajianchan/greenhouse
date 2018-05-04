package edu.haut.greenhouse.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.haut.greenhouse.common.util.WebUtils;

/**
 * 
 * @Description 首页
 * @author chen haijian
 * @date 2018年5月3日
 * @version 1.0
 */
@Controller
public class HomeController {

	/**
	 * 进入首页
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	public String goIndex(HttpServletRequest request, Map<Object, Object> map) {
		
		String realIp = WebUtils.getRealIp(request);
		Map<Object, Object> ipInfo = WebUtils.getIpInfo(realIp);
		map.put("cityName", ipInfo.get("city").toString());
		
		return "/home/index";
	}
}
