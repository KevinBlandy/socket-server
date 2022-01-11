package socketserver;

import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import socketserver.handler.CommandInvokeHandler;
import socketserver.handler.ExceptionHandler;
import socketserver.handler.IdleStateEventHandler;
import socketserver.handler.ReqPacketDecoder;
import socketserver.handler.RespPacketEncoder;

/**
 * 
 * 线程不安全
 * 
 * @author KevinBlandy
 *
 */
@Slf4j
public class SocketChannelInitializer extends ChannelInitializer<Channel> {

	public static final SocketChannelInitializer INSTANCE = new SocketChannelInitializer();

	private SocketChannelInitializer() {
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {

		ChannelPipeline pipeline = ch.pipeline();

		if (log.isDebugEnabled()) {
			pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
		}
		
		// 解码RespPacket【出】
		pipeline.addLast(new RespPacketEncoder());

		// SocketFrame，TCP拆/粘包 【入】
		pipeline.addLast(new LengthFieldBasedFrameDecoder(ByteOrder.BIG_ENDIAN, 0xFFFF, 5, 12, 2, 0, true));
		
		// 编码为ReqPacket【入】
		pipeline.addLast(new ReqPacketDecoder());
		
		// 心跳处理，超过120s没有收到设备信息，表示该设备已经离线，主动断开连接【入】
		pipeline.addLast(new IdleStateHandler(false, 2, 0, 0, TimeUnit.MINUTES));
		pipeline.addLast(IdleStateEventHandler.INSTANCE);
		
		
		// 注册指令处理【入】
		pipeline.addLast();
		// 通用指令处理【入】
		pipeline.addLast(CommandInvokeHandler.INSTANCE);  // TODO 耗时请求，可以单独设置线程池作为执行器
		
		// 异常处理
		pipeline.addLast(ExceptionHandler.INSTANCE);
	}
}
