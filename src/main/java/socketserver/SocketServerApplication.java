package socketserver;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author KevinBlandy
 *
 */
@Slf4j
public class SocketServerApplication {
	
	public static void main(String[] args) throws InterruptedException {
		
		EventLoopGroup bossEventLoopGroup = null;
		EventLoopGroup workerEventLoopGroup = null;
		Class<? extends ServerChannel> serverChanneClass = null;
		
		if (Epoll.isAvailable()) {
			bossEventLoopGroup = new EpollEventLoopGroup();
			workerEventLoopGroup = new EpollEventLoopGroup();
			serverChanneClass = EpollServerSocketChannel.class;
		} else {
			bossEventLoopGroup = new NioEventLoopGroup();
			workerEventLoopGroup = new NioEventLoopGroup();
			serverChanneClass = NioServerSocketChannel.class;
		}
		
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossEventLoopGroup, workerEventLoopGroup);
			serverBootstrap.channel(serverChanneClass);
			serverBootstrap.localAddress(new InetSocketAddress("0.0.0.0", 1024));
			serverBootstrap.handler(new LoggingHandler(LogLevel.DEBUG));
			
			serverBootstrap.childHandler(SocketChannelInitializer.INSTANCE);
			
			serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);
			serverBootstrap.option(ChannelOption.SO_RCVBUF, 4098);
			serverBootstrap.option(ChannelOption.SO_REUSEADDR, false);
			serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, false);
			

			ChannelFuture channelFuture = serverBootstrap.bind().sync();
			
			log.info("SocketServer 启动");
			
			channelFuture.channel().closeFuture().sync();
			
			log.info("SocketServer 停止");
		} finally {
			bossEventLoopGroup.shutdownGracefully();
			workerEventLoopGroup.shutdownGracefully();
		}
	}
}
