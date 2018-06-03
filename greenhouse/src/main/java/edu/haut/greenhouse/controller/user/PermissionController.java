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
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;

import edu.haut.greenhouse.bean.PageResult;
import edu.haut.greenhouse.bean.user.PermissionStatus;
import edu.haut.greenhouse.bean.user.ResourceObjStatus;
import edu.haut.greenhouse.common.util.JsonStatus;
import edu.haut.greenhouse.common.util.JsonUtils;
import edu.haut.greenhouse.common.util.WebUtils;
import edu.haut.greenhouse.pojo.user.Permission;
import edu.haut.greenhouse.pojo.user.ResourceObj;
import edu.haut.greenhouse.service.user.PermissionService;
import edu.haut.greenhouse.service.user.ResourceObjService;
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
	
	@Autowired
	private ResourceObjService resourceObjService;
	
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
		ResourceObj obj = new ResourceObj();
		obj.setStatus(ResourceObjStatus.USEABLE.getStatus());
		
		List<ResourceObj> list = resourceObjService.queryListByWhere(obj);
		map.put("resobj", list);
		
		return "/permission/edit";
	}
	
	/**
	 * 保存权限
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request) {
	
		Permission permission = new Permission();
		Integer objId = WebUtils.getInt(request, "objId", null);
		permission.setObjId(objId);
		
		ResourceObj resObj = resourceObjService.queryById(objId);
		
		String op = WebUtils.getNullIfEmpty(request, "op");
		String operate = resObj.getName() + ":" + op;
		permission.setPerName(operate);
		
		String perNameCh = WebUtils.getNullIfEmpty(request, "perNameCh");
		permission.setPerNameCh(perNameCh);
		
		permission.setCreateTime(new Date());
		permission.setStatus(PermissionStatus.USEABLE.getStatus());
		
		permissionService.save(permission);
		
		return "redirect:/permission/pageList";
	}
	
	@RequestMapping("/changeStat")
	public String changeStat(HttpServletRequest request) {
		
		String ids = request.getParameter("id");
		Integer status = WebUtils.getInt(request, "status", null);
		if (ids == null || status == null) {
			return "redirect:/user/pageList";
		}
		
		String[] idsArr = ids.split(",");
		
		List<Object> pidList = new ArrayList<>();
		for (String str : idsArr) {
			pidList.add(Integer.valueOf(str));
		}
		
		Permission permission = new Permission();
		permission.setStatus(status);
		permission.setUpdateTime(new Date());
		
		Example example = new Example(Permission.class);
		Criteria criteria = example.createCriteria();
		criteria.andIn("id", pidList);
		try {
			permissionService.updateSelectiveByWhere(permission, example);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/permission/pageList";
	}
	
	/**
	 * 检验数据唯一性
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkOnly")
	@ResponseBody
	public String checkOnly(HttpServletRequest request) {
		
		Map<Object, Object> map = new HashMap<>();
		
		String opVal = WebUtils.getNullIfEmpty(request, "opVal");
		Integer objId = WebUtils.getInt(request, "objId", null);
		
		if (opVal == null || objId == null) {
			map.put(JsonStatus.STATUS, JsonStatus.SUCCESS);
			map.put(JsonStatus.MSG, "已存在该权限");
			return JsonUtils.toJson(map);
		}
		
		ResourceObj obj = resourceObjService.queryById(objId);
		String perName = obj.getName() + ":" + opVal;
		
		Permission permission = new Permission();
		permission.setPerName(perName);
		List<Permission> list = permissionService.queryListByWhere(permission);
		if (list == null || list.isEmpty()) {
			map.put(JsonStatus.STATUS, JsonStatus.ERROR);
			map.put(JsonStatus.MSG, "未存在该权限");
		} else {
			map.put(JsonStatus.STATUS, JsonStatus.SUCCESS);
			map.put(JsonStatus.MSG, "已存在该权限");
		}
		
		return JsonUtils.toJson(map);
		
	}
}
