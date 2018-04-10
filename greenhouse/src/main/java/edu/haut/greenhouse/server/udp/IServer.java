package edu.haut.greenhouse.server.udp;

public interface IServer {

	/**
	 * 启动服务
	 */
	void start();
	
	/**
	 * 关闭服务
	 */
	void stop();
	
	/**
	 * 重启服务
	 */
	void restart();
}
