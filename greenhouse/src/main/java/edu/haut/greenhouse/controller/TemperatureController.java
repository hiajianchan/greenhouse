package edu.haut.greenhouse.controller;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import edu.haut.greenhouse.server.udp.UDPServer;

@Controller
public class TemperatureController {
	
	@Resource
	private UDPServer udpServer;

	@PostConstruct
	public void init() {
		//初始化方法
		udpServer.start();
		System.out.println("初始化成功！");
	}
	
	
}
