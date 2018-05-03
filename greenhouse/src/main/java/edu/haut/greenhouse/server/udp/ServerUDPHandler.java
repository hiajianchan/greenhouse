package edu.haut.greenhouse.server.udp;

import java.util.Date;

import edu.haut.greenhouse.common.util.redis.RedisManager;
import edu.haut.greenhouse.server.websocket.WebsocketConfig;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.util.CharsetUtil;
/**
 * 
 * @author chj
 * 接收UDP SimpleChannelInboundHandler<DatagramPacket> 的handler处理类
 */
@Sharable
public class ServerUDPHandler extends ChannelInboundHandlerAdapter {

	public ServerUDPHandler() {
		super();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException {

		
		DatagramPacket packet = (DatagramPacket) msg;
		
		//获取客户端的IP
		String senderHost = packet.sender().getHostString();
		System.out.println("客户端IP：" + senderHost);
		int senderPort = packet.sender().getPort();
		System.out.println("客户端端口号：" + senderPort);
		
		ByteBuf buf = packet.copy().content();
		
		byte[] req = new byte[buf.readableBytes()];
		
		buf.readBytes(req);
		
		String body = new String(req, CharsetUtil.UTF_8);
		
		System.out.println("接收到的消息是：" + body);
		//将数据存到redis
		try {
			String key = new Date().toString();
			key = "tem&hum:"+key;
			RedisManager.set(key.getBytes(), body.getBytes());
		} catch (Exception e) {
		}
		
		//通过websocket协议将此消息群发给客户端
		TextWebSocketFrame tsf = new TextWebSocketFrame(body);
		WebsocketConfig.group.writeAndFlush(tsf);
		
		
		//回复一条消息给客户端
//		ctx.writeAndFlush(new DatagramPacket(
//				Unpooled.copiedBuffer("received your msg " + System.currentTimeMillis(), CharsetUtil.UTF_8), 
//				packet.sender())).sync();
//		ctx.writeAndFlush(new DatagramPacket(
//				Unpooled.copiedBuffer("111", CharsetUtil.UTF_8), 
//				packet.sender())).sync();
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		
		if (cause instanceof ReadTimeoutException) {
			System.out.println("超时。。。");
		}
	}
}
