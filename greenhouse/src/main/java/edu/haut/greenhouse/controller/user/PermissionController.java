package edu.haut.greenhouse.controller.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.haut.greenhouse.bean.PageResult;
import edu.haut.greenhouse.common.util.WebUtils;
import edu.haut.greenhouse.pojo.user.Permission;
import edu.haut.greenhouse.service.user.PermissionService;
/**
 * 
 * @Description 权限
 * @author chen haijian
 * @date 2018年4月26日
 * @version 1.0
 */
@Controller
@RequestMapping("permission")
public class PermissionController {

	private static final int DISPLAY_PAGE = 5;
	
	private static final int PAGE_SIZE = 10;
	
	@Autowired
	private PermissionService permissionService;
	
	/**
	 * 分页查询
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
		
		PageResult result = permissionService.pageList(page, PAGE_SIZE);
		map.put("result", result);
		int fromPage = Math.max(1, (page - 1) / DISPLAY_PAGE * DISPLAY_PAGE + 1);
		int toPage = Math.min(fromPage + DISPLAY_PAGE - 1, result.getPages());
		map.put("fromPage", fromPage);
		map.put("toPage", toPage);
		
		return "/permission/perManager";
	}
	
	/**
	 * 跳转到权限编辑页面
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/toEdit")
	public String toEdit(HttpServletRequest request, Map<Object, Object> map) {
	
		Integer pid = WebUtils.getInt(request, "pid", null);
		if (pid != null) {
			Permission permission = permissionService.queryById(pid);
			map.put("permission", permission);
		}
		
		return "/permission/edit";
	}
}
