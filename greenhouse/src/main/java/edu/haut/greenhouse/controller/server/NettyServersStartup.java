package edu.haut.greenhouse.controller.server;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import edu.haut.greenhouse.server.udp.UDPServer;
import edu.haut.greenhouse.server.websocket.WebSocketServer;

@Controller
public class NettyServersStartup {
	
	@Resource
	private UDPServer udpServer;
	
	@Resource
	private WebSocketServer websocketServer;

	@PostConstruct
	public void init() {
		//启动UDP Server
//		udpServer.start();
		
		//启动websocket Server
		//websocketServer.start();
		
//		System.out.println("初始化成功！");
	}
	
	
}
