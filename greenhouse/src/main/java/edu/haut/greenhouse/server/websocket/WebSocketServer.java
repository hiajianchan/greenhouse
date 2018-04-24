package edu.haut.greenhouse.server.websocket;

import edu.haut.greenhouse.server.AfterSpringBegin;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 负责启动websocket服务
 * @author chj
 *
 */
public class WebSocketServer extends AfterSpringBegin {

	@Override
	public void run() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workGroup);
			b.channel(NioServerSocketChannel.class);
			b.childHandler(new MyWebsocketChannelHandler());
			System.out.println("websocket服务器启动等待客户端连接。。。");
			Channel ch = b.bind(WebsocketConfig.WEBSOCKET_PORT).sync().channel();
			ch.closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
		
	}
}
