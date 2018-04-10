package edu.haut.greenhouse.server.udp;
/**
 * 
 * @author chj
 * 阻塞模式  与  非阻塞模式  的创建工厂
 */

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.DatagramChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

final class ServerUDPChannelFactory {

	protected static Channel createAcceptorChannel(
			final ChannelType channelType,
			final InetSocketAddress localAddress,
			final ServerUDPHandler serverHandler) {
		
		final Bootstrap serverBootstrap = ServerUDPBootstrapFactory.createServerBootstrap(channelType);
		serverBootstrap.option(ChannelOption.SO_REUSEADDR, false)
		.handler(new ChannelInitializer<DatagramChannel>() {

			@Override
			protected void initChannel(DatagramChannel ch) throws Exception {
				
				final ChannelPipeline pipeline = ch.pipeline();
				//pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(60));  //读写超时操作
				pipeline.addLast("handler", serverHandler);
			}
			
		});
		System.out.println("创建UDP。。。。。。。。。。");
		
		try {
			ChannelFuture channelFuture = serverBootstrap.bind(
					new InetSocketAddress(localAddress.getPort())).sync();
			channelFuture.awaitUninterruptibly();
			if (channelFuture.isSuccess()) {
				return channelFuture.channel();
			} else {
				System.out.println(String.format("Failed to open socket! Cannot bind to port: %d!",
                        localAddress.getPort()));
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(localAddress.getPort());
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
