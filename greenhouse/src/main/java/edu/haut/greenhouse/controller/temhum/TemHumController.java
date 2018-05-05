package edu.haut.greenhouse.controller.temhum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import edu.haut.greenhouse.pojo.temhum.TemAndHum;
import edu.haut.greenhouse.service.temhum.TemAndHumService;

/**
 * @Description 温湿度
 * @author chen haijian
 * @date 2018年5月4日
 * @version 1.0
 */
@Controller
@RequestMapping("temhum")
public class TemHumController {
	
	@Autowired
	private TemAndHumService temAndHumService;

	/**
	 * 从mysql中获取指定天数数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/todayData")
	@ResponseBody
	public String getTodayData(HttpServletRequest request) {
		Map<Object, Object> map = new HashMap<>();
		
		String today = WebUtils.getNullIfEmpty(request, "today");
		if (today == null) {
			today = "2018-05-05";
		}
		
		try {
			List<TemAndHum> result = temAndHumService.getDayDataFromMysql(today);
			if (result == null || result.isEmpty()) {
				map.put(JsonStatus.STATUS, JsonStatus.ERROR);
				map.put(JsonStatus.MSG, "无查询结果");
			} else {
				//拼凑数据
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				List<Map<Object, Object>> temData = new ArrayList<>();
				List<Map<Object, Object>> humData = new ArrayList<>();
				
				for (TemAndHum th : result) {
					//填充温度
					Map<Object, Object> temItem = new HashMap<>();
					temItem.put("name", sdf.format(th.getCreateTime()));
					
					List<Object> temList = new ArrayList<>();
					temList.add(sdf.format(th.getCreateTime()));
					temList.add(th.getTem());
					
					temItem.put("value", temList);
					temData.add(temItem);
					
					//填充湿度
					
					Map<Object, Object> humItem = new HashMap<>();
					humItem.put("name", sdf.format(th.getCreateTime()));
					
					List<Object> humList = new ArrayList<>();
					humList.add(sdf.format(th.getCreateTime()));
					humList.add(th.getHum());
					
					humItem.put("value", humList);
					humData.add(humItem);
				}
				
				map.put(JsonStatus.STATUS, JsonStatus.SUCCESS);
				map.put("temData", temData);
				map.put("humData", humData);
			}
			
		} catch (Exception e) {
			map.put(JsonStatus.STATUS, JsonStatus.SERVER_ERROR);
			map.put(JsonStatus.MSG, JsonStatus.SERVER_ERROR_MSG);
		}
		
		return JsonUtils.toJson(map);
	}
	
	/**
	 * 从数据库中获取最新的数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/lateData")
	@ResponseBody
	public String getLateData(HttpServletRequest request) {
		Map<Object, Object> map = new HashMap<>();
		
		try {
			TemAndHum result = temAndHumService.selectLateData();
			if (result == null) {
				map.put(JsonStatus.STATUS, JsonStatus.ERROR);
				map.put(JsonStatus.MSG, "查询失败");
			} else {
				map.put(JsonStatus.STATUS, JsonStatus.SUCCESS);
				map.put(JsonStatus.RESULT, result);
			}
		} catch (Exception e) {
			map.put(JsonStatus.STATUS, JsonStatus.SERVER_ERROR);
			map.put(JsonStatus.MSG, JsonStatus.SERVER_ERROR_MSG);
		}
		return JsonUtils.toJson(map);
	}
}
