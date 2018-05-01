package edu.haut.greenhouse.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @Description 服务器异常跳转的页面
 * @author chen haijian
 * @date 2018年5月1日
 * @version 1.0
 */
@Controller
@RequestMapping("error")
public class ErrorController {

	/**
	 * 404 跳转
	 * @return
	 */
	@RequestMapping("/404")
	public String error404(HttpServletRequest request) {
		return "/error/404";
	}
	
	/**
	 * 500 跳转
	 * @param request
	 * @return
	 */
	@RequestMapping("/500")
	public String error500(HttpServletRequest request) {
		return "/error/500";
	}
}
