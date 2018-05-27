package edu.haut.greenhouse.server.udp;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import edu.haut.greenhouse.common.util.JsonUtils;
import edu.haut.greenhouse.common.util.redis.RedisManager;
import edu.haut.greenhouse.pojo.temhum.TemAndHum;
import edu.haut.greenhouse.server.websocket.WebsocketConfig;
import edu.haut.greenhouse.service.temhum.TemAndHumService;
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
	
	@Resource
	private TemAndHumService temAndHumService;

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
		Date date = new Date();
		if (body !=null) {
			String[] temAndHum = body.split(",");
			if (temAndHum.length == 2) {
				BigDecimal tem = new BigDecimal(temAndHum[0]).setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal hum = new BigDecimal(temAndHum[1]).setScale(2, BigDecimal.ROUND_HALF_UP);
			
				TemAndHum th = new TemAndHum();
				th.setCreateTime(date);
				th.setHum(hum);
				th.setTem(tem);
				try {
					//将数据存到mysql
					temAndHumService.insertData(th);
				} catch (Exception e1) {
				}
				
				try {
					//将数据存到redis
					String key = "tem&hum:"+ date.toString();
					String value = JsonUtils.toJson(th);
					RedisManager.set(key.getBytes(), value.getBytes());
				} catch (Exception e) {
				}
				//通过websocket协议将此消息群发给客户端
				TextWebSocketFrame tsf = new TextWebSocketFrame(tem+","+hum);
				WebsocketConfig.group.writeAndFlush(tsf);
			}
		}
		
		
		
		
		
		
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
