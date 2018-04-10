package edu.haut.greenhouse.server.websocket;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
/**
 * 存储websocket服务的全局配置
 * @author chj
 *
 */
public class WebsocketConfig {
	
	public static String ONLINE_IP = "39.106.184.51";
	
	public static String TEST_IP = "localhost";
	
	public static int WEBSOCKET_PORT = 1125; 

	/**
	 * 存储每一个客户端接入进来的channel对象
	 */
	public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
