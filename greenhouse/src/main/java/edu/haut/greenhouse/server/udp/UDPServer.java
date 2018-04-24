package edu.haut.greenhouse.server.udp;

import java.net.InetSocketAddress;

import javax.annotation.Resource;

import edu.haut.greenhouse.server.AfterSpringBegin;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

/**
 * 
 * @author chj
 * UDP 服务器
 */
public class UDPServer extends AfterSpringBegin implements IServer {
	
	private ChannelType channelType;
	private InetSocketAddress localAddress;
	private Channel acceptorChannel;

	@Resource
	ServerUDPHandler udpHandler;
	
    public UDPServer(ChannelType channelType, int port) {
    	super();
    	this.channelType = channelType;
    	this.localAddress = new InetSocketAddress(port);
    }
	
	@Override
	public void start() {
		acceptorChannel = ServerUDPChannelFactory.createAcceptorChannel(channelType, localAddress, udpHandler);
		
	}

	@Override
	public void stop() {
		if (acceptorChannel != null) {
			acceptorChannel.close().addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void restart() {
		stop();
		start();
	}

	@Override
	public void run() {
		start();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
