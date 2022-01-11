package socketserver.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import socketserver.protocol.ReqPacket;
import io.netty.channel.ChannelHandler.Sharable;

/**
 * 
 * 通用的指令处理器
 * @author KevinBlandy
 *
 */
@Sharable
public class CommandInvokeHandler extends SimpleChannelInboundHandler<ReqPacket> {
	
	public static final CommandInvokeHandler INSTANCE = new CommandInvokeHandler();
	
	private CommandInvokeHandler (){}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ReqPacket msg) throws Exception {
		msg.getCommand().handler.accept(ctx, msg);
	}
}
