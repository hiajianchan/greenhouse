package edu.haut.greenhouse.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.haut.greenhouse.common.util.JsonStatus;
import edu.haut.greenhouse.common.util.JsonUtils;
import edu.haut.greenhouse.common.util.StringUtil;
import edu.haut.greenhouse.spider.Weather;
import edu.haut.greenhouse.spider.WeatherSpider;

/**
 * @Description 获取爬虫数据的Api
 * @author chen haijian
 * @date 2018年5月4日
 * @version 1.0
 */
@Controller
@RequestMapping("api")
public class SpiderControllerApi {

	@Resource
	private WeatherSpider weatherSpider;
	
	@RequestMapping("/getWeather")
	@ResponseBody
	public String getWeather(HttpServletRequest request) {
		Map<Object, Object> map = new HashMap<>();
		
		String cityName = request.getParameter("cityName");
		if (cityName == null || !StringUtil.isNotEmpty(cityName)) {
			map.put(JsonStatus.STATUS, JsonStatus.ERROR);
			map.put(JsonStatus.MSG, "输入城市为空");
			return JsonUtils.toJson(map);
		}
		
		try {
			List<Weather> result = weatherSpider.getWeather(cityName);
			map.put(JsonStatus.STATUS, JsonStatus.SUCCESS);
			map.put(JsonStatus.RESULT, result);
		} catch (Exception e) {
			map.put(JsonStatus.STATUS, JsonStatus.SERVER_ERROR);
			map.put(JsonStatus.MSG, JsonStatus.SERVER_ERROR_MSG);
		}
		
		return JsonUtils.toJson(map);
	}
}
