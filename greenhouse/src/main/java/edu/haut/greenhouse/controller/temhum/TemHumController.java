package edu.haut.greenhouse.controller.temhum;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description 温湿度
 * @author chen haijian
 * @date 2018年5月4日
 * @version 1.0
 */
@Controller
@RequestMapping("temhum")
public class TemHumController {

	@RequestMapping("/todayData")
	public String getTodayData(HttpServletRequest request) {
		
		
		return null;
	}
}
