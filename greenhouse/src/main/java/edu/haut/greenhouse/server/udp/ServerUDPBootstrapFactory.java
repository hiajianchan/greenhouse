package edu.haut.greenhouse.server.udp;
/**
 * 
 * @author chj
 * 工厂模式  UDP服务器  bootstrap工厂创建
 */

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.oio.OioDatagramChannel;

public class ServerUDPBootstrapFactory {

	private ServerUDPBootstrapFactory() {
		
	}
	
	public static Bootstrap createServerBootstrap(final ChannelType channelType) {
		
		Bootstrap serverBootstrap = new Bootstrap();
		switch(channelType) {
		case NIO:
			serverBootstrap.group(new NioEventLoopGroup());
			serverBootstrap.channel(NioDatagramChannel.class);
			
			return serverBootstrap;
		case OIO:
			serverBootstrap.group(new OioEventLoopGroup());
			serverBootstrap.channel(OioDatagramChannel.class);
			
			return serverBootstrap;
		default:
			throw new UnsupportedOperationException("Failed to create ServerBootstrap,  " + channelType + " not supported!");
		}
	}
	
}
