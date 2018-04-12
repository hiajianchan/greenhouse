package edu.haut.greenhouse.server.udp;

import edu.haut.greenhouse.server.websocket.WebsocketConfig;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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
		ByteBuf buf = packet.copy().content();
		
		byte[] req = new byte[buf.readableBytes()];
		
		buf.readBytes(req);
		
		String body = new String(req, CharsetUtil.UTF_8);
		
		System.out.println("接收到的消息是：" + body);
		
		//通过websocket协议将此消息群发给客户端
		TextWebSocketFrame tsf = new TextWebSocketFrame(body);
		WebsocketConfig.group.writeAndFlush(tsf);
		
		
		//回复一条消息给客户端
//		ctx.writeAndFlush(new DatagramPacket(
//				Unpooled.copiedBuffer("received your msg " + System.currentTimeMillis(), CharsetUtil.UTF_8), 
//				packet.sender())).sync();
		ctx.writeAndFlush(new DatagramPacket(
				Unpooled.copiedBuffer("111", CharsetUtil.UTF_8), 
				packet.sender())).sync();
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		
		if (cause instanceof ReadTimeoutException) {
			System.out.println("超时。。。");
		}
	}
}
