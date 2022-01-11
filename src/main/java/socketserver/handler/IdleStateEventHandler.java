package socketserver.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleUserEventChannelHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import io.netty.channel.ChannelHandler.Sharable;

@Sharable
@Slf4j
public class IdleStateEventHandler extends SimpleUserEventChannelHandler<IdleStateEvent> {
	
	public static final IdleStateEventHandler INSTANCE = new IdleStateEventHandler();

	@Override
	protected void eventReceived(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
		if (evt.state() == IdleState.READER_IDLE) {
			log.warn("连接 {} 读取超时，立即断开", ctx.channel().id());
			ctx.close();
		}
	}
}
