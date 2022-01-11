package socketserver.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;


import io.netty.channel.ChannelHandler.Sharable;

@Slf4j
@Sharable
public class ExceptionHandler extends ChannelInboundHandlerAdapter {
	
	public static final ExceptionHandler INSTANCE = new ExceptionHandler();
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		
		log.error("error: {}", cause.getMessage());
		
		log.error("stack trace: {}{}", System.lineSeparator(), ThrowableUtil.stackTraceToString(cause));

		
		ctx.close();
	}
}








