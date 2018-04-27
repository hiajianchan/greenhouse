package edu.haut.greenhouse.controller.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.haut.greenhouse.bean.user.ResourceObjStatus;
import edu.haut.greenhouse.common.util.JsonUtils;
import edu.haut.greenhouse.pojo.user.ResourceObj;
import edu.haut.greenhouse.service.user.ResourceObjService;

/**
 * 
 * @Description 资源对象
 * @author chen haijian
 * @date 2018年4月27日
 * @version 1.0
 */
@Controller
@RequestMapping("resourceObj")
public class ResourceObjController {

	@Autowired
	private ResourceObjService resourceObjService;
	
	
	@RequestMapping("/all")
	@ResponseBody
	public String getAllUseable(HttpServletRequest request, Map<Object, Object> map) {
		
		ResourceObj obj = new ResourceObj();
		obj.setStatus(ResourceObjStatus.USEABLE.getStatus());
		
		List<ResourceObj> list = resourceObjService.queryListByWhere(obj);
		map.put("resobj", list);
		
		return JsonUtils.toJson(map);
	}
}
